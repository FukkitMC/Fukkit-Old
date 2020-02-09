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

import io.github.fukkitmc.fukkit.extra.ChunkProviderServerExtra;
import net.minecraft.server.ChunkMapDistance;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.PlayerChunkMap;
import net.minecraft.server.WorldServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkProviderServer.class)
public abstract class ChunkProviderServerMixin implements ChunkProviderServerExtra {

    @Shadow
    @Final
    private WorldServer world;

    @Shadow
    @Final
    private ChunkMapDistance chunkMapDistance;

    @Shadow
    protected abstract boolean tickDistanceManager();

    @Shadow
    @Final
    public PlayerChunkMap playerChunkMap;

    @Shadow
    protected abstract void clearCache();

    @Override
    public void purgeUnload() {
        world.getMethodProfiler().enter("purge");
        chunkMapDistance.purgeTickets();
        tickDistanceManager();
        world.getMethodProfiler().exitEnter("unload");
        playerChunkMap.unloadChunks(() -> true);
        world.getMethodProfiler().exit();
        clearCache();
    }
}
