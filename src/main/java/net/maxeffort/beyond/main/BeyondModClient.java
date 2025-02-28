package net.maxeffort.beyond.main;

import net.maxeffort.helper.block.HelperBlocks;
import net.maxeffort.helper.main.HelperClientInitializer;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.screen.BookScrapScreen;
import net.maxeffort.beyond.screen.ModScreenHandlers;
import net.maxeffort.beyond.screen.PurifierScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BeyondModClient extends HelperClientInitializer {
    @Override
    public void onInitializeClient() {
//        HandledScreens.register(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
//        HandledScreens.register(ModScreenHandlers.BOOK_SCRAP_SCREEN_HANDLER, BookScrapScreen::new);
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE_GATE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.CROSS).toArray(new Block[0]));
    }
}
