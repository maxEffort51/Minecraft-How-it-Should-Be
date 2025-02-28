package net.maxeffort.beyond.item.armor;

import net.maxeffort.helper.item.HelperItems;
import net.maxeffort.beyond.main.BeyondMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import java.util.ArrayList;
import java.util.List;

public class ModArmorItems extends HelperItems {
    public static final ItemCaller firite = createCaller("beyond_firite_armor",BeyondMod.id());
    public static final ItemCaller purviumElytra = createCaller("beyond_purvium_elytra",BeyondMod.id());
    public static final ItemCaller purvium = createCaller("beyond_purvium_armor",BeyondMod.id());
    public static final ItemCaller depnetum = createCaller("beyond_depnetum_armor",BeyondMod.id());
    public static final ItemCaller fortolium = createCaller("beyond_fortolium_armor",BeyondMod.id());
    public static final ItemCaller infitium = createCaller("beyond_infitium_armor",BeyondMod.id());
    public static final ItemCaller untillium = createCaller("beyond_untillium_armor",BeyondMod.id());
    public static final ItemCaller armite = createCaller("beyond_armite_armor",BeyondMod.id());

    private static final ItemCaller armor = createCaller("beyond_armor",BeyondMod.id())
            .addChild(firite).addChild(purvium).addChild(purviumElytra).addChild(depnetum).addChild(fortolium)
            .addChild(infitium).addChild(untillium).addChild(armite);

    public static final Item NETHERITE_FIRITE_HELMET = makeArmor(firite, "netherite_firite_helmet", ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.HELMET, new Item.Settings().maxCount(1).fireproof(), 37);
    public static final Item NETHERITE_FIRITE_CHESTPLATE = makeArmor(firite, "netherite_firite_chestplate", ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1).fireproof(), 37);
    public static final Item NETHERITE_FIRITE_LEGGINGS = makeArmor(firite, "netherite_firite_leggings", ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1).fireproof(), 37);
    public static final Item NETHERITE_FIRITE_BOOTS = makeArmor(firite, "netherite_firite_boots", ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.BOOTS, new Item.Settings().maxCount(1).fireproof(), 37);

    public static final Item PURVIUM_HELMET = makeArmor(purvium, "purvium_helmet", ModArmorMaterials.PURVIUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 39);
    public static final Item PURVIUM_ELYTRA = makeArmor(purviumElytra, "purvium_elytra", ModArmorMaterials.PURVIUM, ItemTypes.ELYTRA, new Item.Settings().maxCount(1), 60);
    public static final Item PURVIUM_LEGGINGS = makeArmor(purvium, "purvium_leggings", ModArmorMaterials.PURVIUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 39);
    public static final Item PURVIUM_BOOTS = makeArmor(purvium, "purvium_boots", ModArmorMaterials.PURVIUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 39);

    public static final Item DEPNETUM_HELMET = makeArmor(depnetum, "depnetum_helmet", ModArmorMaterials.DEPNETUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 41);
    public static final Item DEPNETUM_CHESTPLATE = makeArmor(depnetum, "depnetum_chestplate", ModArmorMaterials.DEPNETUM, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 41);
    public static final Item DEPNETUM_LEGGINGS = makeArmor(depnetum, "depnetum_leggings", ModArmorMaterials.DEPNETUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 41);
    public static final Item DEPNETUM_BOOTS = makeArmor(depnetum, "depnetum_boots", ModArmorMaterials.DEPNETUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 41);

    public static final Item FORTOLIUM_HELMET = makeArmor(fortolium, "fortolium_helmet", ModArmorMaterials.FORTOLIUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 45);
    public static final Item FORTOLIUM_CHESTPLATE = makeArmor(fortolium, "fortolium_chestplate", ModArmorMaterials.FORTOLIUM, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 45);
    public static final Item FORTOLIUM_LEGGINGS = makeArmor(fortolium, "fortolium_leggings", ModArmorMaterials.FORTOLIUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 45);
    public static final Item FORTOLIUM_BOOTS = makeArmor(fortolium, "fortolium_boots", ModArmorMaterials.FORTOLIUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 45);

    public static final Item INFITIUM_HELMET = makeArmor(infitium, "infitium_helmet", ModArmorMaterials.INFITIUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 47);
    public static final Item INFITIUM_CHESTPLATE = makeArmor(infitium, "infitium_chestplate", ModArmorMaterials.INFITIUM, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 47);
    public static final Item INFITIUM_LEGGINGS = makeArmor(infitium, "infitium_leggings", ModArmorMaterials.INFITIUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 47);
    public static final Item INFITIUM_BOOTS = makeArmor(infitium, "infitium_boots", ModArmorMaterials.INFITIUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 47);

    public static final Item UNTILLIUM_HELMET = makeArmor(untillium, "untillium_helmet", ModArmorMaterials.UNTILLIUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 49);
    public static final Item UNTILLIUM_CHESTPLATE = makeArmor(untillium, "untillium_chestplate", ModArmorMaterials.UNTILLIUM, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 49);
    public static final Item UNTILLIUM_LEGGINGS = makeArmor(untillium, "untillium_leggings", ModArmorMaterials.UNTILLIUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 49);
    public static final Item UNTILLIUM_BOOTS = makeArmor(untillium, "untillium_boots", ModArmorMaterials.UNTILLIUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 49);

    public static final Item ARMITE_FURCAP = makeArmor(armite, "armite_furcap", ModArmorMaterials.THICK_ARMITE, ItemTypes.HELMET, new Item.Settings().maxCount(1), 50);
    public static final Item ARMITE_FURCOAT = makeArmor(armite, "armite_furcoat", ModArmorMaterials.THICK_ARMITE, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 50);
    public static final Item ARMITE_THICKPANTS = makeArmor(armite, "armite_thickpants", ModArmorMaterials.THICK_ARMITE, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 50);
    public static final Item ARMITE_SNOWBOOTS = makeArmor(armite, "armite_snowboots", ModArmorMaterials.THICK_ARMITE, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 50);

    public static Item[] all() { return all(armor); }
    public static Item[] allArmor() { List<Item> i = new ArrayList<>(List.of(all(armor))); i.remove(purviumElytra()); return i.toArray(new Item[0]);}
    public static Item[] netheriteFirite() { return all(firite); }
    public static Item[] purvium(boolean includeElytra) { return all(purvium, includeElytra); }
    public static Item purviumElytra() { return all(purviumElytra)[0]; }
    public static Item[] depnetum() { return all(depnetum); }
    public static Item[] fortolium() { return all(fortolium); }
    public static Item[] infitium() { return all(infitium); }
    public static Item[] untillium() { return all(untillium); }
    public static Item[] armite() { return all(armite); }

    public static void registerModArmor() {
        BeyondMod.debug("Registering Mod Armor Items for " + BeyondMod.id());

        addToItemGroup(ItemGroups.COMBAT, all());
    }


}
