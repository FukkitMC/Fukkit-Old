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
import net.minecraft.server.EntityHuman;
import net.minecraft.server.MobEffect;
import net.minecraft.server.TileEntityBeacon;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.potion.PotionEffect;

import java.util.List;

// TODO: Implement the mixin
public class TileEntityBeaconRedirects {

    public static PotionEffect getPrimaryEffect(TileEntityBeacon beacon) {
        return beacon.primaryEffect != null ? CraftPotionUtil.toBukkit(new MobEffect(beacon.primaryEffect, getLevel(beacon), getAmplification(beacon), true, true)) : null;
    }

    public static PotionEffect getSecondaryEffect(TileEntityBeacon beacon) {
        return hasSecondaryEffect(beacon) ? CraftPotionUtil.toBukkit(new MobEffect(beacon.secondaryEffect, getLevel(beacon), getAmplification(beacon), true, true)) : null;
    }

    public static byte getAmplification(TileEntityBeacon beacon) {
        byte b0 = 0;

        if (beacon.levels >= 4 && beacon.primaryEffect == beacon.secondaryEffect) {
            b0 = 1;
        }

        return b0;
    }

    public static int getLevel(TileEntityBeacon beacon) {
        return (9 + beacon.levels * 2) * 20;
    }

    public static boolean hasSecondaryEffect(TileEntityBeacon beacon) {
        return beacon.levels >= 4 && beacon.primaryEffect != beacon.secondaryEffect && beacon.secondaryEffect != null;
    }

    public static List<EntityHuman> getHumansInRange(TileEntityBeacon beacon) {
        double d0 = beacon.levels * 10 + 10;
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(beacon.position)).g(d0).b(0.0D, beacon.world.getBuildHeight(), 0.0D);
        return beacon.world.a(EntityHuman.class, axisalignedbb);
    }
}
