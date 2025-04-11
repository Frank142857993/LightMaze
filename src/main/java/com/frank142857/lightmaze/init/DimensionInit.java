package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.world.dimension.lightmaze.WorldProviderLM;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionInit {
    public static DimensionType lightmaze;

    public static void init(){
        registerDimensionTypes();
        registerDimensions();
    }

    public static void registerDimensionTypes(){
        lightmaze = DimensionType.register("LightMaze", "_lightmaze", ConfigInit.LIGHT_MAZE, WorldProviderLM.class, false);
    }

    public static void registerDimensions(){
        DimensionManager.registerDimension(ConfigInit.LIGHT_MAZE, lightmaze);
    }
}