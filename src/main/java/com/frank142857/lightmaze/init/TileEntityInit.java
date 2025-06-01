package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.tileentity.TileEntityFlowerPotLM;
import com.frank142857.lightmaze.tileentity.TileEntityItemDisplayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityInit {
    public static void register(){
        GameRegistry.registerTileEntity(TileEntityFlowerPotLM.class, "lightmaze:evergrowth_flower_pot");
        GameRegistry.registerTileEntity(TileEntityItemDisplayer.class, "lightmaze:item_displayer");
    }
}
