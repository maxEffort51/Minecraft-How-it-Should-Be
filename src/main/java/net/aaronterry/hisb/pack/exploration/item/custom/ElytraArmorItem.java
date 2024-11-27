package net.aaronterry.hisb.pack.exploration.item.custom;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
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
    private static final Identifier DEFENSE_ID = Identifier.of(HisbMod.id(), "elytra_armor_defense");
    private static final Identifier KNOCKBACK_RESISTANCE_ID = Identifier.of(HisbMod.id(), "elytra_armor_knockback_resistance");
    private static final Identifier TOUGHNESS_ID = Identifier.of(HisbMod.id(), "elytra_armor_toughness");
    private final ArmorMaterial material;
    private boolean reset = true;

    public ElytraArmorItem(RegistryEntry<ArmorMaterial> material, Settings settings) {
        // TOUGHNESS, KNOCKBACK RESISTANCE, DEFENSE, EQUIP SOUND
        super(settings);
        this.material = material.value();
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
        addAddModifier(user, EntityAttributes.GENERIC_ARMOR, DEFENSE_ID, material.defense().get(ArmorItem.Type.CHESTPLATE));
        addAddModifier(user, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, TOUGHNESS_ID, material.toughness());
        addAddModifier(user, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID, material.knockbackResistance());
        addAddModifier(user, EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID, material.knockbackResistance()/2);
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

    @Override public RegistryEntry<SoundEvent> getEquipSound() { return material.equipSound(); }

    @Override public boolean canRepair(ItemStack stack, ItemStack ingredient) { return ingredient.isOf(Items.PHANTOM_MEMBRANE) || ingredient.isOf(ModItems.PURVIUM_CHUNK); }
}

//public class ElytraArmorItem extends ArmorItem {
//    public ElytraArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
//        super(material, type, settings);
//    }
//
//    public void elytraTick(ItemStack stack, net.minecraft.entity.Entity entity) {
//        if (entity instanceof PlayerEntity player) {
//            if (player.isFallFlying() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() == this) {
//                if (stack.getDamage() < stack.getMaxDamage() - 1) { stack.damage(1, player, EquipmentSlot.CHEST); } else { player.stopFallFlying(); }
//                setupFireworkBoost(player, stack);
//            }
//        }
//    }
//
//    private void setupFireworkBoost(PlayerEntity player, ItemStack stack) {
//        HisbMod.debug("Setting up firework boost");
//        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
//            ItemStack heldItem = playerEntity.getStackInHand(hand);
//            if (!world.isClient && heldItem.getItem() instanceof FireworkRocketItem) {
//                HisbMod.debug("Held Item is a Firework Rocket!");
//                boolean isElytraArmor = playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraArmorItem;
//                HisbMod.debug("Is flying: {}, is an ElytraArmorItem: {}", player.isFallFlying(), isElytraArmor);
//                if (player.isFallFlying() && isElytraArmor) {
//                    HisbMod.debug("Should be speeding up now");
//                    stack.getItem().useOnEntity(stack, player, playerEntity, hand);
//                    player.setVelocity(player.getRotationVector().multiply(3)); // Apply velocity boost
//                    heldItem.decrement(1); // Consume one firework
//                    stack.damage(3, player, EquipmentSlot.CHEST); // Apply durability loss
//                    return TypedActionResult.success(heldItem);
//                }
//            }
//            return TypedActionResult.pass(heldItem);
//        });
//    }
//
//    @Override
//    public boolean canRepair(ItemStack stack, ItemStack ingredient) { return ingredient.isOf(Items.PHANTOM_MEMBRANE) || ingredient.isOf(ModItems.PURVIUM_CHUNK); }
//}