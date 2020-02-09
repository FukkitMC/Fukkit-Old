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
import net.minecraft.server.ContainerAccess;
import net.minecraft.server.ContainerAnvil;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerAnvil.class)
public class ContainerAnvilMixin implements ContainerExtra {

    @Shadow
    @Final
    private ContainerAccess containerAccess;

    @Shadow
    @Final
    private IInventory repairInventory;

    @Shadow
    @Final
    private IInventory resultInventory;

    @Shadow
    @Final
    private EntityHuman player;

    private CraftInventoryView bukkitEntity;

    @Override
    public InventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        bukkitEntity = new CraftInventoryView(player.getBukkitEntity(), new CraftInventoryAnvil(containerAccess.getLocation(), repairInventory, resultInventory, (ContainerAnvil) (Object) this), (ContainerAnvil) (Object) this);

        return bukkitEntity;
    }
}
