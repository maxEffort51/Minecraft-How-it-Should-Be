package net.maxeffort.helper.item;

import net.maxeffort.helper.datagen.HelperRecipeProvider;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.ArrayList;
import java.util.List;

public class HelperToolItems extends HelperItems {

    /* STATIC QUICK CLASSES */
    public static ToolBuilder makeTool(ItemCaller caller1, ItemCaller caller2, String name, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return new ToolBuilder(caller1, type).addCaller(caller2).name(name).material(material).attack(baseAttackDamage, attackSpeed);
    }
    public static ToolBuilder makeTool(ItemCaller caller1, ItemCaller caller2, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return makeTool(caller1, caller2, caller2.getName() + "_" + type, type, material, baseAttackDamage, attackSpeed);
    }
    public static ToolBuilder makeTool(ItemCaller caller, String name, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return new ToolBuilder(caller, type).name(name).material(material).attack(baseAttackDamage, attackSpeed);
    }
    public static ToolBuilder makeTool(ItemCaller caller, String type, ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return new ToolBuilder(caller, type).material(material).attack(baseAttackDamage, attackSpeed);
    }

    /* TOOL BUILDER */
    public static ToolBuilder toolBuilder(ItemCaller root, String type) { return new ToolBuilder(root, type); }
    public static ToolBuilder toolBuilder(ItemCaller root, String type, ToolMaterial material) { return new ToolBuilder(root, type).material(material); }

    public static class ToolBuilder {
        private final ItemCaller root; private final String type;
        private String name;
        private ToolMaterial material; private float baseAttackDamage;
        private float attackSpeed;
        private Item result;
        private final List<ItemCaller> toolCallers = new ArrayList<>();

        public ToolBuilder(ItemCaller root, String type) {
            this.root = root; this.type = type;
            if (!root.getName().isEmpty()) this.name = root.getName() + "_" + type;
        }

        public ToolBuilder addCaller(ItemCaller caller) { toolCallers.add(caller); return this; }
        public ToolBuilder addCaller(ItemCaller caller, boolean autoName) {
            if (autoName) name = caller.getName() + "_" + type;
            toolCallers.add(caller);
            return this;
        }

        private void prepareResult() {
            if (result == null) {
                result = register(root, name, switch(type) {
                    case "sword" -> new SwordItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
                    case "axe" -> new AxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
                    case "pickaxe" -> new PickaxeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
                    case "shovel" -> new ShovelItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
                    case "hoe" -> new HoeItem(material, ItemSettings.tool(type, material, baseAttackDamage, attackSpeed));
                    default -> new Item(new Item.Settings());
                });
            }
        }

        public ToolBuilder toolRecipe(ItemConvertible ore) {
            prepareResult();
            recipes.add(new HelperRecipeProvider.GenericDetails(HelperRecipeProvider.Recipe.buildTool(
                    type, result, ore.asItem(), Items.STICK, ore.asItem()).getBuilder()));
            return this;
        }
        public ToolBuilder toolRecipe(ItemConvertible ore, ItemConvertible stick) {
            prepareResult();
            recipes.add(new HelperRecipeProvider.GenericDetails(HelperRecipeProvider.Recipe.buildTool(
                    type, result, ore.asItem(), stick.asItem(), ore.asItem()).getBuilder()));
            return this;
        }

        private void finishCallers() {
            if (toolCallers.isEmpty()) {
                root.add(result);
                return;
            }
            toolCallers.forEach(caller -> { if (!root.getChildren().contains(caller)) root.addChild(caller); });
            toolCallers.forEach(caller -> caller.add(result));
            callers.add(root);
        }

        public ToolBuilder name(String name) { this.name = name; return this; }
        public ToolBuilder material(ToolMaterial mat) { this.material = mat; return this; }
        public ToolBuilder attack(float damage, float speed) { this.baseAttackDamage = damage; this.attackSpeed = speed; return this; }
        public HelperRecipeProvider.Shaped.Details recipe(RecipeCategory cat) { prepareResult(); return HelperRecipeProvider.Shaped.recipe(cat, result, this); }
        public ToolBuilder recipe(HelperRecipeProvider.GenericDetails details) { recipes.add(details); return this; }

        public Item finish() {
            prepareResult();
            finishCallers();
            return result;
        }
    }
}
