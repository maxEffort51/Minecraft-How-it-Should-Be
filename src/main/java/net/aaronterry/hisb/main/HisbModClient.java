package net.aaronterry.hisb.main;

import net.aaronterry.hisb.pack.exploration.events.ModEvents;
import net.aaronterry.hisb.pack.exploration.screen.ModScreenHandlers;
import net.aaronterry.hisb.pack.exploration.screen.PurifierScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class HisbModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
        ModEvents.registerModEvents();
    }
}
