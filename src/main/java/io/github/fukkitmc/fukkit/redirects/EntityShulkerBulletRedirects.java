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

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityShulkerBullet;
import net.minecraft.server.EnumDirection;

public class EntityShulkerBulletRedirects {

    public static EntityLiving getShooter(EntityShulkerBullet bullet) {
        return bullet.shooter;
    }

    public static void setShooter(EntityShulkerBullet bullet, EntityLiving shooter) {
        bullet.shooter = shooter;
    }

    public static Entity getTarget(EntityShulkerBullet bullet) {
        return bullet.target;
    }

    public static void setTarget(EntityShulkerBullet bullet, Entity target) {
        bullet.target = target;
        bullet.dir = EnumDirection.UP;
        bullet.a(EnumDirection.EnumAxis.X);
    }
}
