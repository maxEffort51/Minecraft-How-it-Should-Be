package net.aaronterry.helper.block.table;

import net.aaronterry.hisb.main.HisbMod;
import net.aaronterry.hisb.pack.exploration.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TableVariables {

    public static final BlockEntityType<TableBlockEntity> TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(HisbMod.id(), "table_block_entity"),
            BlockEntityType.Builder.create(TableBlockEntity::new, ModBlocks.PURIFIER_TABLE).build());
}
