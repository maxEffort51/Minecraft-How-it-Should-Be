package net.maxeffort.beyond.main;

import net.maxeffort.helper.main.HelperModInitializer;
import net.maxeffort.helper.util.HelperServerTick;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.block.entity.ModBlockEntities;
import net.maxeffort.beyond.item.ModItems;
import net.maxeffort.beyond.effect.ModAbilities;
import net.maxeffort.beyond.screen.ModScreenHandlers;
import net.maxeffort.beyond.worldgen.ModPlacedFeatures;
import net.maxeffort.beyond.worldgen.ModStructures;


public class BeyondMod extends HelperModInitializer {

	@Override public void init() {
		create("beyond",true).addAll(
			ModBlocks::registerModBlocks,
			ModItems::registerModItems,
			ModScreenHandlers::registerScreenHandlers,
			ModBlockEntities::registerBlockEntities,
			ModAbilities::registerModAbilities,
			HelperServerTick::run,
			ModPlacedFeatures::generate,
			ModStructures::run
		);
		// resource(new BookScrapDataLoader());
	}

}

