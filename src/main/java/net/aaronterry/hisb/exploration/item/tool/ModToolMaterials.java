package net.aaronterry.hisb.exploration.item.tool;

import net.aaronterry.hisb.exploration.item.ModItems;
import net.aaronterry.hisb.utility.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

// WOOD -> 59, 2.0F, 0.0F, 15, PLANKS
// STONE -> 131, 4.0F, 1.0F, 5, Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)
// IRON -> 250, 6.0F, 2.0F, 14, Ingredient.ofItems(Items.IRON_INGOT)
// DIAMOND -> 1561, 8.0F, 3.0F, 10, Ingredient.ofItems(Items.DIAMOND)
// GOLD -> 32, 12.0F, 0.0F, 22, Ingredient.ofItems(Items.GOLD_INGOT)
// NETHERITE -> 2031, 9.0F, 4.0F, 15, Ingredient.ofItems(Items.NETHERITE_INGOT)

public enum ModToolMaterials implements ToolMaterial {
    SCULTIUM(ModBlockTags.INCORRECT_FOR_SCULTIUM_TOOL, 1000, 10.0F, 6.0F, 13, () -> Ingredient.ofItems(ModItems.SCULTIUM_BONES)),
    FORTOLIUM(ModBlockTags.INCORRECT_FOR_FORTOLIUM_TOOL, 1600, 12.0F, 7.0F, 30, () -> Ingredient.ofItems(ModItems.FORTOLIUM)),
    DEMANDUM(ModBlockTags.INCORRECT_FOR_DEMANDUM_TOOL, 1500, 14.0F, 8F, 16, () -> Ingredient.ofItems(ModItems.DEMANDUM_CHUNK)),
    UNTILLIUM(ModBlockTags.INCORRECT_FOR_UNTILLIUM_TOOL, 2500, 17.0F, 10F, 23, () -> Ingredient.ofItems(ModItems.UNTILLIUM_BAR)),
    ARMITE(ModBlockTags.INCORRECT_FOR_ARMITE_TOOL, 950, 19.0F, 11F, 17, () -> Ingredient.ofItems(ModItems.ARMITE_CHUNK)),
    VORMITE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 3000, 12.0F, 18F, 18, () -> Ingredient.ofItems(ModItems.VORMITE_CLUMP)),
    SCYTHE(ModBlockTags.INCORRECT_FOR_SCYTHE_TOOL, 500, 30.0F, 25F, 1, () -> Ingredient.ofItems(Blocks.AIR));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag; this.itemDurability = itemDurability; this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage; this.enchantability = enchantability; this.repairIngredient = repairIngredient;
    }

    @Override public int getDurability() { return itemDurability; }
    @Override public float getMiningSpeedMultiplier() { return miningSpeed; }
    @Override public float getAttackDamage() { return attackDamage; }
    @Override public TagKey<Block> getInverseTag() { return inverseTag; }
    @Override public int getEnchantability() { return enchantability; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
}
