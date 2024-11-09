package net.aaronterry.helper;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.item.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;

public class Useful {
    public static void doIf(Runnable yes, Runnable no, boolean maybe) { if (maybe) { yes.run(); } else { no.run(); } }

    public static boolean toggle(Runnable yes, Runnable no, boolean previous) { doIf(yes, no, !previous); return !previous; }

    public static Item[] append(Item[] arr, Item item) { Item[] combined = new Item[arr.length + 1]; System.arraycopy(arr, 0, combined, 0, combined.length - 1); combined[combined.length-1] = item; return combined; }
    public static Block[] append(Block[] arr, Block item) { Block[] combined = new Block[arr.length + 1]; System.arraycopy(arr, 0, combined, 0, combined.length - 1); combined[combined.length-1] = item; return combined; }

    private static int getCount(Object[] arr1, Object[][] arrays) { int count = arr1.length; for (Object[] array : arrays) { count += array.length; } return count; }

    public static Block[] combine(Block[] arr1, Block[]... arrays) {
        int total = Useful.getCount(arr1, arrays); Block[] combined = new Block[total];
        int cumulative = arr1.length; System.arraycopy(arr1, 0, combined, 0, arr1.length);
        for (Block[] array : arrays) { System.arraycopy(array, 0, combined, cumulative, array.length); cumulative += array.length; }
        return combined;
    }
    public static Item[] combine(Item[] arr1, Item[]... arrays) {
        int total = Useful.getCount(arr1, arrays); Item[] combined = new Item[total];
        int cumulative = arr1.length; System.arraycopy(arr1, 0, combined, 0, arr1.length);
        for (Item[] array : arrays) { System.arraycopy(array, 0, combined, cumulative, array.length); cumulative += array.length; }
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

    public static class ItemSettings {
        public static Item.Settings tool(String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
            return new Item.Settings().attributeModifiers(switch(type) {
                case "sword" -> SwordItem.createAttributeModifiers(material, (int) baseAttackDamage, attackSpeed);
                case "axe" -> AxeItem.createAttributeModifiers(material,baseAttackDamage, attackSpeed);
                case "pickaxe" -> PickaxeItem.createAttributeModifiers(material, baseAttackDamage, attackSpeed);
                case "shovel" -> ShovelItem.createAttributeModifiers(material, baseAttackDamage, attackSpeed);
                case "hoe" -> HoeItem.createAttributeModifiers(material,baseAttackDamage, attackSpeed);
                default -> throw new IllegalStateException("Useful.ItemSettings.tool -> Unexpected value: " + type);
            });
        }
    }

    public static class BlockSettings {
        public static AbstractBlock.Settings basic(float strength) { return AbstractBlock.Settings.create().strength(strength).requiresTool(); }
        public static AbstractBlock.Settings basic(float strength, float resistance) { return AbstractBlock.Settings.create().strength(strength, resistance).requiresTool(); }
        public static AbstractBlock.Settings basic(float strength, boolean requiresTool) {
            AbstractBlock.Settings settings = AbstractBlock.Settings.create().strength(strength); return requiresTool ? settings : settings.requiresTool();
        }
        public static AbstractBlock.Settings basic(float strength, float resistance, boolean requiresTool) {
            AbstractBlock.Settings settings = AbstractBlock.Settings.create().strength(strength, resistance); return requiresTool ? settings : settings.requiresTool();
        }
        public static AbstractBlock.Settings sounds(float strength, boolean requiresTool, NoteBlockInstrument instrument, BlockSoundGroup group) { return basic(strength, requiresTool).instrument(instrument).sounds(group); }
        public static AbstractBlock.Settings sounds(float strength, float resistance, boolean requiresTool, NoteBlockInstrument instrument, BlockSoundGroup group) { return basic(strength, resistance, requiresTool).instrument(instrument).sounds(group); }

        public static Block auto(float strength) { return new Block(Useful.BlockSettings.basic(strength)); }
        public static Block auto(float strength, float resistance) { return new Block(Useful.BlockSettings.basic(strength, resistance)); }
    }

    public static class ModDamageSources {
        public static DamageSources DAMAGE_SOURCES = new DamageSources(DynamicRegistryManager.EMPTY);
        public static DamageSource EXPLOSION = DAMAGE_SOURCES.create(DamageTypes.EXPLOSION);
        public static DamageSource GENERIC = DAMAGE_SOURCES.create(DamageTypes.GENERIC);
    }
}
