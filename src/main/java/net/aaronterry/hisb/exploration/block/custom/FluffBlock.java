package net.aaronterry.hisb.exploration.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluffBlock extends HayBlock {
    private final float multiplier;

    public FluffBlock(float multiplier, Settings settings) {
        super(settings);
        this.multiplier = multiplier;
    }
    public FluffBlock(Settings settings) {
        super(settings);
        this.multiplier = 0;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, multiplier, world.getDamageSources().fall());
    }
}
