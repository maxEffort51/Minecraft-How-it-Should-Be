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
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import java.util.List;

public class DoverTalentItem extends Item {
    private static final Identifier DAMAGE_BOOST_ID = Identifier.of(HisbMod.MOD_ID, "damage_boost");
    private static final Identifier ARMOR_BOOST_ID = Identifier.of(HisbMod.MOD_ID, "armor_boost");

    public final int type;

    public DoverTalentItem(int type, Settings settings) {
        super(settings);
        this.type = type;
    }

    public void tick(LivingEntity user, ServerWorld world) {
        HisbMod.debug("Dover talent");
        boolean success = switch(type) {
            case 1 -> redTick(user);
            case 2 -> orangeTick(user, world);
            case 3 -> brightTick(user, world);
            case 4 -> blueTick(user, world);
            default -> false;
        };
        if (!success) HisbMod.debug("Dover Talent tick went wrong somewhere");
    }

    public static void run() {
        HelperServerTick.playerList((server, playerList) -> playerList.forEach(player -> {
            if (player.getActiveItem().isOf(ModItems.BLUE_DOVER_TALENT)) {
                ((DoverTalentItem) player.getActiveItem().getItem()).tick(player, player.getServerWorld());
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
        orangeTick(user, world);
        if (user.isUsingItem()) {
            // push all entities back
            world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(20), entity -> entity != user).forEach(entity -> {
                Vec3d knockbackDirection = entity.getPos().subtract(user.getPos()).normalize();
                double y = Math.abs(knockbackDirection.y) < 2 ? 1 : Math.abs(knockbackDirection.y) / 2;
                entity.setVelocity(knockbackDirection.x * 4, y, knockbackDirection.z * 4);
                entity.velocityModified = true;
            });
            if (user instanceof PlayerEntity player) world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE);
        }
        return true;
    }

    private boolean blueTick(LivingEntity user, ServerWorld world) {
        brightTick(user, world);
        float healthPercentage = user.getHealth() / user.getMaxHealth();
        if (healthPercentage < 1) user.heal(user.getMaxHealth() / 3000);
        return true;
    }
}
