package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockTNT.class)
public class BlockTNTMixin {

    @Inject(method = "a(Lnet/minecraft/server/World;Lnet/minecraft/server/IBlockData;Lnet/minecraft/server/MovingObjectPositionBlock;Lnet/minecraft/server/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/BlockTNT;a(Lnet/minecraft/server/World;Lnet/minecraft/server/BlockPosition;Lnet/minecraft/server/EntityLiving;)V"), cancellable = true)
    private static void someonePleaseFixThisName(World world, IBlockData iBlockData, MovingObjectPositionBlock movingObjectPositionBlock, Entity entity, CallbackInfo callbackInfo) {
        if (CraftEventFactory.callEntityChangeBlockEvent(entity, movingObjectPositionBlock.getBlockPosition(), Blocks.AIR.getBlockData()).isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
