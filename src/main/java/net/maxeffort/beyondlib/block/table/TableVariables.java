package net.maxeffort.helper.block.table;

import net.maxeffort.beyond.main.BeyondMod;
import net.maxeffort.beyond.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TableVariables {

    public static final BlockEntityType<TableBlockEntity> TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(BeyondMod.id(), "table_block_entity"),
            BlockEntityType.Builder.create(TableBlockEntity::new, ModBlocks.PURIFIER_TABLE).build());
}
