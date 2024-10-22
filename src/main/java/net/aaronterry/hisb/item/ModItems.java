package net.aaronterry.hisb.item;

import net.aaronterry.hisb.HisbMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // OVERWORLD ITEMS - DYREMITE
    public static final Item PURIFIED_IRON = registerItem("purified_iron", new Item(new Item.Settings()));
    public static final Item PURIFIED_GOLD = registerItem("purified_gold", new Item(new Item.Settings()));
    public static final Item PURIFIED_LAPIS = registerItem("purified_lapis", new Item(new Item.Settings()));
    public static final Item PURIFIED_REDSTONE = registerItem("purified_redstone", new Item(new Item.Settings()));
    public static final Item PURIFIED_EMERALD = registerItem("purified_emerald", new Item(new Item.Settings()));
    public static final Item PURIFIED_DIAMOND = registerItem("purified_diamond", new Item(new Item.Settings()));
    public static final Item DYREMITE_CHUNK = registerItem("dyremite_chunk", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name), item);
    }

    public static void addAll(FabricItemGroupEntries entries, Item[] items) {
        for (Item item : items) {
            entries.add(item);
        }
    }

    public static void registerModItems() {
        HisbMod.LOGGER.info("Registering Mod Items for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            Item[] overworld = new Item[]{PURIFIED_IRON,PURIFIED_GOLD,PURIFIED_LAPIS,PURIFIED_REDSTONE,PURIFIED_EMERALD,PURIFIED_DIAMOND,DYREMITE_CHUNK};
            addAll(entries, overworld);
        });
    }
}
