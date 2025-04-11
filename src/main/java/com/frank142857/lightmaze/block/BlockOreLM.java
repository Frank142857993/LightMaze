package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockOreLM extends BlockOre implements IHasModel {
    public BlockOreLM(String name, int harvestLevel, int lightValue){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        this.setHardness(4.5F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setLightLevel(lightValue);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return BlockInit.FOUNDATION_STONE.getMapColor(state, worldIn, pos);
    }

    @Override
    public void registerModel(){
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int chance) {
        if (this == BlockInit.DIAMOND_ORE) {
            return Items.DIAMOND;
        } else if (this == BlockInit.REDSTONE_ORE) {
            return Items.REDSTONE;
        } else if (this == BlockInit.QUARTZ_ORE) {
            return Items.QUARTZ;
        } else if (this == BlockInit.IRON_ORE) {
            return Items.IRON_NUGGET;
        } else if (this == BlockInit.GOLD_ORE) {
            return Items.GOLD_NUGGET;
        } else {
            return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess worldIn, BlockPos pos, int chance) {
        Random rand = new Random();

        if (this.getItemDropped(state, rand, chance) != Item.getItemFromBlock(this)) {
            if (this == BlockInit.DIAMOND_ORE) {
                return Blocks.DIAMOND_ORE.getExpDrop(state, worldIn, pos, chance);
            } else if (this == BlockInit.REDSTONE_ORE) {
                return Blocks.REDSTONE_ORE.getExpDrop(state, worldIn, pos, chance);
            } else if (this == BlockInit.QUARTZ_ORE) {
                return Blocks.QUARTZ_ORE.getExpDrop(state, worldIn, pos, chance);
            } else if (this == BlockInit.IRON_ORE) {
                return MathHelper.getInt(rand, 1, 3);
            } else if (this == BlockInit.GOLD_ORE) {
                return MathHelper.getInt(rand, 2, 3);
            }
        }
        return 0;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random rand) {

        if (this == BlockInit.DIAMOND_ORE) {
            return Blocks.DIAMOND_ORE.quantityDropped(state, fortune, rand);
        } else if (this == BlockInit.REDSTONE_ORE) {
            return Blocks.REDSTONE_ORE.quantityDropped(state, fortune, rand);
        } else if (this == BlockInit.QUARTZ_ORE) {
            return Blocks.QUARTZ_ORE.quantityDropped(state, fortune, rand);
        } else if (this == BlockInit.IRON_ORE) {
            return MathHelper.getInt(rand, 3, 5);
        } else if (this == BlockInit.GOLD_ORE) {
            return MathHelper.getInt(rand, 2, 3);
        }
        return 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (this == BlockInit.DIAMOND_ORE) {
            return Blocks.DIAMOND_ORE.quantityDroppedWithBonus(fortune, rand);
        } else if (this == BlockInit.REDSTONE_ORE) {
            return Blocks.REDSTONE_ORE.quantityDroppedWithBonus(fortune, rand);
        } else if (this == BlockInit.QUARTZ_ORE) {
            return Blocks.QUARTZ_ORE.quantityDroppedWithBonus(fortune, rand);
        } else {
            if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getDefaultState(), rand, fortune))
            {
                int i = rand.nextInt(fortune + 2) - 1;
                if (i < 0)
                {
                    i = 0;
                }
                return this.quantityDropped(rand) * (i + 1);
            } else {
                return this.quantityDropped(rand);
            }
        }
    }
}
