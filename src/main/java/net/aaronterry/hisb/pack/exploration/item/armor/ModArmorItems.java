package net.aaronterry.hisb.pack.exploration.item.armor;

import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.main.HisbMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;

public class ModArmorItems extends HelperItems {
    public static final ItemCaller firite = createCaller("hisb_firite_armor");
    public static final ItemCaller purvium = createCaller("hisb_purvium_armor");
    public static final ItemCaller depnetum = createCaller("hisb_depnetum_armor");
    public static final ItemCaller untillium = createCaller("hisb_untillium_armor");

    private static final ItemCaller armor = createCaller("hisb_armor")
            .addChild(firite).addChild(purvium).addChild(depnetum).addChild(untillium);

    public static Identifier id(String input) { return Identifier.of(HisbMod.id(), input); }

    public static final Item NETHERITE_FIRITE_HELMET = makeArmor(firite, id("netherite_firite_helmet"), ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.HELMET, new Item.Settings().fireproof(), 37);
    public static final Item NETHERITE_FIRITE_CHESTPLATE = makeArmor(firite, id("netherite_firite_chestplate"), ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.CHESTPLATE, new Item.Settings().fireproof(), 37);
    public static final Item NETHERITE_FIRITE_LEGGINGS = makeArmor(firite, id("netherite_firite_leggings"), ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.LEGGINGS, new Item.Settings().fireproof(), 37);
    public static final Item NETHERITE_FIRITE_BOOTS = makeArmor(firite, id("netherite_firite_boots"), ModArmorMaterials.NETHERITE_FIRITE, ItemTypes.BOOTS, new Item.Settings().fireproof(), 37);

    public static final Item PURVIUM_HELMET = makeArmor(purvium, id("purvium_helmet"), ModArmorMaterials.PURVIUM, ItemTypes.HELMET, new Item.Settings(), 30);
//    public static final Item PURVIUM_ELYTRA = makeArmor(purvium, id("purvium_elytra"), ModArmorMaterials.PURVIUM, ItemTypes.ELYTRA, new Item.Settings(), 39);
    public static final Item PURVIUM_LEGGINGS = makeArmor(purvium, id("purvium_leggings"), ModArmorMaterials.PURVIUM, ItemTypes.LEGGINGS, new Item.Settings(), 39);
    public static final Item PURVIUM_BOOTS = makeArmor(purvium, id("purvium_boots"), ModArmorMaterials.PURVIUM, ItemTypes.BOOTS, new Item.Settings(), 39);

    public static final Item DEPNETUM_HELMET = makeArmor(depnetum, id("depnetum_helmet"), ModArmorMaterials.DEPNETUM, ItemTypes.HELMET, new Item.Settings(), 41);
    public static final Item DEPNETUM_CHESTPLATE = makeArmor(depnetum, id("depnetum_chestplate"), ModArmorMaterials.DEPNETUM, ItemTypes.CHESTPLATE, new Item.Settings(), 41);
    public static final Item DEPNETUM_LEGGINGS = makeArmor(depnetum, id("depnetum_leggings"), ModArmorMaterials.DEPNETUM, ItemTypes.LEGGINGS, new Item.Settings(), 41);
    public static final Item DEPNETUM_BOOTS = makeArmor(depnetum, id("depnetum_boots"), ModArmorMaterials.DEPNETUM, ItemTypes.BOOTS, new Item.Settings(), 41);

    public static final Item UNTILLIUM_HELMET = makeArmor(untillium, id("untillium_helmet"), ModArmorMaterials.UNTILLIUM, ItemTypes.HELMET, new Item.Settings(), 42);
    public static final Item UNTILLIUM_CHESTPLATE = makeArmor(untillium, id("untillium_chestplate"), ModArmorMaterials.UNTILLIUM, ItemTypes.CHESTPLATE, new Item.Settings(), 42);
    public static final Item UNTILLIUM_LEGGINGS = makeArmor(untillium, id("untillium_leggings"), ModArmorMaterials.UNTILLIUM, ItemTypes.LEGGINGS, new Item.Settings(), 42);
    public static final Item UNTILLIUM_BOOTS = makeArmor(untillium, id("untillium_boots"), ModArmorMaterials.UNTILLIUM, ItemTypes.BOOTS, new Item.Settings(), 42);

    public static Item[] all() { return all(armor); }
    public static Item[] netheriteFirite() { return all(firite); }
    public static Item[] purvium() { return all(purvium); }
    public static Item[] depnetum() { return all(depnetum); }
    public static Item[] untillium() { return all(untillium); }

    public static void registerModArmor() {
        HisbMod.debug("Registering Mod Armor Items for " + HisbMod.id());

        addToItemGroup(ItemGroups.COMBAT, all());
    }
}
