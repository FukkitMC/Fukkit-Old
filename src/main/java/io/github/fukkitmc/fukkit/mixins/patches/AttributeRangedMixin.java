package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.AttributeBase;
import net.minecraft.server.AttributeRanged;
import net.minecraft.server.IAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AttributeRanged.class)
public abstract class AttributeRangedMixin extends AttributeBase {

    protected AttributeRangedMixin(@Nullable IAttribute iAttribute, String string, double d) {
        super(iAttribute, string, d);
    }

    @Inject(method = "a(D)D", at = @At("HEAD"), cancellable = true)
    private void someonePleaseFixThisName(double d, CallbackInfoReturnable<Double> callbackInfoReturnable) {
        if (d != d) {
            callbackInfoReturnable.setReturnValue(getDefault());
        }
    }
}
