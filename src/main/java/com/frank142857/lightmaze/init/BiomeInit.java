package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.world.biomes.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeInit {
    public static final Biome MYSTIC_FOREST = new BiomeForestLM();

    public static void registerBiomes(){
        initBiome(MYSTIC_FOREST, "mystic_forest",
                BiomeManager.BiomeType.COOL,
                BiomeDictionary.Type.FOREST
        );
    }

    private static Biome initBiome(Biome biome, String name, BiomeManager.BiomeType biomeType, BiomeDictionary.Type... types){
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);

        return biome;
    }
}
