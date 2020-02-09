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

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.Entity;
import net.minecraft.server.EnumDirection;

import javax.annotation.Nullable;

public class EntityHangingRedirects {

    public static AxisAlignedBB calculateBoundingBox(@Nullable Entity entity, BlockPosition blockPosition, EnumDirection direction, int width, int height) {
        double d0 = blockPosition.getX() + 0.5D;
        double d1 = blockPosition.getY() + 0.5D;
        double d2 = blockPosition.getZ() + 0.5D;
        double d4 = a(width);
        double d5 = a(height);

        d0 -= (double) direction.getAdjacentX() * 0.46875D;
        d2 -= (double) direction.getAdjacentZ() * 0.46875D;
        d1 += d5;
        EnumDirection enumdirection = direction.g();

        d0 += d4 * enumdirection.getAdjacentX();
        d2 += d4 * enumdirection.getAdjacentZ();

        if (entity != null) {
            entity.setPositionRaw(d0, d1, d2);
        }

        double d6 = width;
        double d7 = height;
        double d8 = width;

        if (direction.m() == EnumDirection.EnumAxis.Z) {
            d8 = 1.0D;
        } else {
            d6 = 1.0D;
        }

        d6 /= 32.0D;
        d7 /= 32.0D;
        d8 /= 32.0D;

        return new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
    }

    private static double a(int i) {
        return i % 32 == 0 ? 0.5D : 0.0D;
    }
}
