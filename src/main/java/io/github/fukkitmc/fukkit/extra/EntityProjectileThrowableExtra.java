package io.github.fukkitmc.fukkit.extra;

import net.minecraft.server.EntityProjectileThrowable;
import net.minecraft.server.ItemStack;

public interface EntityProjectileThrowableExtra {

    default ItemStack getDefaultItem() {
        return ((EntityProjectileThrowable) this).getItem();
    }
}
