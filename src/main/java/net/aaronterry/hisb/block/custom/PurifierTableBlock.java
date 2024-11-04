package net.aaronterry.hisb.block.custom;

import com.mojang.datafixers.types.Type;
import com.mojang.serialization.MapCodec;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.entity.ModBlockEntities;
import net.aaronterry.hisb.block.entity.PurifierBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.*;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurifierTableBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<PurifierTableBlock> CODEC = createCodec(PurifierTableBlock::new);

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build(type));
    }

    @Override
    public MapCodec<? extends PurifierTableBlock> getCodec() {
        return CODEC;
    }

    public PurifierTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        HisbMod.debug("Creating block entity");
        return new PurifierBlockEntity(pos, state);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType) {
        return world.isClient ? null : validateTicker(givenType, (BlockEntityType<? extends PurifierBlockEntity>) ModBlockEntities.PURIFIER_BLOCK_ENTITY, PurifierBlockEntity::tick);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            this.openScreen(world, pos, player);
            return ActionResult.CONSUME;
        }
    }

    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        HisbMod.debug("Opening screen");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PurifierBlockEntity) {
            HisbMod.debug("Open handled screen");
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
        }
    }
}