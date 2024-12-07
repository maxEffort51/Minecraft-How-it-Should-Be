package net.aaronterry.hisb.exploration.worldgen.structure;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class BookRuinStructure extends Structure {
    protected BookRuinStructure(Config config) {
        super(config);
    }

    @Override
    public StructureStart createStructureStart(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, NoiseConfig noiseConfig, StructureTemplateManager structureTemplateManager, long seed, ChunkPos chunkPos, int references, HeightLimitView world, Predicate<RegistryEntry<Biome>> validBiomes) {
        BlockPos surfacePos = chunkGenerator.getHeightOnGround(chunkPos.x * 16, chunkPos.z * 16, Heightmap.Type.WORLD_SURFACE_WG, HeightLimitView.create(60, 20),);
        Random random = new Random();
        BlockPos structurePos = surfacePos.down(random.nextInt(3));
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            positions.add(structurePos.add(random.nextInt(2), 0, random.nextInt(2)));
        }
        return Optional.of(new StructureStart(this, chunkPos, references, ));
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> getType() {
        return null;
    }
}
