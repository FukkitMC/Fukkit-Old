package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityArmorStand;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityDragonFireball;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityEnderPearl;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityFishingHook;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityInsentient;
import net.minecraft.server.EntityLargeFireball;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityLlamaSpit;
import net.minecraft.server.EntityPotion;
import net.minecraft.server.EntityProjectile;
import net.minecraft.server.EntityShulkerBullet;
import net.minecraft.server.EntitySmallFireball;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntitySpectralArrow;
import net.minecraft.server.EntityThrownExpBottle;
import net.minecraft.server.EntityThrownTrident;
import net.minecraft.server.EntityTippedArrow;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.EntityWither;
import net.minecraft.server.EntityWitherSkull;
import net.minecraft.server.EnumHand;
import net.minecraft.server.GenericAttributes;
import net.minecraft.server.MobEffect;
import net.minecraft.server.MobEffectList;
import org.apache.commons.lang.Validate;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftEntityEquipment;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.entity.memory.CraftMemoryKey;
import org.bukkit.craftbukkit.entity.memory.CraftMemoryMapper;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class CraftLivingEntity extends CraftEntity implements LivingEntity {
    private CraftEntityEquipment equipment;

    public CraftLivingEntity(final CraftServer server, final EntityLiving entity) {
        super(server, entity);

        if (entity instanceof EntityInsentient || entity instanceof EntityArmorStand) {
            equipment = new CraftEntityEquipment(this);
        }
    }

    @Override
    public double getHealth() {
        return Math.min(Math.max(0, getHandle().getHealth()), getMaxHealth());
    }

    @Override
    public void setHealth(double health) {
        health = (float) health;
        if ((health < 0) || (health > getMaxHealth())) {
            throw new IllegalArgumentException("Health must be between 0 and " + getMaxHealth() + "(" + health + ")");
        }

        getHandle().setHealth((float) health);

        if (health == 0) {
            getHandle().die(DamageSource.GENERIC);
        }
    }

    @Override
    public double getAbsorptionAmount() {
        return getHandle().getAbsorptionHearts();
    }

    @Override
    public void setAbsorptionAmount(double amount) {
        Preconditions.checkArgument(amount >= 0 && Double.isFinite(amount), "amount < 0 or non-finite");

        getHandle().setAbsorptionHearts((float) amount);
    }

    @Override
    public double getMaxHealth() {
        return getHandle().getMaxHealth();
    }

    @Override
    public void setMaxHealth(double amount) {
        Validate.isTrue(amount > 0, "Max health must be greater than 0");

        getHandle().getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(amount);

        if (getHealth() > amount) {
            setHealth(amount);
        }
    }

    @Override
    public void resetMaxHealth() {
        setMaxHealth(getHandle().getAttributeInstance(GenericAttributes.MAX_HEALTH).getAttribute().getDefault());
    }

    @Override
    public double getEyeHeight() {
        return getHandle().getHeadHeight();
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        return getEyeHeight();
    }

    private List<Block> getLineOfSight(Set<Material> transparent, int maxDistance, int maxLength) {
        if (transparent == null) {
            transparent = Sets.newHashSet(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
        }
        if (maxDistance > 120) {
            maxDistance = 120;
        }
        ArrayList<Block> blocks = new ArrayList<Block>();
        Iterator<Block> itr = new BlockIterator(this, maxDistance);
        while (itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);
            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }
            Material material = block.getType();
            if (!transparent.contains(material)) {
                break;
            }
        }
        return blocks;
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 0);
    }

    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {
        List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
        return blocks.get(0);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 2);
    }

    @Override
    public Block getTargetBlockExact(int maxDistance) {
        return this.getTargetBlockExact(maxDistance, FluidCollisionMode.NEVER);
    }

    @Override
    public Block getTargetBlockExact(int maxDistance, FluidCollisionMode fluidCollisionMode) {
        RayTraceResult hitResult = this.rayTraceBlocks(maxDistance, fluidCollisionMode);
        return (hitResult != null ? hitResult.getHitBlock() : null);
    }

    @Override
    public RayTraceResult rayTraceBlocks(double maxDistance) {
        return this.rayTraceBlocks(maxDistance, FluidCollisionMode.NEVER);
    }

    @Override
    public RayTraceResult rayTraceBlocks(double maxDistance, FluidCollisionMode fluidCollisionMode) {
        Location eyeLocation = this.getEyeLocation();
        Vector direction = eyeLocation.getDirection();
        return this.getWorld().rayTraceBlocks(eyeLocation, direction, maxDistance, fluidCollisionMode, false);
    }

    @Override
    public int getRemainingAir() {
        return getHandle().getAirTicks();
    }

    @Override
    public void setRemainingAir(int ticks) {
        getHandle().setAirTicks(ticks);
    }

    @Override
    public int getMaximumAir() {
        return getHandle().maxAirTicks;
    }

    @Override
    public void setMaximumAir(int ticks) {
        getHandle().maxAirTicks = ticks;
    }

    @Override
    public void damage(double amount) {
        damage(amount, null);
    }

    @Override
    public void damage(double amount, org.bukkit.entity.Entity source) {
        DamageSource reason = DamageSource.GENERIC;

        if (source instanceof HumanEntity) {
            reason = DamageSource.playerAttack(((CraftHumanEntity) source).getHandle());
        } else if (source instanceof LivingEntity) {
            reason = DamageSource.mobAttack(((CraftLivingEntity) source).getHandle());
        }

        entity.damageEntity(reason, (float) amount);
    }

    @Override
    public Location getEyeLocation() {
        Location loc = getLocation();
        loc.setY(loc.getY() + getEyeHeight());
        return loc;
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return getHandle().maxNoDamageTicks;
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        getHandle().maxNoDamageTicks = ticks;
    }

    @Override
    public double getLastDamage() {
        return getHandle().lastDamage;
    }

    @Override
    public void setLastDamage(double damage) {
        getHandle().lastDamage = (float) damage;
    }

    @Override
    public int getNoDamageTicks() {
        return getHandle().noDamageTicks;
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        getHandle().noDamageTicks = ticks;
    }

    @Override
    public EntityLiving getHandle() {
        return (EntityLiving) entity;
    }

    public void setHandle(final EntityLiving entity) {
        super.setHandle(entity);
    }

    @Override
    public String toString() {
        return "CraftLivingEntity{" + "id=" + getEntityId() + '}';
    }

    @Override
    public Player getKiller() {
        return getHandle().killer == null ? null : (Player) getHandle().killer.getBukkitEntity();
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect) {
        return addPotionEffect(effect, false);
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        getHandle().addEffect(new MobEffect(MobEffectList.fromId(effect.getType().getId()), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles()), EntityPotionEffectEvent.Cause.PLUGIN);
        return true;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        boolean success = true;
        for (PotionEffect effect : effects) {
            success &= addPotionEffect(effect);
        }
        return success;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType type) {
        return getHandle().hasEffect(MobEffectList.fromId(type.getId()));
    }

    @Override
    public PotionEffect getPotionEffect(PotionEffectType type) {
        MobEffect handle = getHandle().getEffect(MobEffectList.fromId(type.getId()));
        return (handle == null) ? null : new PotionEffect(PotionEffectType.getById(MobEffectList.getId(handle.getMobEffect())), handle.getDuration(), handle.getAmplifier(), handle.isAmbient(), handle.isShowParticles());
    }

    @Override
    public void removePotionEffect(PotionEffectType type) {
        getHandle().removeEffect(MobEffectList.fromId(type.getId()), EntityPotionEffectEvent.Cause.PLUGIN);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        List<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (MobEffect handle : getHandle().effects.values()) {
            effects.add(new PotionEffect(PotionEffectType.getById(MobEffectList.getId(handle.getMobEffect())), handle.getDuration(), handle.getAmplifier(), handle.isAmbient(), handle.isShowParticles()));
        }
        return effects;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return launchProjectile(projectile, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        net.minecraft.server.World world = ((CraftWorld) getWorld()).getHandle();
        net.minecraft.server.Entity launch = null;

        if (Snowball.class.isAssignableFrom(projectile)) {
            launch = new EntitySnowball(world, getHandle());
            ((EntityProjectile) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, 0.0F, 1.5F, 1.0F); // ItemSnowball
        } else if (Egg.class.isAssignableFrom(projectile)) {
            launch = new EntityEgg(world, getHandle());
            ((EntityProjectile) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, 0.0F, 1.5F, 1.0F); // ItemEgg
        } else if (EnderPearl.class.isAssignableFrom(projectile)) {
            launch = new EntityEnderPearl(world, getHandle());
            ((EntityProjectile) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, 0.0F, 1.5F, 1.0F); // ItemEnderPearl
        } else if (AbstractArrow.class.isAssignableFrom(projectile)) {
            if (TippedArrow.class.isAssignableFrom(projectile)) {
                launch = new EntityTippedArrow(world, getHandle());
//                ((EntityTippedArrow) launch).setType(CraftPotionUtil.fromBukkit(new PotionData(PotionType.WATER, false, false)));
            } else if (SpectralArrow.class.isAssignableFrom(projectile)) {
                launch = new EntitySpectralArrow(world, getHandle());
            } else if (Trident.class.isAssignableFrom(projectile)) {
                launch = new EntityThrownTrident(world, getHandle(), new net.minecraft.server.ItemStack(net.minecraft.server.Items.TRIDENT));
            } else {
                launch = new EntityTippedArrow(world, getHandle());
            }
            ((EntityArrow) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, 0.0F, 3.0F, 1.0F); // ItemBow
        } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
            if (LingeringPotion.class.isAssignableFrom(projectile)) {
                launch = new EntityPotion(world, getHandle());
                ((EntityPotion) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.LINGERING_POTION, 1)));
            } else {
                launch = new EntityPotion(world, getHandle());
                ((EntityPotion) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.SPLASH_POTION, 1)));
            }
            ((EntityProjectile) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, -20.0F, 0.5F, 1.0F); // ItemSplashPotion
        } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
            launch = new EntityThrownExpBottle(world, getHandle());
            ((EntityProjectile) launch).a(getHandle(), getHandle().pitch, getHandle().yaw, -20.0F, 0.7F, 1.0F); // ItemExpBottle
        } else if (FishHook.class.isAssignableFrom(projectile) && getHandle() instanceof EntityHuman) {
            launch = new EntityFishingHook((EntityHuman) getHandle(), world, 0, 0);
        } else if (Fireball.class.isAssignableFrom(projectile)) {
            Location location = getEyeLocation();
            Vector direction = location.getDirection().multiply(10);

            if (SmallFireball.class.isAssignableFrom(projectile)) {
                launch = new EntitySmallFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            } else if (WitherSkull.class.isAssignableFrom(projectile)) {
                launch = new EntityWitherSkull(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            } else if (DragonFireball.class.isAssignableFrom(projectile)) {
                launch = new EntityDragonFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            } else {
                launch = new EntityLargeFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            }

            ((EntityFireball) launch).projectileSource = this;
            launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        } else if (LlamaSpit.class.isAssignableFrom(projectile)) {
            Location location = getEyeLocation();
            Vector direction = location.getDirection();

            launch = EntityTypes.LLAMA_SPIT.a(world);

            // TODO: Fukkit - fix
            // ((EntityLlamaSpit) launch).shooter = getHandle();
            ((EntityLlamaSpit) launch).shoot(direction.getX(), direction.getY(), direction.getZ(), 1.5F, 10.0F); // EntityLlama
            launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        } else if (ShulkerBullet.class.isAssignableFrom(projectile)) {
            Location location = getEyeLocation();

            launch = new EntityShulkerBullet(world, getHandle(), null, null);
            launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        }

        Validate.notNull(launch, "Projectile not supported");

        if (velocity != null) {
            ((T) launch.getBukkitEntity()).setVelocity(velocity);
        }

        world.addEntity(launch);
        return (T) launch.getBukkitEntity();
    }

    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }

    @Override
    public boolean hasLineOfSight(Entity other) {
        return getHandle().hasLineOfSight(((CraftEntity) other).getHandle());
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return getHandle() instanceof EntityInsentient && !((EntityInsentient) getHandle()).persistent;
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {
        if (getHandle() instanceof EntityInsentient) {
            ((EntityInsentient) getHandle()).persistent = !remove;
        }
    }

    @Override
    public EntityEquipment getEquipment() {
        return equipment;
    }

    @Override
    public void setCanPickupItems(boolean pickup) {
        getHandle().canPickUpLoot = pickup;
    }

    @Override
    public boolean getCanPickupItems() {
        return getHandle().canPickUpLoot;
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        if (getHealth() == 0) {
            return false;
        }

        return super.teleport(location, cause);
    }

    @Override
    public boolean isLeashed() {
        if (!(getHandle() instanceof EntityInsentient)) {
            return false;
        }
        return ((EntityInsentient) getHandle()).getLeashHolder() != null;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        if (!isLeashed()) {
            throw new IllegalStateException("Entity not leashed");
        }
        return ((EntityInsentient) getHandle()).getLeashHolder().getBukkitEntity();
    }

    private boolean unleash() {
        if (!isLeashed()) {
            return false;
        }
        ((EntityInsentient) getHandle()).unleash(true, false);
        return true;
    }

    @Override
    public boolean setLeashHolder(Entity holder) {
        if ((getHandle() instanceof EntityWither) || !(getHandle() instanceof EntityInsentient)) {
            return false;
        }

        if (holder == null) {
            return unleash();
        }

        if (holder.isDead()) {
            return false;
        }

        unleash();
        ((EntityInsentient) getHandle()).setLeashHolder(((CraftEntity) holder).getHandle(), true);
        return true;
    }

    @Override
    public boolean isGliding() {
        return getHandle().getFlag(7);
    }

    @Override
    public void setGliding(boolean gliding) {
        getHandle().setFlag(7, gliding);
    }

    @Override
    public boolean isSwimming() {
        return getHandle().isSwimming();
    }

    @Override
    public void setSwimming(boolean swimming) {
        getHandle().setSwimming(swimming);
    }

    @Override
    public boolean isRiptiding() {
        return getHandle().isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return getHandle().isSleeping();
    }

    @Override
    public AttributeInstance getAttribute(Attribute attribute) {
        return getHandle().craftAttributes.getAttribute(attribute);
    }

    @Override
    public void setAI(boolean ai) {
        if (this.getHandle() instanceof EntityInsentient) {
            ((EntityInsentient) this.getHandle()).setNoAI(!ai);
        }
    }

    @Override
    public boolean hasAI() {
        return (this.getHandle() instanceof EntityInsentient) ? !((EntityInsentient) this.getHandle()).isNoAI() : false;
    }

    @Override
    public void attack(Entity target) {
        Preconditions.checkArgument(target != null, "target == null");

        getHandle().B(((CraftEntity) target).getHandle()); // PAIL rename attack
    }

    @Override
    public void swingMainHand() {
        getHandle().a(EnumHand.MAIN_HAND); // PAIL rename swingHand
    }

    @Override
    public void swingOffHand() {
        getHandle().a(EnumHand.OFF_HAND); // PAIL rename swingHand
    }

    @Override
    public void setCollidable(boolean collidable) {
        getHandle().collides = collidable;
    }

    @Override
    public boolean isCollidable() {
        return getHandle().collides;
    }

    @Override
    public <T> T getMemory(MemoryKey<T> memoryKey) {
        return (T) getHandle().getBehaviorController().getMemory(CraftMemoryKey.fromMemoryKey(memoryKey)).map(CraftMemoryMapper::fromNms).orElse(null);
    }

    @Override
    public <T> void setMemory(MemoryKey<T> memoryKey, T t) {
        getHandle().getBehaviorController().setMemory(CraftMemoryKey.fromMemoryKey(memoryKey), CraftMemoryMapper.toNms(t));
    }
}
