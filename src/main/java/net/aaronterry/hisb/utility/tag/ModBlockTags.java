package net.aaronterry.hisb.utility.tag;

import net.aaronterry.helper.util.HelperBlockTags;
import net.aaronterry.hisb.HisbMod;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public class ModBlockTags extends HelperBlockTags {

    public static final TagSorter hisb = sorter(HisbMod.id());

    public static final TagKey<Block> ALL_MOD_BLOCKS = all(hisb,"all_mod_blocks");

    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = needs(hisb, "netherite");

    public static final TagKey<Block> INCORRECT_FOR_SCULTIUM_TOOL = incorrect(hisb, "scultium");
    public static final TagKey<Block> NEEDS_SCULTIUM_TOOL = needs(hisb, "scultium");

    public static final TagKey<Block> INCORRECT_FOR_FORTOLIUM_TOOL = incorrect(hisb, "fortolium");
    public static final TagKey<Block> NEEDS_FORTOLIUM_TOOL = needs(hisb, "fortolium");

    public static final TagKey<Block> INCORRECT_FOR_DEMANDUM_TOOL = incorrect(hisb, "demandum");
    public static final TagKey<Block> NEEDS_DEMANDUM_TOOL = needs(hisb, "demandum");

    public static final TagKey<Block> INCORRECT_FOR_UNTILLIUM_TOOL = incorrect(hisb, "untillium");
    public static final TagKey<Block> NEEDS_UNTILLIUM_TOOL = needs(hisb, "untillium");

    public static final TagKey<Block> INCORRECT_FOR_ARMITE_TOOL = incorrect(hisb, "armite");
    public static final TagKey<Block> NEEDS_ARMITE_TOOL = needs(hisb, "armite");

    public static final TagKey<Block> INCORRECT_FOR_SCYTHE_TOOL = incorrect(hisb, "scythe");

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
