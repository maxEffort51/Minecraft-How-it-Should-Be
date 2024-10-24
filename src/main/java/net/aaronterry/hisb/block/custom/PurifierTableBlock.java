package net.aaronterry.hisb.block.custom;

import com.mojang.serialization.MapCodec;
import net.aaronterry.helper.LargeMap;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.entity.ModBlockEntities;
import net.aaronterry.hisb.block.entity.PurifierTableBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurifierTableBlock extends BlockWithEntity implements BlockEntityProvider {
    public PurifierTableBlock(Settings settings) {
        super(settings);
    }

    public static final LargeMap.Keys PURIFY_HOT_INPUTS = new LargeMap.Keys(new ItemConvertible[] {
            Items.BLAZE_POWDER,
            Items.BLAZE_ROD,
            Items.LAVA_BUCKET
            // Magma
            // Firite
    });

    public static final LargeMap.Keys PURIFY_WET_INPUTS = new LargeMap.Keys(new ItemConvertible[] {
            Items.POTION
            // Spring Water
    });

    public static final LargeMap PURIFY_RECIPES = new LargeMap(new ItemConvertible[] {
            // Lava -> Magma (doesn't exist yet)
            // Cosmium -> Pure Cosmium (doesn't exist yet)
            // Dead Sculk -> Demandi equivalent (doesn't exist yet)
            // Water -> Spring Water (doesn't exist yet)
            // Ancient Nether Star -> Nether Star (doesn't exist yet)
            // Corrupted Mud -> Mud (doesn't exist yet)
            // Hardened Sculk -> Impervium (doesn't exist yet)
            // Reinforced Deepslate -> Ivory Block (doesn't exist yet)
            // Dust -> Sand (doesn't exist yet)
            // Dusty Block -> Normal Block (doesn't exist yet)
            // Netherite Scrap -> Purified Scrap (doesn't exist yet)
            // Quartz -> Crystalline Quartz (doesn't exist yet)
            Blocks.COBBLESTONE, Blocks.OBSIDIAN,
    }, new ItemConvertible[] {
            Blocks.STONE, Items.LAVA_BUCKET
    }, new LargeMap.MultipleInputs[] {new LargeMap.MultipleInputs(new ItemConvertible[]{
            Blocks.TINTED_GLASS, Blocks.BLACK_STAINED_GLASS,
            Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS,
            Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS,
            Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS,
            Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS,
            Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS,
            Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS
    }, Blocks.GLASS), new LargeMap.MultipleInputs(new ItemConvertible[] {
            Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE,
            Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
            Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE,
            Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
            Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE,
            Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
            Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE,
            Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE
    }, Blocks.GLASS_PANE)});

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PurifierTableBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PurifierTableBlockEntity) {
                ItemScatterer.spawn(world, pos, (PurifierTableBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        HisbMod.LOGGER.info("Block Position: " + pos);
        HisbMod.LOGGER.info("Block Entity: " + world.getBlockEntity(pos));
        if (!world.isClient && world.getBlockEntity(pos) instanceof PurifierTableBlockEntity) {
            NamedScreenHandlerFactory screenHandlerFactory = ((PurifierTableBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                HisbMod.LOGGER.info("player: " + player + ", factory: " + screenHandlerFactory);
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.PURIFIER_TABLE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
