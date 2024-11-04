package net.aaronterry.hisb.item;

import com.google.common.base.Suppliers;
import net.aaronterry.hisb.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

// WOOD -> 59, 2.0F, 0,0F, 15, PLANKS
// STONE -> 131, 4.0F, 1.0F, 5, Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)
// IRON -> 250, 6.0F, 2.0F, 14, Ingredient.ofItems(Items.IRON_INGOT)
// DIAMOND -> 1561, 8.0F, 3.0F, 10, Ingredient.ofItems(Items.DIAMOND)
// GOLD -> 32, 12.0F, 0.0F, 22, Ingredient.ofItems(Items.GOLD_INGOT)
// NETHERITE -> 2031, 9.0F, 4.0F, 15, Ingredient.ofItems(Items.NETHERITE_INGOT)

public enum ModToolMaterials implements ToolMaterial {
    SCULTIUM(ModTags.Blocks.INCORRECT_FOR_SCULTIUM_TOOL, 1000, 10.0F, 6.0F, 13, () -> Ingredient.ofItems(ModItems.SCULTIUM_BONES));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(final TagKey<Block> inverseTag, final int itemDurability, final float miningSpeed, final float attackDamage,
                     final int enchantability, final Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
}
