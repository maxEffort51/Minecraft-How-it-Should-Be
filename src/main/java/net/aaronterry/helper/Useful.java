package net.aaronterry.helper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class Useful {
    public static void doIf(Runnable yes, Runnable no, boolean maybe) { if (maybe) { yes.run(); } else { no.run(); } }

    public static boolean toggle(Runnable yes, Runnable no, boolean previous) { doIf(yes, no, !previous); return !previous; }

    public static Item[] combine(Item[] arr, Item item) {
        Item[] combined = new Item[arr.length + 1];
        System.arraycopy(arr, 0, combined, 0, combined.length - 1);
        combined[combined.length-1] = item;
        return combined;
    }

    public static Item[] combine(Item[] arr1, Item[] arr2, Item[] arr3) {
        int total = arr1.length + arr2.length + arr3.length;
        Item[] combined = new Item[total];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        System.arraycopy(arr3, 0, combined, arr1.length+arr2.length, arr3.length);
        return combined;
    }

    public static class Entities {
        public static void addStatusEffects(LivingEntity entity, RegistryEntry<StatusEffect>[] data, int duration) {
            for (RegistryEntry<StatusEffect> datapoint : data) {
                StatusEffectInstance instance = new StatusEffectInstance(datapoint, duration);
                entity.addStatusEffect(instance);
            }
        }
    }
}
