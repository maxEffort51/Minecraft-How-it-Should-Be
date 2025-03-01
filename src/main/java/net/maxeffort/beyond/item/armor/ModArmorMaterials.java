package net.maxeffort.beyond.item.armor;

import net.maxeffort.helper.item.HelperArmorMaterials;
import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.item.ModItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModArmorMaterials extends HelperArmorMaterials {

    public static Identifier id(String input) { return Identifier.of(BeyondMod.id(), input); }

    public static final RegistryEntry<ArmorMaterial> NETHERITE_FIRITE = makeMaterial(id("netherite_firite"),map(5,9,7,4,12),15,4.0f,0.2f)
            .equip(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE).repairWith(ingredient(ModItems.FIRITE_SCRAP)).get();
    public static final RegistryEntry<ArmorMaterial> PURVIUM = makeMaterial(id("purvium"), map(6,10,8,5,13), 17, 3.0f, 0.3f)
            .equip(SoundEvents.ITEM_ARMOR_EQUIP_GOLD).repairWith(ingredient(ModItems.PURVIUM_CHUNK)).get();
    public static final RegistryEntry<ArmorMaterial> DEPNETUM = makeMaterial(id("depnetum"), map(7,11,9,6,14), 13, 3.0f, 0.4f).repairWith(ingredient(ModItems.DEPNETUM_CLUMP)).get();
    public static final RegistryEntry<ArmorMaterial> FORTOLIUM = makeMaterial(id("fortolium"), map(8,12,10,7,16), 30, 3.3f, 0.45f).repairWith(ingredient(ModItems.FORTOLIUM)).get();
    public static final RegistryEntry<ArmorMaterial> INFITIUM = makeMaterial(id("infitium"), map(9,13,11,8,18), 38, 3.6f, 0.5f).repairWith(ingredient(ModItems.INFITIUM_RING)).get();
    public static final RegistryEntry<ArmorMaterial> UNTILLIUM = makeMaterial(id("untillium"), map(10,13,12,9,20), 25, 4.0f, 0.6f).repairWith(ingredient(ModItems.UNTILLIUM_BAR)).get();
    public static final RegistryEntry<ArmorMaterial> THICK_ARMITE = makeMaterial(id("thick_armite"), map(12,15,14,10,23), 19, 4.2f, 0.82f).repairWith(ingredient(ModItems.ARMITE_CHUNK)).get();
}