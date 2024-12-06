package net.aaronterry.hisb;

import net.aaronterry.helper.main.HelperModInitializer;
import net.aaronterry.helper.util.HelperServerTick;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.aaronterry.hisb.exploration.block.entity.ModBlockEntities;
import net.aaronterry.hisb.exploration.item.ModItems;
import net.aaronterry.hisb.exploration.effect.ModAbilities;
import net.aaronterry.hisb.exploration.item.custom.structure.BookScrapDataLoader;
import net.aaronterry.hisb.exploration.screen.ModScreenHandlers;

public class HisbMod extends HelperModInitializer {

	@Override
	public void onInitialize() {
		create("hisb",true).addAll(
			ModBlocks::registerModBlocks,
			ModItems::registerModItems,
			ModScreenHandlers::registerScreenHandlers,
			ModBlockEntities::registerBlockEntities,
			ModAbilities::registerModAbilities
		).add(HelperServerTick::run);
		super.onInitialize();
//		resource(new BookScrapDataLoader());
	}
}

