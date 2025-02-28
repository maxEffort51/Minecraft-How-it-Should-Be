package net.maxeffort.beyond.worldgen;

import net.maxeffort.beyond.main.BeyondMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {
    public static final StructureType<JigsawStructure> BOOK_RUIN = Registry.register(
            Registries.STRUCTURE_TYPE, Identifier.of(BeyondMod.id(),"book_ruin"), StructureType.JIGSAW);
    public static final StructureType<JigsawStructure> WHINTER_STRUCTURE = Registry.register(
            Registries.STRUCTURE_TYPE, Identifier.of(BeyondMod.id(),"whinter_structure"), StructureType.JIGSAW);


    public static void run() {
        BeyondMod.debug("Creating mod structures for beyond");
    }
}
