package net.maxeffort.helper.block.table;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TableBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final MapCodec<TableBlock> CODEC = createCodec(TableBlock::new);

    public TableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends TableBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        // NEEDS: Block Entity
        return new TableBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // NEEDS: Block Entity + Block Entity Type
        return world.isClient ? null : validateTicker(type, (BlockEntityType<? extends TableBlockEntity>) TableVariables.TABLE_ENTITY, TableBlockEntity::tick);
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
        // NEEDS: Block Entity implements NamedScreenHandlerFactory
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TableBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
        }
    }
}

// BLOCK ENTITY TYPE: public static final BlockEntityType<TableBlockEntity> PURIFIER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(BeyondMod.id(), "purifier_be"),
//            BlockEntityType.Builder.create(TableBlockEntity::new, ModBlocks.PURIFIER_TABLE).build());