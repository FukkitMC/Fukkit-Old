package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityBlastFurnace;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;

public class CraftBlastFurnace extends CraftFurnace implements BlastFurnace {

    public CraftBlastFurnace(Block block) {
        super(block, TileEntityBlastFurnace.class);
    }

    public CraftBlastFurnace(Material material, TileEntityBlastFurnace te) {
        super(material, te);
    }
}
