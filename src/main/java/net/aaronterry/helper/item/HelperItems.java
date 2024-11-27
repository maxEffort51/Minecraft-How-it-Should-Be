package net.aaronterry.helper.item;

import net.aaronterry.hisb.pack.exploration.item.custom.ElytraArmorItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HelperItems {
    private static final List<ItemCaller> callers = new ArrayList<>();
    public static final List<Item> elytras = new ArrayList<>(List.of(Items.ELYTRA));

    /*--- GENERAL ---*/

    public static Item[] all(ItemCaller caller, boolean recursive) {
        if (recursive) {
            return all(caller);
        }
        return caller.getItems().toArray(new Item[0]);
    }
    public static Item[] all(ItemCaller caller) {
        if (caller.hasChildren()) {
            List<Item> all = new ArrayList<>();
            caller.getChildren().forEach(child -> all.addAll(List.of(all(child))));
            all.addAll(caller.getItems());
            return all.toArray(new Item[0]);
        }
        return caller.getItems().toArray(new Item[0]);
    }

    public static ItemCaller createCaller(String id, String main, String name) {
        ItemCaller caller = new ItemCaller(id, main, name); callers.add(caller); return caller;
    }
    public static ItemCaller createCaller(String id, String main) {
        ItemCaller caller = new ItemCaller(id, main); callers.add(caller); return caller;
    }
    public static ItemCaller createCaller(String id) {
        ItemCaller caller = new ItemCaller(id); callers.add(caller); return caller;
    }

    public static void addToItemGroup(RegistryKey<ItemGroup> group, Item[] items) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> {
            for (Item item : items) entries.add(item);
        });
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    /*--- ITEM ---*/

    public static Item makeItem(ItemCaller caller, String name) {
        Item item = new Item(new Item.Settings()); caller.add(item); if (!callers.contains(caller)) callers.add(caller);
        Identifier id = caller.hasMain() ? Identifier.of(caller.getMain(), name) : Identifier.of(name);
        return register(id, item);
    }
    public static Item makeItem(ItemCaller caller, String name, Item.Settings settings) {
        Item item = new Item(settings); caller.add(item); if (!callers.contains(caller)) callers.add(caller);
        Identifier id = caller.hasMain() ? Identifier.of(caller.getMain(), name) : Identifier.of(name);
        return register(id, item);
    }
    public static Item makeItem(ItemCaller caller, String name, Item item) {
        caller.add(item); if (!callers.contains(caller)) callers.add(caller);
        Identifier id = caller.hasMain() ? Identifier.of(caller.getMain(), name) : Identifier.of(name);
        return register(id, item);
    }

    /* TOOL */
    public static Item makeTool(ItemCaller caller, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        Item item = switch(type) {
            case "sword" -> new SwordItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "axe" -> new AxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "pickaxe" -> new PickaxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "shovel" -> new ShovelItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "hoe" -> new HoeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            default -> throw new IllegalStateException("ModToolItems.registerTool -> Unexpected value: " + type);
        };
        return makeItem(caller, caller.getName() + "_" + type, item);
    }
    public static Item makeTool(ItemCaller caller, String name, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        Item item = switch(type) {
            case "sword" -> new SwordItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "axe" -> new AxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "pickaxe" -> new PickaxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "shovel" -> new ShovelItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            case "hoe" -> new HoeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
            default -> throw new IllegalStateException("ModToolItems.registerTool -> Unexpected value: " + type);
        };
        return makeItem(caller, name, item);
    }

    /* ARMOR */
    private static ArmorItem.Type getArmorType(String type) {
        return switch(type) {
            case ItemTypes.HELMET -> ArmorItem.Type.HELMET; case ItemTypes.CHESTPLATE -> ArmorItem.Type.CHESTPLATE;
            case ItemTypes.LEGGINGS -> ArmorItem.Type.LEGGINGS; case ItemTypes.BOOTS -> ArmorItem.Type.BOOTS;
            default -> ArmorItem.Type.BODY;
        };
    }
    public static Item makeArmor(ItemCaller caller, String name, RegistryEntry<ArmorMaterial> material, String type, Item.Settings settings, int maxDamageMultiplier) {
        ArmorItem.Type armorType = getArmorType(type);
        Item.Settings maxDamage = settings.maxDamage(armorType.getMaxDamage(maxDamageMultiplier));

        if (type.equals(ItemTypes.ELYTRA)) {
            Item item = new ElytraArmorItem(material, maxDamage);
            elytras.add(item);
            return makeItem(caller, name, item);
        }

        Item item = new ArmorItem(material, armorType, maxDamage);
        return makeItem(caller, name, item);
    }

    /* CLASSES */

    public static class ItemTypes {
        // TOOL ITEM TYPES
        public static final String SWORD = "sword";
        public static final String AXE = "axe";
        public static final String PICKAXE = "pickaxe";
        public static final String SHOVEL = "shovel";
        public static final String HOE = "hoe";

        // ARMOR ITEM TYPES
        public static final String HELMET = "helmet";
        public static final String CHESTPLATE = "chestplate";
        public static final String LEGGINGS = "leggings";
        public static final String BOOTS = "boots";
        public static final String ELYTRA = "elytra";
    }

    public static class ItemCaller {
        private String main = "";
        private String name = "";
        private final List<Item> items = new ArrayList<>();
        private final List<ItemCaller> children = new ArrayList<>();
        private final String id;

        private ItemCaller(String id, String main, String name) {
            this.id = id;
            this.main = main;
            this.name = name;
        }
        private ItemCaller(String id, String main) {
            this.id = id;
            this.main = main;
        }
        private ItemCaller(String id) {
            this.id = id;
        }

        public String getName() { return name; }
        public boolean hasMain() { return !main.isEmpty(); }
        public String getMain() { return main; }
        public void add(Item item) { items.add(item); }
        public ItemCaller addChild(ItemCaller caller) { children.add(caller); return this; }
        public String getId() { return id; }
        public List<Item> getItems() { return new ArrayList<>(items); }
        public boolean hasChildren() { return !children.isEmpty(); }
        public List<ItemCaller> getChildren() { return new ArrayList<>(children); }
    }
}
