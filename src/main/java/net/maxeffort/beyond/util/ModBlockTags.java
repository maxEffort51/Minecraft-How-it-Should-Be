package net.maxeffort.beyond.utility.tag;

import net.maxeffort.helper.util.HelperBlockTags;
import net.maxeffort.beyond.main.BeyondMod;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public class ModBlockTags extends HelperBlockTags {

    public static final TagSorter beyond = sorter(BeyondMod.id());

    public static final TagKey<Block> ALL_MOD_BLOCKS = all(beyond,"all_mod_blocks");

    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = needs(beyond, "netherite");

    public static final TagKey<Block> INCORRECT_FOR_SCULTIUM_TOOL = incorrect(beyond, "scultium");
    public static final TagKey<Block> NEEDS_SCULTIUM_TOOL = needs(beyond, "scultium");

    public static final TagKey<Block> INCORRECT_FOR_FORTOLIUM_TOOL = incorrect(beyond, "fortolium");
    public static final TagKey<Block> NEEDS_FORTOLIUM_TOOL = needs(beyond, "fortolium");

    public static final TagKey<Block> INCORRECT_FOR_DEMANDUM_TOOL = incorrect(beyond, "demandum");
    public static final TagKey<Block> NEEDS_DEMANDUM_TOOL = needs(beyond, "demandum");

    public static final TagKey<Block> INCORRECT_FOR_UNTILLIUM_TOOL = incorrect(beyond, "untillium");
    public static final TagKey<Block> NEEDS_UNTILLIUM_TOOL = needs(beyond, "untillium");

    public static final TagKey<Block> INCORRECT_FOR_ARMITE_TOOL = incorrect(beyond, "armite");
    public static final TagKey<Block> NEEDS_ARMITE_TOOL = needs(beyond, "armite");

    public static final TagKey<Block> INCORRECT_FOR_SCYTHE_TOOL = incorrect(beyond, "scythe");

    public static List<TagKey<Block>> getNeeds() { return List.of(
            BlockTags.NEEDS_IRON_TOOL, BlockTags.NEEDS_DIAMOND_TOOL, // VANILLA
            NEEDS_NETHERITE_TOOL, NEEDS_SCULTIUM_TOOL, NEEDS_FORTOLIUM_TOOL,
            NEEDS_DEMANDUM_TOOL, NEEDS_UNTILLIUM_TOOL, NEEDS_ARMITE_TOOL
    ); }

    public static List<TagKey<Block>> getIncorrect() { return List.of(
            BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, // VANILLA
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, INCORRECT_FOR_SCULTIUM_TOOL, INCORRECT_FOR_FORTOLIUM_TOOL,
            INCORRECT_FOR_DEMANDUM_TOOL, INCORRECT_FOR_UNTILLIUM_TOOL
    ); }
}
