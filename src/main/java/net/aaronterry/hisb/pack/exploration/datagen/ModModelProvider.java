package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.pack.exploration.item.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.aaronterry.hisb.pack.exploration.item.ModToolItems;
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

    public void generateNonBlocks(BlockStateModelGenerator generator, Block main, Block slab, Block stairs) {
        generator.registerCubeAllModelTexturePool(main)
                .slab(slab).stairs(stairs);
    }

    public void generateItems(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.register(item, Models.GENERATED); }
    public void generateItems(ItemModelGenerator generator, Item[] items, Model model) { for (Item item : items) generator.register(item, model); }

    public void generateArmor(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.registerArmor((ArmorItem) item); }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generateBlocks(generator, ModBlocks.NORMAL);

        for (int i = 0; i < ModBlocks.NON_BLOCKS.length; i+=3) generateNonBlocks(generator, ModBlocks.NON_BLOCKS[i],ModBlocks.NON_BLOCKS[i+1],ModBlocks.NON_BLOCKS[i+2]);

        generator.registerLog(ModBlocks.DEAD_LOG)
                .log(ModBlocks.DEAD_LOG);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generateItems(generator, ModItems.ALL);
        generateItems(generator, ModToolItems.ALL, Models.HANDHELD);
        generateArmor(generator, ModArmorItems.ALL);
    }
}
