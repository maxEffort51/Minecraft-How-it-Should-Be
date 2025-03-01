package net.maxeffort.helper.worldgen;

import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.ArrayList;
import java.util.List;

public class HelperConfiguredFeatures extends HelperFeatures {
    private static final List<BuildFeature> ores = new ArrayList<>();

    protected static RegistryKey<ConfiguredFeature<?, ?>> key(String modId, String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id(modId, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void finish(
            Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> ore(String modId, String name, Block blockTest, int vein, Block target, Feature<OreFeatureConfig> type) {
        RegistryKey<ConfiguredFeature<?, ?>> reg = key(modId, name);
        ores.add(new BuildFeature(reg, Test.block(blockTest), vein, target, type));
        return reg;
    }

    public static void build(Registerable<ConfiguredFeature<?, ?>> context) {
        ores.forEach(feature -> {
            OreFeatureConfig.Target target = OreFeatureConfig.createTarget(feature.test, feature.target.getDefaultState());
            finish(context, feature.key, feature.type, new OreFeatureConfig(List.of(target), feature.vein));
        });
    }

    public static class BuildFeature {
        protected final RegistryKey<ConfiguredFeature<?, ?>> key;
        protected final RuleTest test;
        protected final Feature<OreFeatureConfig> type;
        protected final int vein;
        protected final Block target;

        protected BuildFeature(RegistryKey<ConfiguredFeature<?, ?>> key, RuleTest test, int vein, Block target, Feature<OreFeatureConfig> type) {
            this.key = key;
            this.test = test;
            this.vein = vein;
            this.target = target;
            this.type = type;
        }
    }
}