package net.aaronterry.helper.mob;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public class EntityHelper {
    @SafeVarargs public static void addStatusEffects(LivingEntity entity, int duration, RegistryEntry<StatusEffect>... data) {
        for (RegistryEntry<StatusEffect> datapoint : data) {
            StatusEffectInstance instance = new StatusEffectInstance(datapoint, duration);
            entity.addStatusEffect(instance);
        }
    }
}
