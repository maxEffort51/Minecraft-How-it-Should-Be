package net.aaronterry.hisb.exploration.worldgen;

import net.aaronterry.helper.worldgen.HelperConfiguredFeatures;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModConfiguredFeatures extends HelperConfiguredFeatures {
    protected static RegistryKey<ConfiguredFeature<?, ?>> key(String name) { return key(HisbMod.id(), name); }
    protected static RegistryKey<ConfiguredFeature<?, ?>> ore(String name, Block blockTest, int vein, Block target, Feature<OreFeatureConfig> type) {
        return ore(HisbMod.id(), name, blockTest, vein, target, type);
    }

    public static final RegistryKey<ConfiguredFeature<?, ?>> PRISMALITE = ore("ore_prismalite", Blocks.DIORITE, 4, ModBlocks.PRISMALITE_ORE, Feature.ORE);
}
