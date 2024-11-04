package net.aaronterry.hisb;

import net.aaronterry.hisb.events.ModEvents;
import net.aaronterry.hisb.screen.ModScreenHandlers;
import net.aaronterry.hisb.screen.PurifierScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class HisbModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
        ModEvents.registerModEvents();
    }
}
