package net.aaronterry.hisb.exploration.item.tool;

import net.aaronterry.helper.KeyGroup;
import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.HisbMod;
import net.minecraft.item.*;

import java.util.List;

// WOODEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// STONE SWORD -> 3, -2.4F AXE -> 7.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -1.0F, -2.0F
// GOLDEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// IRON SWORD -> 3, -2.4F AXE -> 6.0F, -3.1F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -2.0F, -1.0F
// DIAMOND SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -3.0F, 0.0F
// NETHERITE SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -4.0F, 0.0F
// SCULTIUM SWORD -> 3, -2.0F AXE -> 4.5F, -2.8F PICKAXE -> 1.0F, -2.4F SHOVEL -> 1.5F, -2.6F HOE -> -3.0F, 0.0F

public class ModToolItems extends HelperItems {

    private static final ItemCaller swords = createCaller("hisb_weapon_swords",HisbMod.id());
    private static final ItemCaller weapons = createCaller("hisb_weapon_tools",HisbMod.id()).addChild(swords);
    private static final ItemCaller scultium = createCaller("hisb_scultium_tools",HisbMod.id(),"scultium");
    private static final ItemCaller depnetum = createCaller("hisb_depnetum_tools",HisbMod.id(),"depnetum");
    private static final ItemCaller fortolium = createCaller("hisb_fortolium_tools",HisbMod.id(),"fortolium");
    private static final ItemCaller demandum = createCaller("hisb_demandum_tools",HisbMod.id(),"demandum");
    private static final ItemCaller untillium = createCaller("hisb_untillium_tools",HisbMod.id(),"untillium");
    private static final ItemCaller vormite = createCaller("hisb_vormite_tools",HisbMod.id(),"vormite");
    private static final ItemCaller tools = createCaller("hisb_tools",HisbMod.id()).addChild(scultium)
            .addChild(fortolium).addChild(demandum).addChild(untillium);
    private static final ItemCaller toolsAndWeapons = createCaller("hisb_tools",HisbMod.id()).addChild(tools).addChild(weapons);

    private static final List<ToolMaterial> materials = List.of(ModToolMaterials.SCULTIUM,ModToolMaterials.SCULTIUM,
            ModToolMaterials.DEMANDUM,ModToolMaterials.DEMANDUM,ModToolMaterials.FORTOLIUM,ModToolMaterials.FORTOLIUM,
            ModToolMaterials.UNTILLIUM,ModToolMaterials.UNTILLIUM,ModToolMaterials.VORMITE,ModToolMaterials.VORMITE,ModToolMaterials.SCYTHE);

    public static final Item DEPNETUM_SWORD = makeTool(swords, depnetum, ItemTypes.SWORD, ModToolMaterials.SCULTIUM, 4, -2.0f);
    public static final Item SCULTIUM_AXE = makeTool(weapons, scultium, ItemTypes.AXE, ModToolMaterials.SCULTIUM, 4.5f, -2.8f);
    public static final Item SCULTIUM_PICKAXE = makeTool(scultium, ItemTypes.PICKAXE, ModToolMaterials.SCULTIUM, 1.0f, -2.4f);
    public static final Item SCULTIUM_SHOVEL = makeTool(scultium, ItemTypes.SHOVEL, ModToolMaterials.SCULTIUM, 1.5f, -2.6f);
    public static final Item SCULTIUM_HOE = makeTool(scultium, ItemTypes.HOE, ModToolMaterials.SCULTIUM, -3.0F, -0.1F);

    public static final Item DEMANDUM_SWORD = makeTool(swords, demandum, ItemTypes.SWORD, ModToolMaterials.DEMANDUM, 4.1f, -2.2f);
    public static final Item DEMANDUM_AXE = makeTool(weapons, demandum, ItemTypes.AXE, ModToolMaterials.DEMANDUM, 4.6f, -3.0f);
    public static final Item DEMANDUM_PICKAXE = makeTool(demandum, ItemTypes.PICKAXE, ModToolMaterials.DEMANDUM, 1.1f, -2.5f);
    public static final Item DEMANDUM_SHOVEL = makeTool(demandum, ItemTypes.SHOVEL, ModToolMaterials.DEMANDUM, 1.6f, -2.7f);
    public static final Item DEMANDUM_HOE = makeTool(demandum, ItemTypes.HOE, ModToolMaterials.DEMANDUM, -2.0F, -0.3F);

    public static final Item FORTOLIUM_SWORD = makeTool(swords, fortolium, ItemTypes.SWORD, ModToolMaterials.FORTOLIUM, 4, -2f);
    public static final Item FORTOLIUM_AXE = makeTool(weapons, fortolium, ItemTypes.AXE, ModToolMaterials.FORTOLIUM, 4.5f, -2.7f);
    public static final Item FORTOLIUM_PICKAXE = makeTool(fortolium, ItemTypes.PICKAXE, ModToolMaterials.FORTOLIUM, 1.0f, -2.3f);
    public static final Item FORTOLIUM_SHOVEL = makeTool(fortolium, ItemTypes.SHOVEL, ModToolMaterials.FORTOLIUM, 1.5f, -2.5f);
    public static final Item FORTOLIUM_HOE = makeTool(fortolium, ItemTypes.HOE, ModToolMaterials.FORTOLIUM, -1.0f, 0.0f);

    public static final Item UNTILLIUM_SWORD = makeTool(swords, untillium, ItemTypes.SWORD, ModToolMaterials.UNTILLIUM, 4, -1.9f);
    public static final Item UNTILLIUM_AXE = makeTool(weapons, untillium, ItemTypes.AXE, ModToolMaterials.UNTILLIUM, 4.5f, -2.6f);
    public static final Item UNTILLIUM_PICKAXE = makeTool(untillium, ItemTypes.PICKAXE, ModToolMaterials.UNTILLIUM, 1.0f, -2.2f);
    public static final Item UNTILLIUM_SHOVEL = makeTool(untillium, ItemTypes.SHOVEL, ModToolMaterials.UNTILLIUM, 1.5f, -2.4f);
    public static final Item UNTILLIUM_HOE = makeTool(untillium, ItemTypes.HOE, ModToolMaterials.UNTILLIUM, -1.0f, 0.0f);

    public static final Item VORMITE_SWORD = makeTool(swords, vormite, ItemTypes.SWORD, ModToolMaterials.VORMITE, 3.8f, -1.6f);
    public static final Item VORMITE_AXE = makeTool(weapons, "vormite_axe", ItemTypes.AXE, ModToolMaterials.VORMITE, 4.5f, -3.2f);

    public static final Item REAPER_SCYTHE = makeTool(weapons, "reaper_scythe", ItemTypes.HOE, ModToolMaterials.SCYTHE, 5.5F, -2.0F);

    public static Item[] all() { return all(toolsAndWeapons); }
    public static Item[] tools() { return all(tools); }
    public static Item[] weapons() { return all(weapons); }
    public static KeyGroup<Item,ToolMaterial> swords() { return new KeyGroup<Item,ToolMaterial>(all(swords)).out(materials.toArray(new ToolMaterial[0])); }
    public static Item[] scultiumTools() { return all(scultium); }
    public static Item[] fortoliumTools() { return all(fortolium); }
    public static Item[] demandumTools() { return all(demandum); }
    public static Item[] untilliumTools() { return all(untillium); }

    public static void registerModTools() {
        HisbMod.debug("Registering Mod Tools for " + HisbMod.id());

        addToItemGroup(ItemGroups.COMBAT, weapons());
        addToItemGroup(ItemGroups.TOOLS, tools());
    }
}
