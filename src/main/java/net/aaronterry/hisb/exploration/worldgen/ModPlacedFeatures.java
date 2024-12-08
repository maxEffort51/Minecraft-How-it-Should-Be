package net.aaronterry.hisb.exploration.worldgen;

import net.aaronterry.helper.worldgen.HelperPlacedFeatures;
import net.aaronterry.hisb.HisbMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RandomOffsetPlacementModifier;

import java.util.ArrayList;
import java.util.List;

public class ModPlacedFeatures extends HelperPlacedFeatures {
    protected static RegistryKey<PlacedFeature> key(String name) { return key(HisbMod.id(), name); }
    protected static RegistryKey<PlacedFeature> ore(String name, RegistryKey<ConfiguredFeature<?, ?>> configKey, int perChunk, PlacementModifier... modifiers) {
        return ore(HisbMod.id(), name, configKey, perChunk, modifiers);
    }
    protected static BlockPredicate surroundedBy(Block block, Direction... directions) {
        List<BlockPredicate> predicates = new ArrayList<>();
        for (Direction direction : directions) predicates.add(BlockPredicate.matchingBlocks(direction.getVector(), block));
        return BlockPredicate.anyOf(predicates);
    }

    public static final RegistryKey<PlacedFeature> PRISMALITE = ore("prismalite_placed", ModConfiguredFeatures.PRISMALITE, 100, HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(62)), BlockFilterPlacementModifier.of(
            surroundedBy(Blocks.WATER, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)));

    public static void generate() {
        HisbMod.debug("Generating Placed Features for hisb");

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.OCEAN,
                BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, PRISMALITE);
    }
}
