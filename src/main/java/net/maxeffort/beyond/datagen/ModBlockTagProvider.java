package net.maxeffort.beyond.datagen;

import net.maxeffort.helper.datagen.HelperBlockTagProvider;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.utility.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends HelperBlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

//    public FabricTagBuilder addToTag(TagKey<Block> tag, Block block) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.add(block); return builder; }
//    public FabricTagBuilder addToTag(TagKey<Block> tag, TagKey<Block> tagToAdd) { FabricTagBuilder builder = getOrCreateTagBuilder(tag); builder.addTag(tagToAdd); return builder; }
//    public FabricTagBuilder addToTag(TagKey<Block> tag, List<Block> blocks) {
//        FabricTagBuilder builder = getOrCreateTagBuilder(tag); for (Block block : blocks) builder.add(block); return builder;
//    }
//    public void handleToolLevels(List<TagKey<Block>> toolTags, List<TagKey<Block>> inverse) {
//        for (int i = 0; i < inverse.size(); i++) {
//            FabricTagBuilder builder = getOrCreateTagBuilder(inverse.get(i));
//            for (int j = i; j < toolTags.size(); j++) builder.addTag(toolTags.get(j));
//        }
//    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        connect(ModBlocks::getFromBlockType,ModBlocks::getFromToolType,ModBlocks::getFromToolMaterial,ModBlocks.all())
                .quickJourney(
                        ModBlockTags.ALL_MOD_BLOCKS, // all blocks tag
                        ModBlockTags.getNeeds(), // needs tag list
                        ModBlockTags.getIncorrect()); // incorrect tag list

//        addToTag(ModBlockTags.ALL_MOD_BLOCKS, ModBlocks.all());
//
//        addToTag(BlockTags.AXE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.AXE));
//        addToTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.PICKAXE));
//        addToTag(BlockTags.SHOVEL_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.SHOVEL));
//        addToTag(BlockTags.HOE_MINEABLE, ModBlocks.getFromToolType(HelperBlocks.SortInputs.HOE));
//
//        addToTag(BlockTags.PLANKS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.WOOD_PLANKS)).add(ModBlocks.DEAD_PLANKS);
//        addToTag(BlockTags.DIRT, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.GRASSLIKE));
//
//        addToTag(BlockTags.SLABS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.SLAB));
//        addToTag(BlockTags.STAIRS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STAIRS));
//        addToTag(BlockTags.DOORS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR));
//        addToTag(BlockTags.TRAPDOORS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR));
//        addToTag(BlockTags.FENCES, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE));
//        addToTag(BlockTags.FENCE_GATES, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE_GATE));
//        addToTag(BlockTags.WALLS, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.WALL));
//
//        addToTag(BlockTags.CLIMBABLE, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE));
//
//        addToTag(BlockTags.BASE_STONE_OVERWORLD, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STONE_MODEL));
//
//        addToTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.STONE));
//        addToTag(BlockTags.NEEDS_IRON_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.IRON));
//        addToTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.DIAMOND));
//        addToTag(ModBlockTags.NEEDS_NETHERITE_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.NETHERITE));
//        addToTag(ModBlockTags.NEEDS_SCULTIUM_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.SCULTIUM));
//        addToTag(ModBlockTags.NEEDS_FORTOLIUM_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.FORTOLIUM));
//        addToTag(ModBlockTags.NEEDS_DEMANDUM_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.DEMANDUM));
//        addToTag(ModBlockTags.NEEDS_UNTILLIUM_TOOL, ModBlocks.getFromToolMaterial(HelperBlocks.SortInputs.UNTILLIUM));
//
//        handleToolLevels(ModBlockTags.getNeeds(), ModBlockTags.getIncorrect());
    }
}
