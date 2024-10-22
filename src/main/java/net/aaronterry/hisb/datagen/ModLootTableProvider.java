package net.aaronterry.hisb.datagen;

import net.aaronterry.hisb.block.ModBlocks;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
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

    @Override
    public void generate() {
        addDrop(ModBlocks.DYREMITE_BLOCK);
    }
}
