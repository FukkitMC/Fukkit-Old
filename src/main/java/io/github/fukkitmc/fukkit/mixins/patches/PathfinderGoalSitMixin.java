package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.EntityTameableAnimal;
import net.minecraft.server.PathfinderGoalSit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PathfinderGoalSit.class)
public class PathfinderGoalSitMixin {

    @Shadow
    private boolean willSit;

    @Shadow
    @Final
    private EntityTameableAnimal entity;

    @Inject(method = "a", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void shouldStart(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(willSit && entity.getGoalTarget() == null);
    }
}
