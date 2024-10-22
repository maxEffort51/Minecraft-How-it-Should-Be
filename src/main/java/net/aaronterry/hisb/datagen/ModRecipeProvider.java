package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.ModBlocks;
import net.aaronterry.hisb.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void reverseRecipe(RecipeExporter xpt, ItemConvertible base, ItemConvertible compact, RecipeCategory category) {
        offerReversibleCompactingRecipes(xpt, category, base, category, compact);
    }

    public ShapedRecipeJsonBuilder singleShaped(RecipeCategory cat, ItemConvertible result, String[] pattern, ItemConvertible hash) {
        return ShapedRecipeJsonBuilder.create(cat, result)
                .pattern(pattern[0]).pattern(pattern[1]).pattern(pattern[2])
                .input('#', hash)
                .criterion(hasItem(hash), conditionsFromItem(hash));
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DYREMITE_CHUNK)
                .pattern("DLD").pattern("IGI").pattern("RER")
                .input('I',ModItems.PURIFIED_IRON)
                .input('G',ModItems.PURIFIED_GOLD)
                .input('L',ModItems.PURIFIED_LAPIS)
                .input('E',ModItems.PURIFIED_EMERALD)
                .input('R',ModItems.PURIFIED_REDSTONE)
                .input('D',ModItems.PURIFIED_DIAMOND)
                .criterion(hasItem(ModItems.PURIFIED_DIAMOND), conditionsFromItem(ModItems.PURIFIED_DIAMOND))
                .offerTo(exporter, Identifier.of(HisbMod.MOD_ID, "dyremite_chunk_from_purified"));

        reverseRecipe(exporter, ModItems.DYREMITE_CHUNK, ModBlocks.DYREMITE_BLOCK, RecipeCategory.MISC);
    }
}
