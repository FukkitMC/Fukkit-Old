package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.EntityIllagerIllusioner;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.MobEffect;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityIllagerIllusioner.class)
public class EntityIllagerIllusionerMixin {

    @Mixin(targets = "net/minecraft/server/EntityIllagerIllusioner$a")
    public static class $aMixin {
        @Redirect(method = "j", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/EntityLiving;addEffect(Lnet/minecraft/server/MobEffect;)Z"))
        private static boolean addEffect(EntityLiving entityLiving, MobEffect mobEffect) {
            return entityLiving.addEffect(mobEffect, EntityPotionEffectEvent.Cause.ATTACK);
        }
    }

    @Mixin(targets = "net/minecraft/server/EntityIllagerIllusioner$b")
    public static class $bMixin {
        @Redirect(method = "j", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/EntityIllagerIllusioner$b;addEffect(Lnet/minecraft/server/MobEffect;)Z"))
        private static boolean addEffect(EntityLiving entityLiving, MobEffect mobEffect) {
            return entityLiving.addEffect(mobEffect, EntityPotionEffectEvent.Cause.ILLUSION);
        }
    }
}
