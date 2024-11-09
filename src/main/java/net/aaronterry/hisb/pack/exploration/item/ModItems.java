package net.aaronterry.hisb.pack.exploration.item;

import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.custom.AncientStarItem;
import net.aaronterry.hisb.pack.exploration.item.custom.BlastChargeItem;
import net.aaronterry.hisb.pack.exploration.item.custom.DoverTalentItem;
import net.aaronterry.hisb.pack.exploration.item.custom.SculkEchoItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    // OVERWORLD ITEMS
    public static final Item PRISMALITE_SHARD = registerItem("prismalite_shard", new Item(new Item.Settings()));
    public static final Item PURIFIED_IRON = registerItem("purified_iron", new Item(new Item.Settings()));
    public static final Item PURIFIED_COPPER = registerItem("purified_copper", new Item(new Item.Settings()));
    public static final Item PURIFIED_LAPIS = registerItem("purified_lapis", new Item(new Item.Settings()));
    public static final Item PURIFIED_REDSTONE = registerItem("purified_redstone", new Item(new Item.Settings()));
    public static final Item PURIFIED_EMERALD = registerItem("purified_emerald", new Item(new Item.Settings()));
    public static final Item PURIFIED_DIAMOND = registerItem("purified_diamond", new Item(new Item.Settings()));
    public static final Item DYREMITE_CHUNK = registerItem("dyremite_chunk", new Item(new Item.Settings()));
    // NETHER ITEMS
    public static final Item ANCIENT_STAR = registerItem("ancient_star", new AncientStarItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item FIRITE_SCRAP = registerItem("firite_scrap", new Item(new Item.Settings()));
    public static final Item PURIFIED_SCRAP = registerItem("purified_scrap", new Item(new Item.Settings()));
    public static final Item DIRTY_SCRAP = registerItem("dirty_scrap", new Item(new Item.Settings()));
    public static final Item CRYSTALLINE_QUARTZ = registerItem("crystalline_quartz", new Item(new Item.Settings()));
    // END ITEMS
    public static final Item PURVIUM_CHUNK = registerItem("purvium_chunk", new Item(new Item.Settings()));
    public static final Item BLAST_SHARD = registerItem("blast_shard", new Item(new Item.Settings()));
    public static final Item BLAST_CHARGE = registerItem("blast_charge", new BlastChargeItem(new Item.Settings().rarity(Rarity.UNCOMMON)));
    // DEMANDI ITEMS
    public static final Item SCULTIUM_BONES = registerItem("scultium_bones", new Item(new Item.Settings()));
    public static final Item DEEP_ROD = registerItem("deep_rod", new Item(new Item.Settings()));
    public static final Item DEPNETUM_CLUMP = registerItem("depnetum_clump", new Item(new Item.Settings()));
    public static final Item SCULK_ECHO = registerItem("sculk_echo", new SculkEchoItem(new Item.Settings().maxCount(1)));
    public static final Item DEMANDUM_GEAR = registerItem("demandum_gear", new Item(new Item.Settings()));
    public static final Item DEMANDUM_CHUNK = registerItem("demandum_chunk", new Item(new Item.Settings()));
    public static final Item RED_DOVER_TALENT = registerItem("red_dover_talent", new DoverTalentItem(1, new Item.Settings()));
    public static final Item ORANGE_DOVER_TALENT = registerItem("orange_dover_talent", new DoverTalentItem(2, new Item.Settings()));
    public static final Item BRIGHT_DOVER_TALENT = registerItem("bright_dover_talent", new DoverTalentItem(3, new Item.Settings()));
    public static final Item BLUE_DOVER_TALENT = registerItem("blue_dover_talent", new DoverTalentItem(4, new Item.Settings()));

    public static final Item[] ALL = new Item[] {
            PRISMALITE_SHARD,PURIFIED_IRON,PURIFIED_COPPER,PURIFIED_LAPIS,PURIFIED_REDSTONE,PURIFIED_EMERALD,PURIFIED_DIAMOND,DYREMITE_CHUNK, // OVERWORLD
            ANCIENT_STAR,FIRITE_SCRAP,PURIFIED_SCRAP,DIRTY_SCRAP,CRYSTALLINE_QUARTZ, // NETHER
            PURVIUM_CHUNK, BLAST_SHARD, BLAST_CHARGE, // END
            SCULTIUM_BONES, DEEP_ROD, DEPNETUM_CLUMP, SCULK_ECHO,DEMANDUM_CHUNK,DEMANDUM_GEAR,RED_DOVER_TALENT,ORANGE_DOVER_TALENT,BRIGHT_DOVER_TALENT,BLUE_DOVER_TALENT // DEMANDI
    };

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name), item);
    }

    public static void addAll(FabricItemGroupEntries entries, Item[] items) {
        for (Item item : items) {
            entries.add(item);
        }
    }

    public static void registerModItems() {
        HisbMod.debug("Registering Mod Items for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> addAll(entries, ALL));

        ModArmorItems.registerModArmor();
        ModToolItems.registerModTools();

        DoverTalentItem.run();
    }
}
