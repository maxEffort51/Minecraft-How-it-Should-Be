package net.aaronterry.hisb.main;

import net.aaronterry.helper.main.HelperGeneratorEntrypoint;
import net.aaronterry.hisb.pack.exploration.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class HisbModDataGenerator extends HelperGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		addProviders(
		 // ModItemTagProvider::new,
		 // ModRegistryDataGenerator::new,
			ModBlockTagProvider::new,
			ModLootTableProvider::new,
			ModRecipeProvider::new
		).and(ModModelProvider::new);

		finish(fabricDataGenerator);
	}
}
