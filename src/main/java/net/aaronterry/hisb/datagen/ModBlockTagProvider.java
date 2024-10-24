package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void addToTag(TagKey<Block> tag, Block block) {
        FabricTagBuilder builder = getOrCreateTagBuilder(tag);
        builder.add(block);
    }

    public void addToTag(TagKey<Block> tag, Block[] blocks) {
        FabricTagBuilder builder = getOrCreateTagBuilder(tag);
        for (Block block : blocks) builder.add(block);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        addToTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.DYREMITE_BLOCK);
        addToTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.DYREMITE_BLOCK);
        addToTag(BlockTags.AXE_MINEABLE, ModBlocks.PURIFIER_TABLE);
    }
}
