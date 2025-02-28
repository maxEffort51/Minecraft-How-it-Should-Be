package net.maxeffort.beyond.item;

import net.maxeffort.helper.item.HelperItems;
import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.item.armor.ModArmorItems;
import net.maxeffort.beyond.item.custom.*;
import net.maxeffort.beyond.item.custom.structure.BookScrapItem;
import net.maxeffort.beyond.item.tool.ModToolItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;

public class ModItems extends HelperItems {

    private static final ItemCaller dover = createCaller("beyond_dover_talents",BeyondMod.id());
    private static final ItemCaller unique = createCaller("beyond_unique_items",BeyondMod.id()).addChild(dover);
    private static final ItemCaller ingredient = createCaller("beyond_item_ingredients",BeyondMod.id());
    private static final ItemCaller tool = createCaller("beyond_item_tools",BeyondMod.id());
    private static final ItemCaller general = createCaller("beyond_general",BeyondMod.id()).addChild(unique).addChild(ingredient).addChild(tool);
    
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
    public static final Item RED_DOVER_TALENT = makeItem(dover, "red_dover_talent", new DoverTalentItem(1, new Item.Settings().maxCount(1)));
    public static final Item ORANGE_DOVER_TALENT = makeItem(dover, "orange_dover_talent", new DoverTalentItem(2, new Item.Settings().maxCount(1)));
    public static final Item BRIGHT_DOVER_TALENT = makeItem(dover, "bright_dover_talent", new DoverTalentItem(3, new Item.Settings().maxCount(1)));
    public static final Item BLUE_DOVER_TALENT = makeItem(dover, "blue_dover_talent", new DoverTalentItem(4, new Item.Settings().maxCount(1)));

    public static final Item UNTILLIUM_BAR = makeItem(ingredient, "untillium_bar");
    // RARE ITEMS
    public static final Item LAUREL_BOAT = makeItem(tool, "laurel_boat", new BoatItem(false, BoatEntity.Type.CHERRY,new Item.Settings().maxCount(1)));
    public static final Item JADE = makeItem(ingredient, "jade");
    public static final Item JADEITE = makeItem(ingredient, "jadeite");
    public static final Item FORTOLIUM = makeItem(ingredient, "fortolium");
    public static final Item CALLWETHER_TOTEM = makeItem(unique, "callwether_totem", new Item.Settings().maxCount(1));
    // VORMITE ITEMS
    public static final Item VORMITE_CLUMP = makeItem(ingredient, "vormite_clump");
    public static final Item VORMITE_ROD = makeItem(ingredient, "vormite_rod");
    public static final Item VORMITE_SPEAR = makeItem(tool, "vormite_spear", new TridentItem(new Item.Settings().rarity(Rarity.RARE).maxDamage(530).attributeModifiers(TridentItem.createAttributeModifiers()).component(DataComponentTypes.TOOL, TridentItem.createToolComponent())));
    public static final Item VORMITE_MACE = makeItem(tool, "vormite_mace", new MaceItem(new Item.Settings().rarity(Rarity.RARE).maxDamage(700).component(DataComponentTypes.TOOL, MaceItem.createToolComponent()).attributeModifiers(MaceItem.createAttributeModifiers())));
    public static final Item VORMITE_HOOK = makeItem(ingredient, "vormite_hook", new ArrowItem(new Item.Settings()));
    public static final Item HOOK_AND_STRING = makeItem(tool, "hook_and_string", new FishingRodItem(new Item.Settings().maxDamage(600)));
    public static final Item VORMITE_BOW = makeItem(tool, "vormite_bow", new BowItem(new Item.Settings().maxDamage(450)));
    // INFITIUM ITEMS
    public static final Item INFITIUM_RING = makeItem(ingredient, "infitium_ring");
    public static final Item AFLITE = makeItem(ingredient, "aflite");
    public static final Item CLOUDFLUFF = makeItem(ingredient, "cloudfluff");
    public static final Item IVORY_TUSK = makeItem(ingredient, "ivory_tusk");
    // UNTER ITEMS
    public static final Item ARMITE_CHUNK = makeItem(ingredient, "armite_chunk");
    public static final Item THICK_FUR = makeItem(ingredient, "thick_fur");
    public static final Item PALLECOLDIUM_TOTEM = makeItem(ingredient, "pallecoldium_totem", new FrozenTotem(4,3));
    // NON-SPECIFIC ITEMS
    public static final Item BOOK_RUIN_SCRAP = makeItem(ingredient, "book_ruin_scrap", new BookScrapItem(new Item.Settings().maxCount(1)));

    public static void registerModItems() {
        BeyondMod.debug("Registering Mod Items for " + BeyondMod.id());
        
        addToItemGroup(ItemGroups.INGREDIENTS, ingredients());

        ModToolItems.registerModTools();
        ModArmorItems.registerModArmor();

        addToItemGroup(ItemGroups.TOOLS, uniqueItems());
        addToItemGroup(ItemGroups.TOOLS, tools());
    }
}
