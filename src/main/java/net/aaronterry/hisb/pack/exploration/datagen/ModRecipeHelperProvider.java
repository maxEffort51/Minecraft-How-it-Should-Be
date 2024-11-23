package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.item.armor.ModArmorItems;
import net.aaronterry.hisb.pack.exploration.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeHelperProvider extends FabricRecipeProvider {
    public ModRecipeHelperProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

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

        public static class Details {
            private HelperBlocks.SortingPreset preset;
            private final ShapedRecipeJsonBuilder builder;
            private Identifier id;
            private final List<ItemConvertible> inputs = new ArrayList<>();
            public Details(ShapedRecipeJsonBuilder builder) { this.builder = builder; }
            public Details(ShapedRecipeJsonBuilder builder, HelperBlocks.SortingPreset preset) {
                this.builder = builder; this.preset = preset; }

            public Details needs(ItemConvertible item) { builder.criterion(hasItem(item), conditionsFromItem(item)); return this; }
            public Details needs(int i) { builder.criterion(hasItem(inputs.get(i)), conditionsFromItem(inputs.get(i))); return this; }
            public Details pattern(Pattern pattern) { pattern.with(builder); return this; }
            public Details input(char[] chars, ItemConvertible[] items) { inputs.addAll(List.of(items)); for (int i = 0; i < chars.length; i++) builder.input(chars[i],items[i]); return this; }
            public Details input(char c, ItemConvertible item) { inputs.add(item); builder.input(c, item); return this; }
            public Details id(Identifier id) { this.id = id; return this; }
            public void offer(RecipeExporter xpt) { builder.offerTo(xpt); }
            public void offer(RecipeExporter xpt, String unique) { builder.offerTo(xpt, Identifier.of(HisbMod.id(), unique)); }
            public HelperBlocks.SortingPreset endRecipe() { preset.recipe(new GenericDetails(builder, id)); return preset; }
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
            public void offer(RecipeExporter xpt, String unique) { builder.offerTo(xpt, Identifier.of(HisbMod.id(), unique)); }
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

        private static Shaped.Details _tool(String type, Item tool, Item ore, Item stick, Item criterion) {
            Pattern toolPattern = switch(type) {
                case "sword" -> new Pattern(" @ "," @ "," | "); case "axe" -> new Pattern("@@ ","@| "," | ");
                case "pickaxe" -> new Pattern("@@@"," | ", " | "); case "shovel" -> new Pattern(" @ "," | ", " | ");
                case "hoe" -> new Pattern("@@ "," | ", " | "); default -> new Pattern("","","");
            };
            return Shaped.recipe(RecipeCategory.COMBAT, tool).pattern(toolPattern).needs(criterion).input(new char[] {'@','|'}, new Item[] {ore, stick});
        }
        public static void tool(RecipeExporter xpt, String type, Item tool, Item ore, Item stick, Item criterion) { Recipe._tool(type, tool, ore, stick, criterion).offer(xpt); }
        public static void tool(RecipeExporter xpt, String type, Item tool, Item ore, Item stick, Item criterion, String unique) { Recipe._tool(type, tool, ore, stick, criterion).offer(xpt, unique); }

        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore, Item stick, Item criterion) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, stick, criterion);
        }
        public static void toolset(RecipeExporter xpt, Item[] tools, Item ore, Item stick, Item criterion, String oreType, String unique) {
            String[] types = tools.length == 5 ? new String[] {"sword","axe","pickaxe","shovel","hoe"} : new String[] {"axe","pickaxe","shovel","hoe"};
            for (int i = 0; i < tools.length; i++) Recipe.tool(xpt, types[i], tools[i], ore, stick, criterion, String.format("%s_%s_%s", oreType, types[i], unique));
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
