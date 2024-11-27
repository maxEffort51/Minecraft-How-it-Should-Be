package net.aaronterry.helper.item;

import net.minecraft.item.*;

public class ItemSettings {
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
