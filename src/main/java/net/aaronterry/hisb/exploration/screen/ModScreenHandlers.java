package net.aaronterry.hisb.exploration.screen;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final ScreenHandlerType<PurifierScreenHandler> PURIFIER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, "purifying", new ScreenHandlerType<>(PurifierScreenHandler::new, null));

    public static final ScreenHandlerType<BookScrapScreenHandler> BOOK_SCRAP_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, "book_scrap", new ScreenHandlerType<>(BookScrapScreenHandler::new, null));

    public static void registerScreenHandlers() {
        HisbMod.debug("Registering Screen Handlers for " + HisbMod.id());
    }
}