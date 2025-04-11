package com.frank142857.lightmaze.world.biomes;

import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.ConfigInit;
import com.frank142857.lightmaze.world.dimension.lightmaze.BiomeDecoratorLM;
import com.frank142857.lightmaze.world.gen.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class BiomeForestLM extends Biome {
    public BiomeForestLM(){

        super(new BiomeProperties("Mystic Forest")
                .setBaseHeight(0.1F)
                .setHeightVariation(0.0F)
                .setTemperature(0.205F) //0.21
                .setRainfall(0.4F));

        topBlock = BlockInit.SURFACE_GRASS.getDefaultState();
        fillerBlock = BlockInit.SURFACE_DIRT.getDefaultState();
        this.decorator = new BiomeDecoratorLM();
        this.decorator.treesPerChunk = 8; //from 0
        this.decorator.extraTreeChance = 0; //from 0.03F
        this.decorator.flowersPerChunk = 7;
        this.decorator.mushroomsPerChunk = -100;
        this.addSpawnables();
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos){
        return 0xe6e6e6;
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos){
        return 0x7b8770;
    }


    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return new WorldGenGrassLM();
    }

    @Override
    public int getSkyColorByTemp(float temp){
        if (ConfigInit.USING_FOG_COLOR) return 0xc3c3c3;
        else return super.getSkyColorByTemp(temp);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.CUSTOM)){
            if(rand.nextInt(64) == 0){
                //(new WorldGenMtrFossils()).generate(worldIn, rand, pos); TODO Fossils
            }
        }
    }


    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return new WorldGenSilverTree(false, true);
    }


    @Override
    public void addDefaultFlowers()
    {
        addFlower(BlockInit.FLUORESCENT_FLOWER_RED.getDefaultState(), 15);
        addFlower(BlockInit.FLUORESCENT_FLOWER_GREEN.getDefaultState(), 15);
        addFlower(BlockInit.FLUORESCENT_FLOWER_BLUE.getDefaultState(), 15);
    }

    private void addSpawnables(){
        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        //this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityVillager.class, 1, 1, 1));
    }
}
