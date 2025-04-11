package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockLeavesLM extends BlockLeaves implements IHasModel {
    //private final String name = "silver_leaves";

    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
    protected boolean leavesFancy;
    int[] surroundings;

    public BlockLeavesLM(String name){
        //super(Material.CACTUS); //TODO Glass?
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0.4F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.SAND);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    //public boolean isFullCube(IBlockState state)
    //{
    //return false;
    //}

    /*
    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }*/

    @SideOnly(Side.CLIENT)
    public void setGraphicsLevel(boolean fancy)
    {
        this.leavesFancy = fancy;
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos){ return true; }
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        if (this == BlockInit.FLOWERING_SILVER_LEAVES) {
            NonNullList<ItemStack> list = NonNullList.withSize(2, new ItemStack(BlockInit.SILVER_LEAVES));
            list.set(1, new ItemStack(ItemInit.SILVER_BLOSSOM));
            return list;
        }
        else return NonNullList.withSize(1, new ItemStack(this));
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!state.getValue(DECAYABLE).booleanValue())
        {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }


    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
        /*
        if (worldIn.rand.nextInt(chance) == 0)
        {
            if(Loader.isModLoaded("aether_legacy")) { // TODO Aether Compat
                spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.white_apple));
            } else {
                spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE)); //TODO replace
            }
        }*/

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (this == BlockInit.SILVER_LEAVES || this == BlockInit.FLOWERING_SILVER_LEAVES) return Item.getItemFromBlock(BlockInit.SILVER_SAPLING); //TODO replace
        else return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
