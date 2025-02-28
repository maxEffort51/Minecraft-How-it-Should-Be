package net.maxeffort.beyond.datagen;

import net.maxeffort.helper.block.HelperBlocks;
import net.maxeffort.beyond.block.ModBlocks;
import net.maxeffort.beyond.item.ModItems;
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

import java.util.List;
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

    public void addOreDrops(List<HelperBlocks.OreData> oreData) {
        oreData.forEach(data -> addOreDrop(data.getParent(),data.getDrop().asItem(), data.getMin(), data.getMax()));
    }

    public void addDrop(List<Block> blocks) { for (Block block : blocks) addDrop(block); }
    public void addSlabDrop(List<Block> blocks) { for (Block block : blocks) addDrop(block, slabDrops(block)); }
    public void addDoorDrop(List<Block> blocks) { for (Block block : blocks) addDrop(block, doorDrops(block)); }

    @Override
    public void generate() {
        addDrop(ModBlocks.getFromDropType(HelperBlocks.SortInputs.DROP_SELF));
        addSlabDrop(ModBlocks.getFromBlockType(HelperBlocks.SortInputs.DROP_SLAB));
        addDoorDrop(ModBlocks.getFromDropType(HelperBlocks.SortInputs.DROP_DOOR));

        addDrop(ModBlocks.SHELF_RUIN, ModItems.BOOK_RUIN_SCRAP);

        addOreDrops(ModBlocks.getFromOreType(HelperBlocks.SortInputs.BASIC_ORE));
        addOreDrops(ModBlocks.getFromOreType(HelperBlocks.SortInputs.SPECIFIC_ORE));
    }
}
