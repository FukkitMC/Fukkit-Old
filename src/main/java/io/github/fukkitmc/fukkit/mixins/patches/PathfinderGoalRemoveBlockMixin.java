package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.BlockPosition;
import net.minecraft.server.EntityInsentient;
import net.minecraft.server.PathfinderGoalRemoveBlock;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.event.entity.EntityInteractEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PathfinderGoalRemoveBlock.class)
public class PathfinderGoalRemoveBlockMixin {

    @Shadow @Final private EntityInsentient entity;

    @Inject(method = "e", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/World;a(Lnet/minecraft/server/BlockPosition;Z)Z"), cancellable = true)
    private void someonePleaseFixThisName(CallbackInfo callbackInfo) {
        EntityInteractEvent event = new EntityInteractEvent(entity.getBukkitEntity(), CraftBlock.at(entity.world, new BlockPosition(entity)));
        entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
