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

import io.github.fukkitmc.fukkit.extra.ItemStackExtra;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackExtra {

    @Shadow
    @Final
    @Mutable
    @Deprecated
    public Item item;

    @Shadow
    private int count;

    @Shadow
    private NBTTagCompound tag;

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract void setDamage(int i);

    @Shadow
    public abstract int getDamage();

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void load(NBTTagCompound nbttagcompound) {
        item = IRegistry.ITEM.get(new MinecraftKey(nbttagcompound.getString("id")));
        count = nbttagcompound.getByte("Count");
        if (nbttagcompound.hasKeyOfType("tag", 10)) {
            tag = nbttagcompound.getCompound("tag").clone();
            getItem().a(tag);
        }

        if (getItem().usesDurability()) {
            setDamage(getDamage());
        }
    }
}
