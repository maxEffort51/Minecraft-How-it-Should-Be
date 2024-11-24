package net.aaronterry.hisb.pack.exploration.item;

import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.armor.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.custom.AncientStarItem;
import net.aaronterry.hisb.pack.exploration.item.custom.BlastChargeItem;
import net.aaronterry.hisb.pack.exploration.item.custom.DoverTalentItem;
import net.aaronterry.hisb.pack.exploration.item.custom.SculkEchoItem;
import net.aaronterry.hisb.pack.exploration.item.tool.ModToolItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Rarity;

public class ModItems extends HelperItems {
    
    private static final ItemCaller dover = createCaller("hisb_dover_talents",HisbMod.id());
    private static final ItemCaller general = createCaller("hisb_general",HisbMod.id()).addChild(dover);
    
    public static Item[] all() { return all(general); }
    public static Item[] doverTalents() { return all(dover); }
    
    // OVERWORLD ITEMS
    public static final Item PRISMALITE_SHARD = makeItem(general, "prismalite_shard");
    public static final Item PURIFIED_IRON = makeItem(general, "purified_iron");
    public static final Item PURIFIED_COPPER = makeItem(general, "purified_copper");
    public static final Item PURIFIED_LAPIS = makeItem(general, "purified_lapis");
    public static final Item PURIFIED_REDSTONE = makeItem(general, "purified_redstone");
    public static final Item PURIFIED_EMERALD = makeItem(general, "purified_emerald");
    public static final Item PURIFIED_DIAMOND = makeItem(general, "purified_diamond");
    public static final Item DYREMITE_CHUNK = makeItem(general, "dyremite_chunk");
    // NETHER ITEMS
    public static final Item ANCIENT_STAR = makeItem(general, "ancient_star", new AncientStarItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    public static final Item BAKED_QUARTZ = makeItem(general, "baked_quartz");
    public static final Item CRYSTALLINE_QUARTZ = makeItem(general, "crystalline_quartz");
    public static final Item FIRITE_SCRAP = makeItem(general, "firite_scrap");
    public static final Item PURIFIED_SCRAP = makeItem(general, "purified_scrap");
    public static final Item DIRTY_SCRAP = makeItem(general, "dirty_scrap");
    public static final Item DEBRITINUM_SCRAP = makeItem(general, "debritinum_scrap");
    // END ITEMS
    public static final Item PURVIUM_CHUNK = makeItem(general, "purvium_chunk");
    public static final Item BLAST_SHARD = makeItem(general, "blast_shard");
    public static final Item BLAST_CHARGE = makeItem(general, "blast_charge", new BlastChargeItem(new Item.Settings().rarity(Rarity.UNCOMMON)));
    // DEMANDI ITEMS
    public static final Item SCULTIUM_BONES = makeItem(general, "scultium_bones");
    public static final Item DEEP_ROD = makeItem(general, "deep_rod");
    public static final Item DEPNETUM_CLUMP = makeItem(general, "depnetum_clump");
    public static final Item SCULK_ECHO = makeItem(general, "sculk_echo", new SculkEchoItem(new Item.Settings().maxCount(1)));
    public static final Item DEMANDUM_GEAR = makeItem(general, "demandum_gear");
    public static final Item DEMANDUM_CHUNK = makeItem(general, "demandum_chunk");
    public static final Item RED_DOVER_TALENT = makeItem(dover, "red_dover_talent", new DoverTalentItem(1, new Item.Settings()));
    public static final Item ORANGE_DOVER_TALENT = makeItem(dover, "orange_dover_talent", new DoverTalentItem(2, new Item.Settings()));
    public static final Item BRIGHT_DOVER_TALENT = makeItem(dover, "bright_dover_talent", new DoverTalentItem(3, new Item.Settings()));
    public static final Item BLUE_DOVER_TALENT = makeItem(dover, "blue_dover_talent", new DoverTalentItem(4, new Item.Settings()));

    public static final Item UNTILLIUM_BAR = makeItem(general, "untillium_bar");

    public static void registerModItems() {
        HisbMod.debug("Registering Mod Items for " + HisbMod.id());
        
        addToItemGroup(ItemGroups.INGREDIENTS, all());

        ModArmorItems.registerModArmor();
        ModToolItems.registerModTools();
    }
}
