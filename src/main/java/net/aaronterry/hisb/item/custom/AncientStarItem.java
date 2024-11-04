package net.aaronterry.hisb.item.custom;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.events.ModEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AncientStarItem extends Item {
    public AncientStarItem(Settings settings) {
        super(settings);
    }

    @Override public int getMaxCount() { return 1; }

    public void activate(PlayerEntity player, float damage, Entity entity) {
        if (player.getHealth() < player.getMaxHealth() / 2) player.setHealth(player.getMaxHealth() / 2 );
        RegistryEntry<StatusEffect>[] effects = new RegistryEntry[] {StatusEffects.GLOWING,StatusEffects.REGENERATION,StatusEffects.RESISTANCE,StatusEffects.FIRE_RESISTANCE,StatusEffects.ABSORPTION};
        Useful.Entities.addStatusEffects(player, effects, 1200);
        //player.addStatusEffect(StatusEffects.REGENERATION); -> GLOWING, REGENERATION, RESISTANCE, FIRE-RESISTANCE, ABSORPTION
        // ModDamageSources.ANCIENT_STAR_EXPLOSION for custom damage source
        if (entity instanceof LivingEntity) entity.damage(ModEvents.EMPTY, damage); // right back at ya
        applyKnockback(player);
        HisbMod.debug("BAM!");
    }

    private void applyKnockback(PlayerEntity player) {
        World world = player.getWorld();
        world.getEntitiesByClass(Entity.class, player.getBoundingBox().expand(20.0), entity -> entity != player).forEach(entity -> {
            Vec3d knockbackDirection = entity.getPos().subtract(player.getPos()).normalize();
            entity.setVelocity(knockbackDirection.x * 4, 0.8, knockbackDirection.z * 4);
            entity.velocityModified = true;
        });
        world.playSound(player, player.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.NEUTRAL, 0.9f, 0.5f);
    }
}