package net.aaronterry.hisb.item;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static EnumMap<ArmorItem.Type, Integer> make(int helmet, int chestplate, int leggings, int boots, int body) { return Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, boots); map.put(ArmorItem.Type.LEGGINGS, leggings); map.put(ArmorItem.Type.CHESTPLATE, chestplate); map.put(ArmorItem.Type.HELMET, helmet); map.put(ArmorItem.Type.BODY, body);
    }); }

    public static final RegistryEntry<ArmorMaterial> NETHERITE_FIRITE = register("netherite_firite", make(5,9,7,4,12),
            15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, () -> Ingredient.ofItems(ModItems.FIRITE_SCRAP));
    public static final RegistryEntry<ArmorMaterial> PURVIUM = register("purvium", make(6,10,8,5,13),
            17, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3.0F, 0.3F, () -> Ingredient.ofItems(ModItems.PURVIUM_CHUNK));
    public static final RegistryEntry<ArmorMaterial> DEPNETUM = register("depnetum", make(7,11,9,6,14),
            25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, 0.4F, () -> Ingredient.ofItems(ModItems.DEPNETUM_CLUMP));

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.of(HisbMod.MOD_ID, id)));
        return register(id, defense, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(HisbMod.MOD_ID, id),
                new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance)
        );
    }
}