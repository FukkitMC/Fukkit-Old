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
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public interface EntityPlayerExtra extends EntityHumanExtra {

    int incrementAndGetContainerCounter();

    default long getPlayerTime() {
        EntityPlayer player = (EntityPlayer) this;

        if (player.relativeTime) {
            // Adds timeOffset to the current server time.
            return player.world.getDayTime() + player.timeOffset;
        } else {
            // Adds timeOffset to the beginning of this day.
            return player.world.getDayTime() - (player.world.getDayTime() % 24000) + player.timeOffset;
        }
    }

    default WeatherType getPlayerWeather() {
        return ((EntityPlayer) this).weather;
    }

    default void setPlayerWeather(WeatherType type, boolean plugin) {
        EntityPlayer player = (EntityPlayer) this;

        if (!plugin && player.weather != null) {
            return;
        }

        if (plugin) {
            player.weather = type;
        }

        if (type == WeatherType.DOWNFALL) {
            player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0));
        } else {
            player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0));
        }
    }

    default void updateWeather(float oldRain, float newRain, float oldThunder, float newThunder) {
        EntityPlayer player = (EntityPlayer) this;

        if (player.weather == null) {
            // Vanilla
            if (oldRain != newRain) {
                player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, newRain));
            }
        } else {
            // Plugin
            if (player.pluginRainPositionPrevious != player.pluginRainPosition) {
                player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, player.pluginRainPosition));
            }
        }

        if (oldThunder != newThunder) {
            if (player.weather == WeatherType.DOWNFALL || player.weather == null) {
                player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, newThunder));
            } else {
                player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 0));
            }
        }
    }

    default void tickWeather() {
        EntityPlayer player = (EntityPlayer) this;

        if (player.weather == null) return;

        player.pluginRainPositionPrevious = player.pluginRainPosition;
        if (player.weather == WeatherType.DOWNFALL) {
            player.pluginRainPosition += 0.01;
        } else {
            player.pluginRainPosition -= 0.01;
        }

        player.pluginRainPosition = MathHelper.a(player.pluginRainPosition, 0.0F, 1.0F);
    }

    default void resetPlayerWeather() {
        EntityPlayer player = (EntityPlayer) this;

        player.weather = null;
        player.setPlayerWeather(player.world.getWorldData().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
    }

    // SPIGOT-1903, MC-98153
    default void forceSetPositionRotation(double x, double y, double z, float yaw, float pitch) {
        EntityPlayer player = (EntityPlayer) this;

        player.setPositionRotation(x, y, z, yaw, pitch);
        player.playerConnection.syncPosition();
    }

    // toString, isFrozen and getScoreboard are in the EntityPlayer extra mixin

    default void reset() {
        EntityPlayer player = (EntityPlayer) this;

        float exp = 0;
        boolean keepInventory = player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY);

        if (player.keepLevel || keepInventory) {
            exp = player.exp;
            player.newTotalExp = player.expTotal;
            player.newLevel = player.expLevel;
        }

        player.setHealth(player.getMaxHealth());
        player.fireTicks = 0;
        player.fallDistance = 0;
//        player.foodData = new FoodMetaData(player);
        player.expLevel = player.newLevel;
        player.expTotal = player.newTotalExp;
        player.exp = 0;
        player.deathTicks = 0;
        player.setArrowCount(0);
//        player.removeAllEffects(org.bukkit.event.entity.EntityPotionEffectEvent.Cause.DEATH);
        player.updateEffects = true;
        player.activeContainer = player.defaultContainer;
        player.killer = null;
        player.lastDamager = null;
        player.combatTracker = new CombatTracker(player);
        player.lastSentExp = -1;

        if (player.keepLevel || keepInventory) {
            player.exp = exp;
        } else {
            player.giveExp(player.newExp);
        }

        player.keepLevel = false;
    }

    @Override
    default CraftPlayer getBukkitEntity() {
        return (CraftPlayer) EntityHumanExtra.super.getBukkitEntity();
    }
}
