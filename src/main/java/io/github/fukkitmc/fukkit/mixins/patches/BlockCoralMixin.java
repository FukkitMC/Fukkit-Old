package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BlockCoral.class)
public class BlockCoralMixin {

    @Shadow
    @Final
    private Block a;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/WorldServer;setTypeAndData(Lnet/minecraft/server/BlockPosition;Lnet/minecraft/server/IBlockData;I)Z"), cancellable = true)
    private void someonePleaseFixThisName(IBlockData iBlockData, WorldServer worldServer, BlockPosition blockPosition, Random random, CallbackInfo callbackInfo) {
        if (CraftEventFactory.callBlockFadeEvent(worldServer, blockPosition, a.getBlockData()).isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
