package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockCactus.class)
public class BlockCactusMixin {

    @Inject(method = "a(Lnet/minecraft/server/IBlockData;Lnet/minecraft/server/World;Lnet/minecraft/server/BlockPosition;Lnet/minecraft/server/Entity;)V", at = @At("HEAD"))
    private static void someonePleaseFixThisName(IBlockData iBlockData, World world, BlockPosition blockPosition, Entity entity, CallbackInfo callbackInfo) {
        CraftEventFactory.blockDamage = world.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    @Inject(method = "a(Lnet/minecraft/server/IBlockData;Lnet/minecraft/server/World;Lnet/minecraft/server/BlockPosition;Lnet/minecraft/server/Entity;)V", at = @At("TAIL"))
    private static void someonePleaseFixThisNameV2(IBlockData iBlockData, World world, BlockPosition blockPosition, Entity entity, CallbackInfo callbackInfo) {
        CraftEventFactory.blockDamage = null;
    }
}
