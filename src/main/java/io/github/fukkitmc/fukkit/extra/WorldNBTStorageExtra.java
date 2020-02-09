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

import net.minecraft.server.NBTCompressedStreamTools;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldNBTStorage;

import java.io.*;
import java.util.UUID;

public interface WorldNBTStorageExtra {

    default NBTTagCompound getPlayerData(String s) {
        WorldNBTStorage nbtStorage = (WorldNBTStorage) this;

        try {
            File file1 = new File(nbtStorage.playerDir, s + ".dat");

            if (file1.exists()) {
                return NBTCompressedStreamTools.a(new FileInputStream(file1));
            }
        } catch (Exception exception) {
            WorldNBTStorage.b.warn("Failed to load player data for " + s);
        }

        return null;
    }

    default UUID getUUID() {
        WorldNBTStorage nbtStorage = (WorldNBTStorage) this;

        if (nbtStorage.uuid != null) return nbtStorage.uuid;
        File file1 = new File(nbtStorage.baseDir, "uid.dat");

        if (file1.exists()) {
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(new FileInputStream(file1));
                return nbtStorage.uuid = new UUID(dis.readLong(), dis.readLong());
            } catch (IOException ex) {
                WorldNBTStorage.b.warn("Failed to read " + file1 + ", generating new random UUID", ex);
            } finally {
                if (dis != null) {
                    try {
                        dis.close();
                    } catch (IOException ex) {
                        // NOOP
                    }
                }
            }
        }

        nbtStorage.uuid = UUID.randomUUID();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file1))) {
            dos.writeLong(nbtStorage.uuid.getMostSignificantBits());
            dos.writeLong(nbtStorage.uuid.getLeastSignificantBits());
        } catch (IOException ex) {
            WorldNBTStorage.b.warn("Failed to write " + file1, ex);
        }

        return nbtStorage.uuid;
    }

    default File getPlayerDir() {
        return ((WorldNBTStorage) this).playerDir;
    }
}
