package net.aaronterry.hisb.main;

import net.aaronterry.helper.util.HelperServerTick;
import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.pack.exploration.block.entity.ModBlockEntities;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.aaronterry.hisb.pack.exploration.magic.ModAbilities;
import net.aaronterry.hisb.pack.exploration.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HisbMod implements ModInitializer {
	public static final String MOD_ID = "hisb";
	public static final boolean DEBUG_MODE = true;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

		ModScreenHandlers.registerScreenHandlers();
		ModBlockEntities.registerBlockEntities();
		ModAbilities.registerModAbilities();

		HelperServerTick.run();
	}

	public static void debug(String data) { if (DEBUG_MODE) LOGGER.info(data); }
	public static void debug(String data, Object o) { if (DEBUG_MODE) LOGGER.info(data, o); }
	public static void debug(String data, Object... objects) { if (DEBUG_MODE) LOGGER.info(data, objects); }
}

