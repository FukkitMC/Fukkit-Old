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

import java.util.HashMap;
import java.util.Map;

// TODO: Make the map not immutable
public class CraftingManagerRedirects {

    public static void addRecipe(CraftingManager manager, IRecipe<?> recipe) {
        Map<MinecraftKey, IRecipe<?>> map = manager.recipes.get(recipe.g());

        if (map.containsKey(recipe.getKey())) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + recipe.getKey());
        } else {
            map.put(recipe.getKey(), recipe); // TODO: putAndMoveToFirst
        }
    }

    public static void clearRecipes(CraftingManager manager) {
        manager.recipes = new HashMap<>();

        for (Recipes<?> recipeType : IRegistry.RECIPE_TYPE) {
            manager.recipes.put(recipeType, new HashMap<>());
        }
    }
}
