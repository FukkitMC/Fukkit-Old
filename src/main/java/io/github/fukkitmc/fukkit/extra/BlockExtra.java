package io.github.fukkitmc.fukkit.extra;

import net.minecraft.server.BlockPosition;
import net.minecraft.server.IBlockData;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public interface BlockExtra {

    default int getExpDrop(IBlockData iblockdata, World world, BlockPosition blockposition, ItemStack itemstack) {
        return 0;
    }
}
