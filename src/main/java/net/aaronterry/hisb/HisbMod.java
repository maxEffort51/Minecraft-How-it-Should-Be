package net.aaronterry.hisb;

import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.block.entity.ModBlockEntities;
import net.aaronterry.hisb.events.ModEvents;
import net.aaronterry.hisb.item.ModItems;
import net.aaronterry.hisb.screen.ModScreenHandlers;
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
	}

	public static void debug(String data) { if (DEBUG_MODE) LOGGER.info(data); }
	public static void debug(String data, Object o) { if (DEBUG_MODE) LOGGER.info(data, o); }
	public static void debug(String data, Object... objects) { if (DEBUG_MODE) LOGGER.info(data, objects); }
}

