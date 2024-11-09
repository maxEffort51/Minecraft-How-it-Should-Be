package net.aaronterry.hisb.pack.exploration.datagen;

import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void addOreDrop(Block block, Item result, float min, float max) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        super.addDrop(block, this.dropsWithSilkTouch(block, this.applyExplosionDecay(block, ((LeafEntry.Builder<?>)
                ItemEntry.builder(result).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE))))));
    }

    public void addOreDrops(Block[] blocks, Item[] items) { for (int i = 0; i < blocks.length; i++) addOreDrop(blocks[i], items[i], 1, 1); }
    public void addOreDrops(Block[] blocks, Item[] items, int[] min, int[] max) { for (int i = 0; i < blocks.length; i++) addOreDrop(blocks[i], items[i], min[i], max[i]); }

    public void addDrop(Block[] blocks) { for (Block block : blocks) addDrop(block); }
    public void addSlabDrop(Block[] blocks) { for (Block block : blocks) addDrop(block, slabDrops(block)); }
    public void addSameOreDrop(Block[] blocks, Item result, int min, int max) { for (Block block : blocks) addOreDrop(block, result, min, max); }

    @Override
    public void generate() {
        addDrop(ModBlocks.DROP_SELF);
        addDrop(ModBlocks.NON_BLOCK_DROP_SELF);
        addDrop(ModBlocks.WOOD);
        addSlabDrop(ModBlocks.SLAB);

        // Ores
        addOreDrops(ModBlocks.Ores.BASIC,ModBlocks.Ores.BASIC_DROPS);
        addOreDrops(ModBlocks.Ores.SPECIFIC, ModBlocks.Ores.SPECIFIC_DROPS, ModBlocks.Ores.SPECIFIC_MIN, ModBlocks.Ores.SPECIFIC_MAX);
    }
}
