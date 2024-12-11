package net.aaronterry.hisb.exploration.worldgen;

import net.aaronterry.hisb.HisbMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {
    public static final StructureType<JigsawStructure> BOOK_RUIN = Registry.register(
            Registries.STRUCTURE_TYPE, Identifier.of(HisbMod.id(),"book_ruin"), StructureType.JIGSAW);

    public static void run() {
        HisbMod.debug("Creating mod structures for hisb");
    }
}
