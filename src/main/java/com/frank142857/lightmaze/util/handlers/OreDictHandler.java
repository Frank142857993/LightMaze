package com.frank142857.lightmaze.util.handlers;

import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.ItemInit;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {
    @SubscribeEvent
    public static void registerOreDict(){
        OreDictionary.registerOre("ingotShadowMetal", ItemInit.SHADOW_METAL_INGOT);

        OreDictionary.registerOre("oreDiamond", BlockInit.DIAMOND_ORE);
        OreDictionary.registerOre("oreRedstone", BlockInit.REDSTONE_ORE);
        OreDictionary.registerOre("oreQuartz", BlockInit.QUARTZ_ORE);
        OreDictionary.registerOre("oreShadowMetal", BlockInit.SHADOW_METAL_ORE);

        OreDictionary.registerOre("dirt", BlockInit.SURFACE_DIRT);
        OreDictionary.registerOre("stone", BlockInit.FOUNDATION_STONE);
        OreDictionary.registerOre("cobblestone", BlockInit.FOUNDATION_STONE);
        OreDictionary.registerOre("grass", BlockInit.SURFACE_GRASS);

        OreDictionary.registerOre("blockShadowMetal", BlockInit.SHADOW_METAL_BLOCK);

        OreDictionary.registerOre("cobblestone", BlockInit.SILVER_PLANKS);
    }
}
