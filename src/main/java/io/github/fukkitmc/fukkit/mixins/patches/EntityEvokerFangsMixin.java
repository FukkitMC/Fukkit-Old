package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.EntityEvokerFangs;
import net.minecraft.server.EntityLiving;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityEvokerFangs.class)
public class EntityEvokerFangsMixin {

    @Inject(method = "c", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/EntityLiving;damageEntity(Lnet/minecraft/server/DamageSource;F)Z", ordinal = 0))
    private void someonePleaseFixThisName(EntityLiving entityLiving, CallbackInfo callbackInfo) {
        CraftEventFactory.entityDamage = (EntityEvokerFangs) (Object) this;
    }

    @Inject(method = "c", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/EntityLiving;damageEntity(Lnet/minecraft/server/DamageSource;F)Z", ordinal = 0, shift = At.Shift.AFTER))
    private static void someonePleaseFixThisNameV2(EntityLiving entityLiving, CallbackInfo callbackInfo) {
        CraftEventFactory.entityDamage = null;
    }
}
