package io.github.fukkitmc.fukkit.mixins.patches;

import io.github.fukkitmc.fukkit.DoubleInventory;
import net.minecraft.server.ITileInventory;
import net.minecraft.server.InventoryLargeChest;
import net.minecraft.server.TileEntityChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(targets = "net/minecraft/server/BlockChest$3")
public class BlockChest$3Mixin {

    @Inject(method = "a(Lnet/minecraft/server/TileEntityChest;Lnet/minecraft/server/TileEntityChest;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void someonePleaseFixThisName(TileEntityChest tileEntityChest, TileEntityChest tileEntityChest2, CallbackInfoReturnable<Optional<ITileInventory>> callbackInfoReturnable) {
        final InventoryLargeChest inventorylargechest = new InventoryLargeChest(tileEntityChest, tileEntityChest2);
        callbackInfoReturnable.setReturnValue(Optional.of(new DoubleInventory(tileEntityChest, tileEntityChest2, inventorylargechest)));
    }
}
