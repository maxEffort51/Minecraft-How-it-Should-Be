package net.aaronterry.hisb.block.entity;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<PurifierTableBlockEntity> PURIFIER_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(HisbMod.MOD_ID, "purifier_table"),
                    BlockEntityType.Builder.create(PurifierTableBlockEntity::new, ModBlocks.PURIFIER_TABLE)
                            .build());

    public static void registerBlockEntities() {
        HisbMod.LOGGER.info("Registering Block Entities for " + HisbMod.MOD_ID);
    }
}
