package net.aaronterry.helper.effect;

import net.aaronterry.helper.util.HelperServerTick;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Ability {
    // Goal: add an effect or enchantment or both that is also tied to a list of items or just one item

    private final ItemInputs inputs;
    private final Consumer<LivingEntity> tickFunc;
    private final boolean doEnchant;
//    private RegistryKey<Enchantment> enchantmentKey;
//    private Enchantment enchantment;

    public Ability(ItemInputs inputs, Consumer<LivingEntity> tick) {
        this.inputs = inputs;
        this.tickFunc = tick;
        this.doEnchant = false;
    }

    public void tick(LivingEntity entity) {
        Iterable<ItemStack> itemSearch = switch(inputs.findIn) {
            case "equip" -> entity.getAllArmorItems();
            case "hand" -> entity.getHandItems();
            case "inventory" -> entity instanceof PlayerEntity player ? player.getInventory().main : entity.getEquippedItems();
            default -> new ArrayList<>();
        };
        AtomicBoolean hasnt = new AtomicBoolean(true);
        itemSearch.forEach(stack -> {
            boolean doRun = false;
            for (Item item : inputs.items) {
                if (stack.isOf(item)) { doRun = true; break; }
            }
            //doRun = doRun || (doEnchant && stack.hasEnchantments() && stack.getEnchantments().getEnchantments().contains(enchantmentKey)); // ENCHANTING
            if (doRun && hasnt.get()) { tickFunc.accept(entity); hasnt.set(false); }
        });
    }

//    public Ability enchantment(Identifier identifier, int min, int max, Rarity rarity, List<Item> supportedItems) {
//        this.doEnchant = true;
//        this.enchantmentKey = RegistryKey.of(RegistryKeys.ENCHANTMENT, identifier);
//        this.enchantment = new Enchantment(enchantmentKey, builder.build(enchantmentKey.getValue()));
//        return this;
//    }

    public static void registerAbilities(Ability[] abilities) {
        HelperServerTick.playerList((server, playerList) -> playerList.forEach(player -> {
            for (Ability ability : abilities) ability.tick(player);
        }));
    }

    public static class ItemInputs {
        private final String findIn;
        private final Item[] items;

        private ItemInputs(String in, Item[] items) {
            this.items = items;
            this.findIn = in;
        }

        public static ItemInputs armorSet(Item[] items) { return new ItemInputs("equip", items); }

        public static ItemInputs armor(Item item) { return new ItemInputs("equip", new Item[] {item}); }

        public static ItemInputs toolSet(Item[] items) { return new ItemInputs("hand", items); }

        public static ItemInputs tool(Item item) { return new ItemInputs("hand", new Item[] {item}); }

        public static ItemInputs items(Item[] items) { return new ItemInputs("inventory", items); }

        public static ItemInputs item(Item item) { return new ItemInputs("inventory", new Item[] {item}); }


    }
}
