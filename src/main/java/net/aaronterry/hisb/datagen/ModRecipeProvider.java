package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.item.ModArmorItems;
import net.aaronterry.hisb.item.ModItems;
import net.aaronterry.hisb.item.ModToolItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends ModRecipeHelperProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter xpt) {

        // Shaped.recipes(category, results[], patterns[], inputChars[], inputs[], needs[], xpt)
        // Shaped.recipes(categories[], results[], patterns[], inputChars[], inputs[], needs[], xpt)

        // SHAPED
        Shaped.recipe(RecipeCategory.COMBAT, ModArmorItems.PURVIUM_ELYTRA).pattern(new Pattern("*#*","*#*","* *"))
                .input(new char[] {'*','#'}, new Item[] {ModItems.PURVIUM_CHUNK,Items.PHANTOM_MEMBRANE}).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.MISC, Items.HEART_OF_THE_SEA).pattern(new Pattern("$|$","-*-","$|$")).input(new char[] {'$','|','-','*'},
                new Item[] {ModItems.PRISMALITE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.WATER_BUCKET}).needs(0).offer(xpt);

        // Shapeless.recipes(category, results[], inputs[], amounts[], needsResult, xpt)

        // SHAPELESS
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DYREMITE_CHUNK).input(ModItems.PURIFIED_DIAMOND, ModItems.PURIFIED_IRON, ModItems.PURIFIED_REDSTONE, 2)
                .input(ModItems.PURIFIED_LAPIS, ModItems.PURIFIED_EMERALD, ModItems.PURIFIED_COPPER, 1).needsResult().offer(xpt, "dyremite_chunk_main");
        Shapeless.recipe(RecipeCategory.MISC, ModItems.FIRITE_SCRAP).input(ModItems.PURIFIED_SCRAP, 4, ModItems.CRYSTALLINE_QUARTZ, 3, Items.LAVA_BUCKET, 1)
                .needsResult().offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DIRTY_SCRAP).input(Items.NETHERITE_SCRAP, 5, ModItems.PURIFIED_SCRAP, 2, ModItems.CRYSTALLINE_QUARTZ, 2)
                .needsResult().offer(xpt, "dirty_scrap_main");
        Shapeless.recipe(RecipeCategory.MISC, ModBlocks.PURIFIER_TABLE).input(new Item[]{Items.NETHER_STAR, Items.GHAST_TEAR, Items.BLAZE_ROD, Items.SPONGE, Items.BLUE_ICE},
                new int[] {2,2,2,1,2}).needsResult().offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DEEP_ROD).input(Items.STICK, 3, ModItems.SCULTIUM_BONES, 3).needsResult().offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.BLAST_CHARGE).input(ModItems.BLAST_SHARD,9).needs(ModItems.BLAST_SHARD).offer(xpt);

        // Recipe.specific(xpt, block, item, pattern, category)
        // Recipe.generic("reverse", category, blocks[], items[], xpt)
        // Recipe.generic("specific", category, blocks[], items[], patterns[], xpt)
        // Recipe.generic("tool", tooltypes[], tools[], items[], sticks[], xpt)
        // Recipe.generic("armor", armorsets[][], items[])

        // RECIPE
        Recipe.reverse(xpt, ModItems.DYREMITE_CHUNK, ModBlocks.DYREMITE_BLOCK, RecipeCategory.MISC);
        Recipe.reverse(xpt, ModItems.DIRTY_SCRAP, ModBlocks.DEBRITINUM_BLOCK, RecipeCategory.MISC);
        Recipe.oneIngredient(xpt, ModArmorItems.PURVIUM_ARMOR, ModItems.PURVIUM_CHUNK, RecipeCategory.COMBAT, '#', new Pattern[] {
                new Pattern("###","# #"),new Pattern("###","# #","# #"),new Pattern("# #","# #")});
        Recipe.toolset(xpt, ModToolItems.SCULTIUM, ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD, ModItems.SCULTIUM_BONES);
        Recipe.tool(xpt, "sword", ModToolItems.DEPNETUM_SWORD, ModItems.DEPNETUM_CLUMP, ModItems.DEEP_ROD, ModItems.DEPNETUM_CLUMP);
        Recipe.armor(xpt, ModArmorItems.DEPNETUM_SET, ModItems.DEPNETUM_CLUMP);

        // CUSTOM
        Custom.firite(xpt);
    }
}
