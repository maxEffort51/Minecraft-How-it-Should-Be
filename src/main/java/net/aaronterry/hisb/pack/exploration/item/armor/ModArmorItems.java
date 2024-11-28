package net.aaronterry.hisb.pack.exploration.item.armor;

import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.HisbMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import java.util.ArrayList;
import java.util.List;

public class ModArmorItems extends HelperItems {
    public static final ItemCaller firite = createCaller("hisb_firite_armor",HisbMod.id());
    public static final ItemCaller purviumElytra = createCaller("hisb_purvium_elytra",HisbMod.id());
    public static final ItemCaller purvium = createCaller("hisb_purvium_armor",HisbMod.id());
    public static final ItemCaller depnetum = createCaller("hisb_depnetum_armor",HisbMod.id());
    public static final ItemCaller fortolium = createCaller("hisb_fortolium_armor",HisbMod.id());
    public static final ItemCaller untillium = createCaller("hisb_untillium_armor",HisbMod.id());

    private static final ItemCaller armor = createCaller("hisb_armor",HisbMod.id())
            .addChild(firite).addChild(purvium).addChild(purviumElytra).addChild(depnetum).addChild(fortolium).addChild(untillium);

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

    public static final Item UNTILLIUM_HELMET = makeArmor(untillium, "untillium_helmet", ModArmorMaterials.UNTILLIUM, ItemTypes.HELMET, new Item.Settings().maxCount(1), 48);
    public static final Item UNTILLIUM_CHESTPLATE = makeArmor(untillium, "untillium_chestplate", ModArmorMaterials.UNTILLIUM, ItemTypes.CHESTPLATE, new Item.Settings().maxCount(1), 48);
    public static final Item UNTILLIUM_LEGGINGS = makeArmor(untillium, "untillium_leggings", ModArmorMaterials.UNTILLIUM, ItemTypes.LEGGINGS, new Item.Settings().maxCount(1), 48);
    public static final Item UNTILLIUM_BOOTS = makeArmor(untillium, "untillium_boots", ModArmorMaterials.UNTILLIUM, ItemTypes.BOOTS, new Item.Settings().maxCount(1), 48);

    public static Item[] all() { return all(armor); }
    public static Item[] allArmor() { List<Item> i = new ArrayList<>(List.of(all(armor))); i.remove(purviumElytra()); return i.toArray(new Item[0]);}
    public static Item[] netheriteFirite() { return all(firite); }
    public static Item[] purvium(boolean includeElytra) { return all(purvium, includeElytra); }
    public static Item purviumElytra() { return all(purviumElytra)[0]; }
    public static Item[] depnetum() { return all(depnetum); }
    public static Item[] untillium() { return all(untillium); }

    public static void registerModArmor() {
        HisbMod.debug("Registering Mod Armor Items for " + HisbMod.id());

        addToItemGroup(ItemGroups.COMBAT, all());
    }


}
