package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Nullable public FabricTagBuilder addToTag(TagKey<Block> tag, Block block) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.add(block); return builder; }
    @Nullable public FabricTagBuilder addToTag(TagKey<Block> tag, TagKey<Block> tagToAdd) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.addTag(tagToAdd); return builder; }
    @Nullable public FabricTagBuilder addToTag(TagKey<Block> tag, Block[] blocks) {
        FabricTagBuilder builder = getOrCreateTagBuilder(tag); for (Block block : blocks) builder.add(block); return builder; }

    public void handleToolLevels(TagKey<Block>[] toolTags, TagKey<Block>[] inverse) {
        for (int i = 0; i < inverse.length; i++) {
            FabricTagBuilder builder = getOrCreateTagBuilder(inverse[i]);
            for (int j = i; j < toolTags.length; j++) builder.addTag(toolTags[j]);
        }
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        addToTag(ModTags.Blocks.ALL_MOD_BLOCKS, ModBlocks.ALL);

        addToTag(BlockTags.AXE_MINEABLE, ModBlocks.Tools.AXE);
        addToTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.Tools.PICKAXE);
        addToTag(BlockTags.SHOVEL_MINEABLE, ModBlocks.Tools.SHOVEL);
        addToTag(BlockTags.HOE_MINEABLE, ModBlocks.Tools.HOE);

        addToTag(BlockTags.PLANKS, ModBlocks.DEAD_PLANKS);

        // BLOCK MINING
        addToTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.Tools.NEEDS_STONE);
        addToTag(BlockTags.NEEDS_IRON_TOOL, ModBlocks.Tools.NEEDS_IRON);
        addToTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.Tools.NEEDS_DIAMOND);
        addToTag(ModTags.Blocks.NEEDS_NETHERITE_TOOL, ModBlocks.Tools.NEEDS_NETHERITE);
        addToTag(ModTags.Blocks.NEEDS_SCULTIUM_TOOL, ModBlocks.Tools.NEEDS_SCULTIUM);

        TagKey<Block>[] toolLevels = new TagKey[] {ModTags.Blocks.NEEDS_NETHERITE_TOOL,ModTags.Blocks.NEEDS_SCULTIUM_TOOL};
        TagKey<Block>[] inverseLevels = new TagKey[] {BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL};
        handleToolLevels(toolLevels, inverseLevels);
    }
}
