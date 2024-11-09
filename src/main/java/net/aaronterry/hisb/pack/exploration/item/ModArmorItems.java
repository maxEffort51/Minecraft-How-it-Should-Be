package net.aaronterry.hisb.pack.exploration.item;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.custom.ElytraArmorItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModArmorItems {
    public static final Item NETHERITE_FIRITE_HELMET = registerItem("netherite_firite_helmet", new ArmorItem(
            ModArmorMaterials.NETHERITE_FIRITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37))));
    public static final Item NETHERITE_FIRITE_CHESTPLATE = registerItem("netherite_firite_chestplate", new ArmorItem(
            ModArmorMaterials.NETHERITE_FIRITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(37))));
    public static final Item NETHERITE_FIRITE_LEGGINGS = registerItem("netherite_firite_leggings", new ArmorItem(
            ModArmorMaterials.NETHERITE_FIRITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(37))));
    public static final Item NETHERITE_FIRITE_BOOTS = registerItem("netherite_firite_boots", new ArmorItem(
            ModArmorMaterials.NETHERITE_FIRITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(37))));
    public static final Item[] NETHERITE_FIRITE_SET = new Item[] {NETHERITE_FIRITE_HELMET,NETHERITE_FIRITE_CHESTPLATE,NETHERITE_FIRITE_LEGGINGS,NETHERITE_FIRITE_BOOTS};
    public static final Item PURVIUM_HELMET = registerItem("purvium_helmet", new ArmorItem(
            ModArmorMaterials.PURVIUM, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(40))));
    public static final Item PURVIUM_ELYTRA = registerItem("purvium_elytra", new ElytraArmorItem(
            ModArmorMaterials.PURVIUM, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(40))));
    public static final Item PURVIUM_LEGGINGS = registerItem("purvium_leggings", new ArmorItem(
            ModArmorMaterials.PURVIUM, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(40))));
    public static final Item PURVIUM_BOOTS = registerItem("purvium_boots", new ArmorItem(
            ModArmorMaterials.PURVIUM, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(40))));
    public static final Item[] PURVIUM_ARMOR = new Item[] {PURVIUM_HELMET,PURVIUM_LEGGINGS,PURVIUM_BOOTS};
    public static final Item[] PURVIUM_SET = new Item[] {PURVIUM_HELMET,PURVIUM_ELYTRA,PURVIUM_LEGGINGS,PURVIUM_BOOTS};
    public static final Item DEPNETUM_HELMET = registerItem("depnetum_helmet", new ArmorItem(
            ModArmorMaterials.DEPNETUM, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(43))));
    public static final Item DEPNETUM_CHESTPLATE = registerItem("depnetum_chestplate", new ArmorItem(
            ModArmorMaterials.DEPNETUM, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(43))));
    public static final Item DEPNETUM_LEGGINGS = registerItem("depnetum_leggings", new ArmorItem(
            ModArmorMaterials.DEPNETUM, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(43))));
    public static final Item DEPNETUM_BOOTS = registerItem("depnetum_boots", new ArmorItem(
            ModArmorMaterials.DEPNETUM, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(43))));
    public static final Item[] DEPNETUM_SET = new Item[] {DEPNETUM_HELMET,DEPNETUM_CHESTPLATE,DEPNETUM_LEGGINGS,DEPNETUM_BOOTS};

    public static final Item[] ALL = Useful.combine(NETHERITE_FIRITE_SET,PURVIUM_SET,DEPNETUM_SET);


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name), item);
    }

    public static void addSet(FabricItemGroupEntries entries, Item[] items) {
        for (Item item : items) {
            entries.add(item);
        }
    }

    public static void registerModArmor() {
        HisbMod.debug("Registering Mod Armor Items for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            addSet(entries, NETHERITE_FIRITE_SET);
            addSet(entries, PURVIUM_SET);
            addSet(entries, DEPNETUM_SET);
        });
    }
}
