package io.github.fukkitmc.fukkit.mixins.patches;

import com.google.common.collect.Maps;
import net.minecraft.server.ArgumentBlock;
import net.minecraft.server.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(ArgumentBlock.class)
public class ArgumentBlockMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"))
    private static HashMap<IBlockState<?>, Comparable<?>> newHashMap() {
        return Maps.newLinkedHashMap();
    }
}
