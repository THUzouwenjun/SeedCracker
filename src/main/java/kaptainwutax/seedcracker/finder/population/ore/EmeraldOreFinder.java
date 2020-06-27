package kaptainwutax.seedcracker.finder.population.ore;

import kaptainwutax.seedcracker.SeedCracker;
import kaptainwutax.seedcracker.cracker.population.EmeraldOreData;
import kaptainwutax.seedcracker.finder.BlockFinder;
import kaptainwutax.seedcracker.finder.Finder;
import kaptainwutax.seedcracker.render.Color;
import kaptainwutax.seedcracker.render.Cube;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;

public class EmeraldOreFinder extends BlockFinder {

    protected static List<BlockPos> SEARCH_POSITIONS = Finder.buildSearchPositions(Finder.CHUNK_POSITIONS, pos -> {
        if(pos.getY() < 4)return true;
        if(pos.getY() > 28 + 4)return true;
        return false;
    });

    public EmeraldOreFinder(World world, ChunkPos chunkPos) {
        super(world, chunkPos, Blocks.EMERALD_ORE);
        this.searchPositions = SEARCH_POSITIONS;
    }

    @Override
    public List<BlockPos> findInChunk() {
        Biome biome = this.world.getBiome(this.chunkPos.getCenterBlockPos().add(8, 0, 8));

        List<BlockPos> result = super.findInChunk();

        if(!result.isEmpty() && SeedCracker.get().getDataStorage().addBaseData(new EmeraldOreData(this.chunkPos, biome, result))) {
            //TODO: support more ores.
            this.renderers.add(new Cube(result.get(0), new Color(0, 255, 0)));
        }

        return result;
    }

    @Override
    public boolean isValidDimension(DimensionType dimension) {
        return dimension == DimensionType.getOverworldDimensionType();
    }

    public static List<Finder> create(World world, ChunkPos chunkPos) {
        List<Finder> finders = new ArrayList<>();
        finders.add(new EmeraldOreFinder(world, chunkPos));
        return finders;
    }

}
