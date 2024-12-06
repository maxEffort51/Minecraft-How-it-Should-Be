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

public class BookScrapItem extends Item {

    public BookScrapItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        String dim = world.getRegistryKey().getValue().getPath();
        HisbMod.debug("Dimension: " + dim);
        if (!world.isClient()) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(((syncId, inventory, playerEntity) ->
                new BookScrapScreenHandler(syncId, BookScrapDataLoader.get(dim))), Text.of("Book Scrap")));
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
