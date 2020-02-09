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

import com.mojang.datafixers.util.Either;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Unit;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;

public interface EntityHumanExtra extends EntityExtra {

    @Override
    default CraftHumanEntity getBukkitEntity() {
        return (CraftHumanEntity) EntityExtra.super.getBukkitEntity();
    }

    default Either<EntityHuman.EnumBedResult, Unit> sleep(BlockPosition blockposition, boolean force) {
        throw new UnsupportedOperationException();
    }
}
