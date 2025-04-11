package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.tileentity.TileEntityFlowerPotLM;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityInit {
    public static void register(){
        GameRegistry.registerTileEntity(TileEntityFlowerPotLM.class, "lightmaze:evergrowth_flower_pot");
    }
}
