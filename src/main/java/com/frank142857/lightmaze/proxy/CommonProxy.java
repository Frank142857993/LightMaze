package com.frank142857.lightmaze.proxy;

import com.frank142857.lightmaze.init.*;
import com.frank142857.lightmaze.util.handlers.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event){
        ConfigInit.registerConfig(event);
        DimensionInit.registerDimensionTypes();
        DimensionInit.registerDimensions();
        BiomeInit.registerBiomes();
        RegistryHandler.worldGenRegistries();
    }

    public void init(FMLInitializationEvent event){
        TileEntityInit.register();
        SoundsHandler.registerSounds();
        OreDictHandler.registerOreDict();
        RegistryHandler.registerSmeltingRecipe();
    }

    public void postInit(FMLPostInitializationEvent event){}

    public void registerItemRenderer(Item item, int meta, String id) {}

    public void registerItemRenderer(Item item, int meta, String fileName, String id) {}
}
