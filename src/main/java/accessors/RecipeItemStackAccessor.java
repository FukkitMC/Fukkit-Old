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

package accessors;

import net.minecraft.server.ItemStack;
import net.minecraft.server.RecipeItemStack;

import java.util.stream.Stream;

public class RecipeItemStackAccessor {

    public static Object createProvider(ItemStack itemStack) {
        // TODO
        return null;
    }

    public static RecipeItemStack create(Stream stream) {
        return io.github.fukkitmc.fukkit.mixins.accessor.RecipeItemStackAccessor.create(stream);
    }
}
