package net.aaronterry.hisb.pack.exploration.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SculkEchoItem extends Item {
    public SculkEchoItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            triggerSonicBoom(world, player);

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private void triggerSonicBoom(World world, PlayerEntity player) {
        double range = 22.0;
        float damage = 10.0F;
        double knockbackStrength = 3;

        Vec3d playerDirection = player.getRotationVec(1.0F);
        Box area = player.getBoundingBox().expand(range).stretch(playerDirection.multiply(range));

        for (Entity entity : world.getOtherEntities(player, area)) {
            if (entity instanceof LivingEntity target) {
                Vec3d directionToEntity = target.getPos().subtract(player.getPos()).normalize();

                if (playerDirection.dotProduct(directionToEntity) > 0.5) {
                    target.damage(player.getDamageSources().sonicBoom(player), damage);
                    Vec3d knockback = directionToEntity.multiply(knockbackStrength);
                    target.addVelocity(knockback.x, knockback.y, knockback.z);
                }
            }
        }
    }
}

//public class SculkHornItem extends Item {
//
//    public SculkHornItem(Settings settings) {
//        super(settings);
//    }
//
//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//        if (!world.isClient) {
//            // Trigger the sonic boom effect
//            triggerSonicBoom(world, player);
//
//            // Play the sound for the sonic boom
//            world.playSound(null, player.getX(), player.getY(), player.getZ(),
//                SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 1.0F, 1.0F);
//
//            // Optional: consume the item if it's single-use
//            // player.getStackInHand(hand).decrement(1);
//        }
//        return TypedActionResult.success(player.getStackInHand(hand));
//    }
//
//    private void triggerSonicBoom(World world, PlayerEntity player) {
//        // Set up the parameters for the sonic boom
//        double range = 15.0; // Range in blocks
//        float damage = 10.0F; // Damage dealt by the sonic boom
//        double knockbackStrength = 1.5; // Knockback strength
//
//        // Get the direction the player is facing
//        Vec3d playerDirection = player.getRotationVec(1.0F);
//
//        // Define the cone-shaped area in front of the player
//        Box area = player.getBoundingBox().expand(range).stretch(playerDirection.multiply(range));
//
//        // Find and affect entities within the cone area
//        for (Entity entity : world.getOtherEntities(player, area)) {
//            if (entity instanceof LivingEntity target) {
//                Vec3d directionToEntity = target.getPos().subtract(player.getPos()).normalize();
//
//                // Check if entity is within the cone (angle check)
//                if (playerDirection.dotProduct(directionToEntity) > 0.5) { // Adjust angle sensitivity here
//                    // Apply damage to the entity
//                    target.damage(player.getDamageSources().sonicBoom(), damage);
//
//                    // Apply knockback
//                    Vec3d knockback = directionToEntity.multiply(knockbackStrength);
//                    target.addVelocity(knockback.x, knockback.y, knockback.z);
//                }
//            }
//        }
//    }
//}