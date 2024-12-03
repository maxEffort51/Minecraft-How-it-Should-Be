package net.aaronterry.hisb.exploration.screen;

import net.aaronterry.hisb.exploration.item.custom.structure.BookScrapItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class BookScrapScreenHandler extends ScreenHandler {
    private final String text;

    protected BookScrapScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, (BookScrapItem) inventory.getMainHandStack().getItem());
    }

    public BookScrapScreenHandler(int syncId, BookScrapItem item) {
        super(ModScreenHandlers.BOOK_SCRAP_SCREEN_HANDLER, syncId);
        text = item.getScrapText();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
