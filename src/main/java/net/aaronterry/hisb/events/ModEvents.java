package net.aaronterry.hisb.events;

import com.mojang.datafixers.util.Either;
import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.item.ModArmorItems;
import net.aaronterry.hisb.item.custom.ElytraArmorItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModEvents {
    public static DamageSource EMPTY = new DamageSource(new RegistryEntry<>() {
        @Override public DamageType value() { return null; }
        @Override public boolean hasKeyAndValue() { return false;}
        @Override public boolean matchesId(Identifier id) { return false; }
        @Override public boolean matchesKey(RegistryKey<DamageType> key) { return false; }
        @Override public boolean matches(Predicate<RegistryKey<DamageType>> predicate) { return false; }
        @Override public boolean isIn(TagKey<DamageType> tag) { return false; }
        @Override public boolean matches(RegistryEntry<DamageType> entry) { return false; }
        @Override public Stream<TagKey<DamageType>> streamTags() { return Stream.empty(); }
        @Override public Either<RegistryKey<DamageType>, DamageType> getKeyOrValue() { return null; }
        @Override public Optional<RegistryKey<DamageType>> getKey() { return Optional.empty(); }
        @Override public Type getType() { return null; }
        @Override public boolean ownerEquals(RegistryEntryOwner<DamageType> owner) { return false; }
    });
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
                if (client.player != null && client.options.jumpKey.isPressed()) { ItemStack chestStack = client.player.getEquippedStack(EquipmentSlot.CHEST);
                    if (chestStack.getItem() == registered && client.world != null) {
                        ElytraArmorItem elytra = (ElytraArmorItem) chestStack.getItem().asItem(); elytra.elytraTick(chestStack, client.player);
                        Useful.doIf(() -> client.player.stopFallFlying(), () -> { if (tickCounter >= tickBreak) { client.player.startFallFlying(); tickCounter = 0; } }, client.player.isFallFlying());
                        if (tickCounter <= tickBreak + 1) tickCounter++;
                    }
                }
            });
        }
    }

    public static void registerModEvents() {
        CustomElytraTick.create((ElytraArmorItem) ModArmorItems.PURVIUM_ELYTRA);
        //AncientStarTick.create((AncientStarItem) ModItems.ANCIENT_STAR);
    }
}
