package net.aaronterry.helper.main;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.List;

public class HelperClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // HelperScreens.register();
        // HelperClientTick.register();
    }

    public void renderCutout(Block[] blocks) {
        for (Block block : blocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}
