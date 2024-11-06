package net.aaronterry.hisb.item;

import net.aaronterry.helper.Useful;
import net.aaronterry.hisb.HisbMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// WOODEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// STONE SWORD -> 3, -2.4F AXE -> 7.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -1.0F, -2.0F
// GOLDEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// IRON SWORD -> 3, -2.4F AXE -> 6.0F, -3.1F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -2.0F, -1.0F
// DIAMOND SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -3.0F, 0.0F
// NETHERITE SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -4.0F, 0.0F
// SCULTIUM SWORD -> 3, -2.0F AXE -> 4.5F, -2.8F PICKAXE -> 1.0F, -2.4F SHOVEL -> 1.5F, -2.6F HOE -> -3.0F, 0.0F

public class ModToolItems {
    public static final Item SCULTIUM_AXE = registerTool("scultium_axe", new ToolData(ModToolMaterials.SCULTIUM, "axe", 4.5f, -2.8f));
    public static final Item SCULTIUM_PICKAXE = registerTool("scultium_pickaxe", new ToolData(ModToolMaterials.SCULTIUM, "pickaxe", 1.0f, -2.4f));
    public static final Item SCULTIUM_SHOVEL = registerTool("scultium_shovel", new ToolData(ModToolMaterials.SCULTIUM, "shovel", 1.5f, -2.6f));
    public static final Item SCULTIUM_HOE = registerTool("scultium_hoe", new ToolData(ModToolMaterials.SCULTIUM, "hoe", -3.0F, 0.0F));
    public static final Item[] SCULTIUM = new Item[] {SCULTIUM_AXE,SCULTIUM_PICKAXE,SCULTIUM_SHOVEL,SCULTIUM_HOE};
    public static final Item DEPNETUM_SWORD = registerTool("depnetum_sword", new ToolData(ModToolMaterials.SCULTIUM, "sword", 4, -2.0f));

    public static final Item[] ALL = Useful.append(SCULTIUM, DEPNETUM_SWORD);

    private static Item registerTool(String name, ToolData data) {

        Item item = switch(data.itemType) {
            case "sword" -> new SwordItem(data.material, Useful.ItemSettings.tool(data.itemType,data.material, data.baseAttackDamage, data.attackSpeed));
            case "axe" -> new AxeItem(data.material, Useful.ItemSettings.tool(data.itemType,data.material, data.baseAttackDamage, data.attackSpeed));
            case "pickaxe" -> new PickaxeItem(data.material, Useful.ItemSettings.tool(data.itemType,data.material, data.baseAttackDamage, data.attackSpeed));
            case "shovel" -> new ShovelItem(data.material, Useful.ItemSettings.tool(data.itemType,data.material, data.baseAttackDamage, data.attackSpeed));
            case "hoe" -> new HoeItem(data.material, Useful.ItemSettings.tool(data.itemType,data.material, data.baseAttackDamage, data.attackSpeed));
            default -> throw new IllegalStateException("ModToolItems.registerTool -> Unexpected value: " + data.itemType);
        };
        return registerItem(name, item);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HisbMod.MOD_ID, name), item);
    }

    public static void addAll(FabricItemGroupEntries entries, Item[] items) {
        for (Item item : items) {
            entries.add(item);
        }
    }

    public static void registerModTools() {
        HisbMod.debug("Registering Mod Tools for " + HisbMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> addAll(entries, ALL));
    }

    private static class ToolData {
        public ToolMaterial material;
        public String itemType;
        public float baseAttackDamage;
        public float attackSpeed;

        public ToolData(ToolMaterial material, String itemType, float baseAttackDamage, float attackSpeed) {
            this.material = material;
            this.itemType = itemType;
            this.baseAttackDamage = baseAttackDamage;
            this.attackSpeed = attackSpeed;
        }
    }
}
