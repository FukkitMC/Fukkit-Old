package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.server.EntityCat;
import net.minecraft.server.EnumColor;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class CraftCat extends CraftTameableAnimal implements Cat {

    public CraftCat(CraftServer server, EntityCat entity) {
        super(server, entity);
    }

    @Override
    public EntityCat getHandle() {
        return (EntityCat) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.CAT;
    }

    @Override
    public String toString() {
        return "CraftCat";
    }

    @Override
    public Type getCatType() {
        return Type.values()[getHandle().getCatType()];
    }

    @Override
    public void setCatType(Type type) {
        Preconditions.checkArgument(type != null, "Cannot have null Type");

        getHandle().setCatType(type.ordinal());
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) getHandle().getCollarColor().getColorIndex());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        getHandle().setCollarColor(EnumColor.fromColorIndex(color.getWoolData()));
    }
}
