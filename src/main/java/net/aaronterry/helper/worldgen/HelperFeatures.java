package net.aaronterry.helper.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.structure.rule.*;
import net.minecraft.util.Identifier;

public class HelperFeatures {
    protected static Identifier id(String modId, String name) { return Identifier.of(modId, name); }

    public static class Test {
        public static RuleTest tag(TagKey<Block> key) { return new TagMatchRuleTest(key); }
        public static RuleTest block(Block block) { return new BlockMatchRuleTest(block); }
        public static RuleTest state(BlockState block) { return new BlockStateMatchRuleTest(block); }
        public static RuleTest blockRandom(Block block, float probability) { return new RandomBlockMatchRuleTest(block, probability); }
    }
}
