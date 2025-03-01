package net.maxeffort.helper.datagen;

import net.maxeffort.helper.KeyGroup;
import net.maxeffort.helper.block.HelperBlocks;
import net.maxeffort.helper.item.HelperToolItems;
import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.item.armor.ModArmorItems;
import net.maxeffort.beyond.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HelperRecipeProvider extends FabricRecipeProvider {
    public HelperRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static Pattern pattern(String layer1, String layer2, String layer3) { return new Pattern(layer1, layer2, layer3); }

    public static class GenericDetails {
        private final String type;
        private ShapedRecipeJsonBuilder shapedBuilder;
        private Identifier id;
        private ShapelessRecipeJsonBuilder shapelessBuilder;
        private ReverseData reverse;

        public GenericDetails(ShapedRecipeJsonBuilder shaped) {
            shapedBuilder = shaped;
            this.type = "shaped";
        }
        public GenericDetails(ShapelessRecipeJsonBuilder shapeless) {
            shapelessBuilder = shapeless;
            this.type = "shapeless";
        }
        public GenericDetails(RecipeCategory cat, ItemConvertible base, ItemConvertible compact) {
            reverse = new ReverseData(cat, base, compact);
            this.type = "reverse";
        }

        public GenericDetails(ShapedRecipeJsonBuilder builder, Identifier id) {
            this(builder);
            this.id = id;
        }
        public GenericDetails(ShapelessRecipeJsonBuilder builder, Identifier id) {
            this(builder);
            this.id = id;
        }

        public void offer(RecipeExporter xpt) {
            switch(type) {
                case "shaped": if (id != null) { shapedBuilder.offerTo(xpt, id); } else shapedBuilder.offerTo(xpt); break;
                case "shapeless": if (id != null) { shapelessBuilder.offerTo(xpt, id); } else shapelessBuilder.offerTo(xpt); break;
                case "reverse": offerReversibleCompactingRecipes(xpt, reverse.category, reverse.base, reverse.category, reverse.compact);
            }
        }
        public void offer(RecipeExporter xpt, Identifier id) {
            switch(type) {
                case "shaped": shapedBuilder.offerTo(xpt, id);
                case "shapeless": shapelessBuilder.offerTo(xpt, id);
                case "reverse": offerReversibleCompactingRecipes(xpt, reverse.category, reverse.base, reverse.category, reverse.compact);
            }
        }

        // generic recipe details
        private static class ReverseData {
            public RecipeCategory category;
            public ItemConvertible base;
            public ItemConvertible compact;

            public ReverseData(RecipeCategory cat, ItemConvertible base, ItemConvertible compact) {
                category = cat; this.base = base; this.compact = compact;
            }
        }
    }

    public static class Shaped {
        public static Details recipe(RecipeCategory cat, ItemConvertible result) {
            ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(cat, result); return new Details(builder);
        }
        public static Details recipe(RecipeCategory cat, ItemConvertible result, int count) {
            ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(cat, result, count); return new Details(builder);
        }
        // PRESET-BUILT
        public static Details recipe(RecipeCategory cat, ItemConvertible result, HelperBlocks.SortingPreset preset) {
            ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(cat, result); return new Details(builder, preset);
        }
        public static Details recipe(RecipeCategory cat, ItemConvertible result, int count, HelperBlocks.SortingPreset preset) {
            ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(cat, result, count); return new Details(builder, preset);
        }

        public static Details recipe(RecipeCategory cat, Item result, HelperToolItems.ToolBuilder toolBuilder) {
            ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(cat, result); return new Details(builder, toolBuilder);
        }

        public static class Details {
            private HelperToolItems.ToolBuilder toolBuilder;
            private HelperBlocks.SortingPreset preset;
            private final ShapedRecipeJsonBuilder builder;
            private Identifier id;
            private final List<ItemConvertible> inputs = new ArrayList<>();
            public Details(ShapedRecipeJsonBuilder builder) { this.builder = builder; }
            public Details(ShapedRecipeJsonBuilder builder, HelperBlocks.SortingPreset preset) {
                this.builder = builder; this.preset = preset; }
            public Details(ShapedRecipeJsonBuilder builder, HelperToolItems.ToolBuilder tool) {
                this.builder = builder; this.toolBuilder = tool; }

            public ShapedRecipeJsonBuilder getBuilder() { return builder; }

            public Details needs(ItemConvertible item) { builder.criterion(hasItem(item), conditionsFromItem(item)); return this; }
            public Details needs(int i) { builder.criterion(hasItem(inputs.get(i)), conditionsFromItem(inputs.get(i))); return this; }
            public Details pattern(Pattern pattern) { pattern.with(builder); return this; }
            public Details input(char[] chars, ItemConvertible[] items) { inputs.addAll(List.of(items)); for (int i = 0; i < chars.length; i++) builder.input(chars[i],items[i]); return this; }
            public Details input(List<Character> chars, List<ItemConvertible> items) { inputs.addAll(items); for (int i = 0; i < chars.size(); i++) builder.input(chars.get(i),items.get(i)); return this; }
            public Details input(char c, ItemConvertible item) { inputs.add(item); builder.input(c, item); return this; }
            public Details id(Identifier id) { this.id = id; return this; }
            public void offer(RecipeExporter xpt) { builder.offerTo(xpt); }
            public void offer(RecipeExporter xpt, String unique) { builder.offerTo(xpt, Identifier.of(BeyondMod.id(), unique)); }
            public HelperBlocks.SortingPreset endRecipe() { preset.recipe(new GenericDetails(builder, id)); return preset; }
            public HelperToolItems.ToolBuilder toolBuilder() { return toolBuilder.recipe(new GenericDetails(builder)); }
        }
    }

    public static class Shapeless {
        public static Details recipe(RecipeCategory cat, ItemConvertible result) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(cat, result); return new Details(builder, result);
        }
        public static Details recipe(RecipeCategory cat, ItemConvertible result, int count) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(cat, result, count); return new Details(builder, result);
        }
        // PRESET-BUILT
        public static Details recipe(RecipeCategory cat, ItemConvertible result, HelperBlocks.SortingPreset preset) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(cat, result); return new Details(builder, result, preset);
        }
        public static Details recipe(RecipeCategory cat, ItemConvertible result, int count, HelperBlocks.SortingPreset preset) {
            ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(cat, result, count); return new Details(builder, result, preset);
        }

        public static class Details {
            private HelperBlocks.SortingPreset preset;
            private Identifier id;
            private final ShapelessRecipeJsonBuilder builder;
            private final ItemConvertible result;

            public Details(ShapelessRecipeJsonBuilder builder, ItemConvertible result) { this.builder = builder; this.result = result; }
            public Details(ShapelessRecipeJsonBuilder builder, ItemConvertible result, HelperBlocks.SortingPreset preset) {
                this.builder = builder; this.result = result; this.preset = preset; }

            public ShapelessRecipeJsonBuilder getBuilder() { return builder; }
            public Details needs(ItemConvertible item) { builder.criterion(hasItem(item), conditionsFromItem(item)); return this; }
            public Details needsResult() { builder.criterion(hasItem(result), conditionsFromItem(result)); return this; }
            public Details input(ItemConvertible input, int amount) { builder.input(input, amount); return this; }
            public Details input(ItemConvertible input) { builder.input(input, 1); return this; }
            public Details input(ItemConvertible i, ItemConvertible i2) { return this.input(i, 1, i2, 1); }
            public Details input(ItemConvertible[] inputs, int[] amounts) { for(int i = 0; i < inputs.length; i++) { builder.input(inputs[i], amounts[i]); } return this; }
            public Details input(ItemConvertible i, int a, ItemConvertible i2, int a2) { return this.input(new ItemConvertible[] {i, i2}, new int[] {a, a2}); }
            public Details input(ItemConvertible i, int a, ItemConvertible i2, int a2, ItemConvertible i3, int a3) { return this.input(new ItemConvertible[] {i, i2, i3}, new int[] {a, a2, a3}); }
            public Details input(ItemConvertible i, ItemConvertible i2, ItemConvertible i3, int a) { return this.input(new ItemConvertible[] {i, i2, i3}, new int[] {a, a, a}); }
            public Details id(Identifier id) { this.id = id; return this; }
            public void offer(RecipeExporter xpt) { if (id != null) { builder.offerTo(xpt, id); } else builder.offerTo(xpt); }
            public void offer(RecipeExporter xpt, String unique) { builder.offerTo(xpt, Identifier.of(BeyondMod.id(), unique)); }
            public HelperBlocks.SortingPreset endRecipe() { preset.recipe(new GenericDetails(builder, id)); return preset; }
        }
    }

    public static class Recipe {
        public static void reverse(RecipeExporter xpt, ItemConvertible base, ItemConvertible compact, RecipeCategory category) { offerReversibleCompactingRecipes(xpt, category, base, category, compact); }

        public static void oneIngredient(RecipeExporter xpt, Item[] items, Item input, RecipeCategory cat, char c, Pattern[] patterns) {
            for (int i = 0; i < items.length; i++) Shaped.recipe(cat,items[i]).pattern(patterns[i]).input(c, input).needs(input).offer(xpt);
        }

        public static void armor(RecipeExporter xpt, Item[] set, Item input) {
            Pattern[] patterns = new Pattern[] { new Pattern("###","# #"), new Pattern("# #","###","###"), new Pattern("###","# #","# #"), new Pattern("# #","# #") };
            for (int i = 0; i < set.length; i++) Shaped.recipe(RecipeCategory.COMBAT,set[i]).pattern(patterns[i]).input('#', input).needs(input).offer(xpt);
        }

        public static Shaped.Details buildTool(String type, Item tool, Item ore, Item stick, Item criterion) {
            Pattern toolPattern = switch(type) {
                case "sword" -> new Pattern(" @ "," @ "," | "); case "axe" -> new Pattern("@@ ","@| "," | ");
                case "pickaxe" -> new Pattern("@@@"," | ", " | "); case "shovel" -> new Pattern(" @ "," | ", " | ");
                case "hoe" -> new Pattern("@@ "," | ", " | "); default -> new Pattern("","","");
            };
            return Shaped.recipe(RecipeCategory.COMBAT, tool).pattern(toolPattern).needs(criterion).input(new char[] {'@','|'}, new Item[] {ore, stick});
        }
        public static void tool(RecipeExporter xpt, String type, Item tool, Item ore, Item stick, Item criterion) { Recipe.buildTool(type, tool, ore, stick, criterion).offer(xpt); }
        public static void tool(RecipeExporter xpt, String type, Item tool, Item ore, Item stick, Item criterion, String unique) { Recipe.buildTool(type, tool, ore, stick, criterion).offer(xpt, unique); }

        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, Items.STICK, ore);
        }
        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore, Item stick) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, stick, ore);
        }
        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore, Item stick, Item criterion) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, stick, criterion);
        }
        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore, Item stick, Item criterion, String oreType, String unique) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, stick, criterion, String.format("%s_%s_%s", oreType, types[i], unique));
        }

        public static void swords(RecipeExporter xpt, KeyGroup<Item,ToolMaterial> swords, Item... stickTypes) {
            // create a sword recipe for each weapon
            for (int i = 0; i < swords.count(); i++) {
                ToolMaterial material = swords.get(i);
                Item ore = material.getRepairIngredient().getMatchingStacks()[0].getItem();
                Item stick = (i >= stickTypes.length ? stickTypes[stickTypes.length-1] : stickTypes[i]);
                tool(xpt, "sword", swords.getKey(i), ore, stick, ore);
            }
        }
    }

    public static class Custom {
        public static void firite(RecipeExporter xpt) {
            Item[] NETHERITE_SET = new Item[] {Items.NETHERITE_HELMET,Items.NETHERITE_CHESTPLATE,Items.NETHERITE_LEGGINGS,Items.NETHERITE_BOOTS};
            for (int i = 0; i < ModArmorItems.netheriteFirite().length; i++) Shapeless.recipe(RecipeCategory.COMBAT, ModArmorItems.netheriteFirite()[i]).input(ModItems.FIRITE_SCRAP,NETHERITE_SET[i]).needs(ModItems.FIRITE_SCRAP).offer(xpt);
        }
    }

    public static class Pattern {
        private final String[] pattern;
        private final int length;

        public Pattern(String layer1, String layer2, String layer3) { pattern = new String[] {layer1, layer2, layer3}; length = 3; }
        public Pattern(String layer1, String layer2) { pattern = new String[] {layer1, layer2}; length = 2; }

        public void with(ShapedRecipeJsonBuilder builder) { for (String layer : pattern) builder.pattern(layer); }
        public String get(int i) { return pattern[i]; }
        public String[] get() { return pattern; }
        public char[] getUniqueChars() {
            char[] unique = new char[9];
            int length = 0;
            for (String layer : pattern) {
                for (char item : layer.toCharArray()) if (!has(unique, item)) unique[length] = item; length++;
            }
            char[] uniqueChars = new char[length];
            for (int i = 0; i < unique.length; i++) {
                if (unique[i] != '\0' || unique[i] != ' ') uniqueChars[i] = unique[i];
            }
            return uniqueChars;
        }

        private boolean has(char[] arr, char item) {
            for (char c : arr) if (c == item) return true;
            return false;
        }
    }

    @Override public void generate(RecipeExporter recipeExporter) { }
}
