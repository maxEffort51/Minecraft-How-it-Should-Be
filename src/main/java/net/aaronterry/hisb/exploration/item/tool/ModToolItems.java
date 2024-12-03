package net.aaronterry.hisb.exploration.item.tool;

import net.aaronterry.helper.KeyGroup;
import net.aaronterry.helper.datagen.HelperRecipeProvider;
import net.aaronterry.helper.item.HelperToolItems;
import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.item.ModItems;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;

// WOODEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// STONE SWORD -> 3, -2.4F AXE -> 7.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -1.0F, -2.0F
// GOLDEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// IRON SWORD -> 3, -2.4F AXE -> 6.0F, -3.1F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -2.0F, -1.0F
// DIAMOND SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -3.0F, 0.0F
// NETHERITE SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -4.0F, 0.0F
// SCULTIUM SWORD -> 3, -2.0F AXE -> 4.5F, -2.8F PICKAXE -> 1.0F, -2.4F SHOVEL -> 1.5F, -2.6F HOE -> -3.0F, 0.0F

public class ModToolItems extends HelperToolItems {

    private static final ItemCaller swords = createCaller("hisb_weapon_swords",HisbMod.id());
    private static final ItemCaller weapons = createCaller("hisb_weapon_tools",HisbMod.id()).addChild(swords);
    private static final ItemCaller scultium = createCaller("hisb_scultium_tools",HisbMod.id(),"scultium");
    private static final ItemCaller depnetum = createCaller("hisb_depnetum_tools",HisbMod.id(),"depnetum");
    private static final ItemCaller fortolium = createCaller("hisb_fortolium_tools",HisbMod.id(),"fortolium");
    private static final ItemCaller demandum = createCaller("hisb_demandum_tools",HisbMod.id(),"demandum");
    private static final ItemCaller untillium = createCaller("hisb_untillium_tools",HisbMod.id(),"untillium");
    private static final ItemCaller armite = createCaller("hisb_armite_tools",HisbMod.id(),"armite");
    private static final ItemCaller vormite = createCaller("hisb_vormite_tools",HisbMod.id(),"vormite");
    private static final ItemCaller tools = createCaller("hisb_tools",HisbMod.id()).addChild(scultium)
            .addChild(fortolium).addChild(demandum).addChild(untillium).addChild(armite);
    private static final ItemCaller toolsAndWeapons = createCaller("hisb_tools",HisbMod.id()).addChild(tools).addChild(weapons);

    private static final List<ToolMaterial> materials = List.of(ModToolMaterials.SCULTIUM,ModToolMaterials.SCULTIUM,
            ModToolMaterials.DEMANDUM,ModToolMaterials.DEMANDUM,ModToolMaterials.FORTOLIUM,ModToolMaterials.FORTOLIUM,
            ModToolMaterials.UNTILLIUM,ModToolMaterials.UNTILLIUM,ModToolMaterials.VORMITE,ModToolMaterials.VORMITE,ModToolMaterials.SCYTHE);

    public static final Item DEPNETUM_SWORD = makeTool(swords, depnetum, ItemTypes.SWORD, ModToolMaterials.SCULTIUM, 4, -2.0f).toolRecipe(ModItems.DEPNETUM_CLUMP, ModItems.DEEP_ROD).finish();
    public static final Item SCULTIUM_AXE = makeTool(weapons, scultium, ItemTypes.AXE, ModToolMaterials.SCULTIUM, 4.5f, -2.8f).toolRecipe(ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD).finish();
    public static final Item SCULTIUM_PICKAXE = makeTool(scultium, ItemTypes.PICKAXE, ModToolMaterials.SCULTIUM, 1.0f, -2.4f).toolRecipe(ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD).finish();
    public static final Item SCULTIUM_SHOVEL = makeTool(scultium, ItemTypes.SHOVEL, ModToolMaterials.SCULTIUM, 1.5f, -2.6f).toolRecipe(ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD).finish();
    public static final Item SCULTIUM_HOE = makeTool(scultium, ItemTypes.HOE, ModToolMaterials.SCULTIUM, -3.0F, -0.1F).toolRecipe(ModItems.SCULTIUM_BONES, ModItems.DEEP_ROD).finish();

