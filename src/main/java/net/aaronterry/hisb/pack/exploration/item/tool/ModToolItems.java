package net.aaronterry.hisb.pack.exploration.item.tool;

import net.aaronterry.helper.item.HelperItems;
import net.aaronterry.hisb.main.HisbMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// WOODEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// STONE SWORD -> 3, -2.4F AXE -> 7.0F, -3.2F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -1.0F, -2.0F
// GOLDEN SWORD -> 3, -2.4F AXE -> 6.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> 0.0F, -3.0F
// IRON SWORD -> 3, -2.4F AXE -> 6.0F, -3.1F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -2.0F, -1.0F
// DIAMOND SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -3.0F, 0.0F
// NETHERITE SWORD -> 3, -2.4F AXE -> 5.0F, -3.0F PICKAXE -> 1.0F, -2.8F SHOVEL -> 1.5F, -3.0F HOE -> -4.0F, 0.0F
// SCULTIUM SWORD -> 3, -2.0F AXE -> 4.5F, -2.8F PICKAXE -> 1.0F, -2.4F SHOVEL -> 1.5F, -2.6F HOE -> -3.0F, 0.0F

public class ModToolItems extends HelperItems {

    private static final ItemCaller scultium = createCaller("hisb_scultium_tools",HisbMod.id(),"scultium");
    private static final ItemCaller depnetum = createCaller("hisb_depnetum_tools",HisbMod.id(),"depnetum");
    private static final ItemCaller demandum = createCaller("hisb_demandum_tools",HisbMod.id(),"demandum");
    private static final ItemCaller untillium = createCaller("hisb_untillium_tools",HisbMod.id(),"untillium");
    private static final ItemCaller reaper = createCaller("hisb_reaper_tools",HisbMod.id());
    private static final ItemCaller tools = createCaller("hisb_tools",HisbMod.id()).addChild(scultium).addChild(depnetum)
            .addChild(demandum).addChild(untillium).addChild(reaper);

    public static final Item DEPNETUM_SWORD = makeTool(depnetum, ItemTypes.SWORD, ModToolMaterials.SCULTIUM, 4, -2.0f);
    public static final Item SCULTIUM_AXE = makeTool(scultium, ItemTypes.AXE, ModToolMaterials.SCULTIUM, 4.5f, -2.8f);
    public static final Item SCULTIUM_PICKAXE = makeTool(scultium, ItemTypes.PICKAXE, ModToolMaterials.SCULTIUM, 1.0f, -2.4f);
    public static final Item SCULTIUM_SHOVEL = makeTool(scultium, ItemTypes.SHOVEL, ModToolMaterials.SCULTIUM, 1.5f, -2.6f);
    public static final Item SCULTIUM_HOE = makeTool(scultium, ItemTypes.HOE, ModToolMaterials.SCULTIUM, -3.0F, -0.1F);

    public static final Item DEMANDUM_SWORD = makeTool(demandum, ItemTypes.SWORD, ModToolMaterials.DEMANDUM, 4.1f, -2.2f);
    public static final Item DEMANDUM_AXE = makeTool(demandum, ItemTypes.AXE, ModToolMaterials.DEMANDUM, 4.6f, -3.0f);
    public static final Item DEMANDUM_PICKAXE = makeTool(demandum, ItemTypes.PICKAXE, ModToolMaterials.DEMANDUM, 1.1f, -2.5f);
    public static final Item DEMANDUM_SHOVEL = makeTool(demandum, ItemTypes.SHOVEL, ModToolMaterials.DEMANDUM, 1.6f, -2.7f);
    public static final Item DEMANDUM_HOE = makeTool(demandum, ItemTypes.HOE, ModToolMaterials.DEMANDUM, -2.0F, -0.3F);

    public static final Item UNTILLIUM_SWORD = makeTool(untillium, ItemTypes.SWORD, ModToolMaterials.UNTILLIUM, 4, -1.9f);
    public static final Item UNTILLIUM_AXE = makeTool(untillium, ItemTypes.AXE, ModToolMaterials.UNTILLIUM, 4.5f, -2.6f);
    public static final Item UNTILLIUM_PICKAXE = makeTool(untillium, ItemTypes.PICKAXE, ModToolMaterials.UNTILLIUM, 1.0f, -2.2f);
    public static final Item UNTILLIUM_SHOVEL = makeTool(untillium, ItemTypes.SHOVEL, ModToolMaterials.UNTILLIUM, 1.5f, -2.4f);
    public static final Item UNTILLIUM_HOE = makeTool(untillium, ItemTypes.HOE, ModToolMaterials.UNTILLIUM, -1.0f, 0.0f);

    public static final Item REAPER_SCYTHE = makeTool(reaper, "reaper_scythe", "hoe", ModToolMaterials.SCYTHE, 5.5F, -2.0F);

    public static final Item[] all() { return all(tools); }
    public static final Item depnetumTool() { return all(depnetum)[0]; }
    public static final Item[] scultiumTools() { return all(scultium); }
    public static final Item[] demandumTools() { return all(demandum); }
    public static final Item[] untilliumTools() { return all(untillium); }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HisbMod.id(), name), item);
    }

    public static void addAll(FabricItemGroupEntries entries, Item[] items) {
        for (Item item : items) {
            entries.add(item);
        }
    }

    public static void registerModTools() {
        HisbMod.debug("Registering Mod Tools for " + HisbMod.id());

        addToItemGroup(ItemGroups.TOOLS, all());
    }
}
