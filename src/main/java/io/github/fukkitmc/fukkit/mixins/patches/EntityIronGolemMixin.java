package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.EntityIronGolem;
import net.minecraft.server.EntityLiving;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityIronGolem.class)
public class EntityIronGolemMixin {

    @Redirect(method = "C", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/EntityIronGolem;setGoalTarget(Lnet/minecraft/server/EntityLiving;)V"))
    private static void setGoalTarget(EntityIronGolem entityIronGolem, EntityLiving entityLiving) {
        entityIronGolem.setGoalTarget(entityLiving, EntityTargetLivingEntityEvent.TargetReason.COLLISION, true);
    }
}
