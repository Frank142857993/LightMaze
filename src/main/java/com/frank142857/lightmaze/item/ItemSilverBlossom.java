package com.frank142857.lightmaze.item;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.item.Item;

public class ItemSilverBlossom extends Item implements IHasModel {
    private final String name = "silver_blossom";

    public ItemSilverBlossom(){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        ItemInit.REGISTER_ITEMS.add(this);
    }

    @Override
    public void registerModel(){
        LightMaze.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
