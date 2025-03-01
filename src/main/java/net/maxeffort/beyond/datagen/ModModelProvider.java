package net.maxeffort.beyond.datagen;

import net.maxeffort.helper.block.HelperBlocks;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.item.armor.ModArmorItems;
import net.maxeffort.beyond.item.ModItems;
import net.maxeffort.beyond.item.tool.ModToolItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
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

    public void generateLogs(BlockStateModelGenerator generator, List<Block> blocks) {
        blocks.forEach(log -> generator.registerLog(log).log(log));
    }

    public void generateArmor(ItemModelGenerator generator, Item[] items) { for (Item item : items) generator.registerArmor((ArmorItem) item); }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        // documentation - NOTE: LEAVES AND VINES NEED A CUSTOM FILE, CAN'T BE GENERATED THROUGH DATAGEN; HERE'S SOME PRESETS ...

        // NORMAL MODEL BLOCKS
        generateBlocks(generator, ModBlocks.getNotSorted());
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.NORMAL_TYPE,false));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TABLE,false));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.STONE_MODEL,false));
        generateBlocks(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.WOOD_PLANKS,false));

        // DOOR AND TRAPDOOR BLOCKS
        ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR).forEach(generator::registerDoor);
        ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR).forEach(generator::registerTrapdoor);

        // SLAB, STAIRS, WALLS, FENCES, & FENCE GATE BLOCKS
        List<HelperBlocks.Sorted> slabs = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.SLAB);
        List<HelperBlocks.Sorted> stairs = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.STAIRS);
        List<HelperBlocks.Sorted> walls = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.WALL);
        List<HelperBlocks.Sorted> fences = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.FENCE);
        List<HelperBlocks.Sorted> fence_gates = ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.FENCE_GATE);
        List<HelperBlocks.Sorted> parents = ModBlocks.getParents();
        parents.forEach(parent -> {
            BlockStateModelGenerator.BlockTexturePool texture = generator.registerCubeAllModelTexturePool(parent.getBlock());
            slabs.forEach(slab -> { if (slab.hasParent() && slab.getParent().equals(parent.getBlock())) texture.slab(slab.getBlock()); });
            stairs.forEach(stair -> { if (stair.hasParent() && stair.getParent().equals(parent.getBlock())) texture.stairs(stair.getBlock()); });
            walls.forEach(wall -> { if (wall.hasParent() && wall.getParent().equals(parent.getBlock())) texture.wall(wall.getBlock()); });
            fences.forEach(fence -> { if (fence.hasParent() && fence.getParent().equals(parent.getBlock())) texture.fence(fence.getBlock()); });
            fence_gates.forEach(fence -> { if (fence.hasParent() && fence.getParent().equals(parent.getBlock())) texture.fenceGate(fence.getBlock()); });
        });

        // TORCHES & WALL TORCHES
        ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.WALL_TORCH).forEach(wallTorch -> {
            if (wallTorch.hasParent()) generator.registerTorch(wallTorch.getParent(),wallTorch.getBlock());
        });

        // GRASSLIKE BLOCKS
//        ModBlocks.sortWithBlockType(HelperBlocks.SortInputs.GRASSLIKE).forEach(grasslike -> generator.modelCollector.accept(
//                grasslike.id(),
//                () -> Models.CUBE_BOTTOM_TOP.createJson(grasslike.id(), new KeyGroup<TextureKey,Identifier>(TextureKey.TOP,TextureKey.SIDE,TextureKey.BOTTOM).out(grasslike.id("top"),grasslike.id("side"),grasslike.id("bottom")).asMap())
//        ));

        // PILLAR BLOCKS AND LOGS
        generateLogs(generator, ModBlocks.getFromBlockType(HelperBlocks.SortInputs.PILLAR));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generateItems(generator, ModItems.all());
        generateItems(generator, ModToolItems.all(), Models.HANDHELD);
        generateArmor(generator, ModArmorItems.allArmor());
        generator.register(ModArmorItems.purviumElytra(), Models.GENERATED);
    }
}