package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.ChatComponentText;
import net.minecraft.server.Container;
import net.minecraft.server.ContainerAnvil;
import net.minecraft.server.ContainerBeacon;
import net.minecraft.server.ContainerBlastFurnace;
import net.minecraft.server.ContainerBrewingStand;
import net.minecraft.server.ContainerCartography;
import net.minecraft.server.ContainerChest;
import net.minecraft.server.ContainerDispenser;
import net.minecraft.server.ContainerEnchantTable;
import net.minecraft.server.ContainerFurnaceFurnace;
import net.minecraft.server.ContainerGrindstone;
import net.minecraft.server.ContainerHopper;
import net.minecraft.server.ContainerLectern;
import net.minecraft.server.ContainerLoom;
import net.minecraft.server.ContainerMerchant;
import net.minecraft.server.ContainerProperties;
import net.minecraft.server.ContainerShulkerBox;
import net.minecraft.server.ContainerSmoker;
import net.minecraft.server.ContainerStonecutter;
import net.minecraft.server.ContainerWorkbench;
import net.minecraft.server.Containers;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PacketPlayOutOpenWindow;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class CraftContainer extends Container {

    private final InventoryView view;
    private InventoryType cachedType;
    private String cachedTitle;
    private Container delegate;
    private final int cachedSize;

    public CraftContainer(InventoryView view, EntityHuman player, int id) {
        super(getNotchInventoryType(view.getTopInventory()), id);
        this.view = view;
        // TODO: Do we need to check that it really is a CraftInventory?
        IInventory top = ((CraftInventory) view.getTopInventory()).getInventory();
        PlayerInventory bottom = (PlayerInventory) ((CraftInventory) view.getBottomInventory()).getInventory();
        cachedType = view.getType();
        cachedTitle = view.getTitle();
        cachedSize = getSize();
        setupSlots(top, bottom, player);
    }

    public CraftContainer(final Inventory inventory, final EntityHuman player, int id) {
        this(new InventoryView() {
            @Override
            public Inventory getTopInventory() {
                return inventory;
            }

            @Override
            public Inventory getBottomInventory() {
                return getPlayer().getInventory();
            }

            @Override
            public HumanEntity getPlayer() {
                return player.getBukkitEntity();
            }

            @Override
            public InventoryType getType() {
                return inventory.getType();
            }

            @Override
            public String getTitle() {
                return inventory instanceof CraftInventoryCustom ? ((CraftInventoryCustom.MinecraftInventory) ((CraftInventory) inventory).getInventory()).getTitle() : inventory.getType().getDefaultTitle();
            }
        }, player, id);
    }

    @Override
    public InventoryView getBukkitView() {
        return view;
    }

    private int getSize() {
        return view.getTopInventory().getSize();
    }

    @Override
    public boolean c(EntityHuman entityhuman) {
        if (cachedType == view.getType() && cachedSize == getSize() && cachedTitle.equals(view.getTitle())) {
            return true;
        }
        // If the window type has changed for some reason, update the player
        // This method will be called every tick or something, so it's
        // as good a place as any to put something like this.
        boolean typeChanged = (cachedType != view.getType());
        cachedType = view.getType();
        cachedTitle = view.getTitle();
        if (view.getPlayer() instanceof CraftPlayer) {
            CraftPlayer player = (CraftPlayer) view.getPlayer();
            Containers<?> type = getNotchInventoryType(view.getTopInventory());
            IInventory top = ((CraftInventory) view.getTopInventory()).getInventory();
            PlayerInventory bottom = (PlayerInventory) ((CraftInventory) view.getBottomInventory()).getInventory();
            this.items.clear();
            this.slots.clear();
            if (typeChanged) {
                setupSlots(top, bottom, player.getHandle());
            }
            int size = getSize();
            player.getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.windowId, type, new ChatComponentText(cachedTitle)));
            player.updateInventory();
        }
        return true;
    }

    public static Containers getNotchInventoryType(Inventory inventory) {
        switch (inventory.getType()) {
            case PLAYER:
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
                switch(inventory.getSize()) {
                    case 9:
                        return Containers.GENERIC_9X1;
                    case 18:
                        return Containers.GENERIC_9X2;
                    case 27:
                        return Containers.GENERIC_9X3;
                    case 36:
                    case 41: // PLAYER
                        return Containers.GENERIC_9X4;
                    case 45:
                        return Containers.GENERIC_9X5;
                    case 54:
                        return Containers.GENERIC_9X6;
                    default:
                        throw new IllegalArgumentException("Unsupported custom inventory size " + inventory.getSize());
                }
            case WORKBENCH:
                return Containers.CRAFTING;
            case FURNACE:
                return Containers.FURNACE;
            case DISPENSER:
                return Containers.GENERIC_3X3;
            case ENCHANTING:
                return Containers.ENCHANTMENT;
            case BREWING:
                return Containers.BREWING_STAND;
            case BEACON:
                return Containers.BEACON;
            case ANVIL:
                return Containers.ANVIL;
            case HOPPER:
                return Containers.HOPPER;
            case DROPPER:
                return Containers.GENERIC_3X3;
            case SHULKER_BOX:
                return Containers.SHULKER_BOX;
            case BLAST_FURNACE:
                return Containers.BLAST_FURNACE;
            case LECTERN:
                return Containers.LECTERN;
            case SMOKER:
                return Containers.SMOKER;
            case LOOM:
                return Containers.LOOM;
            case CARTOGRAPHY:
                return Containers.CARTOGRAPHY_TABLE;
            case GRINDSTONE:
                return Containers.GRINDSTONE;
            case STONECUTTER:
                return Containers.STONECUTTER;
            case CREATIVE:
            case CRAFTING:
            case MERCHANT:
                throw new IllegalArgumentException("Can't open a " + inventory.getType() + " inventory!");
            default:
                // TODO: If it reaches the default case, should we throw an error?
                return Containers.GENERIC_9X3;
        }
    }

    private void setupSlots(IInventory top, PlayerInventory bottom, EntityHuman entityhuman) {
        int windowId = -1;
        switch (cachedType) {
            case CREATIVE:
                break; // TODO: This should be an error?
            case PLAYER:
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
                delegate = new ContainerChest(Containers.GENERIC_9X3, windowId, bottom, top, top.getSize() / 9);
                break;
            case DISPENSER:
            case DROPPER:
                delegate = new ContainerDispenser(windowId, bottom, top);
                break;
            case FURNACE:
                delegate = new ContainerFurnaceFurnace(windowId, bottom, top, new ContainerProperties(4));
                break;
            case CRAFTING: // TODO: This should be an error?
            case WORKBENCH:
                setupWorkbench(top, bottom); // SPIGOT-3812 - manually set up slots so we can use the delegated inventory and not the automatically created one
                break;
            case ENCHANTING:
                delegate = new ContainerEnchantTable(windowId, bottom);
                break;
            case BREWING:
                delegate = new ContainerBrewingStand(windowId, bottom, top, new ContainerProperties(2));
                break;
            case HOPPER:
                delegate = new ContainerHopper(windowId, bottom, top);
                break;
            case ANVIL:
                delegate = new ContainerAnvil(windowId, bottom);
                break;
            case BEACON:
                delegate = new ContainerBeacon(windowId, bottom);
                break;
            case SHULKER_BOX:
                delegate = new ContainerShulkerBox(windowId, bottom, top);
                break;
            case BLAST_FURNACE:
                delegate = new ContainerBlastFurnace(windowId, bottom, top, new ContainerProperties(4));
                break;
            case LECTERN:
                // TODO: Fukkit - fix
//                 delegate = new ContainerLectern(windowId, top, new ContainerProperties(1), bottom);
                break;
            case SMOKER:
                delegate = new ContainerSmoker(windowId, bottom, top, new ContainerProperties(4));
                break;
            case LOOM:
                delegate = new ContainerLoom(windowId, bottom);
                break;
            case CARTOGRAPHY:
                delegate = new ContainerCartography(windowId, bottom);
                break;
            case GRINDSTONE:
                delegate = new ContainerGrindstone(windowId, bottom);
                break;
            case STONECUTTER:
                delegate = new ContainerStonecutter(windowId, bottom);
                break;
            case MERCHANT:
                delegate = new ContainerMerchant(windowId, bottom);
                break;
        }

        if (delegate != null) {
            this.items = delegate.items;
            this.slots = delegate.slots;
        }

        // SPIGOT-4598 - we should still delegate the shift click handler
        if (cachedType == InventoryType.WORKBENCH) {
            delegate = new ContainerWorkbench(windowId, bottom);
        }
    }

    private void setupWorkbench(IInventory top, IInventory bottom) {
        // This code copied from ContainerWorkbench
        this.a(new Slot(top, 0, 124, 35));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.a(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col, 8 + col * 18, 142));
        }
        // End copy from ContainerWorkbench
    }

    @Override
    public ItemStack shiftClick(EntityHuman entityhuman, int i) {
        return (delegate != null) ? delegate.shiftClick(entityhuman, i) : super.shiftClick(entityhuman, i);
    }

    @Override
    public boolean canUse(EntityHuman entity) {
        return true;
    }

    @Override
    public Containers<?> getType() {
        return getNotchInventoryType(view.getTopInventory());
    }
}
