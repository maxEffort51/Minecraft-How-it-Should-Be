package net.aaronterry.hisb.main;

import net.aaronterry.hisb.pack.exploration.datagen.ModBlockTagProvider;
import net.aaronterry.hisb.pack.exploration.datagen.ModLootTableProvider;
import net.aaronterry.hisb.pack.exploration.datagen.ModModelProvider;
import net.aaronterry.hisb.pack.exploration.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class HisbModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
//		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
//		pack.addProvider(ModRegistryDataGenerator::new);
	}
}
