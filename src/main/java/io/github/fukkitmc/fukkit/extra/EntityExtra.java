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

import net.minecraft.server.BlockPosition;
import net.minecraft.server.DimensionManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArmorStand;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.entity.CraftEntity;

public interface EntityExtra {

    default CraftEntity getBukkitEntity() {
        Entity entity = (Entity) this;

        if (entity.bukkitEntity == null) {
            entity.bukkitEntity = CraftEntity.getEntity(entity.world.getServer(), entity);
        }

        return entity.bukkitEntity;
    }

    default boolean isChunkLoaded() {
        Entity entity = (Entity) this;

        return entity.world.isChunkLoaded((int) Math.floor(entity.locX()) >> 4, (int) Math.floor(entity.locZ()) >> 4);
    }

    default float getBukkitYaw() {
        Entity self = (Entity) this;

        if (self instanceof EntityArmorStand) {
            return self.yaw;
        } else if (self instanceof EntityLiving) {
            return self.getHeadRotation();
        }

        return self.yaw;
    }

    default Entity teleportTo(DimensionManager dimensionmanager, BlockPosition location) {
        throw new UnsupportedOperationException();
    }
}
