package com.frank142857.lightmaze.world.gen;

import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.DimensionInit;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenOreLM implements IWorldGenerator {

    private WorldGenerator marble, mineral_mud, iron, gold, diamond, redstone, ore_lm_quartz, shadow_metal;

    public WorldGenOreLM(){
        marble = new WorldGenMinable(BlockInit.RAW_MARBLE.getDefaultState(), 33, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        mineral_mud = new WorldGenMinable(BlockInit.MINERAL_MUD.getDefaultState(), 33, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        iron = new WorldGenMinable(BlockInit.IRON_ORE.getDefaultState(), 9, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        gold = new WorldGenMinable(BlockInit.GOLD_ORE.getDefaultState(), 9, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        diamond = new WorldGenMinable(BlockInit.DIAMOND_ORE.getDefaultState(), 8, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        redstone = new WorldGenMinable(BlockInit.REDSTONE_ORE.getDefaultState(), 8, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        shadow_metal = new WorldGenMinable(BlockInit.SHADOW_METAL_ORE.getDefaultState(), 9, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
        ore_lm_quartz = new WorldGenMinable(BlockInit.QUARTZ_ORE.getDefaultState(), 14, BlockMatcher.forBlock(BlockInit.FOUNDATION_STONE));
    }

    private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight){
        if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256){
            throw new IllegalArgumentException("Ore generation LM: out of bounds");
        }

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chance; i++){
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);

            int y = minHeight + random.nextInt(heightDiff);

            generator.generate(world, random, new BlockPos(x, y, z));
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        if(world.provider.getDimension() == DimensionInit.lightmaze.getId()){
            runGenerator(marble, world, random, chunkX, chunkZ, 4, 6, 64);
            runGenerator(mineral_mud, world, random, chunkX, chunkZ, 4, 6, 64);
            runGenerator(iron, world, random, chunkX, chunkZ, 8, 6, 60);
            runGenerator(gold, world, random, chunkX, chunkZ, 3, 6, 38);
            runGenerator(diamond, world, random, chunkX, chunkZ, 3, 6, 22);
            runGenerator(redstone, world, random, chunkX, chunkZ, 6, 6, 22);
            runGenerator(shadow_metal, world, random, chunkX, chunkZ, 8, 6, 40);
            runGenerator(ore_lm_quartz, world, random, chunkX, chunkZ, 6, 6, 54);
        }
    }
}
