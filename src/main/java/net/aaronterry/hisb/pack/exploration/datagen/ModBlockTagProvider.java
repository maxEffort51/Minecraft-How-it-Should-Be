package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public FabricTagBuilder addToTag(TagKey<Block> tag, Block block) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.add(block); return builder; }
    public FabricTagBuilder addToTag(TagKey<Block> tag, TagKey<Block> tagToAdd) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.addTag(tagToAdd); return builder; }
    public FabricTagBuilder addToTag(TagKey<Block> tag, List<Block> blocks) {
        FabricTagBuilder builder = getOrCreateTagBuilder(tag); for (Block block : blocks) builder.add(block); return builder; }

    public void handleToolLevels(List<TagKey<Block>> toolTags, List<TagKey<Block>> inverse) {
        for (int i = 0; i < inverse.size(); i++) {
            FabricTagBuilder builder = getOrCreateTagBuilder(inverse.get(i));
            for (int j = i; j < toolTags.size(); j++) builder.addTag(toolTags.get(j));
        }
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        addToTag(ModTags.Blocks.ALL_MOD_BLOCKS, ModBlocks.all());

        addToTag(BlockTags.AXE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.AXE));
        addToTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.PICKAXE));
        addToTag(BlockTags.SHOVEL_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.SHOVEL));
        addToTag(BlockTags.HOE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.HOE));

        addToTag(BlockTags.PLANKS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.WOOD_PLANKS)).add(ModBlocks.DEAD_PLANKS);
        addToTag(BlockTags.DIRT, ModBlocks.getFromToolType(HelperBlocks.SortInputs.GRASSLIKE));

        addToTag(BlockTags.SLABS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.SLAB));
        addToTag(BlockTags.STAIRS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STAIRS));
        addToTag(BlockTags.DOORS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR));
        addToTag(BlockTags.TRAPDOORS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR));
        addToTag(BlockTags.FENCES, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE));
        addToTag(BlockTags.FENCE_GATES, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE_GATE));

        addToTag(BlockTags.CLIMBABLE, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE));

        addToTag(BlockTags.BASE_STONE_OVERWORLD, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STONE_BLOCK_TYPE));

        addToTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.STONE));
        addToTag(BlockTags.NEEDS_IRON_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.IRON));
        addToTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.DIAMOND));
        addToTag(ModTags.Blocks.NEEDS_NETHERITE_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.NETHERITE));
        addToTag(ModTags.Blocks.NEEDS_SCULTIUM_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.SCULTIUM));

        List<TagKey<Block>> toolLevels = List.of(ModTags.Blocks.NEEDS_NETHERITE_TOOL,ModTags.Blocks.NEEDS_SCULTIUM_TOOL/*,ModTags.Blocks.NEEDS_DEMANDUM_TOOL,ModTags.Blocks.NEEDS_UNTILLIUM_TOOL*/);
        List<TagKey<Block>> inverseLevels = List.of(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL/*,ModTags.Blocks.INCORRECT_FOR_DEMANDUM_TOOL,ModTags.Blocks.INCORRECT_FOR_UNTILLIUM_TOOL*/);
        handleToolLevels(toolLevels, inverseLevels);
    }
}
