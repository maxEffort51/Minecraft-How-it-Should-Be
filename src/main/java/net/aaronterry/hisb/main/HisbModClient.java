package net.aaronterry.hisb.main;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.helper.main.HelperClientInitializer;
import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.aaronterry.hisb.pack.exploration.screen.ModScreenHandlers;
import net.aaronterry.hisb.pack.exploration.screen.PurifierScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class HisbModClient extends HelperClientInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.TRAPDOOR).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.FENCE_GATE).toArray(new Block[0]));
        renderCutout(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.VINE).toArray(new Block[0]));
    }
}
