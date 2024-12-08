package net.aaronterry.hisb.main;

import net.aaronterry.helper.main.HelperDataGenerator;
import net.aaronterry.hisb.exploration.datagen.*;
import net.aaronterry.hisb.exploration.worldgen.ModConfiguredFeatures;
import net.aaronterry.hisb.exploration.worldgen.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class HisbModDataGenerator extends HelperDataGenerator {

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
