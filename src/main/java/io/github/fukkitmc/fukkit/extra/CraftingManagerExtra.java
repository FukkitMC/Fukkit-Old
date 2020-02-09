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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface CraftingManagerExtra {

    default void addRecipe(IRecipe<?> recipe) {
        Map<MinecraftKey, IRecipe<?>> map = ((CraftingManager) this).recipes.get(recipe.g());

        if (map.containsKey(recipe.getKey())) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + recipe.getKey());
        } else {
            putAndMoveToFirst(map, recipe.getKey(), recipe);
        }
    }

    default void clearRecipes() {
        CraftingManager manager = (CraftingManager) this;
        manager.recipes = new HashMap<>();

        for (Recipes<?> recipeType : IRegistry.RECIPE_TYPE) {
            manager.recipes.put(recipeType, new HashMap<>());
        }
    }

    static void putAndMoveToFirst(Map<MinecraftKey, IRecipe<?>> map, MinecraftKey key, IRecipe<?> value) {
        Map<MinecraftKey, IRecipe<?>> copy = new LinkedHashMap<>(map);
        map.clear();
        map.put(key, value);
        map.putAll(copy);
    }
}
