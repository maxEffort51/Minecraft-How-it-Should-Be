package net.aaronterry.hisb.item.custom;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.TypedActionResult;

public class ElytraArmorItem extends ArmorItem {
    public ElytraArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public void elytraTick(ItemStack stack, net.minecraft.entity.Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (player.isFallFlying() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() == this) {
                if (stack.getDamage() < stack.getMaxDamage() - 1) { stack.damage(1, player, EquipmentSlot.CHEST); } else { player.stopFallFlying(); }
                setupFireworkBoost(player, stack);
            }
        }
    }

    private void setupFireworkBoost(PlayerEntity player, ItemStack stack) {
        HisbMod.debug("Setting up firework boost");
        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            ItemStack heldItem = playerEntity.getStackInHand(hand);
            if (!world.isClient && heldItem.getItem() instanceof FireworkRocketItem) {
                HisbMod.debug("Held Item is a Firework Rocket!");
                boolean isElytraArmor = playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraArmorItem;
                HisbMod.debug("Is flying: {}, is an ElytraArmorItem: {}", player.isFallFlying(), isElytraArmor);
                if (player.isFallFlying() && isElytraArmor) {
                    HisbMod.debug("Should be speeding up now");
                    stack.getItem().useOnEntity(stack, player, playerEntity, hand);
                    player.setVelocity(player.getRotationVector().multiply(3)); // Apply velocity boost
                    heldItem.decrement(1); // Consume one firework
                    stack.damage(3, player, EquipmentSlot.CHEST); // Apply durability loss
                    return TypedActionResult.success(heldItem);
                }
            }
            return TypedActionResult.pass(heldItem);
        });
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) { return ingredient.isOf(Items.PHANTOM_MEMBRANE) || ingredient.isOf(ModItems.PURVIUM_CHUNK); }
}
