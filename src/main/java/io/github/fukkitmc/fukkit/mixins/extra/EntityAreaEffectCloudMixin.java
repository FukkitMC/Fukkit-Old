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

import io.github.fukkitmc.fukkit.extra.EntityAreaEffectCloudExtra;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(EntityAreaEffectCloud.class)
public abstract class EntityAreaEffectCloudMixin extends Entity implements EntityAreaEffectCloudExtra {

    @Shadow
    private boolean hasColor;

    @Shadow
    @Final
    private static DataWatcherObject<Integer> COLOR;

    @Shadow
    @Final
    public List<MobEffect> effects;

    @Shadow
    private PotionRegistry potionRegistry;

    @Shadow
    public abstract void a(PotionRegistry potionRegistry);

    public EntityAreaEffectCloudMixin(EntityTypes<?> entityTypes, World world) {
        super(entityTypes, world);
    }

    @Override
    public void refreshEffects() {
        if (!hasColor) {
            getDataWatcher().set(COLOR, PotionUtil.a(PotionUtil.a(potionRegistry, effects)));
        }
    }

    @Override
    public String getType() {
        return IRegistry.POTION.getKey(potionRegistry).toString();
    }

    @Override
    public void setType(String string) {
        a(IRegistry.POTION.get(new MinecraftKey(string)));
    }
}
