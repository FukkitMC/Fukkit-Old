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

package io.github.fukkitmc.fukkit.mixins.extra;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.fukkit.extra.EntityPlayerExtra;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Scoreboard;
import net.minecraft.server.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityHuman implements EntityPlayerExtra {

    @Shadow
    public abstract void nextContainerCounter();

    @Shadow
    private int containerCounter;

    public EntityPlayerMixin(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Override
    public int incrementAndGetContainerCounter() {
        nextContainerCounter();
        return containerCounter;
    }

    @Override
    protected boolean isFrozen() {
        return super.isFrozen() || !getBukkitEntity().isOnline();
    }

    @Override
    public Scoreboard getScoreboard() {
        return getBukkitEntity().getScoreboard().getHandle();
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.getName() + " at " + this.locX() + "," + this.locY() + "," + this.locZ() + ")";
    }
}
