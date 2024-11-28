package net.aaronterry.hisb.pack.exploration.item;

import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.armor.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.custom.AncientStarItem;
import net.aaronterry.hisb.pack.exploration.item.custom.BlastChargeItem;
import net.aaronterry.hisb.pack.exploration.item.custom.DoverTalentItem;
import net.aaronterry.hisb.pack.exploration.item.custom.SculkEchoItem;
import net.aaronterry.hisb.pack.exploration.item.tool.ModToolItems;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;

public class ModItems extends HelperItems {

    private static final ItemCaller dover = createCaller("hisb_dover_talents",HisbMod.id());
    private static final ItemCaller unique = createCaller("hisb_unique_items",HisbMod.id()).addChild(dover);
    private static final ItemCaller ingredient = createCaller("hisb_item_ingredients",HisbMod.id());
    private static final ItemCaller tool = createCaller("hisb_item_tools",HisbMod.id());
    private static final ItemCaller general = createCaller("hisb_general",HisbMod.id()).addChild(unique).addChild(ingredient).addChild(tool);
    
    public static Item[] all() { return all(general); }
    public static Item[] doverTalents() { return all(dover); }
    public static Item[] ingredients() { return all(ingredient); }
    public static Item[] tools() { return all(tool); }
    public static Item[] uniqueItems() { return all(unique); }
    
    // OVERWORLD ITEMS
    public static final Item PRISMALITE_SHARD = makeItem(ingredient, "prismalite_shard");
    public static final Item PURIFIED_IRON = makeItem(ingredient, "purified_iron");
    public static final Item PURIFIED_COPPER = makeItem(ingredient, "purified_copper");
    public static final Item PURIFIED_LAPIS = makeItem(ingredient, "purified_lapis");
    public static final Item PURIFIED_REDSTONE = makeItem(ingredient, "purified_redstone");
    public static final Item PURIFIED_EMERALD = makeItem(ingredient, "purified_emerald");
    public static final Item PURIFIED_DIAMOND = makeItem(ingredient, "purified_diamond");
    public static final Item DYREMITE_CHUNK = makeItem(ingredient, "dyremite_chunk");
    // NETHER ITEMS
    public static final Item ANCIENT_STAR = makeItem(tool, "ancient_star", new AncientStarItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    public static final Item BAKED_QUARTZ = makeItem(ingredient, "baked_quartz");
    public static final Item CRYSTALLINE_QUARTZ = makeItem(ingredient, "crystalline_quartz");
    public static final Item FIRITE_SCRAP = makeItem(ingredient, "firite_scrap");
    public static final Item PURIFIED_SCRAP = makeItem(ingredient, "purified_scrap");
    public static final Item DIRTY_SCRAP = makeItem(ingredient, "dirty_scrap");
    public static final Item DEBRITINUM_SCRAP = makeItem(ingredient, "debritinum_scrap");
    // END ITEMS
    public static final Item PURVIUM_CHUNK = makeItem(ingredient, "purvium_chunk");
    public static final Item BLAST_SHARD = makeItem(ingredient, "blast_shard");
    public static final Item BLAST_CHARGE = makeItem(tool, "blast_charge", new BlastChargeItem(new Item.Settings().rarity(Rarity.UNCOMMON)));
    // DEMANDI ITEMS
    public static final Item SCULTIUM_BONES = makeItem(ingredient, "scultium_bones");
    public static final Item DEEP_ROD = makeItem(ingredient, "deep_rod");
    public static final Item DEPNETUM_CLUMP = makeItem(ingredient, "depnetum_clump");
    public static final Item SCULK_ECHO = makeItem(tool, "sculk_echo", new SculkEchoItem(new Item.Settings().maxCount(1)));
    public static final Item DEMANDUM_GEAR = makeItem(ingredient, "demandum_gear");
    public static final Item DEMANDUM_CHUNK = makeItem(ingredient, "demandum_chunk");
    public static final Item RED_DOVER_TALENT = makeItem(dover, "red_dover_talent", new DoverTalentItem(1, new Item.Settings()));
    public static final Item ORANGE_DOVER_TALENT = makeItem(dover, "orange_dover_talent", new DoverTalentItem(2, new Item.Settings()));
    public static final Item BRIGHT_DOVER_TALENT = makeItem(dover, "bright_dover_talent", new DoverTalentItem(3, new Item.Settings()));
    public static final Item BLUE_DOVER_TALENT = makeItem(dover, "blue_dover_talent", new DoverTalentItem(4, new Item.Settings()));

    public static final Item UNTILLIUM_BAR = makeItem(ingredient, "untillium_bar");
    // RARE ITEMS
    public static final Item LAUREL_BOAT = makeItem(tool, "laurel_boat", new BoatItem(false, BoatEntity.Type.CHERRY,new Item.Settings().maxCount(1).fireproof()));
    public static final Item JADE = makeItem(ingredient, "jade");
    public static final Item JADEITE = makeItem(ingredient, "jadeite");
    public static final Item FORTOLIUM = makeItem(ingredient, "fortolium");
    public static final Item CALLWETHER_TOTEM = makeItem(unique, "callwether_totem");

    public static void registerModItems() {
        HisbMod.debug("Registering Mod Items for " + HisbMod.id());
        
        addToItemGroup(ItemGroups.INGREDIENTS, ingredients());

        ModToolItems.registerModTools();
        ModArmorItems.registerModArmor();

        addToItemGroup(ItemGroups.TOOLS, uniqueItems());
        addToItemGroup(ItemGroups.TOOLS, tools());
    }
}
