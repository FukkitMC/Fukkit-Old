package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityVillagerTrader;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;

public class CraftWanderingTrader extends CraftAbstractVillager implements WanderingTrader {

    public CraftWanderingTrader(CraftServer server, EntityVillagerTrader entity) {
        super(server, entity);
    }

    @Override
    public EntityVillagerTrader getHandle() {
        return (EntityVillagerTrader) entity;
    }

    @Override
    public String toString() {
        return "CraftWanderingTrader";
    }

    @Override
    public EntityType getType() {
        return EntityType.WANDERING_TRADER;
    }
}
