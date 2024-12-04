package net.aaronterry.hisb.exploration.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class BookScrapScreenHandler extends ScreenHandler {
    private static String scrapText = "";

    protected BookScrapScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, "");
    }

    public BookScrapScreenHandler(int syncId, String text) {
        super(ModScreenHandlers.BOOK_SCRAP_SCREEN_HANDLER, syncId);
        scrapText = text;

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public String getScrapText() { return scrapText; }
}
