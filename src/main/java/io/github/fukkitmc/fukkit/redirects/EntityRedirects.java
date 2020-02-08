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

package io.github.fukkitmc.fukkit.redirects;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class EntityRedirects {

    public static CraftEntity getBukkitEntity(Entity entity) {
        if (entity.bukkitEntity == null) {
            entity.bukkitEntity = CraftEntity.getEntity(entity.world.getServer(), entity);
        }

        return entity.bukkitEntity;
    }

    public static CraftHumanEntity getHumanEntity(EntityHuman entity) {
        return (CraftHumanEntity) getBukkitEntity(entity);
    }

    public static CraftPlayer getPlayerEntity(EntityPlayer entity) {
        return (CraftPlayer) getBukkitEntity(entity);
    }

    public static boolean isChunkLoaded(Entity entity) {
        return entity.world.isChunkLoaded((int) Math.floor(entity.locX()) >> 4, (int) Math.floor(entity.locZ()) >> 4);
    }
}