    public static final Item DEMANDUM_SWORD = makeTool(swords, demandum, ItemTypes.SWORD, ModToolMaterials.DEMANDUM, 4.1f, -2.2f).toolRecipe(ModItems.DEMANDUM_CHUNK, ModItems.DEEP_ROD).finish();
    public static final Item DEMANDUM_AXE = makeTool(weapons, demandum, ItemTypes.AXE, ModToolMaterials.DEMANDUM, 4.6f, -3.0f).toolRecipe(ModItems.DEMANDUM_CHUNK, ModItems.DEEP_ROD).finish();
    public static final Item DEMANDUM_PICKAXE = makeTool(demandum, ItemTypes.PICKAXE, ModToolMaterials.DEMANDUM, 1.1f, -2.5f).toolRecipe(ModItems.DEMANDUM_CHUNK, ModItems.DEEP_ROD).finish();
    public static final Item DEMANDUM_SHOVEL = makeTool(demandum, ItemTypes.SHOVEL, ModToolMaterials.DEMANDUM, 1.6f, -2.7f).toolRecipe(ModItems.DEMANDUM_CHUNK, ModItems.DEEP_ROD).finish();
    public static final Item DEMANDUM_HOE = makeTool(demandum, ItemTypes.HOE, ModToolMaterials.DEMANDUM, -2.0F, -0.3F).toolRecipe(ModItems.DEMANDUM_CHUNK, ModItems.DEEP_ROD).finish();

    public static final Item FORTOLIUM_SWORD = makeTool(swords, fortolium, ItemTypes.SWORD, ModToolMaterials.FORTOLIUM, 4, -2f).toolRecipe(ModItems.FORTOLIUM).finish();
    public static final Item FORTOLIUM_AXE = makeTool(weapons, fortolium, ItemTypes.AXE, ModToolMaterials.FORTOLIUM, 4.5f, -2.7f).toolRecipe(ModItems.FORTOLIUM).finish();
    public static final Item FORTOLIUM_PICKAXE = makeTool(fortolium, ItemTypes.PICKAXE, ModToolMaterials.FORTOLIUM, 1.0f, -2.3f).toolRecipe(ModItems.FORTOLIUM).finish();
    public static final Item FORTOLIUM_SHOVEL = makeTool(fortolium, ItemTypes.SHOVEL, ModToolMaterials.FORTOLIUM, 1.5f, -2.5f).toolRecipe(ModItems.FORTOLIUM).finish();
    public static final Item FORTOLIUM_HOE = makeTool(fortolium, ItemTypes.HOE, ModToolMaterials.FORTOLIUM, -1.0f, 0.0f).toolRecipe(ModItems.FORTOLIUM).finish();

    public static final Item UNTILLIUM_SWORD = makeTool(swords, untillium, ItemTypes.SWORD, ModToolMaterials.UNTILLIUM, 4, -1.9f).toolRecipe(ModItems.UNTILLIUM_BAR).finish();
    public static final Item UNTILLIUM_AXE = makeTool(weapons, untillium, ItemTypes.AXE, ModToolMaterials.UNTILLIUM, 4.5f, -2.6f).toolRecipe(ModItems.UNTILLIUM_BAR).finish();
    public static final Item UNTILLIUM_PICKAXE = makeTool(untillium, ItemTypes.PICKAXE, ModToolMaterials.UNTILLIUM, 1.0f, -2.2f).toolRecipe(ModItems.UNTILLIUM_BAR).finish();
    public static final Item UNTILLIUM_SHOVEL = makeTool(untillium, ItemTypes.SHOVEL, ModToolMaterials.UNTILLIUM, 1.5f, -2.4f).toolRecipe(ModItems.UNTILLIUM_BAR).finish();
    public static final Item UNTILLIUM_HOE = makeTool(untillium, ItemTypes.HOE, ModToolMaterials.UNTILLIUM, -1.0f, 0.0f).toolRecipe(ModItems.UNTILLIUM_BAR).finish();

