package net.aaronterry.hisb.util;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ALL_MOD_BLOCKS = createTag("all_mod_blocks");

        public static final TagKey<Block> NEEDS_NETHERITE_TOOL = createTag("needs_netherite_tool");

        public static final TagKey<Block> INCORRECT_FOR_SCULTIUM_TOOL = createTag("incorrect_for_scultium_tool");
        public static final TagKey<Block> NEEDS_SCULTIUM_TOOL = createTag("needs_scultium_tool");
        public static final TagKey<Block> INCORRECT_FOR_DEMANDUM_TOOL = createTag("incorrect_for_demandum_tool");
        public static final TagKey<Block> NEEDS_DEMANDUM_TOOL = createTag("needs_demandum_tool");
        public static final TagKey<Block> INCORRECT_FOR_UNTILLIUM_TOOL = createTag("incorrect_for_untillium_tool");
        public static final TagKey<Block> NEEDS_UNTILLIUM_TOOL = createTag("needs_untillium_tool");
        public static final TagKey<Block> INCORRECT_FOR_SCYTHE_TOOL = createTag("incorrect_for_scythe_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(HisbMod.id(), name));
        }
    }
}
