package org.bukkit.craftbukkit.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaArmorStand extends CraftMetaItem {

    static final ItemMetaKey ENTITY_TAG = new ItemMetaKey("EntityTag", "entity-tag");
    NBTTagCompound entityTag;

    CraftMetaArmorStand(CraftMetaItem meta) {
        super(meta);
    }

    CraftMetaArmorStand(NBTTagCompound tag) {
        super(tag);

        if (tag.hasKey(ENTITY_TAG.NBT)) {
            entityTag = tag.getCompound(ENTITY_TAG.NBT);
        }
    }

    CraftMetaArmorStand(Map<String, Object> map) {
        super(map);
    }

    @Override
    void deserializeInternal(NBTTagCompound tag, Object context) {
        super.deserializeInternal(tag, context);

        if (tag.hasKey(ENTITY_TAG.NBT)) {
            entityTag = tag.getCompound(ENTITY_TAG.NBT);
        }
    }

    @Override
    void serializeInternal(Map<String, NBTBase> internalTags) {
        if (entityTag != null && !entityTag.isEmpty()) {
            internalTags.put(ENTITY_TAG.NBT, entityTag);
        }
    }

    @Override
    void applyToItem(NBTTagCompound tag) {
        super.applyToItem(tag);

        if (entityTag != null) {
            tag.set(ENTITY_TAG.NBT, entityTag);
        }
    }

    @Override
    boolean applicableTo(Material type) {
        switch (type) {
            case ARMOR_STAND:
                return true;
            default:
                return false;
        }
    }

    @Override
    boolean isEmpty() {
        return super.isEmpty() && isArmorStandEmpty();
    }

    boolean isArmorStandEmpty() {
        return !(entityTag != null);
    }

    @Override
    boolean equalsCommon(CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaArmorStand) {
            CraftMetaArmorStand that = (CraftMetaArmorStand) meta;

            return entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : entityTag == null;
        }
        return true;
    }

    @Override
    boolean notUncommon(CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaArmorStand || isArmorStandEmpty());
    }

    @Override
    int applyHash() {
        final int original;
        int hash = original = super.applyHash();

        if (entityTag != null) {
            hash = 73 * hash + entityTag.hashCode();
        }

        return original != hash ? CraftMetaArmorStand.class.hashCode() ^ hash : hash;
    }

    @Override
    Builder<String, Object> serialize(Builder<String, Object> builder) {
        super.serialize(builder);

        return builder;
    }

    @Override
    public CraftMetaArmorStand clone() {
        CraftMetaArmorStand clone = (CraftMetaArmorStand) super.clone();

        if (entityTag != null) {
            clone.entityTag = entityTag.clone();
        }

        return clone;
    }
}
