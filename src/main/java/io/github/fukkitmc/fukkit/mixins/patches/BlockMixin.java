package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Redirect(method = "a(Lnet/minecraft/server/World;Lnet/minecraft/server/BlockPosition;Lnet/minecraft/server/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/World;addEntity(Lnet/minecraft/server/Entity;)Z"))
    private static boolean addEntity(World world, Entity entity) {
        if (world.captureDrops != null && entity instanceof EntityItem) {
            return world.captureDrops.add((EntityItem) entity);
        } else {
            return world.addEntity(entity);
        }
    }
}
