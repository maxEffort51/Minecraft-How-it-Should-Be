package net.aaronterry.helper.worldgen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class HelperPlacedFeatures extends HelperFeatures {
    protected static RegistryKey<PlacedFeature> key(String modId, String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id(modId, name));
    }

    private static void finish(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static RegistryKey<PlacedFeature> feature(String modId, String name, RegistryEntry<ConfiguredFeature<?, ?>> config, PlacementModifier... modifiers) {
        // save something to build later !!!!!

        return key(modId, name);
    }

    public static void build(Registerable<PlacedFeature> context) {
        var configured = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        // build using finish : context, placedKey, config feature, placement modifiers !!!!!
    }
}

//public static final RegistryKey<PlacedFeature> PINK_GARNET_ORE_PLACED_KEY = registerKey("pink_garnet_ore_placed");
//    public static final RegistryKey<PlacedFeature> NETHER_PINK_GARNET_ORE_PLACED_KEY = registerKey("nether_pink_garnet_ore_placed");
//    public static final RegistryKey<PlacedFeature> END_PINK_GARNET_ORE_PLACED_KEY = registerKey("end_pink_garnet_ore_placed");
//
//    public static final RegistryKey<PlacedFeature> DRIFTWOOD_PLACED_KEY = registerKey("driftwood_placed");
//
//    public static final RegistryKey<PlacedFeature> HONEY_BERRY_BUSH_PLACED_KEY = registerKey("honey_berry_bush_placed");
//
//    public static void bootstrap(Registerable<PlacedFeature> context) {
//        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
//
//        register(context, PINK_GARNET_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PINK_GARNET_ORE_KEY),
//                ModOrePlacement.modifiersWithCount(14,
//                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(80))));
//        register(context, NETHER_PINK_GARNET_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_PINK_GARNET_ORE_KEY),
//                ModOrePlacement.modifiersWithCount(14,
//                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
//        register(context, END_PINK_GARNET_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_PINK_GARNET_ORE_KEY),
//                ModOrePlacement.modifiersWithCount(14,
//                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
//
//        register(context, DRIFTWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DRIFTWOOD_KEY),
//                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
//                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 2), ModBlocks.DRIFTWOOD_SAPLING));
//
//        register(context, HONEY_BERRY_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.HONEY_BERRY_BUSH_KEY),
//                RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
//    }
//
//    public static RegistryKey<PlacedFeature> registerKey(String name) {
//        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(TutorialMod.MOD_ID, name));
//    }
//
//    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
//                                 List<PlacementModifier> modifiers) {
//        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
//    }
//
//    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
//                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
//                                                                                   PlacementModifier... modifiers) {
//        register(context, key, configuration, List.of(modifiers));
//    }