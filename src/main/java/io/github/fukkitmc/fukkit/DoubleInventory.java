package io.github.fukkitmc.fukkit;

import net.minecraft.server.*;

import javax.annotation.Nullable;

public class DoubleInventory implements ITileInventory {

    private final TileEntityChest tileentitychest;
    private final TileEntityChest tileentitychest1;
    public final InventoryLargeChest inventorylargechest;

    public DoubleInventory(TileEntityChest tileentitychest, TileEntityChest tileentitychest1, InventoryLargeChest inventorylargechest) {
        this.tileentitychest = tileentitychest;
        this.tileentitychest1 = tileentitychest1;
        this.inventorylargechest = inventorylargechest;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
        if (tileentitychest.e(entityhuman) && tileentitychest1.e(entityhuman)) {
            tileentitychest.d(playerinventory.player);
            tileentitychest1.d(playerinventory.player);
            return ContainerChest.b(i, playerinventory, inventorylargechest);
        } else {
            return null;
        }
    }

    @Override
    public IChatBaseComponent getScoreboardDisplayName() {
        return tileentitychest.hasCustomName() ? tileentitychest.getScoreboardDisplayName() : (tileentitychest1.hasCustomName() ? tileentitychest1.getScoreboardDisplayName() : new ChatMessage("container.chestDouble"));
    }
}
