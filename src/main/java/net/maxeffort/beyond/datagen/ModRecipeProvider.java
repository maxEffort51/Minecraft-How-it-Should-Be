package net.maxeffort.beyond.datagen;

import net.maxeffort.helper.block.HelperBlocks;
import net.maxeffort.helper.datagen.HelperRecipeProvider;
import net.maxeffort.helper.item.HelperItems;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.item.armor.ModArmorItems;
import net.maxeffort.beyond.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends HelperRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter xpt) {
        /*--- ITEMS ---*/

        // OVERWORLD ITEM RECIPES
        Shaped.recipe(RecipeCategory.MISC, Items.HEART_OF_THE_SEA).pattern(new Pattern("$|$","-*-","$|$")).input(new char[] {'$','|','-','*'},
                new Item[] {ModItems.PRISMALITE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.WATER_BUCKET}).needs(0).offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DYREMITE_CHUNK).input(ModItems.PURIFIED_DIAMOND, ModItems.PURIFIED_IRON, ModItems.PURIFIED_REDSTONE, 2)
                .input(ModItems.PURIFIED_LAPIS, ModItems.PURIFIED_EMERALD, ModItems.PURIFIED_COPPER, 1).needsResult().offer(xpt, "dyremite_chunk_main");
        // NETHER ITEM RECIPES
        Shapeless.recipe(RecipeCategory.MISC, ModItems.FIRITE_SCRAP, 4).input(ModItems.PURIFIED_SCRAP, 4, ModItems.CRYSTALLINE_QUARTZ, 3, Items.LAVA_BUCKET, 1)
                .needsResult().offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DIRTY_SCRAP, 4).input(Items.NETHERITE_SCRAP, 5, ModItems.PURIFIED_SCRAP, 2, ModItems.CRYSTALLINE_QUARTZ, 2)
                .needsResult().offer(xpt, "dirty_scrap_main");
        // END ITEM RECIPES
        Shapeless.recipe(RecipeCategory.MISC, ModItems.BLAST_CHARGE).input(ModItems.BLAST_SHARD,9).needs(ModItems.BLAST_SHARD).offer(xpt);
        // DEMANDI ITEM RECIPES
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DEEP_ROD, 3).input(Items.STICK, 3, ModItems.SCULTIUM_BONES, 3).needsResult().offer(xpt);
        Shaped.recipe(RecipeCategory.MISC, ModItems.DEMANDUM_GEAR).pattern(new Pattern(" # ","###"," # ")).input('#',ModItems.DEMANDUM_CHUNK).needs(0).offer(xpt);

        // ORE SMELTING RECIPES
        offerSmelting(xpt, List.of(ModBlocks.PRISMALITE_ORE), RecipeCategory.MISC, ModItems.PRISMALITE_SHARD, 2.5f, 150, "prismalite_process");
        offerSmelting(xpt, List.of(Items.QUARTZ), RecipeCategory.MISC, ModItems.BAKED_QUARTZ, 3.5f, 300, "baking_quartz");
        offerSmelting(xpt, List.of(ModItems.DIRTY_SCRAP), RecipeCategory.MISC, ModItems.DEBRITINUM_SCRAP, 10f, 300, "debritinum_process");
        offerSmelting(xpt, List.of(ModBlocks.PURVIUM_ORE,ModBlocks.BURPLE_PURVIUM_ORE,ModBlocks.PURPUR_PURVIUM_ORE), RecipeCategory.MISC, ModItems.PURVIUM_CHUNK, 2f, 150, "purvium_process");
        offerSmelting(xpt, List.of(ModBlocks.DEPNETUM_ORE,ModBlocks.DARK_DEPNETUM_ORE), RecipeCategory.MISC, ModItems.DEPNETUM_CLUMP, 12f, 350, "depnetum_process");
        offerSmelting(xpt, List.of(ModBlocks.SCULTIUM_ORE), RecipeCategory.MISC, ModItems.SCULTIUM_BONES, 6f, 250, "scultium_smelting");
        offerSmelting(xpt, List.of(ModBlocks.UNTILLIUM_ORE,ModBlocks.INDUG_UNTILLIUM_ORE), RecipeCategory.MISC, ModItems.UNTILLIUM_BAR, 15f, 200, "untillium_process");
        offerSmelting(xpt, List.of(ModBlocks.JADE_ORE), RecipeCategory.MISC, ModItems.JADEITE, 3.7f, 250, "jadeite_smelting");
        offerSmelting(xpt, List.of(ModBlocks.VORMITE_ORE,ModBlocks.BRAWNSTONE_VORMITE_ORE), RecipeCategory.MISC, ModItems.VORMITE_CLUMP, 13f, 200, "vormite_process");
        offerSmelting(xpt, List.of(ModBlocks.INFITIUM_ORE), RecipeCategory.MISC, ModItems.INFITIUM_RING, 14f, 180, "infitium_process");
        offerSmelting(xpt, List.of(ModBlocks.ARMITE_ORE, ModBlocks.BLEAKSTONE_ARMITE_ORE), RecipeCategory.MISC, ModItems.ARMITE_CHUNK, 7f, 120, "armite_process");

        /* ARMOR */
        Custom.firite(xpt);
        // PURVIUM -> Recipe.buildArmor(xpt, ...).pattern(1, new Pattern("*#*","*#*""* *"), Items.PHANTOM_MEMBRANE).finish();
        Recipe.oneIngredient(xpt, ModArmorItems.purvium(false), ModItems.PURVIUM_CHUNK, RecipeCategory.COMBAT, '#', new Pattern[] {
                new Pattern("###","# #"),new Pattern("###","# #","# #"),new Pattern("# #","# #")});

        Recipe.armor(xpt, ModArmorItems.depnetum(), ModItems.DEPNETUM_CLUMP);
        Recipe.armor(xpt, ModArmorItems.fortolium(), ModItems.FORTOLIUM);
        Recipe.armor(xpt, ModArmorItems.infitium(), ModItems.INFITIUM_RING);
        Recipe.armor(xpt, ModArmorItems.untillium(), ModItems.UNTILLIUM_BAR);

        HelperItems.runRecipes(xpt);

        /*--- BLOCKS ---*/

        List<HelperBlocks.Sorted> recipes = ModBlocks.getBlocksWithRecipes();
        for (HelperBlocks.Sorted sort : recipes) {
            List<GenericDetails> details = sort.getRecipes();
            details.forEach(recipe -> recipe.offer(xpt));
        }

        /* IMPROVEMENT IDEAS */

//         Shaped.recipes(category, results[], patterns[], inputChars[], inputs[], needs[], xpt)
//         Shaped.recipes(categories[], results[], patterns[], inputChars[], inputs[], needs[], xpt)
//         Shapeless.recipes(category, results[], inputs[], amounts[], needsResult, xpt)
//         Recipe.specific(xpt, block, item, pattern, category)
//         Recipe.generic("reverse", category, blocks[], items[], xpt)
//         Recipe.generic("specific", category, blocks[], items[], patterns[], xpt)
//         Recipe.generic("tool", tooltypes[], tools[], items[], sticks[], xpt)
//         Recipe.generic("armor", armorsets[][], items[])
    }
}
