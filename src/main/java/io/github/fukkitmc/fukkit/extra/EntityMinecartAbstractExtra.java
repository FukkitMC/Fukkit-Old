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

import net.minecraft.server.EntityMinecartAbstract;
import org.bukkit.util.Vector;

public interface EntityMinecartAbstractExtra {

    default Vector getFlyingVelocityMod() {
        EntityMinecartAbstract minecart = (EntityMinecartAbstract) this;

        return new Vector(minecart.flyingX, minecart.flyingY, minecart.flyingZ);
    }

    default void setFlyingVelocityMod(Vector flying) {
        EntityMinecartAbstract minecart = (EntityMinecartAbstract) this;

        minecart.flyingX = flying.getX();
        minecart.flyingY = flying.getY();
        minecart.flyingZ = flying.getZ();
    }

    default Vector getDerailedVelocityMod() {
        EntityMinecartAbstract minecart = (EntityMinecartAbstract) this;

        return new Vector(minecart.derailedX, minecart.derailedY, minecart.derailedZ);
    }

    default void setDerailedVelocityMod(Vector derailed) {
        EntityMinecartAbstract minecart = (EntityMinecartAbstract) this;

        minecart.derailedX = derailed.getX();
        minecart.derailedY = derailed.getY();
        minecart.derailedZ = derailed.getZ();
    }
}
