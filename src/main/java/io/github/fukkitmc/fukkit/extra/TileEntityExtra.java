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

package io.github.fukkitmc.fukkit.extra;

import net.minecraft.server.TileEntity;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;

public interface TileEntityExtra {

    default InventoryHolder getOwner() {
        TileEntity tile = (TileEntity) this;

        if (tile.world == null) {
            return null;
        }

        BlockState state = tile.world.getWorld().getBlockAt(tile.position.getX(), tile.position.getY(), tile.position.getZ()).getState();

        if (state instanceof InventoryHolder) {
            return (InventoryHolder) state;
        }

        return null;
    }
}
