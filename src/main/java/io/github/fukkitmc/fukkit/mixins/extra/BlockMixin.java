package io.github.fukkitmc.fukkit.mixins.extra;

import io.github.fukkitmc.fukkit.extra.BlockExtra;
import net.minecraft.server.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin implements BlockExtra {
}
