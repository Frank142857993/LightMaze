package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMudLM extends Block implements IHasModel {

    protected static final AxisAlignedBB MUD_BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockMudLM(String name){
        super(Material.GROUND, MapColor.GRAY);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        this.setHarvestLevel("shovel", 1);
        this.setHardness(0.8F);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return MUD_BB;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemInit.MINERAL_MUD_BALL;
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(3) + 1;
    }
}
