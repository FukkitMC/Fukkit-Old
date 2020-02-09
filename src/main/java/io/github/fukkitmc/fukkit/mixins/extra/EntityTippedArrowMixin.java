/*
 * Copyright 2020 ramidzkh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fukkitmc.fukkit.mixins.extra;

import io.github.fukkitmc.fukkit.extra.EntityTippedArrowExtra;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(EntityTippedArrow.class)
public abstract class EntityTippedArrowMixin extends EntityArrow implements EntityTippedArrowExtra {

    @Shadow @Final private static DataWatcherObject<Integer> COLOR;

    @Shadow @Final public Set<MobEffect> effects;

    @Shadow private PotionRegistry potionRegistry;

    protected EntityTippedArrowMixin(EntityTypes<? extends EntityArrow> entityTypes, World world) {
        super(entityTypes, world);
    }

    @Override
    public void refreshEffects() {
        getDataWatcher().set(COLOR, PotionUtil.a(PotionUtil.a(potionRegistry, effects)));
    }

    @Override
    public String getType() {
        return IRegistry.POTION.getKey(potionRegistry).toString();
    }

    @Override
    public void setType(String string) {
        potionRegistry = IRegistry.POTION.get(new MinecraftKey(string));
        getDataWatcher().set(COLOR, PotionUtil.a(PotionUtil.a(potionRegistry, effects)));
    }

    @Override
    public boolean isTipped() {
        return !(effects.isEmpty() && potionRegistry == Potions.EMPTY);
    }
}
