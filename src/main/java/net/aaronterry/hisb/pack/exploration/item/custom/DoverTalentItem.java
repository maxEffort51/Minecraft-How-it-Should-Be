package net.aaronterry.hisb.pack.exploration.item.custom;

import net.aaronterry.helper.effect.Ability;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class DoverTalentItem extends Item {
    private static final Identifier DAMAGE_BOOST_ID = Identifier.of(HisbMod.id(), "dover_damage_boost");
    private static final Identifier ARMOR_BOOST_ID = Identifier.of(HisbMod.id(), "dover_armor_boost");

    public final int type;

    private static Ability RED_ABILITY;

    public DoverTalentItem(int type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // if this item is selected, use it
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (entity instanceof LivingEntity user) {
            doverTick(user, serverWorld);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HisbMod.debug("Dover Talent Use Attempt");
        ItemStack doverStack = user.getStackInHand(hand);
        if (type < 3) return TypedActionResult.pass(doverStack);
        MinecraftServer server = world.getServer();
        if (server == null) return TypedActionResult.fail(doverStack);
        ServerWorld serverWorld = server.getWorld(world.getRegistryKey());
        if (serverWorld == null || !brightTick(user, serverWorld)) return TypedActionResult.fail(doverStack);
        return TypedActionResult.success(doverStack);
    }

    public void doverTick(LivingEntity user, ServerWorld world) {
        boolean success = switch(type) {
            case 1 -> redTick(user);
            case 2, 3 -> orangeTick(user, world);
            case 4 -> blueTick(user, world);
            default -> false;
        };
        if (!success) HisbMod.debug("Dover Talent tick went wrong somewhere");
    }

    private boolean redTick(LivingEntity user) {
        HisbMod.debug("Dover Tick");
        if (RED_ABILITY == null) activate();
        RED_ABILITY.tick(user); return true;
    }

    private boolean orangeTick(LivingEntity user, ServerWorld world) {
        try {
            if (!redTick(user)) return false;
            Box destroyBorder = user.getBoundingBox().expand(1);
            List<ProjectileEntity> projectiles = world.getEntitiesByClass(ProjectileEntity.class, destroyBorder, entity -> true);
            for (ProjectileEntity projectile : projectiles) { if (projectile.getBoundingBox().intersects(destroyBorder)) projectile.discard(); }
            return true;
        } catch (Exception e) {
            HisbMod.debug("Dover Talent - Orange Tick Error: " + e.getMessage());
            return false;
        }
    }

    private boolean brightTick(LivingEntity user, ServerWorld world) {
        // BRIGHT TICK
        world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(20), entity -> entity != user).forEach(entity -> {
            Vec3d knockbackDirection = entity.getPos().subtract(user.getPos()).normalize();
            double y = Math.abs(knockbackDirection.y) < 2 ? 1 : Math.abs(knockbackDirection.y) / 2;
            entity.setVelocity(knockbackDirection.x * 4, y, knockbackDirection.z * 4);
            entity.velocityModified = true;
        });
        world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 0.9f, 1.3f);
        return true;
    }

    private boolean blueTick(LivingEntity user, ServerWorld world) {
        try {
            if (!orangeTick(user, world)) return false;
            float healthPercentage = user.getHealth() / user.getMaxHealth();
            if (healthPercentage < 1) user.heal(user.getMaxHealth() / 3000);
            return true;
        } catch (Exception e) {
            HisbMod.debug("Dover Talent - Blue Tick Error: " + e.getMessage());
            return false;
        }
    }

    private static void activate() {
        RED_ABILITY = new Ability(Ability.ItemInputs.items(ModItems.doverTalents()),entity -> {
            try {
                // ATTACK DAMAGE
                EntityAttributeInstance attackInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                if (attackInstance != null && !attackInstance.hasModifier(DAMAGE_BOOST_ID)) {
                    attackInstance.addTemporaryModifier(new EntityAttributeModifier(DAMAGE_BOOST_ID, 1, EntityAttributeModifier.Operation.ADD_VALUE));
                }
                // ARMOR BOOST
                EntityAttributeInstance protectInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
                if (protectInstance != null && !protectInstance.hasModifier(ARMOR_BOOST_ID)) {
                    protectInstance.addTemporaryModifier(new EntityAttributeModifier(ARMOR_BOOST_ID, 1, EntityAttributeModifier.Operation.ADD_VALUE));
                }
            } catch (Exception e) {
                HisbMod.debug("Dover Talent - Red Tick Error: " + e.getMessage());
            }
        }, entity -> {
            // ATTACK DAMAGE RESET
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (instance != null && instance.hasModifier(DAMAGE_BOOST_ID)) instance.removeModifier(DAMAGE_BOOST_ID);
            // ARMOR BOOST RESET
            EntityAttributeInstance protectInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
            if (protectInstance != null && protectInstance.hasModifier(ARMOR_BOOST_ID)) protectInstance.removeModifier(ARMOR_BOOST_ID);
        });
    }
}
