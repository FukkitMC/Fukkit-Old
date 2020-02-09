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

import net.minecraft.server.EntityFireball;
import net.minecraft.server.MathHelper;

import java.util.Random;

public interface EntityFireballExtra {

    Random random();

    default void setDirection(double d0, double d1, double d2) {
        EntityFireball fireball = (EntityFireball) this;

        d0 += random().nextGaussian() * 0.4D;
        d1 += random().nextGaussian() * 0.4D;
        d2 += random().nextGaussian() * 0.4D;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

        fireball.dirX = d0 / d3 * 0.1D;
        fireball.dirY = d1 / d3 * 0.1D;
        fireball.dirZ = d2 / d3 * 0.1D;
    }
}
