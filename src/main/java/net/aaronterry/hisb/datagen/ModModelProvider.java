package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.item.ModArmorItems;
import net.aaronterry.hisb.item.ModItems;
import net.aaronterry.hisb.item.ModToolItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlocks(BlockStateModelGenerator generator, Block[] blocks) {
        for (Block block : blocks) generator.registerSimpleCubeAll(block);
    }

    public void generateItems(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.register(item, Models.GENERATED); }
    public void generateItems(ItemModelGenerator generator, Item[] items, Model model) { for (Item item : items) generator.register(item, model); }

    public void generateArmor(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.registerArmor((ArmorItem) item); }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        generateBlocks(blockStateModelGenerator, ModBlocks.ALL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generateItems(generator, ModItems.ALL);
        generateItems(generator, ModToolItems.ALL, Models.HANDHELD);
        generateArmor(generator, ModArmorItems.ALL);
    }
}
