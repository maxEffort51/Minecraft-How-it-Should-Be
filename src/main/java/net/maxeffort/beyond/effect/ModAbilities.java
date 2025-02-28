package net.maxeffort.beyond.effect;

import net.maxeffort.helper.effect.Ability;
import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.item.armor.ModArmorItems;
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
    public static final Ability GOLD_CHARM = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.netheriteFirite()), entity -> {
        if (entity instanceof PlayerEntity player) {
            Box boundingBox = player.getBoundingBox().expand(15);
            // SET
            for (PiglinEntity piglin : player.getWorld().getEntitiesByClass(PiglinEntity.class, boundingBox, piglinEntity -> piglinEntity.isAngryAt(player))) {
                piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
                piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                piglin.setSilent(true);
                piglin.setAttacking(false);
                piglin.setCharging(false);
            }
            for (PiglinBruteEntity piglin : player.getWorld().getEntitiesByClass(PiglinBruteEntity.class, boundingBox, piglinBruteEntity -> piglinBruteEntity.isAngryAt(player))) {
                piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
                piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                piglin.setSilent(true);
                piglin.setAttacking(false);
            }
        }
    });
    private static final EntityAttributeModifier FALL_DISTANCE = new EntityAttributeModifier(Identifier.of(BeyondMod.id(), "ability_fall_distance"), 4, EntityAttributeModifier.Operation.ADD_VALUE);

    public static final Ability SAFE_FALL = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.purvium(true)), (entity,pieces) -> {
        float slowFallBuildup = switch(pieces) {
            case 1 -> 0.95f; case 2 -> 0.9f; case 3 -> 0.83f; case 4 -> 0.7f;
            default -> 1;
        };
        if (!entity.isOnGround()) {
            entity.setVelocity(entity.getVelocity().multiply(1, slowFallBuildup, 1));
        }
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE);
        if (instance != null && !instance.hasModifier(FALL_DISTANCE.comp_2447())) instance.addTemporaryModifier(FALL_DISTANCE);
    }, entity -> {
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE);
        if (instance != null && instance.hasModifier(FALL_DISTANCE.comp_2447())) instance.removeModifier(FALL_DISTANCE);
    });

    public static final Ability UNDARK = new Ability(Ability.ItemInputs.armor(ModArmorItems.DEPNETUM_HELMET), entity -> { if (entity.hasStatusEffect(StatusEffects.DARKNESS)) entity.removeStatusEffect(StatusEffects.DARKNESS); });
    private static int d = 0;
    public static final Ability DEEP_CALM = new Ability(Ability.ItemInputs.armorSet(ModArmorItems.depnetum()), entity -> {
        Box boundingBox = entity.getBoundingBox().expand(15);
        for (WardenEntity warden : entity.getWorld().getEntitiesByClass(WardenEntity.class, boundingBox, wardenEntity -> entity instanceof PlayerEntity player && wardenEntity.isAngryAt(player))) {
            if (d > 3) {
                warden.increaseAngerAt(entity, -1, false); d = 0;
            } else d++;
            if (warden.getAnger() < 0) warden.increaseAngerAt(entity, -warden.getAnger()+1, false);
        }
    });
    public static final Ability[] ALL = new Ability[] {GOLD_CHARM,SAFE_FALL,UNDARK,DEEP_CALM};

    public static void registerModAbilities() {
        BeyondMod.debug("Preparing Mod Abilities for " + BeyondMod.id());
        Ability.registerAbilities(ALL);
    }
}
