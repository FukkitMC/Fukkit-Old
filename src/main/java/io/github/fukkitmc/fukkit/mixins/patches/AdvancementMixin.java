package io.github.fukkitmc.fukkit.mixins.patches;

import net.minecraft.server.Advancement;
import org.bukkit.craftbukkit.advancement.CraftAdvancement;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Advancement.class)
public class AdvancementMixin {

    {
        Advancement self = (Advancement) (Object) this;
        self.bukkit = new CraftAdvancement(self);
    }
}
