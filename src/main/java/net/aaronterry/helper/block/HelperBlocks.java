package net.aaronterry.helper.block;

import net.aaronterry.helper.Useful;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

/* ADDITIONS
 * ItemGroups organization
 * Functionality organization
 * Biome organization ?
 */

public class HelperBlocks {
    protected static Identifier id(String mod, String name) { return Identifier.of(mod, name); }
    private static final List<Block> notSorted = new ArrayList<>();
    private static final List<Sorted> sorted = new ArrayList<>();

    /* HELPER FUNCTIONS */
    protected static void registerAsItem(Identifier identifier, Block block, Item.Settings settings, boolean sorted) {
        if (!sorted) notSorted.add(block);
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, settings));
    }
    protected static void addToItemGroup(RegistryKey<ItemGroup> group, List<Block> blocks) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> blocks.forEach(entries::add));
    }
    protected static Block registerBlock(Identifier identifier, Block block) { return Registry.register(Registries.BLOCK, identifier, block); }

    /* REGISTER A BLOCK */
    protected static Block register(Identifier identifier, Block block) { return register(identifier, block, new Item.Settings()); }
    protected static Block register(Identifier identifier, Block block, Rarity rarity) { return register(identifier, block, new Item.Settings().rarity(rarity)); }
    protected static Block register(Identifier identifier, Block block, Item.Settings itemSettings) {
        registerAsItem(identifier, block, itemSettings, false); return Registry.register(Registries.BLOCK, identifier, block);
    }
    protected static Block registerSort(Identifier identifier, Block block, Item.Settings itemSettings) {
        registerAsItem(identifier, block, itemSettings, true); return Registry.register(Registries.BLOCK, identifier, block);
    }
    /* REGISTER AND SORT A BLOCK */
    protected static Sorting.Dimension sortBlock(Identifier id, Block block) {  return sortBlock(id, block, new Item.Settings()); }
    protected static Sorting.Dimension sortBlock(Identifier id, Block block, Rarity rarity) { return sortBlock(id, block, new Item.Settings().rarity(rarity)); }
    protected static Sorting.Dimension sortBlock(Identifier id, Block block, Item.Settings itemSettings) { return new Sorting.Dimension(new Sorting(registerSort(id, block, itemSettings))); }
    protected static SortingPreset sortBlock(Identifier id, Block block, SortingPreset preset) {  return sortBlock(id, block, new Item.Settings(), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Rarity rarity, SortingPreset preset) { return sortBlock(id, block, new Item.Settings().rarity(rarity), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Item.Settings itemSettings, SortingPreset preset) { preset.copy().create(registerSort(id, block, itemSettings)); return preset; }

    /* GET A BLOCK BASED ON SORTING */
    private static List<Block> get(String sortBy, String requirement) {
        List<Block> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.matches(sortBy, requirement)) result.add(sortedBlock.getBlock()); });
        return result;
    }
    public static List<Block> getFromDimension(String req) { return get("dim",req); }
    public static List<Block> getFromBlockType(String req) { return get("blockType",req); }
    public static List<Block> getFromDropType(String req) { return get("dropType",req); }
    public static List<Block> getFromToolType(String req) { return get("toolType",req); }
    public static List<Block> getFromToolMaterial(String req) { return get("toolMaterial",req); }
    public static List<OreData> getFromOreType(String req) {
        List<OreData> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.matches("oreType", req)) result.add(sortedBlock.getOreData()); });
        return result;
    }
    public static List<Block> all() {
        List<Block> sortedBlocks = new ArrayList<>(sorted.stream().map(Sorted::getBlock).toList());
        sortedBlocks.addAll(notSorted);
        return sortedBlocks;
    }
    public static Sorted getFromBlock(Block block) {
        for (Sorted sortedItem : sorted) { if (sortedItem.getBlock().equals(block)) { return sortedItem; } }
        throw new IllegalArgumentException("HelperBlocks.getFromBlock - illegal block argument " + block.toString());
    }

    /* SORTING PRESET */
    public static class SortingPreset {
        private final Sorting sortData;
        private int lastOff = -1;

        public SortingPreset() { sortData = new Sorting(Useful.BlockSettings.auto(0)); }
        private SortingPreset(SortingPreset preset) { sortData = preset.sortData; lastOff = preset.lastOff; }

        public void create(Block block) { sortData.block = block; }

        public SortingPreset copy() { return new SortingPreset(this); }

        private SortingPreset dimension(String dim) { sortData.dim = dim; if (lastOff < 0) { lastOff = 0; } return this; }
        public SortingPreset inOverworld() { return dimension(SortInputs.OVERWORLD); }
        public SortingPreset inNether() { return dimension(SortInputs.NETHER); }
        public SortingPreset inEnd() { return dimension(SortInputs.END); }
        public SortingPreset inDemandi() { return dimension(SortInputs.DEMANDI); }
        public SortingPreset inNexus() { return dimension(SortInputs.NEXUS); }
        public SortingPreset inCustom() { return dimension(SortInputs.CUSTOM); }
        public SortingPreset noDimension() { return dimension(SortInputs.NONE); }

        private SortingPreset blockType(String type) { sortData.blockType = type; if (lastOff < 1) { lastOff = 1; } return this; }
        public SortingPreset pillar() { return blockType(SortInputs.PILLAR); }
        public SortingPreset multifaceted() { return blockType(SortInputs.MULTIFACETED); }
        public SortingPreset tableBlock() { return blockType(SortInputs.TABLE); }
        public SortingPreset vine() { return blockType(SortInputs.VINE); }
        public SortingPreset slab() { sortData.blockType = SortInputs.SLAB; return drop(SortInputs.DROP_SLAB); }
        public SortingPreset stairs() { return blockType(SortInputs.STAIRS); }
        public SortingPreset door() { return blockType(SortInputs.DOOR); }
        public SortingPreset trapdoor() { return blockType(SortInputs.TRAPDOOR); }
        public SortingPreset normalType() { return blockType(SortInputs.NORMAL_TYPE); }

        private SortingPreset drop(String drop) { sortData.dropType = drop; if (lastOff < 2) { lastOff = 2; } return this; }
        public SortingPreset dropSelf() { return drop(SortInputs.DROP_SELF); }
        public SortingPreset ore(ItemConvertible drop) { sortData.oreType = SortInputs.BASIC_ORE; sortData.oreData = new OreData(sortData.block,drop); return drop(SortInputs.ORE); }
        public SortingPreset ore(ItemConvertible drop, int min, int max) { sortData.oreType = SortInputs.SPECIFIC_ORE; sortData.oreData = new OreData(sortData.block,drop, min, max); return drop(SortInputs.ORE); }
        public SortingPreset needsShears() { return drop(SortInputs.NEEDS_SHEARS); }
        public SortingPreset noDrops() { return drop(SortInputs.NO_DROPS); }

        private SortingPreset tool(String tool) { sortData.toolType = tool; if (lastOff < 3) { lastOff = 3; } return this; }
        public SortingPreset withAxe() { return tool(SortInputs.AXE); }
        public SortingPreset withPickaxe() { return tool(SortInputs.PICKAXE); }
        public SortingPreset withShovel() { return tool(SortInputs.SHOVEL); }
        public SortingPreset withHoe() { return tool(SortInputs.HOE); }
        public SortingPreset noTool() { return tool(SortInputs.HAND); }

        private SortingPreset toolMaterial(String mat) { sortData.toolMaterial = mat; if (lastOff < 4) { lastOff = 4; } return this; }
        public SortingPreset stoneTool() { return toolMaterial(SortInputs.STONE); }
        public SortingPreset ironTool() { return toolMaterial(SortInputs.IRON); }
        public SortingPreset diamondTool() { return toolMaterial(SortInputs.DIAMOND); }
        public SortingPreset netheriteTool() { return toolMaterial(SortInputs.NETHERITE); }
        public SortingPreset scultiumTool() { return toolMaterial(SortInputs.SCULTIUM); }
        public SortingPreset anyMaterial() { return toolMaterial(SortInputs.NO_MATERIAL); }

        public Block get() { return sortData.block; }

        public Block copy(Block block) {
            Sorted sortedBlock = getFromBlock(block);
            switch(lastOff) {
                case -1: dimension(sortedBlock.dim);
                case 0: blockType(sortedBlock.blockType);
                case 1: drop(sortedBlock.dropType);
                case 2: tool(sortedBlock.toolType);
                case 3: toolMaterial(sortedBlock.toolMaterial);
            }
            return get();
        }
    }

    /* ALREADY SORTED */
    public static class Sorted {
        private final Block block;
        private final String dim;
        private final String blockType;
        private final String dropType;
        private final String oreType;
        private final OreData oreData;
        private final String toolType;
        private final String toolMaterial;

        private Sorted(Sorting template) {
            block = template.block; dim = template.dim; blockType = template.blockType;
            dropType = template.dropType; oreType = template.oreType; oreData = template.oreData;
            toolType = template.toolType; toolMaterial = template.toolMaterial;
        }

        public Block getBlock() { return block; }

        public OreData getOreData() { return oreData; }

        public boolean matches(String type, String condition) {
            return switch(type) {
                case "dim" -> dim.equals(condition);
                case "blockType" -> blockType.equals(condition);
                case "dropType" -> dropType.equals(condition);
                case "oreType" -> oreType.equals(condition);
                case "toolType" -> toolType.equals(condition);
                case "toolMaterial" -> toolMaterial.equals(condition);
                default -> false;
            };
        }
    }

    /* POSSIBLE SORT INPUTS */
    public static class SortInputs {
        // DIMENSIONS
        public static final String OVERWORLD = "overworld";
        public static final String NETHER = "overworld";
        public static final String END = "overworld";
        public static final String DEMANDI = "demandi";
        public static final String NEXUS = "nexus";
        public static final String CUSTOM = "custom";
        public static final String NONE = "dim_empty";
        // BLOCK TYPES
        public static final String PILLAR = "pillar";
        public static final String MULTIFACETED = "multi";
        public static final String TABLE = "table";
        public static final String VINE = "vine";
        public static final String SLAB = "slab";
        public static final String STAIRS = "stairs";
        public static final String DOOR = "door";
        public static final String TRAPDOOR = "trapdoor";
        public static final String NORMAL_TYPE = "block_empty";
        // DROP TYPES
        public static final String DROP_SELF = "self";
        public static final String DROP_SLAB = "drop_slab";
        public static final String ORE = "ore";
        public static final String BASIC_ORE = "basic_ore";
        public static final String SPECIFIC_ORE = "specific_ore";
        public static final String NEEDS_SHEARS = "shears";
        public static final String NO_DROPS = "drop_empty";
        // TOOL TYPES
        public static final String AXE = "overworld";
        public static final String PICKAXE = "overworld";
        public static final String SHOVEL = "overworld";
        public static final String HOE = "custom";
        public static final String HAND = "tool_empty";
        // TOOL MATERIALS
        public static final String STONE = "stone";
        public static final String IRON = "iron";
        public static final String DIAMOND = "diamond";
        public static final String NETHERITE = "netherite";
        public static final String SCULTIUM = "scultium";
        public static final String NO_MATERIAL = "material_empty";
    }

    /* ORE DATA */
    public static class OreData {
        private final Block parent; private final ItemConvertible drop;
        private final int min; private final int max;

        private OreData(Block parent, ItemConvertible drop) { this.parent = parent; this.drop = drop; this.min = 1; this.max = 1; }
        private OreData(Block parent, ItemConvertible drop, int min, int max) { this.parent = parent; this.drop = drop; this.min = min; this.max = max; }

        public Block getParent() { return parent; }
        public ItemConvertible getDrop() { return this.drop; }
        public int getMin() { return this.min; }
        public int getMax() { return this.max; }
    }

    /* SORTING PROCESS CLASS */
    public static class Sorting {
        private Block block; private String dim; private String blockType; private String dropType;
        private String oreType; private OreData oreData; private String toolType; private String toolMaterial;

        private Sorting(Block block) { this.block = block; }

        public Block get() { sorted.add(new Sorted(this)); return block; }

        // SORT BY DIMENSION FIRST
        public static class Dimension {
            private final Sorting sortData;
            private Dimension(Sorting data) { sortData = data; }

            public SortingPreset preset(SortingPreset preset) { preset.copy().create(sortData.block); return preset; }

            private BlockType dimension(String dim) { sortData.dim = dim; return new BlockType(sortData); }
            public BlockType inOverworld() { return dimension(SortInputs.OVERWORLD); }
            public BlockType inNether() { return dimension(SortInputs.NETHER); }
            public BlockType inEnd() { return dimension(SortInputs.END); }
            public BlockType inDemandi() { return dimension(SortInputs.DEMANDI); }
            public BlockType inNexus() { return dimension(SortInputs.NEXUS); }
            public BlockType inCustom() { return dimension(SortInputs.CUSTOM); }
            public BlockType noDimension() { return dimension(SortInputs.NONE); }
        }

        // SORT BY BLOCK TYPE NEXT
        public static class BlockType {
            private final Sorting sortData;
            private BlockType(Sorting data) { sortData = data; }

            private DropType blockType(String type) { sortData.blockType = type; return new DropType(sortData); }
            public DropType pillar() { return blockType(SortInputs.PILLAR); }
            public DropType multifaceted() { return blockType(SortInputs.MULTIFACETED); }
            public DropType tableBlock() { return blockType(SortInputs.TABLE); }
            public DropType vine() { return blockType(SortInputs.VINE); }
            public Tool slab() { sortData.blockType = SortInputs.SLAB; sortData.dropType = SortInputs.DROP_SLAB; return new Tool(sortData); }
            public DropType stairs() { return blockType(SortInputs.STAIRS); }
            public DropType door() { return blockType(SortInputs.DOOR); }
            public DropType trapdoor() { return blockType(SortInputs.TRAPDOOR); }
            public DropType normalType() { return blockType(SortInputs.NORMAL_TYPE); }
        }

        // SORT BY DROP TYPE NEXT
        public static class DropType {
            private final Sorting sortData;
            private DropType(Sorting data) { sortData = data; }

            private Tool drop(String drop) { sortData.dropType = drop; return new Tool(sortData); }
            public Tool dropSelf() { return drop(SortInputs.DROP_SELF); }
            public Tool ore() { sortData.oreType = SortInputs.BASIC_ORE; return drop(SortInputs.ORE); }
            public Tool ore(ItemConvertible drop) { sortData.oreType = SortInputs.BASIC_ORE; sortData.oreData = new OreData(sortData.block,drop); return drop(SortInputs.ORE); }
            public Tool ore(ItemConvertible drop, int min, int max) { sortData.oreType = SortInputs.SPECIFIC_ORE; sortData.oreData = new OreData(sortData.block, drop, min, max); return drop(SortInputs.ORE); }
            public Tool needsShears() { return drop(SortInputs.NEEDS_SHEARS); }
            public Tool noDrops() { return drop(SortInputs.NO_DROPS); }
        }

        // SORT BY TOOL TYPE NEXT
        public static class Tool {
            private final Sorting sortData;
            private Tool(Sorting data) { sortData = data; }

            private ToolMaterial tool(String tool) { sortData.toolType = tool; return new ToolMaterial(sortData); }
            public ToolMaterial withAxe() { return tool(SortInputs.AXE); }
            public ToolMaterial withPickaxe() { return tool(SortInputs.PICKAXE); }
            public ToolMaterial withShovel() { return tool(SortInputs.SHOVEL); }
            public ToolMaterial withHoe() { return tool(SortInputs.HOE); }
            public Sorting noTool() { sortData.toolType = SortInputs.HAND; return sortData; }
        }

        // SORT BY TOOL MATERIAL LAST
        public static class ToolMaterial {
            private final Sorting sortData;
            public ToolMaterial(Sorting data) { sortData = data; }

            private Sorting toolMaterial(String mat) { sortData.toolMaterial = mat; return sortData; }
            public Sorting stoneTool() { return toolMaterial(SortInputs.STONE); }
            public Sorting ironTool() { return toolMaterial(SortInputs.IRON); }
            public Sorting diamondTool() { return toolMaterial(SortInputs.DIAMOND); }
            public Sorting netheriteTool() { return toolMaterial(SortInputs.NETHERITE); }
            public Sorting scultiumTool() { return toolMaterial(SortInputs.SCULTIUM); }
            public Sorting anyMaterial() { return toolMaterial(SortInputs.NO_MATERIAL); }
        }
    }
}

