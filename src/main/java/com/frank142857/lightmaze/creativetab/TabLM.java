package com.frank142857.lightmaze.creativetab;

import com.frank142857.lightmaze.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabLM extends CreativeTabs {
    public TabLM(){
        super("lightmaze");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(BlockInit.BLOCK_PORTAL_LM));
    } //TODO Change it to LM Portal

}
