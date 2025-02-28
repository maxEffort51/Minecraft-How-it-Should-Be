package net.maxeffort.beyond.item.custom;

import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElytraArmorItem extends ElytraItem {
    private static final Identifier DEFENSE_ID = Identifier.of(BeyondMod.id(), "elytra_armor_defense");
    private static final Identifier KNOCKBACK_RESISTANCE_ID = Identifier.of(BeyondMod.id(), "elytra_armor_knockback_resistance");
    private static final Identifier TOUGHNESS_ID = Identifier.of(BeyondMod.id(), "elytra_armor_toughness");
    private final ArmorMaterial material;
    private boolean reset = true;

    public ElytraArmorItem(RegistryEntry<ArmorMaterial> material, Settings settings) {
        // TOUGHNESS, KNOCKBACK RESISTANCE, DEFENSE, EQUIP SOUND
        super(settings);
        this.material = material.comp_349();
    }


    // WHEN EQUIP, ADD MODIFIERS
    @Override public TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> value = super.equipAndSwap(item, world, user, hand);
        if (value.getResult().isAccepted()) {
            actAsArmor(user);
            reset = false;
        }
        return value;
    }

    // WHEN NOT RESET AND NOT EQUIPPED, RESET IT
    @Override public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!reset && !world.isClient() && entity instanceof PlayerEntity player) {
            if (!player.getEquippedStack(EquipmentSlot.CHEST).equals(new ItemStack(this))) {
                reset = true; stopActingAsArmor(player);
            }
        }
    }

    // GENERAL ARMOR MODIFIERS
    private void actAsArmor(PlayerEntity user) {
        addAddModifier(user, EntityAttributes.GENERIC_ARMOR, DEFENSE_ID, material.getProtection(ArmorItem.Type.CHESTPLATE));
        addAddModifier(user, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, TOUGHNESS_ID, material.comp_2303());
        addAddModifier(user, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID, material.comp_2304());
        addAddModifier(user, EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID, material.comp_2304()/2);
    }
    private void stopActingAsArmor(PlayerEntity user) {
        removeModifier(user, EntityAttributes.GENERIC_ARMOR, DEFENSE_ID);
        removeModifier(user, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, TOUGHNESS_ID);
        removeModifier(user, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID);
        removeModifier(user, EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID);
    }

    private void addAddModifier(PlayerEntity user, RegistryEntry<EntityAttribute> attribute, Identifier id, float change) {
        EntityAttributeInstance instance = user.getAttributeInstance(attribute);
        if (instance != null && !instance.hasModifier(id)) {
            instance.addTemporaryModifier(new EntityAttributeModifier(id, change, EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }

    private void removeModifier(PlayerEntity user, RegistryEntry<EntityAttribute> attribute, Identifier id) {
        EntityAttributeInstance instance = user.getAttributeInstance(attribute);
        if (instance != null && instance.hasModifier(id)) instance.removeModifier(id);
    }

    @Override public RegistryEntry<SoundEvent> getEquipSound() { return material.comp_2300(); }

    @Override public boolean canRepair(ItemStack stack, ItemStack ingredient) { return ingredient.isOf(Items.PHANTOM_MEMBRANE) || ingredient.isOf(ModItems.PURVIUM_CHUNK); }
}