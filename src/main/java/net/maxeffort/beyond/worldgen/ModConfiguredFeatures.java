package net.maxeffort.beyond.worldgen;

import net.maxeffort.helper.worldgen.HelperConfiguredFeatures;
import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModConfiguredFeatures extends HelperConfiguredFeatures {
    protected static RegistryKey<ConfiguredFeature<?, ?>> ore(String name, Block blockTest, int vein, Block target, Feature<OreFeatureConfig> type) {
        return ore(BeyondMod.id(), name, blockTest, vein, target, type);
    }

    public static final RegistryKey<ConfiguredFeature<?, ?>> PRISMALITE = ore("ore_prismalite", Blocks.DIORITE, 4, ModBlocks.PRISMALITE_ORE, Feature.ORE);
}
