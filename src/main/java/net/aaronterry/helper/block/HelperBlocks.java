package net.aaronterry.helper.block;

import net.aaronterry.helper.datagen.ModRecipeHelperProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

/* ADDITIONS
 * ItemGroup organization
 * Functionality organization
 * Recipe organization
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
        Block registered = registerSort(id, block, itemSettings); return new Sorting.Dimension(new Sorting(registered, id)); }

    protected static SortingPreset sortBlock(Identifier id, Block block, SortingPreset preset) {  return sortBlock(id, block, new Item.Settings(), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Rarity rarity, SortingPreset preset) { return sortBlock(id, block, new Item.Settings().rarity(rarity), preset); }
    protected static SortingPreset sortBlock(Identifier id, Block block, Item.Settings itemSettings, SortingPreset preset) {
        Block registered = registerSort(id, block, itemSettings); return preset.copyAndCreate(block, id); }

    /* GET A BLOCK BASED ON SORTING */
    private static List<Block> get(String sortBy, String requirement) {
        List<Block> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.hasBlock() && sortedBlock.matches(sortBy, requirement)) result.add(sortedBlock.getBlock()); });
        return result;
    }
    private static List<Block> get(String sortBy, String requirement, boolean includeParent) {
        List<Block> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> {
            if (sortedBlock.hasBlock() && sortedBlock.matches(sortBy, requirement) && (includeParent || !sortedBlock.isParentBlock())) result.add(sortedBlock.getBlock());
        });
        return result;
    }
    public static List<Block> getNotSorted() { return notSorted.stream().toList(); }
    public static List<Block> getFromDimension(String req) { return get("dim",req); }
    public static List<Block> getFromDimension(String req, boolean includeParents) { return get("dim",req,includeParents); }
    public static List<Block> getFromBlockType(String req) { return get("blockType",req); }
    public static List<Block> getFromBlockType(String req, boolean includeParents) { return get("blockType",req,includeParents); }
    public static List<Block> getFromDropType(String req) { return get("dropType",req); }
    public static List<Block> getFromDropType(String req, boolean includeParents) { return get("dropType",req,includeParents); }
    public static List<Block> getFromToolType(String req) { return get("toolType",req); }
    public static List<Block> getFromToolType(String req, boolean includeParents) { return get("toolType",req,includeParents); }
    public static List<Block> getFromToolMaterial(String req) { return get("toolMaterial",req); }
    public static List<Block> getFromToolMaterial(String req, boolean includeParents) { return get("toolMaterial",req,includeParents); }
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
    public static List<Sorted> getParents() {
        List<Sorted> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if (sortedBlock.isParentBlock()) result.add(sortedBlock); });
        return result;
    }
    public static List<Sorted> getBlocksWithRecipes() {
        List<Sorted> result = new ArrayList<>();
        sorted.forEach(sortedBlock -> { if(sortedBlock.hasRecipes()) result.add(sortedBlock); });
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

        public SortingPreset() { sortData = new Sorting(Identifier.of("")); }
        private SortingPreset(SortingPreset preset) { sortData = new Sorting(preset.sortData); lastOff = preset.lastOff; }

        public void create(Block block, Identifier id) { sortData.block = block; sortData.identifier = id; sortData.hasBlock = true; }

        public Block get() { return sortData.get(); }

        private boolean isEmpty(String str) { return str.equals("_empty_"); }

        public SortingPreset copy() { return new SortingPreset(this); }
        public Block copy(Block block) {
            if (block == null) { throw new IllegalArgumentException("Null input in HelperBlocks.copy"); }
            Sorted sortedBlock = getFromBlock(block);
            if (isEmpty(sortData.dim)) sortData.dim = sortedBlock.dim;
            if (isEmpty(sortData.blockType)) sortData.blockType = sortedBlock.blockType;
            if (isEmpty(sortData.dropType)) sortData.dropType = sortedBlock.dropType;
            if (isEmpty(sortData.oreType)) sortData.oreType = sortedBlock.oreType;
            if (isEmpty(sortData.toolType)) sortData.toolType = sortedBlock.toolType;
            if (isEmpty(sortData.toolMaterial)) sortData.toolMaterial = sortedBlock.toolMaterial;
            return get();
        }
        public Block copyAsParent(Block block) {
            if (block == null) { throw new IllegalArgumentException("Null input in HelperBlocks.copy"); }
            Sorted sortedBlock = getFromBlock(block);
            if (isEmpty(sortData.dim)) sortData.dim = sortedBlock.dim;
            if (isEmpty(sortData.blockType)) sortData.blockType = sortedBlock.blockType;
            if (isEmpty(sortData.dropType)) sortData.dropType = sortedBlock.dropType;
            if (isEmpty(sortData.oreType)) sortData.oreType = sortedBlock.oreType;
            if (isEmpty(sortData.toolType)) sortData.toolType = sortedBlock.toolType;
            if (isEmpty(sortData.toolMaterial)) sortData.toolMaterial = sortedBlock.toolMaterial;
            if (sortedBlock.isParent) sortData.isParent = true;
            return get();
        }

        public Block parent(Block parent) {
            sortData.parent = parent; return copy(parent);
        }

        public SortingPreset copyAndCreate(Block create, Identifier id) {
            SortingPreset newPreset = this.copy(); newPreset.create(create, id); return newPreset;
        }

        public ModRecipeHelperProvider.Shaped.Details shapedRecipe(RecipeCategory cat) {
            return ModRecipeHelperProvider.Shaped.recipe(cat, sortData.block, copy());
        }
        public ModRecipeHelperProvider.Shaped.Details shapedRecipe(RecipeCategory cat, int count) {
            return ModRecipeHelperProvider.Shaped.recipe(cat, sortData.block, count, copy());
        }
        public ModRecipeHelperProvider.Shapeless.Details shapelessRecipe(RecipeCategory cat) {
            return ModRecipeHelperProvider.Shapeless.recipe(cat, sortData.block, copy());
        }
        public ModRecipeHelperProvider.Shapeless.Details shapelessRecipe(RecipeCategory cat, int count) {
            return ModRecipeHelperProvider.Shapeless.recipe(cat, sortData.block, count, copy());
        }
        public SortingPreset reverseRecipe(RecipeCategory cat, ItemConvertible base) {
            sortData.recipes.add(new ModRecipeHelperProvider.GenericDetails(cat, base, sortData.block));
            return this;
        }

        public void recipe(ModRecipeHelperProvider.GenericDetails details) {
            sortData.recipes.add(details);
        }

        public SortingPreset parent() { sortData.isParent = true; return this; }

        public SortingPreset dimension(String dim) { sortData.dim = dim; if (lastOff < 0) { lastOff = 0; } return this; }

        public SortingPreset model(String type) { sortData.blockType = type; if (lastOff < 1) { lastOff = 1; } return this; }

        public SortingPreset drop(String drop) { sortData.dropType = drop; if (lastOff < 2) { lastOff = 2; } return this; }

        public SortingPreset ore(ItemConvertible drop) { sortData.oreType = SortInputs.BASIC_ORE; sortData.oreData = new OreData(sortData.block,drop); return drop(SortInputs.ORE); }
        public SortingPreset ore(ItemConvertible drop, int min, int max) { sortData.oreType = SortInputs.SPECIFIC_ORE; sortData.oreData = new OreData(sortData.block, drop, min, max); return drop(SortInputs.ORE); }

        public SortingPreset tool(String tool) { sortData.toolType = tool; if (lastOff < 3) { lastOff = 3; } return this; }

        public SortingPreset material(String mat) { sortData.toolMaterial = mat; if (lastOff < 4) { lastOff = 4; } return this; }

        public SortingPreset and(SortingPreset preset) {
            if (!isEmpty(preset.sortData.dim) && isEmpty(sortData.dim)) sortData.dim = preset.sortData.dim;
            if (!isEmpty(preset.sortData.blockType) && isEmpty(sortData.blockType)) sortData.blockType = preset.sortData.blockType;
            if (!isEmpty(preset.sortData.dropType) && isEmpty(sortData.dropType)) sortData.dropType = preset.sortData.dropType;
            if (!isEmpty(preset.sortData.oreType) && isEmpty(sortData.oreType)) sortData.oreType = preset.sortData.oreType;
            if (!isEmpty(preset.sortData.toolType) && isEmpty(sortData.toolType)) sortData.toolType = preset.sortData.toolType;
            if (!isEmpty(preset.sortData.toolMaterial) && isEmpty(sortData.toolMaterial)) sortData.toolMaterial = preset.sortData.toolMaterial;
            return this;
        }
    }

    /* ALREADY SORTED */
    public static class Sorted {
        private final Block block;
        private final Identifier identifier;
        private final Block parent;
        private final String dim;
        private final String blockType;
        private final String dropType;
        private final String oreType;
        private final OreData oreData;
        private final String toolType;
        private final String toolMaterial;
        private final boolean hasBlock;
        private final boolean isParent;
        private final List<ModRecipeHelperProvider.GenericDetails> recipes;

        private Sorted(Sorting template) {
            identifier = template.identifier; block = template.block; dim = template.dim; blockType = template.blockType;
            dropType = template.dropType; oreType = template.oreType; oreData = template.oreData;
            toolType = template.toolType; toolMaterial = template.toolMaterial; parent = template.parent;
            hasBlock = template.hasBlock; isParent = template.isParent; recipes = template.recipes;
        }

        public Identifier id() { return identifier; }
        public Identifier id(String extension) { return Identifier.of(identifier.getNamespace(),identifier.getPath()+"_"+extension); }

        public boolean hasParent() { return parent != null; }

        public Block getParent() { return parent; }

        public boolean hasBlock() { return hasBlock; }

        public Block getBlock() { return block; }

        public OreData getOreData() { return oreData; }

        public boolean hasRecipes() { return !recipes.isEmpty(); }

        public List<ModRecipeHelperProvider.GenericDetails> getRecipes() { return recipes; }

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

        public boolean isParentBlock() { return isParent; }
    }

    /* POSSIBLE SORT INPUTS */
    public static class SortInputs {
        // DIMENSIONS
        public static final String OVERWORLD = "overworld";
        public static final String NETHER = "nether";
        public static final String END = "end";
        public static final String DEMANDI = "demandi";
        public static final String NEXUS = "nexus";
        public static final String RARE = "rare";
        public static final String CUSTOM_DIM = "custom";
        public static final String NO_DIM = "dim_empty";
        // MODEL TYPES
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
        public static final String WALL = "wall";
        public static final String GRASSLIKE = "grasslike";
        public static final String FENCE = "fence";
        public static final String FENCE_GATE = "fence_gate";
        public static final String CROSS = "cross_section";
        public static final String TORCH = "torch";
        public static final String WALL_TORCH = "wall_torch";
        public static final String NORMAL_TYPE = "block_empty";
        // DROP TYPES
        public static final String DROP_SELF = "self";
        public static final String DROP_SLAB = "drop_slab";
        public static final String DROP_DOOR = "drop_door";
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
        private Block block; private String dim = "_empty_"; private String blockType = "_empty_"; private String dropType = "_empty_";
        private String oreType = "_empty_"; private OreData oreData; private String toolType = "_empty_"; private String toolMaterial = "_empty_";
        private boolean isParent = false; private Block parent; private boolean hasBlock; private final List<ModRecipeHelperProvider.GenericDetails> recipes = new ArrayList<>();
        private Identifier identifier;

        private Sorting(Identifier id) { this.identifier = id; this.hasBlock = false; }
        private Sorting(Block block, Identifier id) { this.block = block; this.identifier = id; this.hasBlock = true; }
        private Sorting(Sorting template) {
            identifier = template.identifier; block = template.block; dim = template.dim; blockType = template.blockType;dropType = template.dropType;
            oreType = template.oreType; oreData = template.oreData; toolType = template.toolType; toolMaterial = template.toolMaterial;
            isParent = template.isParent; parent = template.parent; hasBlock = template.hasBlock; recipes.addAll(template.recipes);
        }

        private boolean isEmpty(String str) { return str.equals("_empty"); }

        private void fillEmpty() {
            if (isEmpty(dim)) dim = SortInputs.NO_DIM;
            if (isEmpty(blockType)) blockType = SortInputs.NORMAL_TYPE;
            if (isEmpty(dropType)) dropType = SortInputs.NO_DROPS;
            if (isEmpty(oreType)) oreType = SortInputs.NO_ORE;
            if (isEmpty(toolType)) toolType = SortInputs.HAND;
            if (isEmpty(toolMaterial)) toolMaterial = SortInputs.NO_MATERIAL;
        }

        public Block get() {
            fillEmpty();
            sorted.add(new Sorted(this));
            return block;
        }

        // SORT BY DIMENSION FIRST
        public static class Dimension {
            private final Sorting sortData;
            private Dimension(Sorting data) { sortData = data; }

            public SortingPreset preset(SortingPreset preset) { return preset.copyAndCreate(sortData.block, sortData.identifier); }

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
            public Tool door() { sortData.blockType = SortInputs.DOOR; sortData.dropType = SortInputs.DROP_DOOR; return new Tool(sortData); }
            public DropType trapdoor() { return blockType(SortInputs.TRAPDOOR); }
            public DropType wall() { return blockType(SortInputs.WALL); }
            public DropType fence() { return blockType(SortInputs.FENCE); }
            public DropType fenceGate() { return blockType(SortInputs.FENCE_GATE); }
            public DropType parentBlock() { sortData.isParent = true; return blockType(SortInputs.NORMAL_TYPE); }
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