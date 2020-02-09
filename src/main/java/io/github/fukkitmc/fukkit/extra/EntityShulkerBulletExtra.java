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

import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityShulkerBullet;

public interface EntityShulkerBulletExtra {

    default EntityLiving getShooter() {
        return ((EntityShulkerBullet) this).shooter;
    }

    default void setShooter(EntityLiving entity) {
        ((EntityShulkerBullet) this).shooter = entity;
    }

    default Entity getTarget() {
        return ((EntityShulkerBullet) this).target;
    }

    default void setTarget(Entity entity) {
        ((EntityShulkerBullet) this).target = entity;
    }
}
