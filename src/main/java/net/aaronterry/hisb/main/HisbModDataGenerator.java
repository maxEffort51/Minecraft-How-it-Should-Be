package net.aaronterry.hisb.main;

import net.aaronterry.helper.main.HelperGeneratorEntrypoint;
import net.aaronterry.hisb.exploration.datagen.*;
import net.aaronterry.hisb.exploration.worldgen.ModConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class HisbModDataGenerator extends HelperGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		addProviders(
		 // ModItemTagProvider::new,
			ModBlockTagProvider::new,
			ModLootTableProvider::new,
			ModRecipeProvider::new
		).and(ModModelProvider::new);

		finish(fabricDataGenerator);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::build);
	}
}
