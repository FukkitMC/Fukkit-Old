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

import io.github.fukkitmc.fukkit.extra.ContainerExtra;
import net.minecraft.server.ContainerBeacon;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerBeacon.class)
public class ContainerBeaconMixin implements ContainerExtra {

    @Shadow
    @Final
    private IInventory beacon;

    // TODO
    private EntityPlayer player;

    static {
        if (true) {
            throw new RuntimeException();
        }
    }

    private CraftInventoryView bukkitEntity;

    @Override
    public InventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        org.bukkit.craftbukkit.inventory.CraftInventory inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryBeacon(beacon);
        bukkitEntity = new CraftInventoryView(player.getBukkitEntity(), inventory, (ContainerBeacon) (Object) this);
        return bukkitEntity;
    }
}
