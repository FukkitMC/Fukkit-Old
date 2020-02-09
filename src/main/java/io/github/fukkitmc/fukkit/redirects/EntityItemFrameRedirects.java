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

public class EntityItemFrameRedirects {

    public static AxisAlignedBB calculateBoundingBox(@Nullable Entity entity, BlockPosition blockPosition, EnumDirection direction, int width, int height) {
        double d1 = blockPosition.getX() + 0.5D - direction.getAdjacentX() * 0.46875D;
        double d2 = blockPosition.getY() + 0.5D - direction.getAdjacentY() * 0.46875D;
        double d3 = blockPosition.getZ() + 0.5D - direction.getAdjacentZ() * 0.46875D;

        if (entity != null) {
            entity.setPositionRaw(d1, d2, d3);
        }

        double d4 = width;
        double d5 = height;
        double d6 = width;

        switch (direction.m()) {
            case X:
                d4 = 1.0D;
                break;
            case Y:
                d5 = 1.0D;
                break;
            case Z:
                d6 = 1.0D;
        }

        d4 /= 32.0D;
        d5 /= 32.0D;
        d6 /= 32.0D;

        return new AxisAlignedBB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6);
    }
}
