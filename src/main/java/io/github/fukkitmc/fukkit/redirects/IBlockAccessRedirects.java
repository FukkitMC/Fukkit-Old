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

import net.minecraft.server.*;

public class IBlockAccessRedirects {

    public static MovingObjectPositionBlock rayTraceBlock(IBlockAccess blockAccess, RayTrace raytrace1, BlockPosition blockposition) {
        IBlockData iblockdata = blockAccess.getType(blockposition);
        Fluid fluid = blockAccess.getFluid(blockposition);
        Vec3D vec3d = raytrace1.b();
        Vec3D vec3d1 = raytrace1.a();
        VoxelShape voxelshape = raytrace1.a(iblockdata, blockAccess, blockposition);
        MovingObjectPositionBlock movingobjectpositionblock = blockAccess.rayTrace(vec3d, vec3d1, blockposition, voxelshape, iblockdata);
        VoxelShape voxelshape1 = raytrace1.a(fluid, blockAccess, blockposition);
        MovingObjectPositionBlock movingobjectpositionblock1 = voxelshape1.rayTrace(vec3d, vec3d1, blockposition);
        double d0 = movingobjectpositionblock == null ? Double.MAX_VALUE : raytrace1.b().distanceSquared(movingobjectpositionblock.getPos());
        double d1 = movingobjectpositionblock1 == null ? Double.MAX_VALUE : raytrace1.b().distanceSquared(movingobjectpositionblock1.getPos());
        return d0 <= d1 ? movingobjectpositionblock : movingobjectpositionblock1;
    }
}
