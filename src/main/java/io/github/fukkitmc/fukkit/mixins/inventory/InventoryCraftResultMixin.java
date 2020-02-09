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

package io.github.fukkitmc.fukkit.mixins.inventory;

import io.github.fukkitmc.fukkit.extra.IInventoryExtra;
import net.minecraft.server.InventoryCraftResult;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NonNullList;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(InventoryCraftResult.class)
public class InventoryCraftResultMixin implements IInventoryExtra {

    @Shadow
    @Final
    private NonNullList<ItemStack> items;

    private int maxStack = MAX_STACK;

    @Override
    public List<ItemStack> getContents() {
        return items;
    }

    @Override
    public void onOpen(CraftHumanEntity who) {
    }

    @Override
    public void onClose(CraftHumanEntity who) {
    }

    @Override
    public List<HumanEntity> getViewers() {
        return new ArrayList<>();
    }

    @Override
    public InventoryHolder getOwner() {
        return null;
    }

    @Override
    public int getMaxStackSize() {
        return maxStack;
    }

    @Override
    public void setMaxStackSize(int size) {
        maxStack = size;
    }

    @Override
    public Location getLocation() {
        return null;
    }
}