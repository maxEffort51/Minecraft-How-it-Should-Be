package net.aaronterry.hisb.block;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.custom.PurifierTableBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block DYREMITE_BLOCK = registerBlock("dyremite_block",
            new Block(AbstractBlock.Settings.create().strength(6f)
                    .requiresTool()));

    public static final Block PURIFIER_TABLE = registerBlock("purifier_table",
            new PurifierTableBlock(AbstractBlock.Settings.create().strength(3f)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(HisbMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        HisbMod.LOGGER.info("Registering Mod Blocks for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.DYREMITE_BLOCK);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(ModBlocks.PURIFIER_TABLE);
        });
    }
}
