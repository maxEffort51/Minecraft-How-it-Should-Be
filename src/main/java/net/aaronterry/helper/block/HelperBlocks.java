package net.aaronterry.helper.block;

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
        Registry.register(Registries.ITEM, identifier, new BlockItem(block, settings));
        if (!sorted) notSorted.add(block);
    }
    protected static void addToItemGroup(RegistryKey<ItemGroup> group, List<Block> blocks) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> blocks.forEach(entries::add));
    }
    protected static Block registerBlock(Identifier identifier, Block block) { return Registry.register(Registries.BLOCK, identifier, block); }

    /* REGISTER A BLOCK */
    protected static Block register(Identifier identifier, Block block) { return register(identifier, block, new Item.Settings()); }
    protected static Block register(Identifier identifier, Block block, Rarity rarity) { return register(identifier, block, new Item.Settings().rarity(rarity)); }
    protected static Block register(Identifier identifier, Block block, Item.Settings itemSettings) {
        registerBlock(identifier,block); registerAsItem(identifier, block, itemSettings, false); return block;
    }
    protected static Block registerSort(Identifier identifier, Block block, Item.Settings itemSettings) {
        Block registered = registerBlock(identifier,block); registerAsItem(identifier, block, itemSettings, true); return registered;
    }
    /* REGISTER AND SORT A BLOCK */
    protected static Sorting.Dimension sortBlock(Identifier id, Block block) {  return sortBlock(id, block, new Item.Settings()); }
    protected static Sorting.Dimension sortBlock(Identifier id, Block block, Rarity rarity) { return sortBlock(id, block, new Item.Settings().rarity(rarity)); }
    protected static Sorting.Dimension sortBlock(Identifier id, Block block, Item.Settings itemSettings) {
        Block registered = registerSort(id, block, itemSettings); return new Sorting.Dimension(new Sorting(registered)); }

    protected static SortingPreset sortBlock(Identifier id, Block block, SortingPreset preset) {  return sortBlock(id, block, new Item.Settings(), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Rarity rarity, SortingPreset preset) { return sortBlock(id, block, new Item.Settings().rarity(rarity), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Item.Settings itemSettings, SortingPreset preset) {
        Block registered = registerSort(id, block, itemSettings); return preset.copyAndCreate(block); }

    /* GET A BLOCK BASED ON SORTING */
    private static List<Block> get(String sortBy, String requirement) {
        List<Block> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.hasBlock() && sortedBlock.matches(sortBy, requirement)) result.add(sortedBlock.getBlock()); });
        return result;
    }
    public static List<Block> getNotSorted() { return notSorted.stream().toList(); }
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
    private static List<Sorted> find(String sortBy, String requirement) {
        List<Sorted> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.matches(sortBy, requirement)) result.add(sortedBlock); });
        return result;
    }
    public static List<Sorted> sortWithDimension(String req) { return find("dim",req); }
    public static List<Sorted> sortWithBlockType(String req) { return find("blockType",req); }
    public static List<Sorted> sortWithDropType(String req) { return find("dropType",req); }
    public static List<Sorted> sortWithToolType(String req) { return find("toolType",req); }
    public static List<Sorted> sortWithToolMaterial(String req) { return find("toolMaterial",req); }
    public static List<Block> all() {
        List<Block> sortedBlocks = new ArrayList<>(sorted.stream().map(Sorted::getBlock).toList());
        sortedBlocks.addAll(notSorted);
        return sortedBlocks;
    }
    public static Sorted getFromBlock(Block block) {
        for (Sorted sortedItem : sorted) { if (sortedItem.hasBlock() && sortedItem.getBlock().equals(block)) { return sortedItem; } }
        throw new IllegalArgumentException("HelperBlocks.getFromBlock - illegal block argument " + (block != null ? block.toString() : "null"));
    }

    /* SORTING PRESET */
    public static class SortingPreset {
        private final Sorting sortData;
        private int lastOff = -1;

        public SortingPreset() { sortData = new Sorting(); }
        private SortingPreset(SortingPreset preset) { sortData = new Sorting(preset.sortData); lastOff = preset.lastOff; }

        public void create(Block block) { sortData.block = block; sortData.hasBlock = true; }

        public Block get() { return sortData.get(); }

        public SortingPreset copy() { return new SortingPreset(this); }
        public Block copy(Block block) {
            if (block == null) { throw new IllegalArgumentException("Null input in HelperBlocks.copy"); }
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

        public Block parent(Block parent) {
            sortData.parent = parent; return copy(parent);
        }

        public SortingPreset copyAndCreate(Block create) {
            SortingPreset newPreset = this.copy(); newPreset.create(create); return newPreset;
        }

        private SortingPreset dimension(String dim) { sortData.dim = dim; if (lastOff < 0) { lastOff = 0; } return this; }
        public SortingPreset inOverworld() { return dimension(SortInputs.OVERWORLD); }
        public SortingPreset inNether() { return dimension(SortInputs.NETHER); }
        public SortingPreset inEnd() { return dimension(SortInputs.END); }
        public SortingPreset inDemandi() { return dimension(SortInputs.DEMANDI); }
        public SortingPreset inNexus() { return dimension(SortInputs.NEXUS); }
        public SortingPreset inCustom() { return dimension(SortInputs.CUSTOM_DIM); }
        public SortingPreset noDimension() { return dimension(SortInputs.NO_DIM); }

        private SortingPreset blockType(String type) { sortData.blockType = type; if (lastOff < 1) { lastOff = 1; } return this; }
        public SortingPreset pillar() { return blockType(SortInputs.PILLAR); }
        public SortingPreset stone() { return blockType(SortInputs.STONE_BLOCK_TYPE); }
        public SortingPreset planks() { return blockType(SortInputs.WOOD_PLANKS); }
        public SortingPreset multifaceted() { return blockType(SortInputs.MULTIFACETED); }
        public SortingPreset tableBlock() { return blockType(SortInputs.TABLE); }
        public SortingPreset vine() { return blockType(SortInputs.VINE); }
        public SortingPreset slab() { sortData.blockType = SortInputs.SLAB; return drop(SortInputs.DROP_SLAB); }
        public SortingPreset stairs() { return blockType(SortInputs.STAIRS); }
        public SortingPreset door() { return blockType(SortInputs.DOOR); }
        public SortingPreset trapdoor() { return blockType(SortInputs.TRAPDOOR); }
        public SortingPreset parentBlock() { return blockType(SortInputs.PARENT_BLOCK); }
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
    }

    /* ALREADY SORTED */
    public static class Sorted {
        private final Block block;
        private final Block parent;
        private final String dim;
        private final String blockType;
        private final String dropType;
        private final String oreType;
        private final OreData oreData;
        private final String toolType;
        private final String toolMaterial;
        private final boolean hasBlock;

        private Sorted(Sorting template) {
            block = template.block; dim = template.dim; blockType = template.blockType;
            dropType = template.dropType; oreType = template.oreType; oreData = template.oreData;
            toolType = template.toolType; toolMaterial = template.toolMaterial; parent = template.parent;
            hasBlock = template.hasBlock;
        }

        public boolean hasParent() { return parent != null; }

        public Block getParent() { return parent; }

        public boolean hasBlock() { return hasBlock; }

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
        public static final String NETHER = "nether";
        public static final String END = "end";
        public static final String DEMANDI = "demandi";
        public static final String NEXUS = "nexus";
        public static final String CUSTOM_DIM = "custom";
        public static final String NO_DIM = "dim_empty";
        // BLOCK TYPES
        public static final String PILLAR = "pillar";
        public static final String MULTIFACETED = "multi";
        public static final String STONE_BLOCK_TYPE = "stone_block_type";
        public static final String WOOD_PLANKS = "wood_planks";
        public static final String TABLE = "table";
        public static final String VINE = "vine";
        public static final String SLAB = "slab";
        public static final String STAIRS = "stairs";
        public static final String DOOR = "door";
        public static final String TRAPDOOR = "trapdoor";
        public static final String PARENT_BLOCK = "parent_block";
        public static final String NORMAL_TYPE = "block_empty";
        // DROP TYPES
        public static final String DROP_SELF = "self";
        public static final String DROP_SLAB = "drop_slab";
        public static final String ORE = "ore";
        public static final String BASIC_ORE = "basic_ore";
        public static final String SPECIFIC_ORE = "specific_ore";
        public static final String NO_ORE = "no_ore";
        public static final String NEEDS_SHEARS = "shears";
        public static final String NO_DROPS = "drop_empty";
        // TOOL TYPES
        public static final String AXE = "axe";
        public static final String PICKAXE = "pickaxe";
        public static final String SHOVEL = "shovel";
        public static final String HOE = "hoe";
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
        private Block parent; private boolean hasBlock;

        private Sorting() { this.hasBlock = false; }
        private Sorting(Block block) { this.block = block; this.hasBlock = true; }
        private Sorting(Sorting template) {
            block = template.block; dim = template.dim;blockType = template.blockType;dropType = template.dropType;
            oreType = template.oreType; oreData = template.oreData; toolType = template.toolType; toolMaterial = template.toolMaterial;
            parent = template.parent; hasBlock = template.hasBlock;
        }

        private void fillNull() {
            if (dim == null) dim = SortInputs.NO_DIM;
            if (blockType == null) blockType = SortInputs.NORMAL_TYPE;
            if (dropType == null) dropType = SortInputs.NO_DROPS;
            if (oreType == null) oreType = SortInputs.NO_ORE;
            if (toolType == null) toolType = SortInputs.HAND;
            if (toolMaterial == null) toolMaterial = SortInputs.NO_MATERIAL;
        }

        public Block get() {
            fillNull();
            sorted.add(new Sorted(this));
            return block;
        }

        // SORT BY DIMENSION FIRST
        public static class Dimension {
            private final Sorting sortData;
            private Dimension(Sorting data) { sortData = data; }

            public SortingPreset preset(SortingPreset preset) { return preset.copyAndCreate(sortData.block); }

            private BlockType dimension(String dim) { sortData.dim = dim; return new BlockType(sortData); }
            public BlockType inOverworld() { return dimension(SortInputs.OVERWORLD); }
            public BlockType inNether() { return dimension(SortInputs.NETHER); }
            public BlockType inEnd() { return dimension(SortInputs.END); }
            public BlockType inDemandi() { return dimension(SortInputs.DEMANDI); }
            public BlockType inNexus() { return dimension(SortInputs.NEXUS); }
            public BlockType inCustom() { return dimension(SortInputs.CUSTOM_DIM); }
            public BlockType noDimension() { return dimension(SortInputs.NO_DIM); }
        }

        // SORT BY BLOCK TYPE NEXT
        public static class BlockType {
            private final Sorting sortData;
            private BlockType(Sorting data) { sortData = data; }

            private DropType blockType(String type) { sortData.blockType = type; return new DropType(sortData); }
            public DropType pillar() { return blockType(SortInputs.PILLAR); }
            public DropType multifaceted() { return blockType(SortInputs.MULTIFACETED); }
            public DropType tableBlock() { return blockType(SortInputs.TABLE); }
            public DropType stone() { return blockType(SortInputs.STONE_BLOCK_TYPE); }
            public DropType planks() { return blockType(SortInputs.WOOD_PLANKS); }
            public DropType vine() { return blockType(SortInputs.VINE); }
            public Tool slab() { sortData.blockType = SortInputs.SLAB; sortData.dropType = SortInputs.DROP_SLAB; return new Tool(sortData); }
            public DropType stairs() { return blockType(SortInputs.STAIRS); }
            public DropType door() { return blockType(SortInputs.DOOR); }
            public DropType trapdoor() { return blockType(SortInputs.TRAPDOOR); }
            public DropType parentBlock() { return blockType(SortInputs.PARENT_BLOCK); }
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