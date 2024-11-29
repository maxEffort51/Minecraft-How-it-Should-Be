package net.aaronterry.hisb.exploration.datagen;

import net.aaronterry.helper.block.HelperBlocks;
import net.aaronterry.helper.datagen.HelperRecipeProvider;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.aaronterry.hisb.exploration.item.armor.ModArmorItems;
import net.aaronterry.hisb.exploration.item.ModItems;
import net.aaronterry.hisb.exploration.item.tool.ModToolItems;
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

        Shaped.recipe(RecipeCategory.MISC, Items.HEART_OF_THE_SEA).pattern(new Pattern("$|$","-*-","$|$")).input(new char[] {'$','|','-','*'},
                new Item[] {ModItems.PRISMALITE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.WATER_BUCKET}).needs(0).offer(xpt);
        offerSmelting(xpt, List.of(ModBlocks.PRISMALITE_ORE), RecipeCategory.MISC, ModItems.PRISMALITE_SHARD, 2.5f, 150, "prismalite_process");
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DYREMITE_CHUNK).input(ModItems.PURIFIED_DIAMOND, ModItems.PURIFIED_IRON, ModItems.PURIFIED_REDSTONE, 2)
                .input(ModItems.PURIFIED_LAPIS, ModItems.PURIFIED_EMERALD, ModItems.PURIFIED_COPPER, 1).needsResult().offer(xpt, "dyremite_chunk_main");

        offerSmelting(xpt, List.of(Items.QUARTZ), RecipeCategory.MISC, ModItems.BAKED_QUARTZ, 10f, 300, "baking_quartz");
        Shapeless.recipe(RecipeCategory.MISC, ModItems.FIRITE_SCRAP, 4).input(ModItems.PURIFIED_SCRAP, 4, ModItems.CRYSTALLINE_QUARTZ, 3, Items.LAVA_BUCKET, 1)
                .needsResult().offer(xpt);
        Shapeless.recipe(RecipeCategory.MISC, ModItems.DIRTY_SCRAP, 4).input(Items.NETHERITE_SCRAP, 5, ModItems.PURIFIED_SCRAP, 2, ModItems.CRYSTALLINE_QUARTZ, 2)
                .needsResult().offer(xpt, "dirty_scrap_main");
        offerSmelting(xpt, List.of(ModItems.DIRTY_SCRAP), RecipeCategory.MISC, ModItems.DEBRITINUM_SCRAP, 10f, 300, "debritinum_process");

        Shapeless.recipe(RecipeCategory.MISC, ModItems.BLAST_CHARGE).input(ModItems.BLAST_SHARD,9).needs(ModItems.BLAST_SHARD).offer(xpt);
        offerSmelting(xpt, List.of(ModBlocks.PURVIUM_ORE,ModBlocks.BURPLE_PURVIUM_ORE,ModBlocks.PURPUR_PURVIUM_ORE), RecipeCategory.MISC, ModItems.PURVIUM_CHUNK, 2f, 150, "purvium_process");

        Shapeless.recipe(RecipeCategory.MISC, ModItems.DEEP_ROD, 3).input(Items.STICK, 3, ModItems.SCULTIUM_BONES, 3).needsResult().offer(xpt);
        Shaped.recipe(RecipeCategory.MISC, ModItems.DEMANDUM_GEAR).pattern(new Pattern(" # ","###"," # ")).input('#',ModItems.DEMANDUM_CHUNK).needs(0).offer(xpt);
        offerSmelting(xpt, List.of(ModBlocks.DEPNETUM_ORE,ModBlocks.DARK_DEPNETUM_ORE), RecipeCategory.MISC, ModItems.DEPNETUM_CLUMP, 12f, 350, "depnetum_process");
        offerSmelting(xpt, List.of(ModBlocks.SCULTIUM_ORE), RecipeCategory.MISC, ModItems.SCULTIUM_BONES, 6f, 250, "scultium_smelting");

        // HESPER WOOD: SLAB, STAIRS, DOOR, TRAPDOOR, FENCE, FENCE GATE
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_SLAB, 6).input('#',ModBlocks.HESPER_PLANKS).pattern(new Pattern("   ","###")).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_STAIRS, 4).input('#',ModBlocks.HESPER_PLANKS).pattern(new Pattern("  #"," ##","###")).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_DOOR, 3).input('#',ModBlocks.HESPER_PLANKS).pattern(new Pattern("##","##","##")).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_TRAPDOOR, 2).input('#',ModBlocks.HESPER_PLANKS).pattern(new Pattern("###","###")).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_FENCE, 3).input('#',ModBlocks.HESPER_PLANKS).input('/',Items.STICK).pattern(new Pattern("#/#","#/#")).needs(0).offer(xpt);
        Shaped.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HESPER_FENCE_GATE, 1).input('#',ModBlocks.HESPER_PLANKS).input('/',Items.STICK).pattern(new Pattern("/#/","/#/")).needs(0).offer(xpt);
        offerSmelting(xpt, List.of(ModBlocks.UNTILLIUM_ORE,ModBlocks.INDUG_UNTILLIUM_ORE), RecipeCategory.MISC, ModItems.UNTILLIUM_BAR, 15f, 200, "untillium_process");

        /* TOOLS */
        Recipe.swords(xpt, ModToolItems.swords(),ModItems.DEEP_ROD,Items.STICK);
        Recipe.toolset(xpt, ModToolItems.scultiumTools(), ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD);
        Recipe.toolset(xpt, ModToolItems.fortoliumTools(), ModItems.FORTOLIUM);
        Recipe.toolset(xpt, ModToolItems.demandumTools(), ModItems.DEMANDUM_CHUNK, Items.BLAZE_ROD);
        Recipe.toolset(xpt, ModToolItems.untilliumTools(), ModItems.UNTILLIUM_BAR);

        /* ARMOR */
        Custom.firite(xpt);
        // PURVIUM -> Recipe.buildArmor(xpt, ...).pattern(1, new Pattern("*#*","*#*""* *"), Items.PHANTOM_MEMBRANE).finish();
        Recipe.oneIngredient(xpt, ModArmorItems.purvium(false), ModItems.PURVIUM_CHUNK, RecipeCategory.COMBAT, '#', new Pattern[] {
                new Pattern("###","# #"),new Pattern("###","# #","# #"),new Pattern("# #","# #")});
        Recipe.armor(xpt, ModArmorItems.depnetum(), ModItems.DEPNETUM_CLUMP);
        Recipe.armor(xpt, ModArmorItems.untillium(), ModItems.UNTILLIUM_BAR);

        /*--- BLOCKS ---*/

        List<HelperBlocks.Sorted> recipes = ModBlocks.getBlocksWithRecipes();
        for (HelperBlocks.Sorted sort : recipes) {
            List<GenericDetails> details = sort.getRecipes();
            details.forEach(recipe -> recipe.offer(xpt));
        }

        /* IMPROVEMENT IDEAS */

        // Shaped.recipes(category, results[], patterns[], inputChars[], inputs[], needs[], xpt)
        // Shaped.recipes(categories[], results[], patterns[], inputChars[], inputs[], needs[], xpt)
        // Shapeless.recipes(category, results[], inputs[], amounts[], needsResult, xpt)
        // Recipe.specific(xpt, block, item, pattern, category)
        // Recipe.generic("reverse", category, blocks[], items[], xpt)
        // Recipe.generic("specific", category, blocks[], items[], patterns[], xpt)
        // Recipe.generic("tool", tooltypes[], tools[], items[], sticks[], xpt)
        // Recipe.generic("armor", armorsets[][], items[])

        /* OLD BLOCK RECIPES */
//        Shapeless.recipe(RecipeCategory.MISC, ModBlocks.PURIFIER_TABLE).input(new Item[]{Items.NETHER_STAR, Items.GHAST_TEAR, Items.BLAZE_ROD, Items.SPONGE, Items.BLUE_ICE},
//                new int[] {2,2,2,1,2}).needsResult().offer(xpt);
//        Shapeless.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEAD_PLANKS, 4).input(ModBlocks.DEAD_LOG).needs(ModBlocks.DEAD_LOG).offer(xpt);

//        Shapeless.recipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STIFF_STONE).input(ModBlocks.STIFF_SOIL, 4).needs(ModBlocks.STIFF_SOIL).offer(xpt);
//        Recipe.reverse(xpt, ModItems.DYREMITE_CHUNK, ModBlocks.DYREMITE_BLOCK, RecipeCategory.MISC);
//        Recipe.reverse(xpt, ModItems.DIRTY_SCRAP, ModBlocks.DEBRITINUM_BLOCK, RecipeCategory.MISC);
    }
}
