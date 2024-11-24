package net.aaronterry.hisb.pack.exploration.block;

import net.aaronterry.helper.Useful;
import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.block.custom.PurifierTableBlock;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks extends HelperBlocks {
    private static Identifier id(String value) { return id(HisbMod.id(), value); }

    /* SORTING PRESETS */
    private static final SortingPreset ps_NORMAL = new SortingPreset().model(SortInputs.NORMAL_TYPE).drop(SortInputs.DROP_SELF);
    private static final SortingPreset ps_DEMANDI = ps_NORMAL.copy().dimension(SortInputs.DEMANDI).tool(SortInputs.HOE);
    // MODEL VARIANTS
    private static final SortingPreset ps_TABLE = ps_NORMAL.copy().model(SortInputs.TABLE);
    private static final SortingPreset ps_STAIRS = ps_NORMAL.copy().model(SortInputs.STAIRS);
    private static final SortingPreset ps_TRAPDOOR = ps_NORMAL.copy().model(SortInputs.TRAPDOOR);
    private static final SortingPreset ps_WALL = ps_NORMAL.copy().model(SortInputs.WALL);
    private static final SortingPreset ps_FENCE = ps_NORMAL.copy().model(SortInputs.FENCE);
    private static final SortingPreset ps_FENCE_GATE = ps_NORMAL.copy().model(SortInputs.FENCE_GATE);
    private static final SortingPreset ps_FLOWER = ps_NORMAL.copy().model(SortInputs.CROSS);
    private static final SortingPreset ps_LOG = ps_NORMAL.copy().model(SortInputs.PILLAR).tool(SortInputs.AXE);

    private static final SortingPreset ps_ORE = new SortingPreset().model(SortInputs.NORMAL_TYPE).tool(SortInputs.PICKAXE);
    private static final SortingPreset ps_STONE = ps_NORMAL.copy().model(SortInputs.STONE_BLOCK_TYPE).tool(SortInputs.PICKAXE);
    private static final SortingPreset ps_VINE = new SortingPreset().model(SortInputs.VINE).drop(SortInputs.NEEDS_SHEARS).tool(SortInputs.HAND);
    private static final SortingPreset ps_SLAB = new SortingPreset().model(SortInputs.SLAB).drop(SortInputs.DROP_SLAB);
    private static final SortingPreset ps_DOOR = new SortingPreset().model(SortInputs.DOOR).drop(SortInputs.DROP_DOOR);
    private static final SortingPreset ps_END_ORE = ps_ORE.copy().dimension(SortInputs.END).material(SortInputs.IRON);
    private static final SortingPreset ps_DEMANDI_ORE = ps_ORE.copy().dimension(SortInputs.DEMANDI).material(SortInputs.DIAMOND);
    private static final SortingPreset ps_DEMHEART = ps_DEMANDI.copy().drop(SortInputs.NO_DROPS).material(SortInputs.SCULTIUM);
    private static final SortingPreset ps_PLANKS = ps_NORMAL.copy().model(SortInputs.WOOD_PLANKS).tool(SortInputs.AXE);

    /* BLOCK SET TYPES */
    private static final BlockSetType HESPER = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY).build(id("hesper"));
    private static final WoodType HESPER_WOOD = WoodTypeBuilder.copyOf(WoodType.CHERRY).build(id("hesper_wood"), HESPER);

    /* TABLES */

    // new PurifierTableBlock(Useful.BlockSettings.sounds(3.5f, false, NoteBlockInstrument.BASS, BlockSoundGroup.METAL))
    public static final Block PURIFIER_TABLE = sortBlock(id("purifier_table"), new PurifierTableBlock(Useful.BlockSettings.sounds(3.5f, false, NoteBlockInstrument.BASS, BlockSoundGroup.METAL)))
            .preset(ps_TABLE).dimension(SortInputs.NO_DIM).tool(SortInputs.PICKAXE).material(SortInputs.STONE).shapelessRecipe(RecipeCategory.MISC)
            .input(new Item[]{Items.NETHER_STAR, Items.GHAST_TEAR, Items.BLAZE_ROD, Items.SPONGE, Items.BLUE_ICE}, new int[] {2,2,2,1,2})
            .needsResult().endRecipe().get();

    /* BASIC BLOCKS */
    public static final Block BURPLE_BLOCK = sortBlock(id("burple_block"), Useful.BlockSettings.auto(6f),ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.SHOVEL).material(SortInputs.IRON).parent().get();
    public static final Block CONDENSED_PURPUR_BLOCK = sortBlock(id("condensed_purpur_block"), Useful.BlockSettings.auto(8f),ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.PICKAXE).material(SortInputs.IRON).parent().get();
    // DEMANDI
    public static final Block DEEP = sortBlock(id("deep"), Useful.BlockSettings.auto(20f),ps_DEMANDI).material(SortInputs.IRON).get();
    public static final Block DARK = sortBlock(id("dark"), Useful.BlockSettings.auto(9f),ps_DEMANDI).material(SortInputs.IRON).parent().get();
    public static final Block DEEP_SCULK = sortBlock(id("deep_sculk"), Useful.BlockSettings.auto(25f),ps_DEMANDI).material(SortInputs.IRON).get();
    public static final Block CORRUPTED_MUD = sortBlock(id("corrupted_mud"), Useful.BlockSettings.auto(14f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.STONE).get();
    public static final Block WASHED_DARK = sortBlock(id("washed_dark"), Useful.BlockSettings.auto(10f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.IRON).get();
    public static final Block WASHED_SCULK = sortBlock(id("washed_sculk"), Useful.BlockSettings.auto(26f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.IRON).get();
    public static final Block STIFF_SOIL = sortBlock(id("stiff_soil"), Useful.BlockSettings.auto(20f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.DIAMOND).get();
    public static final Block DEEP_STONE = sortBlock(id("deep_stone"), Useful.BlockSettings.auto(24f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.IRON).parent().get();
    public static final Block STIFF_STONE = sortBlock(id("stiff_stone"), Useful.BlockSettings.auto(30f),ps_STONE).dimension(SortInputs.DEMANDI).material(SortInputs.DIAMOND).parent()
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS).input(ModBlocks.STIFF_SOIL, 4).needs(ModBlocks.STIFF_SOIL).endRecipe().get();
    public static final Block SCULKSTONE = sortBlock(id("sculkstone"), Useful.BlockSettings.auto(29f),ps_STONE).dimension(SortInputs.DEMANDI).material(SortInputs.IRON).get();
    public static final Block DEAD_SCULK = sortBlock(id("dead_sculk"), Useful.BlockSettings.auto(15f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND).parent().get();
    public static final Block HARDENED_SCULK = sortBlock(id("hardened_sculk"), Useful.BlockSettings.auto(100f, 10f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE).get();
    public static final Block IMPERVIUM_BLOCK = sortBlock(id("impervium_block"), Useful.BlockSettings.auto(200f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM).parent().get();
    // NEXUS
    public static final Block CELESTE_BLOCK = sortBlock(id("celeste_block"), Useful.BlockSettings.auto(15f)).inNexus().normalType().dropSelf().withShovel().anyMaterial().get();
    public static final Block CELESTE_STONE = sortBlock(id("celeste_stone"), Useful.BlockSettings.auto(18f),ps_STONE).dimension(SortInputs.NEXUS).material(SortInputs.IRON).parent().get();
    public static final Block INDUG_STONE = sortBlock(id("indug_stone"), Useful.BlockSettings.auto(20f),ps_STONE).dimension(SortInputs.NEXUS).material(SortInputs.IRON).parent().get();

    /* WOOD BLOCKS */
    public static final Block DEAD_LOG = sortBlock(id("dead_log"), new PillarBlock(Useful.BlockSettings.sounds(8f, 3.0f, true, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD)),ps_LOG).dimension(SortInputs.DEMANDI).material(SortInputs.STONE).get();
    public static final Block DEAD_PLANKS = sortBlock(id("dead_planks"), Useful.BlockSettings.auto(10f),ps_DEMANDI).tool(SortInputs.AXE).material(SortInputs.STONE)
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, 4).input(ModBlocks.DEAD_LOG).needs(ModBlocks.DEAD_LOG).endRecipe().get();
    // NEXUS
    public static final Block HESPER_LOG = sortBlock(id("hesper_log"), new PillarBlock(Useful.BlockSettings.sounds(10f, 3.0f, true, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD)),ps_LOG).dimension(SortInputs.NEXUS).material(SortInputs.NO_MATERIAL).get();
    public static final Block HESPER_PLANKS = sortBlock(id("hesper_planks"), Useful.BlockSettings.auto(12f),ps_PLANKS).dimension(SortInputs.NEXUS).material(SortInputs.NO_MATERIAL).parent().get();

    /* LIVING BLOCKS */
    public static final Block DEEP_DARK_HEART = sortBlock(id("deep_dark_heart"), Useful.BlockSettings.auto(60f),ps_DEMHEART).get();
    public static final Block DEEP_HEART = sortBlock(id("deep_heart"), Useful.BlockSettings.auto(40f),ps_DEMHEART).get();
    public static final Block DARK_HEART = sortBlock(id("dark_heart"), Useful.BlockSettings.auto(40f),ps_DEMHEART).get();

    /* CONDENSED BLOCKS */
    public static final Block DYREMITE_BLOCK = sortBlock(id("dyremite_block"), Useful.BlockSettings.auto(60f, 500f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.OVERWORLD).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DYREMITE_CHUNK).get();
    public static final Block DEBRITINUM_BLOCK = sortBlock(id("debritinum_block"), Useful.BlockSettings.auto(70f, 1500f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.NETHER).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DEBRITINUM_SCRAP).get();
    public static final Block PURVIUM_BLOCK = sortBlock(id("purvium_block"), Useful.BlockSettings.auto(80f, 400f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.PURVIUM_CHUNK).get();
    public static final Block SCULTIUM_BLOCK = sortBlock(id("scultium_block"), Useful.BlockSettings.auto(75f, 600f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.SCULTIUM_BONES).get();
    public static final Block DEPNETUM_BLOCK = sortBlock(id("depnetum_block"), Useful.BlockSettings.auto(80f, 1000f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DEPNETUM_CLUMP).get();
    public static final Block UNTILLIUM_BLOCK = sortBlock(id("untillium_block"), Useful.BlockSettings.auto(20f),ps_NORMAL).dimension(SortInputs.NEXUS).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.UNTILLIUM_BAR).get();

    /* ORES */
    public static final Block PRISMALITE_ORE = sortBlock(id("prismalite_ore"), Useful.BlockSettings.auto(6f),ps_ORE).dimension(SortInputs.OVERWORLD).ore(ModItems.PRISMALITE_SHARD).material(SortInputs.IRON).get();
    public static final Block PURVIUM_ORE = sortBlock(id("purvium_ore"), Useful.BlockSettings.auto(15f),ps_END_ORE).ore(ModItems.PURVIUM_CHUNK).get();
    public static final Block BURPLE_PURVIUM_ORE = sortBlock(id("burple_purvium_ore"), Useful.BlockSettings.auto(12f),ps_END_ORE).ore(ModItems.PURVIUM_CHUNK).get();
    public static final Block PURPUR_PURVIUM_ORE = sortBlock(id("purpur_purvium_ore"), Useful.BlockSettings.auto(14f),ps_END_ORE).ore(ModItems.PURVIUM_CHUNK,1,2).get();
    public static final Block SCULTIUM_ORE = sortBlock(id("scultium_ore"), Useful.BlockSettings.auto(32f),ps_DEMANDI_ORE).ore(ModItems.SCULTIUM_BONES).get();
    public static final Block DEPNETUM_ORE = sortBlock(id("depnetum_ore"), Useful.BlockSettings.auto(40f),ps_DEMANDI_ORE).ore(ModItems.DEPNETUM_CLUMP).get();
    public static final Block DARK_DEPNETUM_ORE = sortBlock(id("dark_depnetum_ore"), Useful.BlockSettings.auto(35f),ps_DEMANDI_ORE).ore(ModItems.DEPNETUM_CLUMP,1,2).get();
    public static final Block UNTILLIUM_ORE = sortBlock(id("untillium_ore"), Useful.BlockSettings.auto(22f)).inNexus().normalType().ore(ModItems.UNTILLIUM_BAR).withPickaxe().netheriteTool().get();
    public static final Block INDUG_UNTILLIUM_ORE = sortBlock(id("indug_untillium_ore"), Useful.BlockSettings.auto(24f)).inNexus().normalType().ore(ModItems.UNTILLIUM_BAR).withPickaxe().netheriteTool().get();

    /* NON-BLOCK BLOCKS */
    public static final Block CONDENSED_PURPUR_SLAB = sortBlock(id("condensed_purpur_slab"), new SlabBlock(Useful.BlockSettings.basic(8f, true)),ps_SLAB).parent(CONDENSED_PURPUR_BLOCK);
    public static final Block CONDENSED_PURPUR_STAIRS = sortBlock(id("condensed_purpur_stairs"), new StairsBlock(CONDENSED_PURPUR_BLOCK.getDefaultState(), Useful.BlockSettings.basic(8f, true)),ps_STAIRS).parent(CONDENSED_PURPUR_BLOCK);
    public static final Block BURPLE_SLAB = sortBlock(id("burple_slab"), new SlabBlock(Useful.BlockSettings.basic(6f, true)),ps_SLAB).parent(BURPLE_BLOCK);
    public static final Block BURPLE_STAIRS = sortBlock(id("burple_stairs"), new StairsBlock(BURPLE_BLOCK.getDefaultState(), Useful.BlockSettings.basic(6f, true)),ps_STAIRS).parent(BURPLE_BLOCK);
    // DEMANDI
    public static final Block DARK_SLAB = sortBlock(id("dark_slab"), new SlabBlock(Useful.BlockSettings.basic(9f, true)),ps_SLAB).parent(DARK);
    public static final Block DARK_STAIRS = sortBlock(id("dark_stairs"), new StairsBlock(DARK.getDefaultState(), Useful.BlockSettings.basic(9f, true)),ps_STAIRS).parent(DARK);
    public static final Block DEEP_STONE_SLAB = sortBlock(id("deep_stone_slab"), new SlabBlock(Useful.BlockSettings.basic(24f, true)),ps_SLAB).parent(DEEP_STONE);
    public static final Block DEEP_STONE_STAIRS = sortBlock(id("deep_stone_stairs"), new StairsBlock(DEEP_STONE.getDefaultState(), Useful.BlockSettings.basic(24f, true)),ps_STAIRS).parent(DEEP_STONE);
    public static final Block STIFF_STONE_SLAB = sortBlock(id("stiff_stone_slab"), new SlabBlock(Useful.BlockSettings.basic(30f, true)),ps_SLAB).parent(STIFF_STONE);
    public static final Block STIFF_STONE_STAIRS = sortBlock(id("stiff_stone_stairs"), new StairsBlock(STIFF_STONE.getDefaultState(), Useful.BlockSettings.basic(30f, true)),ps_STAIRS).parent(STIFF_STONE);
    public static final Block DEAD_SCULK_SLAB = sortBlock(id("dead_sculk_slab"), new SlabBlock(Useful.BlockSettings.basic(15f, true)),ps_SLAB).parent(DEAD_SCULK);
    public static final Block DEAD_SCULK_STAIRS = sortBlock(id("dead_sculk_stairs"), new StairsBlock(DEAD_SCULK.getDefaultState(), Useful.BlockSettings.basic(15f, true)),ps_STAIRS).parent(DEAD_SCULK);
    public static final Block IMPERVIUM_SLAB = sortBlock(id("impervium_slab"), new SlabBlock(Useful.BlockSettings.basic(180f, true)),ps_SLAB).parent(IMPERVIUM_BLOCK);
    public static final Block IMPERVIUM_STAIRS = sortBlock(id("impervium_stairs"), new StairsBlock(IMPERVIUM_BLOCK.getDefaultState(), Useful.BlockSettings.basic(180f, true)),ps_STAIRS).parent(IMPERVIUM_BLOCK);
    // NEXUS
    public static final Block HESPER_SLAB = sortBlock(id("hesper_slab"), new SlabBlock(Useful.BlockSettings.basic(12f, true)),ps_SLAB).parent(HESPER_PLANKS);
    public static final Block HESPER_STAIRS = sortBlock(id("hesper_stairs"), new StairsBlock(HESPER_PLANKS.getDefaultState(), Useful.BlockSettings.basic(12f, true)),ps_STAIRS).parent(HESPER_PLANKS);
    public static final Block HESPER_DOOR = sortBlock(id("hesper_door"), new DoorBlock(HESPER, Useful.BlockSettings.basic(12f, true).nonOpaque()),ps_DOOR).parent(HESPER_PLANKS);
    public static final Block HESPER_TRAPDOOR = sortBlock(id("hesper_trapdoor"), new TrapdoorBlock(HESPER, Useful.BlockSettings.basic(12f, true).nonOpaque()),ps_TRAPDOOR).parent(HESPER_PLANKS);
    public static final Block HESPER_FENCE = sortBlock(id("hesper_fence"), new FenceBlock(Useful.BlockSettings.basic(12f, true)),ps_FENCE).parent(HESPER_PLANKS);
    public static final Block HESPER_FENCE_GATE = sortBlock(id("hesper_fence_gate"), new FenceGateBlock(HESPER_WOOD, Useful.BlockSettings.basic(12f, true)),ps_FENCE_GATE).parent(HESPER_PLANKS);
    public static final Block CELESTE_STONE_SLAB = sortBlock(id("celeste_stone_slab"), new SlabBlock(Useful.BlockSettings.basic(18f, true)),ps_SLAB).parent(CELESTE_STONE);
    public static final Block CELESTE_STONE_STAIRS = sortBlock(id("celeste_stone_stairs"), new StairsBlock(CELESTE_STONE.getDefaultState(), Useful.BlockSettings.basic(18f, true)),ps_STAIRS).parent(CELESTE_STONE);
    public static final Block CELESTE_STONE_WALL = sortBlock(id("celeste_stone_wall"), new WallBlock(Useful.BlockSettings.basic(18f, true)),ps_WALL).parent(CELESTE_STONE);
    public static final Block INDUG_STONE_SLAB = sortBlock(id("indug_stone_slab"), new SlabBlock(Useful.BlockSettings.basic(20f, true)),ps_SLAB).parent(INDUG_STONE);
    public static final Block INDUG_STONE_STAIRS = sortBlock(id("indug_stone_stairs"), new StairsBlock(INDUG_STONE.getDefaultState(), Useful.BlockSettings.basic(20f, true)),ps_STAIRS).parent(INDUG_STONE);

    /* CUSTOM-RENDER BLOCKS */
    public static final Block DEAD_VINE = sortBlock(id("dead_vine"), new VineBlock(AbstractBlock.Settings.create().replaceable().noCollision()
            .ticksRandomly().strength(0.3f).sounds(BlockSoundGroup.VINE).burnable().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)),ps_VINE).dimension(SortInputs.DEMANDI).get();
    public static final Block HESPER_LEAVES = sortBlock(id("hesper_leaves"), new LeavesBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_LEAVES).strength(2f).nonOpaque()),ps_NORMAL).model("").dimension(SortInputs.NEXUS).tool(SortInputs.HOE).material(SortInputs.NO_MATERIAL).get();
    public static final Block CELESTEA = sortBlock(id("celestea"), new FlowerBlock(StatusEffects.ABSORPTION, 10, AbstractBlock.Settings.copy(Blocks.SUNFLOWER).strength(1f)),ps_FLOWER).dimension(SortInputs.NEXUS).get();

    public static void registerModBlocks() {
        HisbMod.debug("Registering Mod Blocks for " + HisbMod.id());

        HisbMod.debug("Mod Blocks: " + ModBlocks.all());
        HisbMod.debug("Slabs: " + ModBlocks.getFromBlockType(SortInputs.SLAB));
        HisbMod.debug("Stairs: " + ModBlocks.getFromBlockType(SortInputs.STAIRS));
        HisbMod.debug("Door: " + ModBlocks.getFromBlockType(SortInputs.DOOR));
        HisbMod.debug("Trapdoor: " + ModBlocks.getFromBlockType(SortInputs.TRAPDOOR));
        HisbMod.debug("Wall: " + ModBlocks.getFromBlockType(SortInputs.WALL));
        HisbMod.debug("Fence: " + ModBlocks.getFromBlockType(SortInputs.FENCE));
        HisbMod.debug("Gate: " + ModBlocks.getFromBlockType(SortInputs.FENCE_GATE));
        HisbMod.debug("Parent Blocks: " + ModBlocks.getParents().stream().map(Sorted::getBlock).toList());

        addToItemGroup(ItemGroups.BUILDING_BLOCKS,all());
    }
}