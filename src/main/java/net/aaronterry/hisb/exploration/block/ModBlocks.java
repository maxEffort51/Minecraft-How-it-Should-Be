package net.aaronterry.hisb.exploration.block;

import net.aaronterry.helper.block.BlockSettings;
import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.helper.datagen.HelperRecipeProvider;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.block.custom.FluffBlock;
import net.aaronterry.hisb.exploration.block.custom.PurifierTableBlock;
import net.aaronterry.hisb.exploration.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

public class ModBlocks extends HelperBlocks {
    public static Identifier id(String value) { return id(HisbMod.id(), value); }

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
    private static final SortingPreset ps_GRASSLIKE = ps_NORMAL.copy().model(SortInputs.GRASSLIKE);
    private static final SortingPreset ps_TORCH = ps_NORMAL.copy().model(SortInputs.TORCH);
    private static final SortingPreset ps_WALL_TORCH = ps_NORMAL.copy().model(SortInputs.WALL_TORCH).hidden();
    private static final SortingPreset ps_LOG = ps_NORMAL.copy().model(SortInputs.PILLAR).tool(SortInputs.AXE);

    private static final SortingPreset ps_ORE = new SortingPreset().model(SortInputs.NORMAL_TYPE).tool(SortInputs.PICKAXE);
    private static final SortingPreset ps_STONE = ps_NORMAL.copy().model(SortInputs.STONE_MODEL).tool(SortInputs.PICKAXE);
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
    private static final BlockSetType INFITIUM = BlockSetTypeBuilder.copyOf(BlockSetType.IRON).build(id("infitium"));

    /* TABLES */

    // new PurifierTableBlock(Useful.BlockSettings.sounds(3.5f, false, NoteBlockInstrument.BASS, BlockSoundGroup.METAL))
    public static final Block PURIFIER_TABLE = sortBlock(id("purifier_table"), new PurifierTableBlock(new BlockSettings(3.5f).sound(NoteBlockInstrument.BASS, BlockSoundGroup.METAL).get()))
            .preset(ps_TABLE).dimension(SortInputs.NO_DIM).tool(SortInputs.PICKAXE).material(SortInputs.STONE).shapelessRecipe(RecipeCategory.MISC)
            .input(new Item[]{Items.NETHER_STAR, Items.GHAST_TEAR, Items.BLAZE_ROD, Items.SPONGE, Items.BLUE_ICE}, new int[] {2,2,2,1,2})
            .needsResult().endRecipe().get();

