package net.aaronterry.hisb.exploration.item.custom.structure;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.screen.BookScrapScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class BookScrapItem extends Item {
    private String text = "";

    public BookScrapItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        HisbMod.debug("Dimension: " + world.getRegistryKey().getValue().getPath());
        if (text.isEmpty()) text = BookScrapDataLoader.get(world.getRegistryKey().getValue().getPath());
        if (!world.isClient()) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(((syncId, inventory, playerEntity) ->
                new BookScrapScreenHandler(syncId, this)), Text.of("Book Scrap")));
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public String getScrapText() { return text; }
}
