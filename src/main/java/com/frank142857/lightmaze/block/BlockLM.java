package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockLM extends Block implements IHasModel {
    public BlockLM(String name, Material material, MapColor color, String harvestTool, int harvestLevel, float hardness, float lightValue){
        super(material, color);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setHardness(hardness);
        this.setLightLevel(lightValue);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    public BlockLM(String name, Material material, MapColor color, float hardness, float lightValue){
        super(material, color);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(hardness);
        this.setLightLevel(lightValue);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    public BlockLM(String name, Material material, MapColor color){
        super(material, color);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}