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
import java.util.Objects;

public class DoverTalentItem extends Item {
    private static final Identifier DAMAGE_BOOST_ID = Identifier.of(HisbMod.id(), "damage_boost");
    private static final Identifier ARMOR_BOOST_ID = Identifier.of(HisbMod.id(), "armor_boost");

    public final int type;

    public DoverTalentItem(int type, Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // if this item is selected, use it
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (entity instanceof LivingEntity user) {
            if (!selected) { reset(user); return; }
            HisbMod.debug("Dover Tick");
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
        if (!success) HisbMod.debug("Dover Talent tick went wrong somewhere");
    }

//    public static void run() {
//        HelperServerTick.playerList((server, playerList) -> playerList.forEach(player -> {
//            HisbMod.debug("Running dover talent");
//            ItemStack mainStack = player.getMainHandStack();
//            HisbMod.debug("Main stack: " + mainStack);
//            ItemStack offStack = player.getOffHandStack();
//            Item empty = new DoverTalentItem(0,new Item.Settings());
//            if (mainStack.isOf(empty)) {
//                HisbMod.debug("Active item");
//                ((DoverTalentItem) mainStack.getItem()).doverTick(player, player.getServerWorld());
//            } else if (offStack.isOf(empty)) {
//                HisbMod.debug("Active offhand item");
//                ((DoverTalentItem) offStack.getItem()).doverTick(player, player.getServerWorld());
//            } else if (player.getInventory().contains(new ItemStack(empty))){
//                HisbMod.debug("In inventory");
//                PlayerInventory inventory = player.getInventory();
//                DoverTalentItem dover = (DoverTalentItem) inventory.getStack(inventory.getSlotWithStack(new ItemStack(ModItems.BLUE_DOVER_TALENT))).getItem();
//                if (!dover.isReset) dover.reset(player);
//            }
//        }));
//    }

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
        try {
            addAttribute(user, EntityAttributes.GENERIC_ATTACK_DAMAGE, DAMAGE_BOOST_ID, 5);
            addAttribute(user, EntityAttributes.GENERIC_ARMOR, ARMOR_BOOST_ID, 5);
            return true;
        } catch (Exception e) {
            HisbMod.debug("Dover Talent - Red Tick Error: " + e.getMessage());
            return false;
        }
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
        world.playSound((PlayerEntity) user, user.getBlockPos(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE);
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
}
