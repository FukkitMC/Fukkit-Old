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

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.MobEffect;
import net.minecraft.server.TileEntityBeacon;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public interface TileEntityBeaconExtra {

    default PotionEffect getPrimaryEffect() {
        TileEntityBeacon beacon = (TileEntityBeacon) this;
        return beacon.primaryEffect != null ? CraftPotionUtil.toBukkit(new MobEffect(beacon.primaryEffect, getLevel(), getAmplification(), true, true)) : null;
    }

    default PotionEffect getSecondaryEffect() {
        TileEntityBeacon beacon = (TileEntityBeacon) this;
        return hasSecondaryEffect() ? CraftPotionUtil.toBukkit(new MobEffect(beacon.secondaryEffect, getLevel(), getAmplification(), true, true)) : null;
    }

    default byte getAmplification() {
        TileEntityBeacon beacon = (TileEntityBeacon) this;

        if (beacon.levels >= 4 && beacon.primaryEffect == beacon.secondaryEffect) {
            return 1;
        }

        return 0;
    }

    default int getLevel() {
        return (9 + ((TileEntityBeacon) this).levels * 2) * 20;
    }

    default boolean hasSecondaryEffect() {
        TileEntityBeacon beacon = (TileEntityBeacon) this;
        return beacon.levels >= 4 && beacon.primaryEffect != beacon.secondaryEffect && beacon.secondaryEffect != null;
    }

    default List<EntityHuman> getHumansInRange() {
        TileEntityBeacon beacon = (TileEntityBeacon) this;
        double d0 = beacon.levels * 10 + 10;
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(beacon.position)).g(d0).b(0.0D, beacon.world.getBuildHeight(), 0.0D);
        return beacon.world.a(EntityHuman.class, axisalignedbb);
    }
}
