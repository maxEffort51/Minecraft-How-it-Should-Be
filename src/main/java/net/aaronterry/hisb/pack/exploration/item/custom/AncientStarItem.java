package net.aaronterry.hisb.pack.exploration.item.custom;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.main.HisbMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AncientStarItem extends Item {
    private static final RegistryKey<DamageType> ANCIENT_STAR_REFLECT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(HisbMod.id(), "ancient_star_reflect"));

    public AncientStarItem(Item.Settings settings) {
        super(settings);
    }

    @Override public int getMaxCount() { return 1; }

    public void activate(PlayerEntity player, float damage, Entity entity) {
        if (player.getHealth() < player.getMaxHealth() / 2) player.setHealth(player.getMaxHealth() / 2 );
        RegistryEntry<StatusEffect>[] effects = new RegistryEntry[] {StatusEffects.REGENERATION,StatusEffects.RESISTANCE,StatusEffects.FIRE_RESISTANCE,StatusEffects.ABSORPTION};
        Useful.Entities.addStatusEffects(player, effects, 1200);
        DamageSource reflectSource = new DamageSource(player.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ANCIENT_STAR_REFLECT), player);
        if (entity instanceof LivingEntity) entity.damage(reflectSource, damage);
        applyKnockback(player);
        HisbMod.debug("BAM!");
    }

    private void applyKnockback(PlayerEntity player) {
        World world = player.getWorld();
        world.getEntitiesByClass(Entity.class, player.getBoundingBox().expand(5.0), entity -> entity != player).forEach(entity -> {
            Vec3d knockbackDirection = entity.getPos().subtract(player.getPos()).normalize();
            entity.setVelocity(knockbackDirection.x * 4, 0.8, knockbackDirection.z * 4);
            entity.velocityModified = true;
        });
    }
}