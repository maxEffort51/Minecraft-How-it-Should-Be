package net.aaronterry.hisb.screen;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<PurifierScreenHandler> PURIFIER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(HisbMod.MOD_ID, "purifier"),
                    new ScreenHandlerType<>(PurifierScreenHandler::new, null));

    public static void registerScreenHandlers() {
        HisbMod.LOGGER.info("Registering Screen Handlers for " + HisbMod.MOD_ID);
        HandledScreens.register(PURIFIER_SCREEN_HANDLER, PurifierScreen::new);
    }
}
