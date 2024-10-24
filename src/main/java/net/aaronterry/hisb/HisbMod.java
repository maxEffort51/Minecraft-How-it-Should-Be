package net.aaronterry.hisb;

import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.block.entity.ModBlockEntities;
import net.aaronterry.hisb.item.ModItems;
import net.aaronterry.hisb.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HisbMod implements ModInitializer {
	public static final String MOD_ID = "hisb";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}