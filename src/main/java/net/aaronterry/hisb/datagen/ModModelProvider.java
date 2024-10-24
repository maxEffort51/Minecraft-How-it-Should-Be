package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlocks(BlockStateModelGenerator generator, Block[] blocks) {
        for (Block block : blocks) generator.registerSimpleCubeAll(block);
    }

    public void generateItems(ItemModelGenerator generator, Item[] items) {
        for (Item item : items) generator.register(item, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        generateBlocks(blockStateModelGenerator, new Block[]{ModBlocks.DYREMITE_BLOCK,ModBlocks.PURIFIER_TABLE});
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Item[] overworld = new Item[]{ModItems.PURIFIED_IRON, ModItems.PURIFIED_GOLD,ModItems.PURIFIED_LAPIS,ModItems.PURIFIED_REDSTONE,
                ModItems.PURIFIED_EMERALD,ModItems.PURIFIED_DIAMOND,ModItems.DYREMITE_CHUNK};
        generateItems(itemModelGenerator, overworld);
    }
}
