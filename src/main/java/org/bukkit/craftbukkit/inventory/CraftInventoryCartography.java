package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.IInventory;
import org.bukkit.inventory.CartographyInventory;

public class CraftInventoryCartography extends CraftResultInventory implements CartographyInventory {

    public CraftInventoryCartography(IInventory inventory, IInventory resultInventory) {
        super(inventory, resultInventory);
    }
}
