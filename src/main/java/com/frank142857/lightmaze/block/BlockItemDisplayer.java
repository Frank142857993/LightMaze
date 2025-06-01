package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.tileentity.TileEntityItemDisplayer;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockItemDisplayer extends Block implements IHasModel, ITileEntityProvider {
    protected static final AxisAlignedBB ITEM_DISPLAYER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
    private static final String name = "item_displayer";

    public BlockItemDisplayer(){
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(6.0F);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ITEM_DISPLAYER_AABB;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if(side.equals(EnumFacing.DOWN)) return false;
        else return true;
    }

    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // everything is just for debugging.
        // TODO Change the "num" data to actual "item", and try to display the stored item!
        ItemStack itemStack = playerIn.getHeldItem(hand);
        TileEntityItemDisplayer te = this.getTileEntity(worldIn, pos);
        if (te == null) {
            return false;
        } else {
            int num = te.getNumberData();
            if (itemStack.getItem().equals(Items.STICK)) num = num + 1;
            te.setNumberData(num);
            // System.out.println("Num data = " + te.getNumberData());
        }
        te.markDirty();
        return true;
    }

    @Nullable
    private TileEntityItemDisplayer getTileEntity(World worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof TileEntityItemDisplayer ? (TileEntityItemDisplayer) tileentity : null;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityItemDisplayer(0);
    }
}
