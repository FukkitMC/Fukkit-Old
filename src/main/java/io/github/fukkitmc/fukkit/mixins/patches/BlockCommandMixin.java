package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.*;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockCommand.class)
public class BlockCommandMixin {

    @Redirect(method = "doPhysics", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/TileEntityCommand;f()Z"))
    private static boolean someonePleaseFixThisName(TileEntityCommand tileEntityCommand, IBlockData iBlockData, World world, BlockPosition blockPosition, Block block, BlockPosition blockPosition2, boolean bl) {
        org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        int old = tileEntityCommand.f() ? 15 : 0;
        int current = world.isBlockIndirectlyPowered(blockPosition) ? 15 : 0;

        BlockRedstoneEvent event = new BlockRedstoneEvent(bukkitBlock, old, current);
        world.getServer().getPluginManager().callEvent(event);
        return event.getNewCurrent() > 0;
    }
}
