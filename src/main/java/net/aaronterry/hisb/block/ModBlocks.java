package net.aaronterry.hisb.block;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.custom.PurifierTableBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks {
    public static final Block PURIFIER_TABLE = registerBlock("purifier_table",
            new PurifierTableBlock(AbstractBlock.Settings.create().instrument(NoteBlockInstrument.BASS).strength(3.5F).sounds(BlockSoundGroup.METAL)));
    public static final Block DYREMITE_BLOCK = registerBlock("dyremite_block",
            new Block(AbstractBlock.Settings.create().strength(60f,500f).requiresTool()), Rarity.UNCOMMON);
    public static final Block DEBRITINUM_BLOCK = registerBlock("debritinum_block",
            new Block(AbstractBlock.Settings.create().strength(70f,1500f).requiresTool()), Rarity.UNCOMMON);
    public static final Block PURVIUM_ORE = registerBlock("purvium_ore",
            new Block(AbstractBlock.Settings.create().strength(15f).requiresTool()));
    public static final Block BURPLE_BLOCK = registerBlock("burple_block",
            new Block(AbstractBlock.Settings.create().strength(6f).requiresTool()));
    public static final Block BURPLE_PURVIUM_ORE = registerBlock("burple_purvium_ore",
            new Block(AbstractBlock.Settings.create().strength(9f).requiresTool()));
    public static final Block CONDENSED_PURPUR_BLOCK = registerBlock("condensed_purpur_block",
            new Block(AbstractBlock.Settings.create().strength(8f).requiresTool()));
    public static final Block PURPUR_PURVIUM_ORE = registerBlock("purpur_purvium_ore",
            new Block(AbstractBlock.Settings.create().strength(14f).requiresTool()));
    // DEMANDI
    public static final Block HARDENED_SCULK = registerBlock("hardened_sculk",
            new Block(AbstractBlock.Settings.create().strength(100f, 10f).requiresTool()));
    public static final Block DEEP_STONE = registerBlock("deep_stone",
            new Block(AbstractBlock.Settings.create().strength(24f).requiresTool()));
    public static final Block DEEP = registerBlock("deep",
            new Block(AbstractBlock.Settings.create().strength(20f).requiresTool()));
    public static final Block DARK = registerBlock("dark",
            new Block(AbstractBlock.Settings.create().strength(9f).requiresTool()));
    public static final Block WASHED_DARK = registerBlock("washed_dark",
            new Block(AbstractBlock.Settings.create().strength(10f).requiresTool()));
    public static final Block DEEP_SCULK = registerBlock("deep_sculk",
            new Block(AbstractBlock.Settings.create().strength(25f).requiresTool()));
    public static final Block WASHED_SCULK = registerBlock("washed_sculk",
            new Block(AbstractBlock.Settings.create().strength(26f).requiresTool()));
    public static final Block DEPNETUM_BLOCK = registerBlock("depnetum_block",
            new Block(AbstractBlock.Settings.create().strength(80f, 1000f).requiresTool()));
    public static final Block SCULTIUM_BLOCK = registerBlock("scultium_block",
            new Block(AbstractBlock.Settings.create().strength(75f, 600f).requiresTool()));
    public static final Block DEAD_PLANKS = registerBlock("dead_planks",
            new Block(AbstractBlock.Settings.create().strength(10f).requiresTool()));
    public static final Block IMPERVIUM_BLOCK = registerBlock("impervium_block",
            new Block(AbstractBlock.Settings.create().strength(200f).requiresTool()));
    public static final Block DEAD_SCULK = registerBlock("dead_sculk",
            new Block(AbstractBlock.Settings.create().strength(15f).requiresTool()));
    public static final Block STIFF_STONE = registerBlock("stiff_stone",
            new Block(AbstractBlock.Settings.create().strength(30f).requiresTool()));
    public static final Block STIFF_SOIL = registerBlock("stiff_soil",
            new Block(AbstractBlock.Settings.create().strength(20f).requiresTool()));
    public static final Block CORRUPTED_MUD = registerBlock("corrupted_mud",
            new Block(AbstractBlock.Settings.create().strength(14f).requiresTool()));
    public static final Block SCULTIUM_ORE = registerBlock("scultium_ore",
            new Block(AbstractBlock.Settings.create().strength(32f).requiresTool()));
    public static final Block DEPNETUM_ORE = registerBlock("depnetum_ore",
            new Block(AbstractBlock.Settings.create().strength(40f).requiresTool()));
    public static final Block DARK_DEPNETUM_ORE = registerBlock("dark_depnetum_ore",
            new Block(AbstractBlock.Settings.create().strength(35f).requiresTool()));

    // Useful.subtract(ALL,ORES);
    public static final Block[] DROP_SELF = new Block[] {PURIFIER_TABLE,DYREMITE_BLOCK,DEBRITINUM_BLOCK,CONDENSED_PURPUR_BLOCK,BURPLE_BLOCK,
            HARDENED_SCULK,DEEP_STONE,DEEP,DARK,WASHED_DARK,DEEP_SCULK,WASHED_SCULK,DEPNETUM_BLOCK,SCULTIUM_BLOCK,DEAD_PLANKS,IMPERVIUM_BLOCK,DEAD_SCULK,STIFF_STONE,STIFF_SOIL,CORRUPTED_MUD};
    public static final Block[] ORES = new Block[] {PURVIUM_ORE,BURPLE_PURVIUM_ORE,PURPUR_PURVIUM_ORE,SCULTIUM_ORE,DEPNETUM_ORE,DARK_DEPNETUM_ORE};

    public static final Block[] AXE = new Block[] {DEAD_PLANKS};
    public static final Block[] PICKAXE = new Block[] {DYREMITE_BLOCK, DEBRITINUM_BLOCK,PURVIUM_ORE,PURIFIER_TABLE,CONDENSED_PURPUR_BLOCK,PURPUR_PURVIUM_ORE,BURPLE_PURVIUM_ORE,
            HARDENED_SCULK,DEEP_STONE,DEPNETUM_BLOCK,SCULTIUM_BLOCK,IMPERVIUM_BLOCK,DEAD_SCULK,STIFF_STONE,SCULTIUM_ORE,DEPNETUM_ORE,DARK_DEPNETUM_ORE};
    public static final Block[] SHOVEL = new Block[] {BURPLE_BLOCK,STIFF_SOIL,CORRUPTED_MUD,WASHED_DARK,WASHED_SCULK};
    public static final Block[] HOE = new Block[] {DEEP,DARK,DEEP_SCULK};
    public static final Block[] ALL = new Block[] {DYREMITE_BLOCK,DEBRITINUM_BLOCK,PURVIUM_ORE,PURIFIER_TABLE,BURPLE_BLOCK,BURPLE_PURVIUM_ORE,CONDENSED_PURPUR_BLOCK,PURPUR_PURVIUM_ORE,
            HARDENED_SCULK,DEEP_STONE,DEEP,DARK,WASHED_DARK,DEEP_SCULK,WASHED_SCULK,DEPNETUM_BLOCK,SCULTIUM_BLOCK,DEAD_PLANKS,IMPERVIUM_BLOCK,DEAD_SCULK,STIFF_STONE,STIFF_SOIL,CORRUPTED_MUD,SCULTIUM_ORE,DEPNETUM_ORE,DARK_DEPNETUM_ORE};

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block, Rarity.COMMON);
        return Registry.register(Registries.BLOCK, Identifier.of(HisbMod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, Rarity rarity) {
        registerBlockItem(name, block, rarity);
        return Registry.register(Registries.BLOCK, Identifier.of(HisbMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, Rarity rarity) {
        Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings().rarity(rarity)));
    }

    public static void addAll(FabricItemGroupEntries entries, Block[] blocks) {
        for (Block block : blocks) {
            entries.add(block);
        }
    }

    public static void registerModBlocks() {
        HisbMod.debug("Registering Mod Blocks for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> addAll(entries, ALL));
    }
}
