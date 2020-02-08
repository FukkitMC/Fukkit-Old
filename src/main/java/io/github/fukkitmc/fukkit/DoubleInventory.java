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

package io.github.fukkitmc.fukkit;

import net.minecraft.server.*;

import javax.annotation.Nullable;

public class DoubleInventory implements ITileInventory {

    public final InventoryLargeChest inventorylargechest;
    private final TileEntityChest tileentitychest;
    private final TileEntityChest tileentitychest1;

    public DoubleInventory(TileEntityChest tileentitychest, TileEntityChest tileentitychest1, InventoryLargeChest inventorylargechest) {
        this.tileentitychest = tileentitychest;
        this.tileentitychest1 = tileentitychest1;
        this.inventorylargechest = inventorylargechest;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
        if (tileentitychest.e(entityhuman) && tileentitychest1.e(entityhuman)) {
            tileentitychest.d(playerinventory.player);
            tileentitychest1.d(playerinventory.player);
            return ContainerChest.b(i, playerinventory, inventorylargechest);
        } else {
            return null;
        }
    }

    @Override
    public IChatBaseComponent getScoreboardDisplayName() {
        return tileentitychest.hasCustomName() ? tileentitychest.getScoreboardDisplayName() : (tileentitychest1.hasCustomName() ? tileentitychest1.getScoreboardDisplayName() : new ChatMessage("container.chestDouble"));
    }
}
