/*
 * Copyright 2020 ramidzkh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use stack file except in compliance with the License.
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

import com.mojang.datafixers.Dynamic;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public interface ItemStackExtra {

    @Deprecated
    void setItem(Item item);

    void load(NBTTagCompound nbttagcompound);

    default void convertStack(int version) {
        ItemStack stack = (ItemStack) this;

        if (0 < version && version < CraftMagicNumbers.INSTANCE.getDataVersion()) {
            NBTTagCompound savedStack = new NBTTagCompound();
            stack.save(savedStack);
            savedStack = (NBTTagCompound) MinecraftServer.getServer().dataConverterManager.update(DataConverterTypes.ITEM_STACK, new Dynamic<>(DynamicOpsNBT.a, savedStack), version, CraftMagicNumbers.INSTANCE.getDataVersion()).getValue();
            stack.load(savedStack);
        }
    }
}
