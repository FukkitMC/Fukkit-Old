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

import java.util.Iterator;

public class ItemBlockRedirects {

    public static IBlockData getBlockState(IBlockData state, NBTTagCompound nbt) {
        BlockStateList<Block, IBlockData> blockstatelist = state.getBlock().getStates();

        for (String s : nbt.getKeys()) {
            IBlockState<?> iblockstate = blockstatelist.a(s);

            if (iblockstate != null) {
                String s1 = nbt.get(s).asString();
                state = a(state, iblockstate, s1);
            }
        }

        return state;
    }

    private static <T extends Comparable<T>> IBlockData a(IBlockData state, IBlockState<T> property, String value) {
        return property.b(value).map(comparable -> state.set(property, comparable)).orElse(state);
    }
}
