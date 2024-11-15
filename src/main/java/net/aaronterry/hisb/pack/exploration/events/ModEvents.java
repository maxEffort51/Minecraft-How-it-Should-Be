package net.aaronterry.hisb.pack.exploration.events;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.custom.ElytraArmorItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ModEvents {
//    public static class AncientStarTick {
//
//
//        public static void create(AncientStarItem ancientStar) {
//            AttackEntityCallback.EVENT.register((entity, world, hand, defend, hitResult) -> {
//                if (defend instanceof PlayerEntity player) {
//                    float damage = calculateDamage(entity, player, (ServerWorld) world, hand);
//                    if (player.getHealth() - damage <= 0.0F) {
//                        ancientStar.activate(player, damage, entity);
//                        return ActionResult.FAIL; // Cancel the original damage
//                    }
//                }
//                return ActionResult.PASS; // Allow other damage to proceed normally
//            });
//        }
//
//        private static float calculateDamage(@NotNull LivingEntity entity, LivingEntity target, ServerWorld world, Hand hand) {
//            float baseDamage = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
//            ItemStack weapon = entity.getStackInHand(hand);
//            if (!weapon.isEmpty()) baseDamage += EnchantmentHelper.getItemDamage(world, weapon, (int) baseDamage);
//            if (entity.fallDistance > 0.0F && !entity.isSprinting() && !entity.isOnGround()) baseDamage *= 1.5F;
//            // POTION EFFECTS
//            if (entity.hasStatusEffect(StatusEffects.STRENGTH)) {
//                int amplifier = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier(); baseDamage += 3 * (amplifier + 1);
//            }
//            if (entity.hasStatusEffect(StatusEffects.WEAKNESS)) {
//                int amplifier = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.WEAKNESS)).getAmplifier(); baseDamage -= 4 * (amplifier + 1);
//            }
//            float armor = (float) target.getAttributeValue(EntityAttributes.GENERIC_ARMOR);
//            float toughness = (float) target.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
//            float armorRatio = armor / 20.0F; float toughnessModifier = 2.0F + toughness / 4.0F;
//            float armorReduction = Math.min(20.0F, Math.max(armor - baseDamage / toughnessModifier, armor * armorRatio)) / 25.0F;
//            int protectionLevel = (int) EnchantmentHelper.getProtectionAmount(world, target, EMPTY);
//            float enchantmentReduction = 1.0F - (protectionLevel * 0.04F);
//            return baseDamage * (1.0F - armorReduction) * enchantmentReduction;
//        }
//    }
    public static class CustomElytraTick {
        private static final int tickBreak = 30;
        private static int tickCounter = 0;
        public static void create(ElytraArmorItem registered) {
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (client.player == null) return;
                // On jump key toggle fall flying state
                if (client.options.jumpKey.isPressed() && !client.player.isOnGround()) { ItemStack chestStack = client.player.getEquippedStack(EquipmentSlot.CHEST);
                    if (chestStack.getItem() == registered && client.world != null) {
                        if (client.player.isFallFlying()) {
                            Useful.doIf(() -> { client.player.stopFallFlying(); tickCounter = 0; }, () -> tickCounter++, tickCounter >= tickBreak);
                        } else {
                            client.player.startFallFlying();
                        }
                    }
                }
                if (client.player.isFallFlying()) { ItemStack chestStack = client.player.getEquippedStack(EquipmentSlot.CHEST);
                    if (chestStack.getItem() == registered && client.world != null) {
                        if (client.player.isOnGround()) {
                            client.player.stopFallFlying();
                        } else {
                            ElytraArmorItem elytra = (ElytraArmorItem) chestStack.getItem().asItem();
                            elytra.elytraTick(chestStack, client.player);
                        }
                    }
                }
            });
        }
    }

    public static void registerModEvents() {
        HisbMod.debug("Preparing Mod Events for " + HisbMod.id());
        CustomElytraTick.create((ElytraArmorItem) ModArmorItems.PURVIUM_ELYTRA);
        //AncientStarTick.create((AncientStarItem) ModItems.ANCIENT_STAR);
    }
}