    /* BASIC BLOCKS */
    public static final Block BURPLE_BLOCK = sortBlock(id("burple_block"), BlockSettings.makeBlock(6f),ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.SHOVEL).material(SortInputs.IRON).parent().get();
    public static final Block CONDENSED_PURPUR_BLOCK = sortBlock(id("condensed_purpur_block"), BlockSettings.makeBlock(8f),ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.PICKAXE).material(SortInputs.IRON).parent().get();
    // DEMANDI
    public static final Block DEEP = sortBlock(id("deep"), BlockSettings.makeBlock(20f),ps_DEMANDI).material(SortInputs.IRON).get();
    public static final Block DARK = sortBlock(id("dark"), BlockSettings.makeBlock(9f),ps_DEMANDI).material(SortInputs.IRON).parent().get();
    public static final Block DEEP_SCULK = sortBlock(id("deep_sculk"), BlockSettings.makeBlock(25f),ps_DEMANDI).material(SortInputs.IRON).get();
    public static final Block CORRUPTED_MUD = sortBlock(id("corrupted_mud"), BlockSettings.makeBlock(14f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.STONE).get();
    public static final Block WASHED_DARK = sortBlock(id("washed_dark"), BlockSettings.makeBlock(10f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.IRON).get();
    public static final Block WASHED_SCULK = sortBlock(id("washed_sculk"), BlockSettings.makeBlock(26f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.IRON).get();
    public static final Block STIFF_SOIL = sortBlock(id("stiff_soil"), BlockSettings.makeBlock(20f),ps_DEMANDI).tool(SortInputs.SHOVEL).material(SortInputs.DIAMOND).get();
    public static final Block DEEP_STONE = sortBlock(id("deep_stone"), BlockSettings.makeBlock(24f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.IRON).parent().get();
    public static final Block STIFF_STONE = sortBlock(id("stiff_stone"), BlockSettings.makeBlock(30f),ps_STONE).dimension(SortInputs.DEMANDI).material(SortInputs.DIAMOND).parent()
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS).input(ModBlocks.STIFF_SOIL, 4).needs(ModBlocks.STIFF_SOIL).endRecipe().get();
    public static final Block SCULKSTONE = sortBlock(id("sculkstone"), BlockSettings.makeBlock(29f),ps_STONE).dimension(SortInputs.DEMANDI).material(SortInputs.IRON).get();
    public static final Block DEAD_SCULK = sortBlock(id("dead_sculk"), BlockSettings.makeBlock(15f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND).parent().get();
    public static final Block HARDENED_SCULK = sortBlock(id("hardened_sculk"), BlockSettings.makeBlock(100f, 10f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE).get();
    public static final Block IMPERVIUM_BLOCK = sortBlock(id("impervium_block"), BlockSettings.makeBlock(200f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM).parent().get();
    // NEXUS
    public static final Block CELESTE_BLOCK = sortBlock(id("celeste_block"), BlockSettings.makeBlock(15f)).inNexus().normalType().dropSelf().withShovel().anyMaterial().get();
    public static final Block CELESTE_STONE = sortBlock(id("celeste_stone"), BlockSettings.makeBlock(18f),ps_STONE).dimension(SortInputs.NEXUS).material(SortInputs.IRON).parent().get();
    public static final Block INDUG_STONE = sortBlock(id("indug_stone"), BlockSettings.makeBlock(20f),ps_STONE).dimension(SortInputs.NEXUS).material(SortInputs.IRON).parent().get();
    // RARE
    public static final Block CLOVE_BLOCK = sortBlock(id("clove_block"), new BlockSettings(1.2f).sound(BlockSoundGroup.GRAVEL).piston(PistonBehavior.PUSH_ONLY).requires().getBlock(),ps_NORMAL).dimension(SortInputs.RARE).tool(SortInputs.SHOVEL).material(SortInputs.STONE).get();
    public static final Block FINCHSTONE = sortBlock(id("finchstone"), new BlockSettings(10f).sound(BlockSoundGroup.STONE).piston(PistonBehavior.DESTROY).requires().getBlock(),ps_STONE).parent().dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.STONE).get();
    public static final Block CEMENT = sortBlock(id("cement"), new BlockSettings(15f).sound(BlockSoundGroup.PACKED_MUD).piston(PistonBehavior.PUSH_ONLY).getBlock(),ps_NORMAL).dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.NO_MATERIAL).get();
    public static final Block COMPACT_CONCRETE = sortBlock(id("compact_concrete"), new BlockSettings(35f).sound(NoteBlockInstrument.BASEDRUM, BlockSoundGroup.INTENTIONALLY_EMPTY).getBlock(),ps_NORMAL).parent().dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.IRON).get();
    public static final Block WHITE_COMPACT_CONCRETE = sortBlock(id("white_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.WHITE).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block BLACK_COMPACT_CONCRETE = sortBlock(id("black_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLACK).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block BLUE_COMPACT_CONCRETE = sortBlock(id("blue_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLUE).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block LIGHT_BLUE_COMPACT_CONCRETE = sortBlock(id("light_blue_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_BLUE).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block YELLOW_COMPACT_CONCRETE = sortBlock(id("yellow_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.YELLOW).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block BROWN_COMPACT_CONCRETE = sortBlock(id("brown_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BROWN).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block CYAN_COMPACT_CONCRETE = sortBlock(id("cyan_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.CYAN).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block GRAY_COMPACT_CONCRETE = sortBlock(id("gray_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GRAY).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block GREEN_COMPACT_CONCRETE = sortBlock(id("green_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GREEN).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block LIGHT_GRAY_COMPACT_CONCRETE = sortBlock(id("light_gray_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_GRAY).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block LIME_COMPACT_CONCRETE = sortBlock(id("lime_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIME).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block MAGENTA_COMPACT_CONCRETE = sortBlock(id("magenta_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.MAGENTA).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block ORANGE_COMPACT_CONCRETE = sortBlock(id("orange_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.ORANGE).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block PINK_COMPACT_CONCRETE = sortBlock(id("pink_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PINK).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block PURPLE_COMPACT_CONCRETE = sortBlock(id("purple_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PURPLE).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    public static final Block RED_COMPACT_CONCRETE = sortBlock(id("red_compact_concrete"), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.RED).getBlock(),ps_NORMAL).parent().copy(COMPACT_CONCRETE);
    // VORTEX
    public static final Block BLIPNUM = sortBlock(id("blipnum"), new BlockSettings(30f,120f).sound(BlockSoundGroup.STONE).piston(PistonBehavior.BLOCK).requires().getBlock(),ps_NORMAL).dimension(SortInputs.VORTEX).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND).get();
    public static final Block BRAWNSTONE = sortBlock(id("brawnstone"), new BlockSettings(40f).sound(BlockSoundGroup.DEEPSLATE).piston(PistonBehavior.BLOCK).requires().getBlock(),ps_NORMAL).dimension(SortInputs.VORTEX).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND).get();
    public static final Block GRENT_BLOCK = sortBlock(id("grent_block"), BlockSettings.copy(Blocks.COARSE_DIRT).strength(1.2f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.VORTEX).tool(SortInputs.SHOVEL).material(SortInputs.STONE).get();
    public static final Block CLEANSTONE = sortBlock(id("cleanstone"), BlockSettings.copy(BLIPNUM).strength(26f,140f).getBlock(),ps_NORMAL).parent().copy(BLIPNUM);
    public static final Block SPRING_WATER = sortBlock(id("spring_water"), new FluidBlock(Fluids.WATER,BlockSettings.copy(Blocks.WATER).get()),ps_NORMAL).copy(BLIPNUM);
    // INFINITE
    public static final Block DUST_BLOCK = sortBlock(id("dust_block"), new ColoredFallingBlock(new ColorCode(12854685), BlockSettings.copy(Blocks.RED_SAND).strength(1.3f).get()),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();
    public static final Block DUST = sortBlock(id("dust"), new SnowBlock(BlockSettings.copy(Blocks.SNOW).strength(0.3f).get()),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();
    public static final Block DUSTSAND = sortBlock(id("dustsand"), BlockSettings.copy(Blocks.SAND).strength(12.3f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.NO_MATERIAL).get();
    public static final Block DUSTSTONE = sortBlock(id("duststone"), BlockSettings.copy(Blocks.SANDSTONE).strength(25.4f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.STONE).get();
    public static final Block SMOOTH_DUSTSTONE = sortBlock(id("smooth_duststone"), BlockSettings.copy(Blocks.SMOOTH_SANDSTONE).strength(25.4f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.STONE).get();
    public static final Block CHISELED_DUSTSTONE = sortBlock(id("chiseled_duststone"), BlockSettings.copy(Blocks.CHISELED_SANDSTONE).strength(25.4f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.STONE).get();
    public static final Block CUT_DUSTSTONE = sortBlock(id("cut_duststone"), BlockSettings.copy(Blocks.CUT_SANDSTONE).strength(25.4f).requires().getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.STONE).get();
    public static final Block CLEAR_FOG = sortBlock(id("clear_fog"), new AirBlock(BlockSettings.copy(Blocks.CAVE_AIR).strength(0.8f).get()),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL)
            .shapedRecipe(RecipeCategory.DECORATIONS).input('*',ModItems.CLOUDFLUFF).input('%',ModItems.AFLITE).pattern(new HelperRecipeProvider.Pattern(" * ","*%*"," * ")).needs(ModItems.CLOUDFLUFF).endRecipe().get();
    public static final Block CLEAR_CORE = sortBlock(id("clear_core"), new FluffBlock(0.02f, BlockSettings.copy(Blocks.HAY_BLOCK).strength(2f).get()),ps_NORMAL).ore(ModItems.CLOUDFLUFF).dimension(SortInputs.INFINITE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL)
            .shapelessRecipe(RecipeCategory.DECORATIONS).input(ModItems.CLOUDFLUFF,9).needs(ModItems.CLOUDFLUFF).endRecipe().get();
    public static final Block AFLITE_BLOCK = sortBlock(id("aflite_block"), new FluffBlock(0.07f, BlockSettings.copy(CLEAR_CORE).strength(15f).get()),ps_ORE).ore(ModItems.AFLITE).dimension(SortInputs.INFINITE).material(SortInputs.STONE)
            .shapelessRecipe(RecipeCategory.TOOLS).input(ModItems.AFLITE,9).needs(ModItems.CLOUDFLUFF).endRecipe().get();
    public static final Block INFITIUM_GLASS = sortBlock(id("infitium_glass"), new StainedGlassBlock(DyeColor.ORANGE, BlockSettings.copy(Blocks.ORANGE_STAINED_GLASS).strength(0.8f,500f).get()),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.HAND).material(SortInputs.NO_MATERIAL).get();
    // UNTER
    public static final Block UNTSTONE = sortBlock(id("untstone"), new BlockSettings(3.8f, 6.7f).sound(NoteBlockInstrument.BASEDRUM,BlockSoundGroup.STONE).requires().getBlock(),ps_STONE).dimension(SortInputs.UNTER).parent().get();
    public static final Block BLEAKSTONE = sortBlock(id("bleakstone"), new BlockSettings(6f, 10f).sound(NoteBlockInstrument.BASEDRUM,BlockSoundGroup.STONE).requires().getBlock(),ps_STONE).dimension(SortInputs.UNTER).material(SortInputs.STONE).get();
    public static final Block PALE_ICE = sortBlock(id("pale_ice"), new TranslucentBlock(BlockSettings.copy(Blocks.BLUE_ICE).strength(4.6f).ice(0.991f).get()),ps_NORMAL).dimension(SortInputs.UNTER).tool(SortInputs.HAND).material(SortInputs.NO_MATERIAL).get();
    public static final Block PALE_SNOW_BLOCK = sortBlock(id("pale_snow_block"), BlockSettings.copy(Blocks.SNOW_BLOCK).strength(0.7f).getBlock(),ps_NORMAL).dimension(SortInputs.UNTER).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();
    public static final Block PALE_SNOW = sortBlock(id("pale_snow"), new SnowBlock(BlockSettings.copy(Blocks.SNOW).strength(0.4f).get()),ps_NORMAL).dimension(SortInputs.UNTER).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();

    /* WOOD BLOCKS */
    public static final Block DEAD_LOG = sortBlock(id("dead_log"), new PillarBlock(new BlockSettings(8f, 2.0f).requires().sound(NoteBlockInstrument.BASS, BlockSoundGroup.WOOD).get()),ps_LOG).dimension(SortInputs.DEMANDI).material(SortInputs.STONE).get();
    public static final Block DEAD_PLANKS = sortBlock(id("dead_planks"), BlockSettings.makeBlock(10f),ps_DEMANDI).tool(SortInputs.AXE).material(SortInputs.STONE)
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, 4).input(ModBlocks.DEAD_LOG).needs(ModBlocks.DEAD_LOG).endRecipe().get();
    // NEXUS
    public static final Block HESPER_LOG = sortBlock(id("hesper_log"), new PillarBlock(new BlockSettings(10f, 1.0f).requires().sound(NoteBlockInstrument.BASS, BlockSoundGroup.WOOD).get()),ps_LOG).dimension(SortInputs.NEXUS).material(SortInputs.NO_MATERIAL).get();
    public static final Block HESPER_PLANKS = sortBlock(id("hesper_planks"), BlockSettings.makeBlock(12f),ps_PLANKS).dimension(SortInputs.NEXUS).material(SortInputs.NO_MATERIAL).parent()
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, 4).input(ModBlocks.HESPER_LOG).needs(ModBlocks.HESPER_LOG).endRecipe().get();
    // RARE
    public static final Block LAUREL_LOG = sortBlock(id("laurel_log"), new PillarBlock(new BlockSettings(12f, 4.0f).sound(NoteBlockInstrument.BASS, BlockSoundGroup.WOOD).get()),ps_LOG).dimension(SortInputs.RARE).material(SortInputs.NO_MATERIAL).get();
    public static final Block LAUREL_PLANKS = sortBlock(id("laurel_planks"), BlockSettings.makeBlock(13f),ps_PLANKS).dimension(SortInputs.RARE).material(SortInputs.NO_MATERIAL).parent()
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, 4).input(ModBlocks.LAUREL_LOG).needs(ModBlocks.LAUREL_LOG).endRecipe().get();
    // UNTER
    public static final Block REDBUD_LOG = sortBlock(id("redbud_log"), new PillarBlock(new BlockSettings(18f, -1.0f).sound(NoteBlockInstrument.BASS, BlockSoundGroup.WOOD).get()),ps_LOG).dimension(SortInputs.UNTER).material(SortInputs.NO_MATERIAL).get();
    public static final Block REDBUD_PLANKS = sortBlock(id("redbud_planks"), BlockSettings.makeBlock(19f),ps_PLANKS).dimension(SortInputs.RARE).material(SortInputs.NO_MATERIAL).parent()
            .shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, 4).input(ModBlocks.REDBUD_LOG).needs(ModBlocks.REDBUD_LOG).endRecipe().get();

    /* LIVING BLOCKS */
    public static final Block DEEP_DARK_HEART = sortBlock(id("deep_dark_heart"), BlockSettings.makeBlock(60f),ps_DEMHEART).get();
    public static final Block DEEP_HEART = sortBlock(id("deep_heart"), BlockSettings.makeBlock(40f),ps_DEMHEART).get();
    public static final Block DARK_HEART = sortBlock(id("dark_heart"), BlockSettings.makeBlock(40f),ps_DEMHEART).get();

    /* CONDENSED BLOCKS */
    public static final Block DYREMITE_BLOCK = sortBlock(id("dyremite_block"), BlockSettings.makeBlock(60f, 80f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.OVERWORLD).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DYREMITE_CHUNK).get();
    public static final Block DEBRITINUM_BLOCK = sortBlock(id("debritinum_block"), BlockSettings.makeBlock(70f, 100f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.NETHER).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DEBRITINUM_SCRAP).get();
    public static final Block PURVIUM_BLOCK = sortBlock(id("purvium_block"), BlockSettings.makeBlock(80f, 100f), Rarity.UNCOMMON,ps_NORMAL).dimension(SortInputs.END).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.PURVIUM_CHUNK).get();
    public static final Block SCULTIUM_BLOCK = sortBlock(id("scultium_block"), BlockSettings.makeBlock(75f, 200f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.SCULTIUM_BONES).get();
    public static final Block DEPNETUM_BLOCK = sortBlock(id("depnetum_block"), BlockSettings.makeBlock(80f, 100f),ps_DEMANDI).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.DEPNETUM_CLUMP).get();
    public static final Block UNTILLIUM_BLOCK = sortBlock(id("untillium_block"), BlockSettings.makeBlock(70f),ps_NORMAL).dimension(SortInputs.NEXUS).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.UNTILLIUM_BAR).get();
    public static final Block JADE_BLOCK = sortBlock(id("jade_block"), BlockSettings.makeBlock(50f),ps_NORMAL).dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.JADE).get();
    public static final Block FORTOLIUM_BLOCK = sortBlock(id("fortolium_block"), BlockSettings.makeBlock(48f),ps_NORMAL).dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.FORTOLIUM).get();
    public static final Block VORMITE_BLOCK = sortBlock(id("vormite_block"), BlockSettings.makeBlock(80f,200f),ps_NORMAL).dimension(SortInputs.VORTEX).tool(SortInputs.PICKAXE).material(SortInputs.SCULTIUM)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.VORMITE_CLUMP).get();
    public static final Block IVORY_BLOCK = sortBlock(id("ivory_block"), BlockSettings.copy(Blocks.BONE_BLOCK).colorMap(MapColor.OFF_WHITE).strength(5f,100f).getBlock(),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.IRON)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.IVORY_TUSK).get();
    public static final Block INFITIUM_BLOCK = sortBlock(id("infitium_block"), BlockSettings.makeBlock(70f,400f),ps_NORMAL).dimension(SortInputs.INFINITE).tool(SortInputs.PICKAXE).material(SortInputs.NETHERITE)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.INFITIUM_RING).parent().get();
    public static final Block PALLECOLDIUM_BLOCK = sortBlock(id("pallecoldium_block"), BlockSettings.makeBlock(40f,10f),ps_NORMAL).dimension(SortInputs.UNTER).tool(SortInputs.PICKAXE).material(SortInputs.IRON)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.PALLECOLDIUM_ALLOY).get();
    public static final Block ARMITE_BLOCK = sortBlock(id("armite_block"), BlockSettings.makeBlock(60f,30f),ps_NORMAL).dimension(SortInputs.UNTER).tool(SortInputs.PICKAXE).material(SortInputs.DIAMOND)
            .reverseRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.ARMITE_CHUNK).get();

    /* ORES */
    public static final Block PRISMALITE_ORE = sortBlock(id("prismalite_ore"), BlockSettings.makeBlock(6f),ps_ORE).dimension(SortInputs.OVERWORLD).ore(ModItems.PRISMALITE_SHARD).material(SortInputs.IRON).get();
    public static final Block PURVIUM_ORE = sortBlock(id("purvium_ore"), BlockSettings.makeBlock(15f),ps_END_ORE).ore(ModItems.PURVIUM_CHUNK).get();
    public static final Block BURPLE_PURVIUM_ORE = sortBlock(id("burple_purvium_ore"), BlockSettings.makeBlock(12f),ps_END_ORE).copy(PURVIUM_ORE);
    public static final Block PURPUR_PURVIUM_ORE = sortBlock(id("purpur_purvium_ore"), BlockSettings.makeBlock(14f),ps_END_ORE).copy(PURVIUM_ORE);
    public static final Block SCULTIUM_ORE = sortBlock(id("scultium_ore"), BlockSettings.makeBlock(32f),ps_DEMANDI_ORE).ore(ModItems.SCULTIUM_BONES).get();
    public static final Block DEPNETUM_ORE = sortBlock(id("depnetum_ore"), BlockSettings.makeBlock(40f),ps_DEMANDI_ORE).ore(ModItems.DEPNETUM_CLUMP).get();
    public static final Block DARK_DEPNETUM_ORE = sortBlock(id("dark_depnetum_ore"), BlockSettings.makeBlock(35f),ps_DEMANDI_ORE).copy(DEPNETUM_ORE);
    public static final Block UNTILLIUM_ORE = sortBlock(id("untillium_ore"), BlockSettings.makeBlock(25f)).inNexus().normalType().ore(ModItems.UNTILLIUM_BAR).withPickaxe().netheriteTool().get();
    public static final Block INDUG_UNTILLIUM_ORE = sortBlock(id("indug_untillium_ore"), BlockSettings.makeBlock(28f),ps_ORE).copy(UNTILLIUM_ORE);
    public static final Block JADE_ORE = sortBlock(id("jade_ore"), BlockSettings.makeBlock(30f)).inNexus().normalType().ore(ModItems.JADE).withPickaxe().diamondTool().get();
    public static final Block VORMITE_ORE = sortBlock(id("vormite_ore"), BlockSettings.makeBlock(35f),ps_ORE).dimension(SortInputs.VORTEX).ore(ModItems.VORMITE_CLUMP).tool(SortInputs.NETHERITE).get();
    public static final Block BRAWNSTONE_VORMITE_ORE = sortBlock(id("brawnstone_vormite_ore"), BlockSettings.makeBlock(38f),ps_ORE).copy(VORMITE_ORE);
    public static final Block INFITIUM_ORE = sortBlock(id("infitium_ore"), BlockSettings.makeBlock(34f),ps_ORE).dimension(SortInputs.INFINITE).ore(ModItems.INFITIUM_RING).tool(SortInputs.DIAMOND).get();
    public static final Block ARMITE_ORE = sortBlock(id("armite_ore"), BlockSettings.makeBlock(32f),ps_ORE).dimension(SortInputs.UNTER).ore(ModItems.ARMITE_CHUNK).tool(SortInputs.IRON).get();
    public static final Block BLEAKSTONE_ARMITE_ORE = sortBlock(id("bleakstone_armite_ore"), BlockSettings.makeBlock(32f),ps_ORE).copy(ARMITE_ORE);

    /* NON-BLOCK BLOCKS */
    public static final Block CONDENSED_PURPUR_SLAB = sortBlock(id("condensed_purpur_slab"), new SlabBlock(new BlockSettings(8f).requires().get()),ps_SLAB).slabRecipe(CONDENSED_PURPUR_BLOCK);
    public static final Block CONDENSED_PURPUR_STAIRS = sortBlock(id("condensed_purpur_stairs"), new StairsBlock(CONDENSED_PURPUR_BLOCK.getDefaultState(), new BlockSettings(8f).requires().get()),ps_STAIRS).stairsRecipe(CONDENSED_PURPUR_BLOCK);
    public static final Block BURPLE_SLAB = sortBlock(id("burple_slab"), new SlabBlock(new BlockSettings(6f).requires().get()),ps_SLAB).slabRecipe(BURPLE_BLOCK);
    public static final Block BURPLE_STAIRS = sortBlock(id("burple_stairs"), new StairsBlock(BURPLE_BLOCK.getDefaultState(), new BlockSettings(6f).requires().get()),ps_STAIRS).stairsRecipe(BURPLE_BLOCK);
    // DEMANDI
    public static final Block DARK_SLAB = sortBlock(id("dark_slab"), new SlabBlock(new BlockSettings(9f).requires().get()),ps_SLAB).slabRecipe(DARK);
    public static final Block DARK_STAIRS = sortBlock(id("dark_stairs"), new StairsBlock(DARK.getDefaultState(), new BlockSettings(9f).requires().get()),ps_STAIRS).stairsRecipe(DARK);
    public static final Block DEEP_STONE_SLAB = sortBlock(id("deep_stone_slab"), new SlabBlock(new BlockSettings(24f).requires().get()),ps_SLAB).slabRecipe(DEEP_STONE);
    public static final Block DEEP_STONE_STAIRS = sortBlock(id("deep_stone_stairs"), new StairsBlock(DEEP_STONE.getDefaultState(), new BlockSettings(24f).requires().get()),ps_STAIRS).stairsRecipe(DEEP_STONE);
    public static final Block STIFF_STONE_SLAB = sortBlock(id("stiff_stone_slab"), new SlabBlock(new BlockSettings(30f).requires().get()),ps_SLAB).slabRecipe(STIFF_STONE);
    public static final Block STIFF_STONE_STAIRS = sortBlock(id("stiff_stone_stairs"), new StairsBlock(STIFF_STONE.getDefaultState(), new BlockSettings(30f).requires().get()),ps_STAIRS).stairsRecipe(STIFF_STONE);
    public static final Block DEAD_SCULK_SLAB = sortBlock(id("dead_sculk_slab"), new SlabBlock(new BlockSettings(15f).requires().get()),ps_SLAB).slabRecipe(DEAD_SCULK);
    public static final Block DEAD_SCULK_STAIRS = sortBlock(id("dead_sculk_stairs"), new StairsBlock(DEAD_SCULK.getDefaultState(), new BlockSettings(15f).requires().get()),ps_STAIRS).stairsRecipe(DEAD_SCULK);
    public static final Block IMPERVIUM_SLAB = sortBlock(id("impervium_slab"), new SlabBlock(new BlockSettings(180f).requires().get()),ps_SLAB).slabRecipe(IMPERVIUM_BLOCK);
    public static final Block IMPERVIUM_STAIRS = sortBlock(id("impervium_stairs"), new StairsBlock(IMPERVIUM_BLOCK.getDefaultState(), new BlockSettings(180f).requires().get()),ps_STAIRS).stairsRecipe(IMPERVIUM_BLOCK);
    // NEXUS
    public static final Block HESPER_SLAB = sortBlock(id("hesper_slab"), new SlabBlock(new BlockSettings(12f).requires().get()),ps_SLAB).slabRecipe(HESPER_PLANKS);
    public static final Block HESPER_STAIRS = sortBlock(id("hesper_stairs"), new StairsBlock(HESPER_PLANKS.getDefaultState(), new BlockSettings(12f).requires().get()),ps_STAIRS).stairsRecipe(HESPER_PLANKS);
    public static final Block HESPER_DOOR = sortBlock(id("hesper_door"), new DoorBlock(HESPER, new BlockSettings(12f).requires().model("non_opaque").get()),ps_DOOR).doorRecipe(HESPER_PLANKS);
    public static final Block HESPER_TRAPDOOR = sortBlock(id("hesper_trapdoor"), new TrapdoorBlock(HESPER, new BlockSettings(12f).requires().model("non_opaque").get()),ps_TRAPDOOR).trapdoorRecipe(HESPER_PLANKS);
    public static final Block HESPER_FENCE = sortBlock(id("hesper_fence"), new FenceBlock(new BlockSettings(12f).requires().get()),ps_FENCE).fenceRecipe(HESPER_PLANKS);
    public static final Block HESPER_FENCE_GATE = sortBlock(id("hesper_fence_gate"), new FenceGateBlock(HESPER_WOOD, new BlockSettings(12f).requires().get()),ps_FENCE_GATE).fenceGateRecipe(HESPER_PLANKS);

    public static final Block CELESTE_STONE_SLAB = sortBlock(id("celeste_stone_slab"), new SlabBlock(new BlockSettings(18f).requires().get()),ps_SLAB).slabRecipe(CELESTE_STONE);
    public static final Block CELESTE_STONE_STAIRS = sortBlock(id("celeste_stone_stairs"), new StairsBlock(CELESTE_STONE.getDefaultState(), new BlockSettings(18f).requires().get()),ps_STAIRS).stairsRecipe(CELESTE_STONE);
    public static final Block CELESTE_STONE_WALL = sortBlock(id("celeste_stone_wall"), new WallBlock(new BlockSettings(18f).requires().get()),ps_WALL).wallRecipe(CELESTE_STONE);
    public static final Block INDUG_STONE_SLAB = sortBlock(id("indug_stone_slab"), new SlabBlock(new BlockSettings(20f).requires().get()),ps_SLAB).slabRecipe(INDUG_STONE);
    public static final Block INDUG_STONE_STAIRS = sortBlock(id("indug_stone_stairs"), new StairsBlock(INDUG_STONE.getDefaultState(), new BlockSettings(20f).requires().get()),ps_STAIRS).stairsRecipe(INDUG_STONE);
    // RARE
    public static final Block LAUREL_SLAB = sortBlock(id("laurel_slab"), new SlabBlock(BlockSettings.copy(LAUREL_PLANKS).get()),ps_SLAB).slabRecipe(LAUREL_PLANKS);
    public static final Block LAUREL_STAIRS = sortBlock(id("laurel_stairs"), new StairsBlock(LAUREL_PLANKS.getDefaultState(), BlockSettings.copy(LAUREL_PLANKS).get()),ps_STAIRS).stairsRecipe(LAUREL_PLANKS);
    public static final Block FINCHSTONE_SLAB = sortBlock(id("finchstone_slab"), new SlabBlock(BlockSettings.copy(FINCHSTONE).get()),ps_SLAB).slabRecipe(FINCHSTONE);
    public static final Block FINCHSTONE_STAIRS = sortBlock(id("finchstone_stairs"), new StairsBlock(FINCHSTONE.getDefaultState(), BlockSettings.copy(FINCHSTONE).get()),ps_STAIRS).stairsRecipe(FINCHSTONE);
    public static final Block FINCHSTONE_WALL = sortBlock(id("finchstone_wall"), new WallBlock(BlockSettings.copy(FINCHSTONE).get()),ps_WALL).wallRecipe(FINCHSTONE);

    public static final Block COMPACT_CONCRETE_SLAB = sortBlock(id("compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).get()),ps_SLAB).slabRecipe(COMPACT_CONCRETE);
    public static final Block COMPACT_CONCRETE_STAIRS = sortBlock(id("compact_concrete_stairs"), new StairsBlock(COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).get()),ps_STAIRS).stairsRecipe(COMPACT_CONCRETE);

    public static final Block WHITE_COMPACT_CONCRETE_SLAB = sortBlock(id("white_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.WHITE).get()),ps_SLAB).slabRecipe(WHITE_COMPACT_CONCRETE);
    public static final Block WHITE_COMPACT_CONCRETE_STAIRS = sortBlock(id("white_compact_concrete_stairs"), new StairsBlock(WHITE_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.WHITE).get()),ps_STAIRS).stairsRecipe(WHITE_COMPACT_CONCRETE);

    public static final Block BLACK_COMPACT_CONCRETE_SLAB = sortBlock(id("black_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLACK).get()),ps_SLAB).slabRecipe(BLACK_COMPACT_CONCRETE);
    public static final Block BLACK_COMPACT_CONCRETE_STAIRS = sortBlock(id("black_compact_concrete_stairs"), new StairsBlock(BLACK_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLACK).get()),ps_STAIRS).stairsRecipe(BLACK_COMPACT_CONCRETE);

    public static final Block BLUE_COMPACT_CONCRETE_SLAB = sortBlock(id("blue_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLUE).get()),ps_SLAB).slabRecipe(BLUE_COMPACT_CONCRETE);
    public static final Block BLUE_COMPACT_CONCRETE_STAIRS = sortBlock(id("blue_compact_concrete_stairs"), new StairsBlock(BLUE_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BLUE).get()),ps_STAIRS).stairsRecipe(BLUE_COMPACT_CONCRETE);

    public static final Block LIGHT_BLUE_COMPACT_CONCRETE_SLAB = sortBlock(id("light_blue_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_BLUE).get()),ps_SLAB).slabRecipe(LIGHT_BLUE_COMPACT_CONCRETE);
    public static final Block LIGHT_BLUE_COMPACT_CONCRETE_STAIRS = sortBlock(id("light_blue_compact_concrete_stairs"), new StairsBlock(LIGHT_BLUE_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_BLUE).get()),ps_STAIRS).stairsRecipe(LIGHT_BLUE_COMPACT_CONCRETE);

    public static final Block YELLOW_COMPACT_CONCRETE_SLAB = sortBlock(id("yellow_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.YELLOW).get()),ps_SLAB).slabRecipe(YELLOW_COMPACT_CONCRETE);
    public static final Block YELLOW_COMPACT_CONCRETE_STAIRS = sortBlock(id("yellow_compact_concrete_stairs"), new StairsBlock(YELLOW_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.YELLOW).get()),ps_STAIRS).stairsRecipe(YELLOW_COMPACT_CONCRETE);

    public static final Block BROWN_COMPACT_CONCRETE_SLAB = sortBlock(id("brown_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BROWN).get()),ps_SLAB).slabRecipe(BROWN_COMPACT_CONCRETE);
    public static final Block BROWN_COMPACT_CONCRETE_STAIRS = sortBlock(id("brown_compact_concrete_stairs"), new StairsBlock(BROWN_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.BROWN).get()),ps_STAIRS).stairsRecipe(BROWN_COMPACT_CONCRETE);

    public static final Block CYAN_COMPACT_CONCRETE_SLAB = sortBlock(id("cyan_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.CYAN).get()),ps_SLAB).slabRecipe(CYAN_COMPACT_CONCRETE);
    public static final Block CYAN_COMPACT_CONCRETE_STAIRS = sortBlock(id("cyan_compact_concrete_stairs"), new StairsBlock(CYAN_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.CYAN).get()),ps_STAIRS).stairsRecipe(CYAN_COMPACT_CONCRETE);

    public static final Block GRAY_COMPACT_CONCRETE_SLAB = sortBlock(id("gray_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GRAY).get()),ps_SLAB).slabRecipe(GRAY_COMPACT_CONCRETE);
    public static final Block GRAY_COMPACT_CONCRETE_STAIRS = sortBlock(id("gray_compact_concrete_stairs"), new StairsBlock(GRAY_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GRAY).get()),ps_STAIRS).stairsRecipe(GRAY_COMPACT_CONCRETE);

    public static final Block GREEN_COMPACT_CONCRETE_SLAB = sortBlock(id("green_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GREEN).get()),ps_SLAB).slabRecipe(GREEN_COMPACT_CONCRETE);
    public static final Block GREEN_COMPACT_CONCRETE_STAIRS = sortBlock(id("green_compact_concrete_stairs"), new StairsBlock(GREEN_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.GREEN).get()),ps_STAIRS).stairsRecipe(GREEN_COMPACT_CONCRETE);

    public static final Block LIGHT_GRAY_COMPACT_CONCRETE_SLAB = sortBlock(id("light_gray_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_GRAY).get()),ps_SLAB).slabRecipe(LIGHT_GRAY_COMPACT_CONCRETE);
    public static final Block LIGHT_GRAY_COMPACT_CONCRETE_STAIRS = sortBlock(id("light_gray_compact_concrete_stairs"), new StairsBlock(LIGHT_GRAY_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIGHT_GRAY).get()),ps_STAIRS).stairsRecipe(LIGHT_GRAY_COMPACT_CONCRETE);

    public static final Block LIME_COMPACT_CONCRETE_SLAB = sortBlock(id("lime_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIME).get()),ps_SLAB).slabRecipe(LIME_COMPACT_CONCRETE);
    public static final Block LIME_COMPACT_CONCRETE_STAIRS = sortBlock(id("lime_compact_concrete_stairs"), new StairsBlock(LIME_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.LIME).get()),ps_STAIRS).stairsRecipe(LIME_COMPACT_CONCRETE);

    public static final Block MAGENTA_COMPACT_CONCRETE_SLAB = sortBlock(id("magenta_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.MAGENTA).get()),ps_SLAB).slabRecipe(MAGENTA_COMPACT_CONCRETE);
    public static final Block MAGENTA_COMPACT_CONCRETE_STAIRS = sortBlock(id("magenta_compact_concrete_stairs"), new StairsBlock(MAGENTA_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.MAGENTA).get()),ps_STAIRS).stairsRecipe(MAGENTA_COMPACT_CONCRETE);

    public static final Block ORANGE_COMPACT_CONCRETE_SLAB = sortBlock(id("orange_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.ORANGE).get()),ps_SLAB).slabRecipe(ORANGE_COMPACT_CONCRETE);
    public static final Block ORANGE_COMPACT_CONCRETE_STAIRS = sortBlock(id("orange_compact_concrete_stairs"), new StairsBlock(ORANGE_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.ORANGE).get()),ps_STAIRS).stairsRecipe(ORANGE_COMPACT_CONCRETE);

    public static final Block PINK_COMPACT_CONCRETE_SLAB = sortBlock(id("pink_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PINK).get()),ps_SLAB).slabRecipe(PINK_COMPACT_CONCRETE);
    public static final Block PINK_COMPACT_CONCRETE_STAIRS = sortBlock(id("pink_compact_concrete_stairs"), new StairsBlock(PINK_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PINK).get()),ps_STAIRS).stairsRecipe(PINK_COMPACT_CONCRETE);

    public static final Block PURPLE_COMPACT_CONCRETE_SLAB = sortBlock(id("purple_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PURPLE).get()),ps_SLAB).slabRecipe(PURPLE_COMPACT_CONCRETE);
    public static final Block PURPLE_COMPACT_CONCRETE_STAIRS = sortBlock(id("purple_compact_concrete_stairs"), new StairsBlock(PURPLE_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.PURPLE).get()),ps_STAIRS).stairsRecipe(PURPLE_COMPACT_CONCRETE);

    public static final Block RED_COMPACT_CONCRETE_SLAB = sortBlock(id("red_compact_concrete_slab"), new SlabBlock(BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.RED).get()),ps_SLAB).slabRecipe(RED_COMPACT_CONCRETE);
    public static final Block RED_COMPACT_CONCRETE_STAIRS = sortBlock(id("red_compact_concrete_stairs"), new StairsBlock(RED_COMPACT_CONCRETE.getDefaultState(), BlockSettings.copy(COMPACT_CONCRETE).colorMap(DyeColor.RED).get()),ps_STAIRS).stairsRecipe(RED_COMPACT_CONCRETE);

    // VORTEX
    public static final Block CLEANSTONE_SLAB = sortBlock(id("cleanstone_slab"), new SlabBlock(BlockSettings.copy(CLEANSTONE).get()),ps_SLAB).slabRecipe(CLEANSTONE);
    public static final Block CLEANSTONE_STAIRS = sortBlock(id("cleanstone_stairs"), new StairsBlock(CLEANSTONE.getDefaultState(), BlockSettings.copy(CLEANSTONE).get()),ps_STAIRS).stairsRecipe(CLEANSTONE);
    // INFINITE
    public static final Block INFITIUM_SLAB = sortBlock(id("infitium_slab"), new SlabBlock(BlockSettings.copy(INFITIUM_BLOCK).get()),ps_SLAB).slabRecipe(INFITIUM_BLOCK);
    public static final Block INFITIUM_STAIRS = sortBlock(id("infitium_stairs"), new StairsBlock(INFITIUM_BLOCK.getDefaultState(), BlockSettings.copy(INFITIUM_BLOCK).get()),ps_STAIRS).stairsRecipe(INFITIUM_BLOCK);
    public static final Block INFITIUM_DOOR = sortBlock(id("infitium_door"), new DoorBlock(INFITIUM, new BlockSettings(18f).requires().model("non_opaque").get()),ps_DOOR).doorRecipe(INFITIUM_BLOCK);
    public static final Block INFITIUM_WALL = sortBlock(id("infitium_wall"), new WallBlock(new BlockSettings(18f).requires().get()),ps_WALL).wallRecipe(INFITIUM_BLOCK);
    // UNTER
    public static final Block UNTSTONE_SLAB = sortBlock(id("untstone_slab"), new SlabBlock(BlockSettings.copy(UNTSTONE).get()),ps_SLAB).slabRecipe(UNTSTONE);
    public static final Block UNTSTONE_STAIRS = sortBlock(id("untstone_stairs"), new StairsBlock(UNTSTONE.getDefaultState(), BlockSettings.copy(UNTSTONE).get()),ps_STAIRS).stairsRecipe(UNTSTONE);

    /* CUSTOM-RENDER BLOCKS */
    public static final Block DEAD_VINE = sortBlock(id("dead_vine"), new VineBlock(new BlockSettings(0.3f).replace().noCollide().randomTick().sound(BlockSoundGroup.VINE).burn().model("non_opaque").piston(PistonBehavior.DESTROY).get()),ps_VINE).dimension(SortInputs.DEMANDI).get();
    public static final Block HESPER_LEAVES = sortBlock(id("hesper_leaves"), new LeavesBlock(BlockSettings.copy(Blocks.MANGROVE_LEAVES).strength(2f).model("non_opaque").get()),ps_NORMAL).model("").dimension(SortInputs.NEXUS).tool(SortInputs.HOE).material(SortInputs.NO_MATERIAL).get();
    public static final Block CELESTEA = sortBlock(id("celestea"), new FlowerBlock(StatusEffects.ABSORPTION, 10, BlockSettings.copy(Blocks.POPPY).instabreak().get()),ps_FLOWER).dimension(SortInputs.NEXUS).get();
    // RARE
    public static final Block CLOVEGRASS = sortBlock(id("clovegrass"), new GrassBlock(new BlockSettings(0.8f).randomTick().sound(BlockSoundGroup.GRASS).get()),ps_GRASSLIKE).dimension(SortInputs.RARE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();
    public static final Block FALLGRASS = sortBlock(id("fallgrass"), new GrassBlock(new BlockSettings(0.7f).randomTick().sound(BlockSoundGroup.GRASS).get()),ps_GRASSLIKE).dimension(SortInputs.RARE).tool(SortInputs.SHOVEL).material(SortInputs.NO_MATERIAL).get();
    public static final Block LAUREL_LEAVES = sortBlock(id("laurel_leaves"), new LeavesBlock(BlockSettings.copy(Blocks.AZALEA_LEAVES).strength(2f).model("non_opaque").get()),ps_NORMAL).model("").dimension(SortInputs.RARE).tool(SortInputs.HOE).material(SortInputs.NO_MATERIAL).get();
    public static final Block AUTUMN_LEAVES = sortBlock(id("autumn_leaves"), new LeavesBlock(BlockSettings.copy(Blocks.OAK_LEAVES).strength(3f).model("non_opaque").get()),ps_NORMAL).model("").dimension(SortInputs.RARE).tool(SortInputs.HOE).material(SortInputs.NO_MATERIAL).get();
    public static final Block CHRYSANTHEMUM = sortBlock(id("chrysanthemum"), new FlowerBlock(StatusEffects.GLOWING, 15, BlockSettings.copy(Blocks.SUNFLOWER).instabreak().get()),ps_FLOWER).dimension(SortInputs.RARE).get();
    public static final Block LUCKY_TORCH = sortBlock(id("lucky_torch"), new TorchBlock(ParticleTypes.SOUL_FIRE_FLAME, BlockSettings.copy(Blocks.TORCH).luminance(state -> 15).colorMap(MapColor.GREEN).get()), false,ps_TORCH).dimension(SortInputs.RARE).tool(SortInputs.PICKAXE).material(SortInputs.NO_MATERIAL).get();
    public static final Block LUCKY_WALL_TORCH = sortBlock(id("lucky_wall_torch"), new WallTorchBlock(ParticleTypes.SOUL_FIRE_FLAME, BlockSettings.copy(LUCKY_TORCH).get()),ps_WALL_TORCH).parent(LUCKY_TORCH);
    // UNTER
    public static final Block REDBUD_LEAVES = sortBlock(id("redbud_leaves"), new LeavesBlock(BlockSettings.copy(Blocks.BIRCH_LEAVES).strength(4f).model("non_opaque").get()),ps_NORMAL).model("").dimension(SortInputs.UNTER).tool(SortInputs.HOE).material(SortInputs.NO_MATERIAL).get();

    public static void registerModBlocks() {
        HisbMod.debug("Registering Mod Blocks for " + HisbMod.id());

        // TORCH ITEM REGISTER
        registerAsItem(id("lucky_torch"), LUCKY_TORCH, new VerticallyAttachableBlockItem(LUCKY_TORCH,LUCKY_WALL_TORCH,new Item.Settings(), Direction.DOWN), true);

        addToItemGroup(ItemGroups.BUILDING_BLOCKS,all());
    }
}