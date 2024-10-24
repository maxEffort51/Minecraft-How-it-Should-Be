package net.aaronterry.helper.customtable;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CustomTableScreen {
    public static final ScreenHandlerType<CustomTableScreenHandler> SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(HisbMod.MOD_ID, "custom_handler"),
                    new ScreenHandlerType<>(CustomTableScreenHandler::new, null));
}