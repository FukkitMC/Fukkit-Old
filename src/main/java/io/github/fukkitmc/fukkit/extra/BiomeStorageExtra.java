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

import net.minecraft.server.BiomeBase;
import net.minecraft.server.BiomeStorage;
import net.minecraft.server.MathHelper;

public interface BiomeStorageExtra {

    default void setBiome(int i, int j, int k, BiomeBase biome) {
        BiomeStorage storage = (BiomeStorage) this;

        int l = i & BiomeStorage.b;
        int i1 = MathHelper.clamp(j, 0, BiomeStorage.c);
        int j1 = k & BiomeStorage.b;

        storage.g[i1 << BiomeStorage.e + BiomeStorage.e | j1 << BiomeStorage.e | l] = biome;
    }
}
