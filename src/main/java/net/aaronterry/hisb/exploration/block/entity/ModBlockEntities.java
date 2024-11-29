package net.aaronterry.hisb.exploration.block.entity;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.exploration.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<PurifierBlockEntity> PURIFIER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(HisbMod.id(), "purifier_be"),
            BlockEntityType.Builder.create(PurifierBlockEntity::new, ModBlocks.PURIFIER_TABLE).build());

    public static void registerBlockEntities() {
        HisbMod.debug("Registering Block Entities for " + HisbMod.id());
    }
}
