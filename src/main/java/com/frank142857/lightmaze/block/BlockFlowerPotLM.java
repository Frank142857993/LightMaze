package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.tileentity.TileEntityFlowerPotLM;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFlowerPotLM extends BlockContainer implements IHasModel {
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyEnum<BlockFlowerPotLM.EnumFlowerType> CONTENTS = PropertyEnum.<BlockFlowerPotLM.EnumFlowerType>create("contents", BlockFlowerPotLM.EnumFlowerType.class);
    protected static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

    private final String name = "evergrowth_flower_pot";

    public BlockFlowerPotLM(){
        super(Material.CIRCUITS, MapColor.PURPLE_STAINED_HARDENED_CLAY);
        this.hasTileEntity = true;
        //this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS, EnumFlowerType.EMPTY).withProperty(LEGACY_DATA, Integer.valueOf(0)));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS, EnumFlowerType.EMPTY).withProperty(POWERED, false));
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        //this.setLightLevel(1.0F);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_POT_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    } // TODO

    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        //if((EnumFlowerType) blockState.getValue(CONTENTS) == EnumFlowerType.FLUORESCENT_FLOWER_RED){
        //return ((Integer)blockState.getValue(POWER)).intValue();
        //}
        //else {
        //    return 0;
        //}
        if(blockState.getValue(POWERED)) return 15;
        else return 0;
    }

    /* TODO UPDATE POWER (TURN ON) AS RED FLOWER PLANTED */
    public void turnOnPower(World worldIn, BlockPos pos){
        //System.out.println("updatePower called");
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity entity = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote){
            if(state.getValue(CONTENTS) == EnumFlowerType.EMPTY && !state.getValue(POWERED)){
                state = state.cycleProperty(POWERED);
                worldIn.setBlockState(pos, state, 3);

                if(entity != null){
                    entity.validate();
                    worldIn.setTileEntity(pos, entity);
                }

                System.out.println("Power Updated"); // log
            } //else {
            //worldIn.setBlockState(pos, state.withProperty(POWERED, false), 3);
            //}
        }
    }

    public void turnOffPower(World worldIn, BlockPos pos){
        //System.out.println("updatePower called");
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity entity = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote){
            if(state.getValue(POWERED)){
                state = state.cycleProperty(POWERED);
                worldIn.setBlockState(pos, state, 3);

                if(entity != null){
                    entity.validate();
                    worldIn.setTileEntity(pos, entity);
                }

                System.out.println("Power Updated"); // log
            } //else {
            //worldIn.setBlockState(pos, state.withProperty(POWERED, false), 3);
            //}
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        TileEntityFlowerPotLM tileentityflowerpot = this.getTileEntity(worldIn, pos);

        if (tileentityflowerpot == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack1 = tileentityflowerpot.getFlowerItemStack();

            if (itemstack1.isEmpty())
            {
                if (!this.canBePotted(itemstack))
                {
                    return false;
                }

                tileentityflowerpot.setItemStack(itemstack);

                playerIn.addStat(StatList.FLOWER_POTTED);

                if (!playerIn.capabilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }
            }
            else
            {
                if (Block.getBlockFromItem(itemstack1.getItem()) == BlockInit.FLUORESCENT_FLOWER_WHITE){
                    worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.10D, 0.0D);
                } else {
                    if (itemstack.isEmpty()) {
                        playerIn.setHeldItem(hand, itemstack1);
                    } else if (!playerIn.addItemStackToInventory(itemstack1)) {
                        playerIn.dropItem(itemstack1, false);
                    }
                }
                tileentityflowerpot.setItemStack(ItemStack.EMPTY);
                this.turnOffPower(worldIn, pos);
            }

            tileentityflowerpot.markDirty();

            //BlockInit.EVERGROWTH_FLOWER_POT.updatePower(worldIn, pos);

            worldIn.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
    }

    private boolean canBePotted(ItemStack stack)
    {
        Block block = Block.getBlockFromItem(stack.getItem());

        if (block != BlockInit.FLUORESCENT_FLOWER_RED && block != BlockInit.FLUORESCENT_FLOWER_GREEN && block != BlockInit.FLUORESCENT_FLOWER_BLUE && block != BlockInit.FLUORESCENT_FLOWER_WHITE && block != BlockInit.SILVER_SAPLING)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityFlowerPotLM tileentityflowerpot = this.getTileEntity(worldIn, pos);

        if (tileentityflowerpot != null)
        {
            ItemStack itemstack = tileentityflowerpot.getFlowerItemStack();

            if (!itemstack.isEmpty())
            {
                return itemstack;
            }
        }

        return new ItemStack(Item.getItemFromBlock(BlockInit.EVERGROWTH_FLOWER_POT));
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState downState = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && (downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        IBlockState downState = worldIn.getBlockState(pos.down());
        if (!downState.isTopSolid() && downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID)
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.capabilities.isCreativeMode)
        {
            TileEntityFlowerPotLM tileentityflowerpot = this.getTileEntity(worldIn, pos);

            if (tileentityflowerpot != null)
            {
                tileentityflowerpot.setItemStack(ItemStack.EMPTY);
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockInit.EVERGROWTH_FLOWER_POT);
    }

    @Nullable
    private TileEntityFlowerPotLM getTileEntity(World worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof TileEntityFlowerPotLM ? (TileEntityFlowerPotLM)tileentity : null;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        Block block = null;
        int i = 0;

        switch (meta)
        {
            case 1:
                block = BlockInit.FLUORESCENT_FLOWER_RED;
                break;
            case 2:
                block = BlockInit.FLUORESCENT_FLOWER_GREEN;
                break;
            case 3:
                block = BlockInit.FLUORESCENT_FLOWER_BLUE;
                break;
            case 4:
                block = BlockInit.FLUORESCENT_FLOWER_WHITE;
                break;
            case 5:
                block = BlockInit.SILVER_SAPLING;
        }

        return new TileEntityFlowerPotLM(Item.getItemFromBlock(block), i);
    }

    protected BlockStateContainer createBlockState()
    {
        //return new BlockStateContainer(this, new IProperty[] {CONTENTS, LEGACY_DATA});
        return new BlockStateContainer(this, new IProperty[] {CONTENTS, POWERED});
    }

    public int getMetaFromState(IBlockState state)
    {
        //return ((Integer)state.getValue(LEGACY_DATA)).intValue();
        return 0;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        EnumFlowerType type = EnumFlowerType.EMPTY;
        TileEntity tileEntity = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        if (tileEntity instanceof TileEntityFlowerPotLM){
            TileEntityFlowerPotLM tileEntityFlowerPotLM = (TileEntityFlowerPotLM) tileEntity;

            Item item = tileEntityFlowerPotLM.getFlowerPotItem();

            if(item instanceof ItemBlock){
                int i = tileEntityFlowerPotLM.getFlowerPotData();
                Block block = Block.getBlockFromItem(item);

                if(block == BlockInit.SILVER_SAPLING){
                    type = EnumFlowerType.SILVER_SAPLING;
                }
                if(block == BlockInit.FLUORESCENT_FLOWER_RED){
                    type = EnumFlowerType.FLUORESCENT_FLOWER_RED;
                }
                if(block == BlockInit.FLUORESCENT_FLOWER_GREEN){
                    type = EnumFlowerType.FLUORESCENT_FLOWER_GREEN;
                }
                if(block == BlockInit.FLUORESCENT_FLOWER_BLUE){
                    type = EnumFlowerType.FLUORESCENT_FLOWER_BLUE;
                }
                if(block == BlockInit.FLUORESCENT_FLOWER_WHITE){
                    type = EnumFlowerType.FLUORESCENT_FLOWER_WHITE;
                }
            }
        }
        return state.withProperty(CONTENTS, type);
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    // FORGE START

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, world, pos, state, fortune);
        TileEntityFlowerPotLM te = world.getTileEntity(pos) instanceof TileEntityFlowerPotLM ? (TileEntityFlowerPotLM)world.getTileEntity(pos) : null;
        if (te != null && te.getFlowerPotItem() != null)
            drops.add(new ItemStack(te.getFlowerPotItem(), 1, te.getFlowerPotData()));
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    // FORGE END

    public enum EnumFlowerType implements IStringSerializable {
        EMPTY("empty"),
        SILVER_SAPLING("silver_sapling"),
        FLUORESCENT_FLOWER_RED("fluorescent_flower_red"),
        FLUORESCENT_FLOWER_GREEN("fluorescent_flower_green"),
        FLUORESCENT_FLOWER_BLUE("fluorescent_flower_blue"),
        FLUORESCENT_FLOWER_WHITE("fluorescent_flower_white");

        private final String name;

        private EnumFlowerType(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }
}
