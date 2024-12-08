package net.aaronterry.hisb.exploration.worldgen.feature;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.rule.BlockStateMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

import java.util.List;

public class OreFeatures {

//    public static class Prismalite {
//        // Configured Feature
//        public static ConfiguredFeature<?, ?> prismaliteCFG =
//                new ConfiguredFeature<>(Feature.ORE,
//                new OreFeatureConfig(
//                        List.of(OreFeatureConfig.createTarget(new BlockStateMatchRuleTest(Blocks.DIORITE.getDefaultState()), ModBlocks.PRISMALITE_ORE.getDefaultState())),
//                        9 // blocks per vein
//                )
//        );
//        // Placed Feature
//        public static PlacedFeature prismalitePLC = new PlacedFeature(RegistryEntry.of(prismaliteCFG),
//                List.of(CountPlacementModifier.of(1), // clusters per chunk
//                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(80))
//                )
//        );
//        public static void registerOrSomething() {
//            prismalitePLC = Registry.register(Registries.FEATURE, Identifier.of(HisbMod.id(),"prismalite_feature"), prismalitePLC);
//            BiomeModifications.addFeature(
//                    BiomeSelectors.foundInOverworld(),
//                    GenerationStep.Feature.UNDERGROUND_ORES,
//                    RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(HisbMod.id(),"prismalite_feature"))
//            );
//        }
//    }
}