// public static final Block PURIFIER_TABLE = registerBlock("purifier_table", new PurifierTableBlock(Useful.BlockSettings.sounds(3.5f, false, NoteBlockInstrument.BASS, BlockSoundGroup.METAL)));
//
//    // VANILLA DIMENSIONS
//    public static final Block PRISMALITE_ORE = registerBlock("prismalite_ore", Useful.BlockSettings.auto(6f));
//    public static final Block DYREMITE_BLOCK = registerBlock("dyremite_block", Useful.BlockSettings.auto(60f, 500f), Rarity.UNCOMMON);
//    public static final Block DEBRITINUM_BLOCK = registerBlock("debritinum_block", Useful.BlockSettings.auto(70f, 1500f), Rarity.UNCOMMON);
//    public static final Block PURVIUM_ORE = registerBlock("purvium_ore", Useful.BlockSettings.auto(15f));
//    public static final Block BURPLE_BLOCK = registerBlock("burple_block", Useful.BlockSettings.auto(6f));
//    public static final Block BURPLE_PURVIUM_ORE = registerBlock("burple_purvium_ore", Useful.BlockSettings.auto(9f));
//    public static final Block CONDENSED_PURPUR_BLOCK = registerBlock("condensed_purpur_block", Useful.BlockSettings.auto(8f));
//    public static final Block PURPUR_PURVIUM_ORE = registerBlock("purpur_purvium_ore", Useful.BlockSettings.auto(14f));
//    public static final Block PURVIUM_BLOCK = registerBlock("purvium_block", Useful.BlockSettings.auto(80f, 400f), Rarity.UNCOMMON);
//    // DEMANDI
//    public static final Block HARDENED_SCULK = registerBlock("hardened_sculk", Useful.BlockSettings.auto(100f, 10f));
//    public static final Block DEEP_STONE = registerBlock("deep_stone", Useful.BlockSettings.auto(24f));
//    public static final Block DEEP_STONE_SLAB = registerBlock("deep_stone_slab", new SlabBlock(Useful.BlockSettings.basic(24f, true)));
//    public static final Block DEEP_STONE_STAIRS = registerBlock("deep_stone_stairs", new StairsBlock(DEEP_STONE.getDefaultState(), Useful.BlockSettings.basic(24f, true)));
//
//    public static final Block DEEP = registerBlock("deep", Useful.BlockSettings.auto(20f));
//    public static final Block DARK = registerBlock("dark", Useful.BlockSettings.auto(9f));
//    public static final Block DARK_SLAB = registerBlock("dark_slab", new SlabBlock(Useful.BlockSettings.basic(9f, true)));
//    public static final Block DARK_STAIRS = registerBlock("dark_stairs", new StairsBlock(DARK.getDefaultState(), Useful.BlockSettings.basic(9f, true)));
//
//    public static final Block WASHED_DARK = registerBlock("washed_dark", Useful.BlockSettings.auto(10f));
//    public static final Block DEEP_SCULK = registerBlock("deep_sculk", Useful.BlockSettings.auto(25f));
//    public static final Block WASHED_SCULK = registerBlock("washed_sculk", Useful.BlockSettings.auto(26f));
//    public static final Block SCULTIUM_BLOCK = registerBlock("scultium_block", Useful.BlockSettings.auto(75f, 600f));
//    public static final Block DEPNETUM_BLOCK = registerBlock("depnetum_block", Useful.BlockSettings.auto(80f, 1000f));
//    public static final Block IMPERVIUM_BLOCK = registerBlock("impervium_block", Useful.BlockSettings.auto(200f));
//    public static final Block IMPERVIUM_SLAB = registerBlock("impervium_slab", new SlabBlock(Useful.BlockSettings.basic(180f, true)));
//    public static final Block IMPERVIUM_STAIRS = registerBlock("impervium_stairs", new StairsBlock(IMPERVIUM_BLOCK.getDefaultState(), Useful.BlockSettings.basic(180f, true)));
//
//    public static final Block DEAD_SCULK = registerBlock("dead_sculk", Useful.BlockSettings.auto(15f));
//    public static final Block DEAD_SCULK_SLAB = registerBlock("dead_sculk_slab", new SlabBlock(Useful.BlockSettings.basic(15f, true)));
//    public static final Block DEAD_SCULK_STAIRS = registerBlock("dead_sculk_stairs", new StairsBlock(DEAD_SCULK.getDefaultState(), Useful.BlockSettings.basic(15f, true)));
//
//    public static final Block STIFF_STONE = registerBlock("stiff_stone", Useful.BlockSettings.auto(30f));
//    public static final Block STIFF_STONE_SLAB = registerBlock("stiff_stone_slab", new SlabBlock(Useful.BlockSettings.basic(30f, true)));
//    public static final Block STIFF_STONE_STAIRS = registerBlock("stiff_stone_stairs", new StairsBlock(STIFF_STONE.getDefaultState(), Useful.BlockSettings.basic(30f, true)));
//
//    public static final Block STIFF_SOIL = registerBlock("stiff_soil", Useful.BlockSettings.auto(20f));
//    public static final Block CORRUPTED_MUD = registerBlock("corrupted_mud", Useful.BlockSettings.auto(14f));
//    public static final Block SCULTIUM_ORE = registerBlock("scultium_ore", Useful.BlockSettings.auto(32f));
//    public static final Block DEPNETUM_ORE = registerBlock("depnetum_ore", Useful.BlockSettings.auto(40f));
//    public static final Block DARK_DEPNETUM_ORE = registerBlock("dark_depnetum_ore", Useful.BlockSettings.auto(35f));
//    public static final Block DEEP_DARK_HEART = registerBlock("deep_dark_heart", Useful.BlockSettings.auto(60f));
//    public static final Block DEEP_HEART = registerBlock("deep_heart", Useful.BlockSettings.auto(40f));
//    public static final Block DARK_HEART = registerBlock("dark_heart", Useful.BlockSettings.auto(40f));
//    public static final Block SCULKSTONE = registerBlock("sculkstone", Useful.BlockSettings.auto(24f));
//    public static final Block DEAD_PLANKS = registerBlock("dead_planks", Useful.BlockSettings.auto(10f));
//    public static final Block DEAD_LOG = registerBlock("dead_log", new PillarBlock(Useful.BlockSettings.sounds(8f, 3.0f, true, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD)));
//    public static final Block POLISHED_IMPERVIUM = registerBlock("polished_impervium", Useful.BlockSettings.auto(180f));
//    public static final Block DEAD_VINE = registerBlock("dead_vine", new VineBlock(AbstractBlock.Settings.create().replaceable().noCollision()
//            .ticksRandomly().strength(0.3F).sounds(BlockSoundGroup.VINE).burnable().pistonBehavior(PistonBehavior.DESTROY)));
//
//    public static final Block HESPER_PLANKS = registerBlock("hesper_planks", Useful.BlockSettings.auto(12f));
//    public static final Block HESPER_LOG = registerBlock("hesper_log", new PillarBlock(Useful.BlockSettings.sounds(10f, 3.0f, true, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD)));
//    public static final Block CELESTE_BLOCK = registerBlock("celeste_block", Useful.BlockSettings.auto(15f));
//
//    public static final Block[] NON_BLOCK_DROP_SELF = new Block[] {DEEP_STONE,DEEP_STONE_STAIRS,DARK,DARK_STAIRS,IMPERVIUM_BLOCK,IMPERVIUM_STAIRS,
//            DEAD_SCULK,DEAD_SCULK_STAIRS,STIFF_STONE,STIFF_STONE_STAIRS};
//    public static final Block[] SLAB = new Block[] {DEEP_STONE_SLAB,DARK_SLAB,IMPERVIUM_SLAB,DEAD_SCULK_SLAB,STIFF_STONE_SLAB};
//
//    public static final Block[] WOOD = new Block[] {DEAD_LOG,HESPER_LOG};
//
//    public static final Block[] NON_BLOCKS = new Block[] {DEEP_STONE,DEEP_STONE_SLAB,DEEP_STONE_STAIRS,DARK,DARK_SLAB,DARK_STAIRS,IMPERVIUM_BLOCK,IMPERVIUM_SLAB,IMPERVIUM_STAIRS,
//            DEAD_SCULK,DEAD_SCULK_SLAB,DEAD_SCULK_STAIRS,STIFF_STONE,STIFF_STONE_SLAB,STIFF_STONE_STAIRS};
//    public static final Block[] DROP_SELF = new Block[] {PURIFIER_TABLE,DYREMITE_BLOCK,DEBRITINUM_BLOCK,CONDENSED_PURPUR_BLOCK,PURVIUM_BLOCK,BURPLE_BLOCK,
//            HARDENED_SCULK,DEEP,WASHED_DARK,DEEP_SCULK,WASHED_SCULK,DEPNETUM_BLOCK,SCULTIUM_BLOCK,STIFF_SOIL,CORRUPTED_MUD,POLISHED_IMPERVIUM,SCULKSTONE,
//            DEAD_PLANKS,HESPER_PLANKS,CELESTE_BLOCK};
//    public static final Block[] OTHER = new Block[] {DEAD_VINE,DEEP_DARK_HEART,DEEP_HEART,DARK_HEART};
//
//    public static final Block[] NORMAL = Useful.combine(DROP_SELF, Ores.ALL, OTHER);
//    public static final Block[] ALL = Useful.combine(NORMAL, NON_BLOCKS, WOOD);
//
//    public static class Ores {
//        public static Block[] BASIC = new Block[] {PURVIUM_ORE,PURPUR_PURVIUM_ORE,SCULTIUM_ORE,DEPNETUM_ORE,PRISMALITE_ORE};
//        public static Block[] SPECIFIC = new Block[] {BURPLE_PURVIUM_ORE,DARK_DEPNETUM_ORE};
//        public static Block[] ALL = Useful.combine(BASIC, SPECIFIC);
//
//        public static Item[] BASIC_DROPS = new Item[] {ModItems.PURVIUM_CHUNK,ModItems.PURVIUM_CHUNK,ModItems.SCULTIUM_BONES,ModItems.DEPNETUM_CLUMP,ModItems.PRISMALITE_SHARD};
//
//        public static Item[] SPECIFIC_DROPS = new Item[] {ModItems.PURVIUM_CHUNK,ModItems.DEPNETUM_CLUMP};
//        public static int[] SPECIFIC_MIN = new int[] {1, 1};
//        public static int[] SPECIFIC_MAX = new int[] {2, 2};
//    }
//
//    public static class Tools {
//        public static final Block[] AXE = new Block[] {DEAD_LOG,DEAD_PLANKS,HESPER_LOG,HESPER_PLANKS};
//        private static final Block[] PICK = new Block[] {DYREMITE_BLOCK, DEBRITINUM_BLOCK,PURIFIER_TABLE,CONDENSED_PURPUR_BLOCK,
//                HARDENED_SCULK,DEEP_STONE,DEEP_STONE_SLAB,DEEP_STONE_STAIRS,DEPNETUM_BLOCK,SCULTIUM_BLOCK,IMPERVIUM_BLOCK,IMPERVIUM_SLAB,
//                IMPERVIUM_STAIRS,POLISHED_IMPERVIUM,DEAD_SCULK,DEAD_SCULK_SLAB,DEAD_SCULK_STAIRS,STIFF_STONE,STIFF_STONE_SLAB,STIFF_STONE_STAIRS,SCULKSTONE,PURVIUM_BLOCK};
//        public static final Block[] PICKAXE = Useful.combine(Ores.ALL, PICK);
//        public static final Block[] SHOVEL = new Block[] {BURPLE_BLOCK,STIFF_SOIL,CORRUPTED_MUD,WASHED_DARK,WASHED_SCULK,CELESTE_BLOCK};
//        public static final Block[] HOE = new Block[] {DEEP,DARK,DARK_SLAB,DARK_STAIRS,DEEP_SCULK,DEAD_VINE,DEEP_DARK_HEART,DEEP_HEART,DARK_HEART};
//
//        public static final Block[] NEEDS_STONE = new Block[]{STIFF_SOIL,CORRUPTED_MUD};
//        public static final Block[] NEEDS_IRON = new Block[] {CONDENSED_PURPUR_BLOCK,PURVIUM_ORE,PURPUR_PURVIUM_ORE,BURPLE_BLOCK,BURPLE_PURVIUM_ORE,DEEP_STONE,DEEP_STONE_STAIRS,DEEP_STONE_SLAB,WASHED_DARK,WASHED_SCULK,DEEP,DARK,DARK_SLAB,DARK_STAIRS,DEEP_SCULK,PRISMALITE_ORE};
//        public static final Block[] NEEDS_DIAMOND = new Block[] {DYREMITE_BLOCK,SCULKSTONE,STIFF_STONE,SCULTIUM_ORE,DEPNETUM_ORE,DARK_DEPNETUM_ORE};
//        public static final Block[] NEEDS_NETHERITE = new Block[] {DEBRITINUM_BLOCK,HARDENED_SCULK,DEPNETUM_BLOCK,SCULTIUM_BLOCK,PURVIUM_BLOCK};
//        public static final Block[] NEEDS_SCULTIUM = new Block[] {IMPERVIUM_BLOCK,IMPERVIUM_SLAB,IMPERVIUM_STAIRS,POLISHED_IMPERVIUM};
//    }