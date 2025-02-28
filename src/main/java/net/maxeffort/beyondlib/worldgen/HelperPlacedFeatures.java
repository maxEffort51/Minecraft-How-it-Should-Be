package net.maxeffort.helper.worldgen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public class HelperPlacedFeatures extends HelperFeatures {
    public static final List<BuildFeature> ores = new ArrayList<>();

    protected static RegistryKey<PlacedFeature> key(String modId, String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id(modId, name));
    }

    private static void finish(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static RegistryKey<PlacedFeature> ore(String modId, String name, RegistryKey<ConfiguredFeature<?, ?>> configKey, int perChunk, PlacementModifier... modifiers) {
        var regKey = key(modId, name);
        ores.add(new BuildFeature(regKey, configKey, perChunk, modifiers));
        return regKey;
    }

    public static void build(Registerable<PlacedFeature> context) {
        var configured = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        ores.forEach(ore -> finish(context, ore.key, configured.getOrThrow(ore.configKey), ore.modifiers()));
//        finish(context, PINK_GARNET_ORE_PLACED_KEY, configured.getOrThrow(ModConfiguredFeatures.PRISMALITE_KEY),
//        OrePlacement.modifiersWithCount(14,
//                ));
    }

    public static class BuildFeature {
        protected final RegistryKey<PlacedFeature> key;
        protected final RegistryKey<ConfiguredFeature<?, ?>> configKey;
        protected final int vein;
        protected final PlacementModifier[] modifiers;

        protected BuildFeature(RegistryKey<PlacedFeature> key, RegistryKey<ConfiguredFeature<?, ?>> configKey, int vein, PlacementModifier... modifiers) {
            this.key = key;
            this.configKey = configKey;
            this.vein = vein;
            this.modifiers = modifiers;
        }

        protected List<PlacementModifier> modifiers() { var mods = new ArrayList<>(List.of(modifiers)); mods.add(CountPlacementModifier.of(vein)); return mods; }
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