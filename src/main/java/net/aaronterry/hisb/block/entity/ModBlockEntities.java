package net.aaronterry.hisb.block.entity;

import net.aaronterry.hisb.HisbMod;
import net.aaronterry.hisb.block.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<PurifierBlockEntity> PURIFIER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(HisbMod.MOD_ID, "purifier_be"),
            BlockEntityType.Builder.create(PurifierBlockEntity::new, ModBlocks.PURIFIER_TABLE).build());

    public static void registerBlockEntities() {
        HisbMod.debug("Registering Block Entities for " + HisbMod.MOD_ID);
    }
}
