package net.aaronterry.hisb.pack.exploration.magic;

import net.aaronterry.helper.effect.Ability;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.ModArmorItems;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

public class ModAbilities {
    public static final Ability GOLD_CHARM = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.NETHERITE_FIRITE_SET), entity -> {
        if (entity instanceof PlayerEntity player) {
            Box boundingBox = player.getBoundingBox().expand(15);
            for (PiglinEntity piglin : player.getWorld().getEntitiesByClass(PiglinEntity.class, boundingBox, piglinEntity -> piglinEntity.isAngryAt(player))) {
                piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
                piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                piglin.setAttacking(false);
                piglin.setCharging(false);
            }
            for (PiglinBruteEntity piglin : player.getWorld().getEntitiesByClass(PiglinBruteEntity.class, boundingBox, piglinBruteEntity -> piglinBruteEntity.isAngryAt(player))) {
                piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
                piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                piglin.setAttacking(false);
            }
        }
    });
    private static final EntityAttributeModifier FALL_PROTECTION = new EntityAttributeModifier(Identifier.of(HisbMod.MOD_ID, "fall_protection"), 0.3f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final Ability SAFE_FALL = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.PURVIUM_SET), entity -> {
        if (!entity.isOnGround()) {
            entity.setVelocity(entity.getVelocity().multiply(1, 0.9, 1));
        }
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE);
        if (instance != null && !instance.hasModifier(FALL_PROTECTION.id())) instance.addTemporaryModifier(FALL_PROTECTION);
    });
    public static final Ability UNDARK = new Ability(Ability.ItemInputs.armor(ModArmorItems.DEPNETUM_HELMET), entity -> { if (entity.hasStatusEffect(StatusEffects.DARKNESS)) entity.removeStatusEffect(StatusEffects.DARKNESS); });
    public static final Ability MUFFLED = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.DEPNETUM_SET), entity -> entity.setSilent(true));
    public static final Ability DEEP_CALM = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.DEPNETUM_SET), entity -> {
        Box boundingBox = entity.getBoundingBox().expand(15);
        for (WardenEntity warden : entity.getWorld().getEntitiesByClass(WardenEntity.class, boundingBox, wardenEntity -> true)) {
            warden.getBrain().forget(MemoryModuleType.ANGRY_AT);
            warden.setAttacking(false);
        }
    });
    public static final Ability[] ALL = new Ability[] {GOLD_CHARM,SAFE_FALL,UNDARK,MUFFLED,DEEP_CALM};

    public static void registerModAbilities() {
        HisbMod.debug("Preparing Mod Abilities for " + HisbMod.MOD_ID);
        Ability.registerAbilities(ALL);
    }
}