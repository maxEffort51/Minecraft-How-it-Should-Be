package net.aaronterry.hisb.exploration.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BlastChargeItem extends Item {
    public BlastChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (!world.isClient && player != null) {
            createExplosion(world, context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 0.5, context.getBlockPos().getZ() + 0.5, player);
            if (!player.isCreative()) player.getStackInHand(context.getHand()).decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            createExplosion(world, player.getX(), player.getY(), player.getZ(), player);
            if (!player.isCreative()) player.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private void createExplosion(World world, double x, double y, double z, PlayerEntity player) {
        float explosionPower = 10.0F;

        // Play explosion sound and trigger explosion effect
        world.playSound(null, x, y, z, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.BLOCKS, 1.0F, 2.0F);

        double playerDist = Math.sqrt(player.squaredDistanceTo(x,y,z));
        double playerKnockback = (1.0 - (playerDist / explosionPower)) * 2;
        player.setVelocity(x / playerDist * playerKnockback, y / playerDist * playerKnockback, z / playerDist * playerKnockback);

        // Find all entities within the explosion radius
        for (Entity entity : world.getOtherEntities(player, player.getBoundingBox().expand(explosionPower))) {
            double distanceSquared = entity.squaredDistanceTo(x, y, z);
            if (distanceSquared < explosionPower * explosionPower) {
                // Knock back and damage entity
                double dx = entity.getX() - x;
                double dy = entity.getY() - y;
                double dz = entity.getZ() - z;
                double distance = Math.sqrt(distanceSquared);
                double knockbackStrength = (1.0 - (distance / explosionPower)) * 2;

                Explosion explosion = new Explosion(world, player, x, y, z, explosionPower, true, Explosion.DestructionType.KEEP);

                // Apply knockback, damage, but avoid player
                if (entity != player) {
                    entity.setVelocity(dx / distance * knockbackStrength, dy / distance * knockbackStrength, dz / distance * knockbackStrength);
                    entity.damage(player.getDamageSources().explosion(explosion), 10.0F); // Set custom damage amount
                }
            }
        }
    }
}
