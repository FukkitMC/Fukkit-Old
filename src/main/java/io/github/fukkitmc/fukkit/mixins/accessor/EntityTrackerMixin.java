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

package io.github.fukkitmc.fukkit.mixins.accessor;

import accessors.EntityTrackerAccessor;
import net.minecraft.server.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(targets = "net/minecraft/server/PlayerChunkMap$EntityTracker")
public abstract class EntityTrackerMixin implements EntityTrackerAccessor {

    @Shadow
    @Final
    public Set<EntityPlayer> trackedPlayers;

    @Override
    public Set<EntityPlayer> getTrackedPlayers() {
        return trackedPlayers;
    }

    @Shadow
    @Override
    public abstract void updatePlayer(EntityPlayer entityplayer);

    @Shadow
    @Override
    public abstract void clear(EntityPlayer entityplayer);
}
