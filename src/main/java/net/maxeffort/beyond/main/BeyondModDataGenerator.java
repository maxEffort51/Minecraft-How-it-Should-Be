package net.maxeffort.beyond.main;

import net.maxeffort.helper.main.HelperDataGenerator;
import net.maxeffort.beyond.datagen.*;
import net.maxeffort.beyond.worldgen.ModConfiguredFeatures;
import net.maxeffort.beyond.worldgen.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class BeyondModDataGenerator extends HelperDataGenerator {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		addProviders(
		 // ModItemTagProvider::new,
			ModBlockTagProvider::new,
			ModLootTableProvider::new,
			ModRecipeProvider::new,
			ModRegistryProvider::new
		).and(ModModelProvider::new);

		finish(fabricDataGenerator);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::build);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::build);
	}
}
