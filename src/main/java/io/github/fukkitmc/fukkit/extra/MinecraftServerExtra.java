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

import net.minecraft.server.*;

public interface MinecraftServerExtra {

    boolean hasStopped();

    void someMethod(WorldData worldData);

    default void initWorld(WorldServer worldserver1, WorldData worlddata, WorldSettings worldsettings) {
        worldserver1.getWorldBorder().b(worlddata);

        if (worldserver1.generator != null) {
            worldserver1.getWorld().getPopulators().addAll(worldserver1.generator.getDefaultPopulators(worldserver1.getWorld()));
        }

        if (!worlddata.u()) {
            try {
                worldserver1.a(worldsettings);
                if (worlddata.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
                    someMethod(worlddata);
                }

                worlddata.d(true);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");

                try {
                    worldserver1.a(crashreport);
                } catch (Throwable ignored) {
                }

                throw new ReportedException(crashreport);
            }

            worlddata.d(true);
        }
    }
}
