package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.Advancements;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.Executors;

@Mixin(Advancements.class)
public class AdvancementsMixin {

    @Redirect(method = "a(Ljava/util/Map;)V", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"))
    private static void noop(Logger logger, String message, Object p0) {
    }
}
