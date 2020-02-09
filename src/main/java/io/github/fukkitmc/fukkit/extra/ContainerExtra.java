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

import com.google.common.base.Preconditions;
import net.minecraft.server.Container;
import net.minecraft.server.IChatBaseComponent;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.InventoryView;

public interface ContainerExtra {

    // TODO:
    InventoryView getBukkitView();

    default void transferTo(Container other, org.bukkit.craftbukkit.entity.CraftHumanEntity player) {
        Container container = (Container) this;

        InventoryView source = getBukkitView(), destination = getBukkitView();
        ((CraftInventory) source.getTopInventory()).getInventory().onClose(player);
        ((CraftInventory) source.getBottomInventory()).getInventory().onClose(player);
        ((CraftInventory) destination.getTopInventory()).getInventory().onOpen(player);
        ((CraftInventory) destination.getBottomInventory()).getInventory().onOpen(player);
    }

    default IChatBaseComponent getTitle() {
        Container container = (Container) this;

        Preconditions.checkState(container.title != null, "Title not set");
        return container.title;
    }

    default void setTitle(IChatBaseComponent title) {
        Container container = (Container) this;

        Preconditions.checkState(container.title == null, "Title already set");
        container.title = title;
    }
}
