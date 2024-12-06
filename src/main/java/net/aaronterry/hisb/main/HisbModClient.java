package net.aaronterry.hisb.main;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.helper.main.HelperClientInitializer;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.aaronterry.hisb.exploration.screen.BookScrapScreen;
import net.aaronterry.hisb.exploration.screen.ModScreenHandlers;
import net.aaronterry.hisb.exploration.screen.PurifierScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class HisbModClient extends HelperClientInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
        HandledScreens.register(ModScreenHandlers.BOOK_SCRAP_SCREEN_HANDLER, BookScrapScreen::new);
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE_GATE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.CROSS).toArray(new Block[0]));
    }
}
