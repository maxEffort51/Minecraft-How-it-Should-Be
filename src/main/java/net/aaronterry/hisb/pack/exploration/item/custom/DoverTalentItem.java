package net.aaronterry.hisb.pack.exploration.item.custom;

import net.aaronterry.helper.util.HelperServerTick;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
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
import java.util.Objects;

public class DoverTalentItem extends Item {
    private static final Identifier DAMAGE_BOOST_ID = Identifier.of(HisbMod.id(), "damage_boost");
    private static final Identifier ARMOR_BOOST_ID = Identifier.of(HisbMod.id(), "armor_boost");

    public final int type;
    public boolean isReset = true;

    public DoverTalentItem(int type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack doverStack = user.getStackInHand(hand);
        if (type < 3) return TypedActionResult.pass(doverStack);
        ServerWorld serverWorld = Objects.requireNonNull(world.getServer()).getWorld(world.getRegistryKey());
        if (serverWorld == null || !brightTick(user, serverWorld)) return TypedActionResult.fail(doverStack);
        return TypedActionResult.success(doverStack);
    }

    public void reset(LivingEntity user) {
        removeAttribute(user, EntityAttributes.GENERIC_ATTACK_DAMAGE, DAMAGE_BOOST_ID);
        removeAttribute(user, EntityAttributes.GENERIC_ARMOR, ARMOR_BOOST_ID);
    }

    public void doverTick(LivingEntity user, ServerWorld world) {
        HisbMod.debug("Dover talent");
        boolean success = switch(type) {
            case 1 -> redTick(user);
            case 2 -> orangeTick(user, world);
            case 3 -> true;
            case 4 -> blueTick(user, world);
            default -> false;
        };
        isReset = false;
        if (!success) HisbMod.debug("Dover Talent tick went wrong somewhere");
    }

    public static void run() {
        HelperServerTick.playerList((server, playerList) -> playerList.forEach(player -> {
            if (player.getActiveItem().isOf(ModItems.BLUE_DOVER_TALENT)) {
                ((DoverTalentItem) player.getActiveItem().getItem()).doverTick(player, player.getServerWorld());
            } else if (player.getInventory().contains(new ItemStack(ModItems.BLUE_DOVER_TALENT))){
                PlayerInventory inventory = player.getInventory();
                DoverTalentItem dover = (DoverTalentItem) inventory.getStack(inventory.getSlotWithStack(new ItemStack(ModItems.BLUE_DOVER_TALENT))).getItem();
                if (!dover.isReset) dover.reset(player);
            }
        }));
    }

    private void addAttribute(LivingEntity user, RegistryEntry<EntityAttribute> type, Identifier attribute, int add) {
        EntityAttributeInstance instance = user.getAttributeInstance(type);
        assert instance != null;
        if (!instance.hasModifier(attribute)) {
            instance.addTemporaryModifier(new EntityAttributeModifier(attribute, add, EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }

    private void removeAttribute(LivingEntity user, RegistryEntry<EntityAttribute> type, Identifier attribute) {
        EntityAttributeInstance instance = user.getAttributeInstance(type);
        assert instance != null;
        if (instance.hasModifier(attribute)) instance.removeModifier(attribute);
    }

    private boolean redTick(LivingEntity user) {
        addAttribute(user, EntityAttributes.GENERIC_ATTACK_DAMAGE, DAMAGE_BOOST_ID, 5);
        addAttribute(user, EntityAttributes.GENERIC_ARMOR, ARMOR_BOOST_ID, 5);
        return true;
    }

    private boolean orangeTick(LivingEntity user, ServerWorld world) {
        redTick(user);
        Box destroyBorder = user.getBoundingBox().expand(1);
        List<ProjectileEntity> projectiles = world.getEntitiesByClass(ProjectileEntity.class, destroyBorder, entity -> true);
        for (ProjectileEntity projectile : projectiles) { if (projectile.getBoundingBox().intersects(destroyBorder)) projectile.discard(); }
        return true;
    }

    private boolean brightTick(LivingEntity user, ServerWorld world) {
        // BRIGHT TICK
        world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(20), entity -> entity != user).forEach(entity -> {
            Vec3d knockbackDirection = entity.getPos().subtract(user.getPos()).normalize();
            double y = Math.abs(knockbackDirection.y) < 2 ? 1 : Math.abs(knockbackDirection.y) / 2;
            entity.setVelocity(knockbackDirection.x * 4, y, knockbackDirection.z * 4);
            entity.velocityModified = true;
        });
        world.playSound((PlayerEntity) user, user.getBlockPos(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE);
        return true;
    }

    private boolean blueTick(LivingEntity user, ServerWorld world) {
        orangeTick(user, world);
        float healthPercentage = user.getHealth() / user.getMaxHealth();
        if (healthPercentage < 1) user.heal(user.getMaxHealth() / 3000);
        return true;
    }
}
