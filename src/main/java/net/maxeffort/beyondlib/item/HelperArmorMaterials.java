package net.maxeffort.helper.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public abstract class HelperArmorMaterials {

    protected static EnumMap<ArmorItem.Type, Integer> map(int helmet, int chestplate, int leggings, int boots, int body) { return Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, boots); map.put(ArmorItem.Type.LEGGINGS, leggings); map.put(ArmorItem.Type.CHESTPLATE, chestplate); map.put(ArmorItem.Type.HELMET, helmet); map.put(ArmorItem.Type.BODY, body);
    }); }
    protected static EnumMap<ArmorItem.Type, Integer> map() { return map(0,0,0,0,0); }

    protected static Ingredient ingredient(Item... items) { return Ingredient.ofItems(items); }
    protected static Ingredient ingredient(TagKey<Item> tag) { return Ingredient.fromTag(tag); }
    protected static Ingredient ingredient() { return Ingredient.empty(); }

    protected static MatBuilder makeMaterial(Identifier id) { return new MatBuilder(id); }
    protected static MatBuilder makeMaterial(Identifier id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability) { return new MatBuilder(id).defense(defense).enchant(enchantability); }
    protected static MatBuilder makeMaterial(Identifier id, float toughness, float knockback) { return new MatBuilder(id).tough(toughness).knockback(knockback); }
    protected static MatBuilder makeMaterial(Identifier id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, float toughness, float knockback) {
        return new MatBuilder(id).defense(defense).enchant(enchantability).tough(toughness).knockback(knockback);
    }


    public static class MatBuilder {
        private final Identifier id;
        private EnumMap<ArmorItem.Type, Integer> defense;
        private int enchantability = 0;
        private RegistryEntry<SoundEvent> sound;
        private float toughness = 0; private float knockbackResistance = 0;
        private Supplier<Ingredient> repair;

        private MatBuilder(Identifier id) {
            this.id = id;
        }

        public MatBuilder defense(EnumMap<ArmorItem.Type, Integer> enumMap) { defense = enumMap; return this; }
        public MatBuilder defense(int helmet, int chestplate, int leggings, int boots, int body) { defense = map(helmet, chestplate, leggings, boots, body); return this; }
        public MatBuilder enchant(int power) { enchantability = power; return this; }
        public MatBuilder equip(RegistryEntry<SoundEvent> sound) { this.sound = sound; return this; }
        public MatBuilder tough(float ness) { toughness = ness; return this; }
        public MatBuilder knockback(float resistance) { knockbackResistance = resistance; return this; }
        public MatBuilder repairWith(Ingredient ingredient) { repair = () -> ingredient; return this; }

        private void defaultMat() {
            if (defense == null) defense = map();
            if (sound == null) sound = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            if (repair == null) repair = Ingredient::empty;
        }

        public RegistryEntry<ArmorMaterial> get() {
            defaultMat();
            return register(id, defense, enchantability, sound, toughness, knockbackResistance, repair);
        }
    }

    protected static RegistryEntry<ArmorMaterial> register(Identifier id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(id));
        return register(id, defense, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static RegistryEntry<ArmorMaterial> register(Identifier id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }
        return Registry.registerReference(Registries.ARMOR_MATERIAL, id, new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }
}
