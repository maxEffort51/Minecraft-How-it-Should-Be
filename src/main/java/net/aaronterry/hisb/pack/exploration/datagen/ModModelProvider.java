package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.pack.exploration.item.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.aaronterry.hisb.pack.exploration.item.ModToolItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlocks(BlockStateModelGenerator generator, List<Block> blocks) { blocks.forEach(generator::registerSimpleCubeAll); }

    public void generateItems(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.register(item, Models.GENERATED); }
    public void generateItems(ItemModelGenerator generator, Item[] items, Model model) { for (Item item : items) generator.register(item, model); }

    public void generateVines(BlockStateModelGenerator generator, List<Block> blocks) { blocks.forEach(vine -> generator.registerParented(Blocks.VINE,vine)); }

    public void generateLogs(BlockStateModelGenerator generator, List<Block> blocks) {
        blocks.forEach(log -> generator.registerLog(log).log(log));
    }

    public void generateArmor(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.registerArmor((ArmorItem) item); }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        // NORMAL MODEL BLOCKS
        generateBlocks(generator, ModBlocks.getNotSorted());
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.NORMAL_TYPE));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TABLE));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STONE_BLOCK_TYPE));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.WOOD_PLANKS));

        // SLAB, STAIRS, DOOR MODEL BLOCKS
        List<HelperBlocks.Sorted> slabs = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.SLAB);
        List<HelperBlocks.Sorted> stairs = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.STAIRS);
        List<HelperBlocks.Sorted> doors = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.DOOR);
        List<HelperBlocks.Sorted> trapdoors = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.TRAPDOOR);
        List<HelperBlocks.Sorted> parents = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.PARENT_BLOCK);
        parents.forEach(parent -> {
            BlockStateModelGenerator.BlockTexturePool texture = generator.registerCubeAllModelTexturePool(parent.getBlock());
            slabs.forEach(slab -> { if (slab.hasParent() && slab.getParent().equals(parent.getBlock())) texture.slab(slab.getBlock()); });
            stairs.forEach(stair -> { if (stair.hasParent() && stair.getParent().equals(parent.getBlock())) texture.stairs(stair.getBlock()); });
            doors.forEach(door -> { if (door.hasParent() && door.getParent().equals(parent.getBlock())) texture.stairs(door.getBlock()); });
            trapdoors.forEach(door -> { if (door.hasParent() && door.getParent().equals(parent.getBlock())) texture.stairs(door.getBlock()); });
        });

        // PILLAR BLOCKS AND LOGS
        generateLogs(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.PILLAR));

        // VINES
        generateVines(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generateItems(generator, ModItems.ALL);
        generateItems(generator, ModToolItems.ALL, Models.HANDHELD);
        generateArmor(generator, ModArmorItems.ALL);
    }
}