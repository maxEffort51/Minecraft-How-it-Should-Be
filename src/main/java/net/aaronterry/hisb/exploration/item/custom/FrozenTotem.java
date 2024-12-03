package net.aaronterry.hisb.exploration.item.custom;

import net.aaronterry.helper.KeyGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FrozenTotem extends Item {
    private final int distance;
    private final int seconds;
    private int tick = -1;
    private KeyGroup<LivingEntity, Float> frozen;

    public FrozenTotem(int dist, int time) {
        super(new Item.Settings().maxCount(1));
        distance = dist; seconds = time;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (tick == -1) return;
        tick++;
        if (tick > 20 * seconds) {
            // RESET ENTITY MOVEMENTS
            List<LivingEntity> keys = frozen.getKeys();
            List<Float> values = frozen.getValues();
            for (int i = 0; i < keys.size(); i++) {
                keys.get(i).setMovementSpeed(values.get(i));
            }
            tick = -1;
        } else {
            frozen.getKeys().forEach(livingEntity -> {
                livingEntity.setMovementSpeed(0); // stay frozen!
            });
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        tick = 0;
        frozen = new KeyGroup<>();
        try {
            world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(distance), entity -> !entity.equals(user)).forEach(entity -> {
                frozen.add(entity, entity.getMovementSpeed());
                entity.setMovementSpeed(0);
            });
            return TypedActionResult.success(user.getStackInHand(hand));
        } catch (Exception e) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }
}
