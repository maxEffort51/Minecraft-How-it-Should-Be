package net.aaronterry.hisb.block;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.custom.PurifierTableBlock;
import net.aaronterry.hisb.item.ModItems;
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
    // CUSTOM BLOCKS
    public static final Block PURIFIER_TABLE = registerBlock("purifier_table", new PurifierTableBlock(Useful.BlockSettings.sounds(3.5f, false, NoteBlockInstrument.BASS, BlockSoundGroup.METAL)));

    // VANILLA DIMENSIONS
    public static final Block PRISMALITE_ORE = registerBlock("prismalite_ore", Useful.BlockSettings.auto(6f));
    public static final Block DYREMITE_BLOCK = registerBlock("dyremite_block", Useful.BlockSettings.auto(60f, 500f), Rarity.UNCOMMON);
    public static final Block DEBRITINUM_BLOCK = registerBlock("debritinum_block", Useful.BlockSettings.auto(70f, 1500f), Rarity.UNCOMMON);
    public static final Block PURVIUM_ORE = registerBlock("purvium_ore", Useful.BlockSettings.auto(15f));
    public static final Block BURPLE_BLOCK = registerBlock("burple_block", Useful.BlockSettings.auto(6f));
    public static final Block BURPLE_PURVIUM_ORE = registerBlock("burple_purvium_ore", Useful.BlockSettings.auto(9f));
    public static final Block CONDENSED_PURPUR_BLOCK = registerBlock("condensed_purpur_block", Useful.BlockSettings.auto(8f));
    public static final Block PURPUR_PURVIUM_ORE = registerBlock("purpur_purvium_ore", Useful.BlockSettings.auto(14f));
    // DEMANDI
    public static final Block HARDENED_SCULK = registerBlock("hardened_sculk", Useful.BlockSettings.auto(100f, 10f));
    public static final Block DEEP_STONE = registerBlock("deep_stone", Useful.BlockSettings.auto(24f));
    public static final Block DEEP = registerBlock("deep", Useful.BlockSettings.auto(20f));
    public static final Block DARK = registerBlock("dark", Useful.BlockSettings.auto(9f));
    public static final Block WASHED_DARK = registerBlock("washed_dark", Useful.BlockSettings.auto(10f));
    public static final Block DEEP_SCULK = registerBlock("deep_sculk", Useful.BlockSettings.auto(25f));
    public static final Block WASHED_SCULK = registerBlock("washed_sculk", Useful.BlockSettings.auto(26f));
    public static final Block DEPNETUM_BLOCK = registerBlock("depnetum_block", Useful.BlockSettings.auto(80f, 1000f));
    public static final Block SCULTIUM_BLOCK = registerBlock("scultium_block", Useful.BlockSettings.auto(75f, 600f));
    public static final Block DEAD_PLANKS = registerBlock("dead_planks", Useful.BlockSettings.auto(10f));
    public static final Block IMPERVIUM_BLOCK = registerBlock("impervium_block", Useful.BlockSettings.auto(200f));
    public static final Block DEAD_SCULK = registerBlock("dead_sculk", Useful.BlockSettings.auto(15f));
    public static final Block STIFF_STONE = registerBlock("stiff_stone", Useful.BlockSettings.auto(30f));
    public static final Block STIFF_SOIL = registerBlock("stiff_soil", Useful.BlockSettings.auto(20f));
    public static final Block CORRUPTED_MUD = registerBlock("corrupted_mud", Useful.BlockSettings.auto(14f));
    public static final Block SCULTIUM_ORE = registerBlock("scultium_ore", Useful.BlockSettings.auto(32f));
    public static final Block DEPNETUM_ORE = registerBlock("depnetum_ore", Useful.BlockSettings.auto(40f));
    public static final Block DARK_DEPNETUM_ORE = registerBlock("dark_depnetum_ore", Useful.BlockSettings.auto(35f));

    public static final Block[] DROP_SELF = new Block[] {PURIFIER_TABLE,DYREMITE_BLOCK,DEBRITINUM_BLOCK,CONDENSED_PURPUR_BLOCK,BURPLE_BLOCK,
            HARDENED_SCULK,DEEP_STONE,DEEP,DARK,WASHED_DARK,DEEP_SCULK,WASHED_SCULK,DEPNETUM_BLOCK,SCULTIUM_BLOCK,DEAD_PLANKS,IMPERVIUM_BLOCK,DEAD_SCULK,STIFF_STONE,STIFF_SOIL,CORRUPTED_MUD};
    public static final Block[] OTHER = new Block[] {};

    public static final Block[] ALL = Useful.combine(DROP_SELF, Ores.ALL, OTHER);

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

    public static class Ores {
        public static Block[] BASIC = new Block[] {PURVIUM_ORE,PURPUR_PURVIUM_ORE,SCULTIUM_ORE,DEPNETUM_ORE,PRISMALITE_ORE};
        public static Block[] SPECIFIC = new Block[] {BURPLE_PURVIUM_ORE,DARK_DEPNETUM_ORE};
        public static Block[] ALL = Useful.combine(BASIC, SPECIFIC);

        public static Item[] BASIC_DROPS = new Item[] {ModItems.PURVIUM_CHUNK,ModItems.PURVIUM_CHUNK,ModItems.SCULTIUM_BONES,ModItems.DEPNETUM_CLUMP,ModItems.PRISMALITE_SHARD};

        public static Item[] SPECIFIC_DROPS = new Item[] {ModItems.PURVIUM_CHUNK,ModItems.DEPNETUM_CLUMP};
        public static int[] SPECIFIC_MIN = new int[] {1, 1};
        public static int[] SPECIFIC_MAX = new int[] {2, 2};
    }

    public static class Tools {
        public static final Block[] AXE = new Block[] {DEAD_PLANKS};
        private static final Block[] PICK = new Block[] {DYREMITE_BLOCK, DEBRITINUM_BLOCK,PURIFIER_TABLE,
                CONDENSED_PURPUR_BLOCK,HARDENED_SCULK,DEEP_STONE,DEPNETUM_BLOCK,SCULTIUM_BLOCK,IMPERVIUM_BLOCK,DEAD_SCULK,STIFF_STONE};
        public static final Block[] PICKAXE = Useful.combine(Ores.ALL, PICK);
        public static final Block[] SHOVEL = new Block[] {BURPLE_BLOCK,STIFF_SOIL,CORRUPTED_MUD,WASHED_DARK,WASHED_SCULK};
        public static final Block[] HOE = new Block[] {DEEP,DARK,DEEP_SCULK};

        public static final Block[] NEEDS_STONE = new Block[] {DEAD_PLANKS,STIFF_SOIL,CORRUPTED_MUD};
        public static final Block[] NEEDS_IRON = new Block[] {CONDENSED_PURPUR_BLOCK,PURVIUM_ORE,PURPUR_PURVIUM_ORE,BURPLE_BLOCK,BURPLE_PURVIUM_ORE,DEEP_STONE,WASHED_DARK,WASHED_SCULK,DEEP,DARK,DEEP_SCULK,PRISMALITE_ORE};
        public static final Block[] NEEDS_DIAMOND = new Block[] {DYREMITE_BLOCK,DEEP_STONE,STIFF_STONE,SCULTIUM_ORE,DEPNETUM_ORE,DARK_DEPNETUM_ORE};
        public static final Block[] NEEDS_NETHERITE = new Block[] {DEBRITINUM_BLOCK,HARDENED_SCULK,IMPERVIUM_BLOCK,DEPNETUM_BLOCK,SCULTIUM_BLOCK};
        public static final Block[] NEEDS_SCULTIUM = new Block[] {};
    }
}
