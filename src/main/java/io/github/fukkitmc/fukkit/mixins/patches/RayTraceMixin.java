package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.Entity;
import net.minecraft.server.RayTrace;
import net.minecraft.server.VoxelShapeCollision;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RayTrace.class)
public class RayTraceMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/VoxelShapeCollision;a(Lnet/minecraft/server/Entity;)Lnet/minecraft/server/VoxelShapeCollision;"))
    private static VoxelShapeCollision someonePleaseFixThisName(Entity entity) {
        if (entity == null) {
            return VoxelShapeCollision.a();
        } else {
            return VoxelShapeCollision.a(entity);
        }
    }
}
