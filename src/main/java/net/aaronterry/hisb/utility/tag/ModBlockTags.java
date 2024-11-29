package net.aaronterry.hisb.utility.tag;

import net.aaronterry.helper.util.HelperBlockTags;
import net.aaronterry.hisb.HisbMod;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags extends HelperBlockTags {

    public static final TagSorter hisb = sorter(HisbMod.id());

    public static final TagKey<Block> ALL_MOD_BLOCKS = all(hisb.getMain(),"all_mod_blocks");

    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = needs(hisb.getMain(), "netherite");

    public static final TagKey<Block> INCORRECT_FOR_SCULTIUM_TOOL = incorrect(hisb.getMain(), "scultium");
    public static final TagKey<Block> NEEDS_SCULTIUM_TOOL = needs(hisb.getMain(), "scultium");

    public static final TagKey<Block> INCORRECT_FOR_FORTOLIUM_TOOL = incorrect(hisb.getMain(), "fortolium");
    public static final TagKey<Block> NEEDS_FORTOLIUM_TOOL = needs(hisb.getMain(), "fortolium");

    public static final TagKey<Block> INCORRECT_FOR_DEMANDUM_TOOL = incorrect(hisb.getMain(), "demandum");
    public static final TagKey<Block> NEEDS_DEMANDUM_TOOL = needs(hisb.getMain(), "demandum");

    public static final TagKey<Block> INCORRECT_FOR_UNTILLIUM_TOOL = incorrect(hisb.getMain(), "untillium");
    public static final TagKey<Block> NEEDS_UNTILLIUM_TOOL = needs(hisb.getMain(), "untillium");

    public static final TagKey<Block> INCORRECT_FOR_SCYTHE_TOOL = incorrect(hisb.getMain(), "scythe");
}
