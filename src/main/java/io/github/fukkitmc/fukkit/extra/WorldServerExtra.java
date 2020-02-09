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

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ParticleParam;
import net.minecraft.server.WorldServer;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface WorldServerExtra {

    default boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        // TODO: IMPLEMENT
        ((WorldServer) this).addEntity(entity);
        return true;
    }

    default <T extends ParticleParam> int sendParticles(EntityPlayer sender, T t0, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, boolean force) {
        WorldServer server = (WorldServer) this;

        server.sender.set(sender);
        server.force.set(force);

        return server.a(t0, d0, d1, d2, i, d3, d4, d5, d6);
    }
}
