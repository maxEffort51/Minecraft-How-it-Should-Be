package net.aaronterry.helper.datagen;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.hisb.utility.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class HelperBlockTagProvider extends FabricTagProvider.BlockTagProvider{
    protected Function<String, List<Block>> blockType;
    protected Function<String, List<Block>> toolType;
    protected Function<String, List<Block>> toolMaterial;
    protected List<Block> all;

    public HelperBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) { }

    public BlockTagTraveler connect(Function<String, List<Block>> block, Function<String, List<Block>> tool, Function<String, List<Block>> material, List<Block> allBlocks) {
        blockType = block; toolType = tool; toolMaterial = material; all = allBlocks;
        return new BlockTagTraveler(this);
    }

    public static class BlockTagTraveler {
        private final HelperBlockTagProvider provider;
        protected Function<String, List<Block>> blockType;
        protected Function<String, List<Block>> toolType;
        protected Function<String, List<Block>> toolMaterial;
        protected List<Block> all;

        public BlockTagTraveler(HelperBlockTagProvider provider) {
            this.provider = provider;
            blockType = provider.blockType;
            toolType = provider.toolType;
            toolMaterial = provider.toolMaterial;
            all = provider.all;
        }

        public BlockTagTraveler defaultJourney(TagKey<Block> all, List<TagKey<Block>> needs, List<TagKey<Block>> incorrect) {
            return allBlocksTag(all).mineable().planks()
                    .grass().nonBlocks().vines().stone()
                    .materials().miningLevels(needs, incorrect);
        }

        public void quickJourney(TagKey<Block> all, List<TagKey<Block>> needs, List<TagKey<Block>> incorrect) {
            allBlocksTag(all).mineable().planks()
                    .grass().nonBlocks().vines().stone()
                    .materials().miningLevels(needs, incorrect).endJourney();
        }

        public BlockTagTraveler model(TagKey<Block> tag, String search) { attach(tag, blockType.apply(search)); return this; }
        public BlockTagTraveler tool(TagKey<Block> tag, String search) { attach(tag, toolType.apply(search)); return this; }
        public BlockTagTraveler material(TagKey<Block> tag, String search) { attach(tag, toolMaterial.apply(search)); return this; }

        private void attach(TagKey<Block> tag, List<Block> blocks) {
            FabricTagBuilder builder = provider.getOrCreateTagBuilder(tag); for (Block block : blocks) builder.add(block);
        }
        private FabricTagBuilder attachAnd(TagKey<Block> tag, List<Block> blocks) {
            FabricTagBuilder builder = provider.getOrCreateTagBuilder(tag); for (Block block : blocks) builder.add(block);
            return builder;
        }

        public BlockTagTraveler allBlocksTag(TagKey<Block> tag) { attach(tag, all); return this; }
        public BlockTagTraveler mineable() {
            attach(BlockTags.AXE_MINEABLE, toolType.apply(HelperBlocks.SortInputs.AXE));
            attach(BlockTags.PICKAXE_MINEABLE, toolType.apply(HelperBlocks.SortInputs.PICKAXE));
            attach(BlockTags.SHOVEL_MINEABLE, toolType.apply(HelperBlocks.SortInputs.SHOVEL));
            attach(BlockTags.HOE_MINEABLE, toolType.apply(HelperBlocks.SortInputs.HOE)); return this;
        }
        public BlockTagTraveler planks(Block block) { attachAnd(BlockTags.PLANKS, provider.blockType.apply(HelperBlocks.SortInputs.WOOD_PLANKS)).add(block); return this; }
        public BlockTagTraveler planks(String search) { attach(BlockTags.PLANKS, provider.blockType.apply(search)); return this; }
        public BlockTagTraveler planks() { return planks(HelperBlocks.SortInputs.WOOD_PLANKS); }
        public BlockTagTraveler grass(TagKey<Block> tag) { attach(tag, provider.blockType.apply(HelperBlocks.SortInputs.GRASSLIKE)); return this; }
        public BlockTagTraveler grass() { return grass(BlockTags.DIRT); }
        public BlockTagTraveler nonBlocks() {
            attach(BlockTags.SLABS, blockType.apply(HelperBlocks.SortInputs.SLAB));
            attach(BlockTags.STAIRS, blockType.apply(HelperBlocks.SortInputs.STAIRS));
            attach(BlockTags.DOORS, blockType.apply(HelperBlocks.SortInputs.DOOR));
            attach(BlockTags.TRAPDOORS, blockType.apply(HelperBlocks.SortInputs.TRAPDOOR));
            attach(BlockTags.FENCES, blockType.apply(HelperBlocks.SortInputs.FENCE));
            attach(BlockTags.FENCE_GATES, blockType.apply(HelperBlocks.SortInputs.FENCE_GATE));
            attach(BlockTags.WALLS, blockType.apply(HelperBlocks.SortInputs.WALL)); return this;
        }
        public BlockTagTraveler vines() { attach(BlockTags.CLIMBABLE, blockType.apply(HelperBlocks.SortInputs.VINE)); return this; }
        public BlockTagTraveler stone(TagKey<Block> tag) { attach(tag, blockType.apply(HelperBlocks.SortInputs.STONE_MODEL)); return this; }
        public BlockTagTraveler stone() { return stone(BlockTags.BASE_STONE_OVERWORLD); }
        public BlockTagTraveler materials() {
            attach(BlockTags.NEEDS_STONE_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.STONE));
            attach(BlockTags.NEEDS_IRON_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.IRON));
            attach(BlockTags.NEEDS_DIAMOND_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.DIAMOND));
            attach(ModBlockTags.NEEDS_NETHERITE_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.NETHERITE));
            attach(ModBlockTags.NEEDS_SCULTIUM_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.SCULTIUM));
            attach(ModBlockTags.NEEDS_FORTOLIUM_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.FORTOLIUM));
            attach(ModBlockTags.NEEDS_DEMANDUM_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.DEMANDUM));
            attach(ModBlockTags.NEEDS_UNTILLIUM_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.UNTILLIUM));
            attach(ModBlockTags.NEEDS_ARMITE_TOOL, toolMaterial.apply(HelperBlocks.SortInputs.ARMITE));
            return this;
        }

        public BlockTagTraveler miningLevels(List<TagKey<Block>> needs, List<TagKey<Block>> incorrect) {
            for (int i = 0; i < incorrect.size(); i++) {
                FabricTagBuilder builder = provider.getOrCreateTagBuilder(incorrect.get(i));
                for (int j = i; j < needs.size(); j++) builder.addTag(needs.get(j));
            } return this;
        }
        public void endJourney() { }
    }
}
