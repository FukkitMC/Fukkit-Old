package io.github.fukkitmc.fukkit.mixins.extra;

import io.github.fukkitmc.fukkit.extra.EntityProjectileThrowableExtra;
import net.minecraft.server.EntityProjectileThrowable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityProjectileThrowable.class)
public class EntityProjectileThrowableMixin implements EntityProjectileThrowableExtra {
}