    private static final List<ItemConvertible> armite_recipe_items = List.of(Items.STICK, ModItems.ARMITE_CHUNK,ModItems.THICK_FUR);
    public static final Item ARMITE_WARMSWORD = toolBuilder(swords, ItemTypes.SWORD, ModToolMaterials.ARMITE).name("armite_warmsword").attack(4,-1.9f).recipe(RecipeCategory.COMBAT)
            .input(List.of('|','&','#'),armite_recipe_items).needs(1).pattern(HelperRecipeProvider.pattern("#&#","#&#"," | ")).toolBuilder().finish();
    public static final Item ARMITE_FURAXE = toolBuilder(weapons, ItemTypes.AXE, ModToolMaterials.ARMITE).addCaller(armite).name("armite_furaxe").attack(4.5f, -2.6f).recipe(RecipeCategory.TOOLS)
            .input(List.of('|','&','#'),armite_recipe_items).needs(1).pattern(HelperRecipeProvider.pattern("&# ","&| "," | ")).toolBuilder().finish();
    public static final Item ARMITE_SPIKEPICK = toolBuilder(armite, ItemTypes.PICKAXE, ModToolMaterials.ARMITE).name("armite_spikepick").attack(1.0f, -2.2f).recipe(RecipeCategory.TOOLS)
            .input(List.of('|','&','#'),armite_recipe_items).needs(1).pattern(HelperRecipeProvider.pattern("&#&"," | "," | ")).toolBuilder().finish();
    public static final Item ARMITE_FUZZY_SPADE = toolBuilder(armite, ItemTypes.SHOVEL, ModToolMaterials.ARMITE).name("armite_fuzzy_spade").attack(1.5f, -2.4f).recipe(RecipeCategory.TOOLS)
            .input(List.of('|','&','#'),armite_recipe_items).needs(1).pattern(HelperRecipeProvider.pattern("&","#","|")).toolBuilder().finish();
    public static final Item ARMITE_THICKHOE = toolBuilder(armite, ItemTypes.HOE, ModToolMaterials.ARMITE).name("armite_thickhoe").attack(-1.0f, 0.0f).recipe(RecipeCategory.TOOLS)
            .input(List.of('|','&','#'),armite_recipe_items).needs(1).pattern(HelperRecipeProvider.pattern("&# "," | "," | ")).toolBuilder().finish();

    public static final Item VORMITE_SWORD = makeTool(swords, vormite, ItemTypes.SWORD, ModToolMaterials.VORMITE, 4.2f, -1.6f).toolRecipe(ModItems.VORMITE_CLUMP, ModItems.VORMITE_ROD).finish();
    public static final Item VORMITE_AXE = makeTool(weapons, "vormite_axe", ItemTypes.AXE, ModToolMaterials.VORMITE, 4.5f, -3.2f).toolRecipe(ModItems.VORMITE_CLUMP, ModItems.VORMITE_ROD).finish();

    public static final Item REAPER_SCYTHE = makeTool(weapons, "reaper_scythe", ItemTypes.HOE, ModToolMaterials.SCYTHE, 5.5F, -2.0F).finish();

    public static Item[] all() { return all(toolsAndWeapons); }
    public static Item[] tools() { return all(tools); }
    public static Item[] weapons() { return all(weapons); }
    public static KeyGroup<Item,ToolMaterial> swords() { return new KeyGroup<Item,ToolMaterial>(all(swords)).out(materials.toArray(new ToolMaterial[0])); }
    public static Item[] scultiumTools() { return all(scultium); }
    public static Item[] fortoliumTools() { return all(fortolium); }
    public static Item[] demandumTools() { return all(demandum); }
    public static Item[] untilliumTools() { return all(untillium); }
    public static Item[] armiteTools() { return all(untillium); }

    public static void registerModTools() {
        HisbMod.debug("Registering Mod Tools for " + HisbMod.id());

        addToItemGroup(ItemGroups.COMBAT, weapons());
        addToItemGroup(ItemGroups.TOOLS, tools());
    }
}
