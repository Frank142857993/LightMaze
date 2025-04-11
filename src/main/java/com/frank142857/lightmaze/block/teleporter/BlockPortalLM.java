package com.frank142857.lightmaze.block.teleporter;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.ConfigInit;
import com.frank142857.lightmaze.init.CreativeTabInit;
import com.frank142857.lightmaze.init.DimensionInit;
import com.frank142857.lightmaze.util.handlers.SoundsHandler;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import com.frank142857.lightmaze.util.particle.EnumParticleTypesLM;
import com.frank142857.lightmaze.util.particle.ParticleSpawner;
import com.frank142857.lightmaze.world.TeleporterLM;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPortalLM extends BlockBreakable implements IHasModel {
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

    public static final String name = "lm_portal";

    public static final Block FRAME = Blocks.QUARTZ_BLOCK;

    public BlockPortalLM(){
        super(Material.PORTAL, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
        this.setHardness(-1.0F);
        this.setResistance(900000F);
        this.setSoundType(SoundType.GLASS);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(1.0F);
        BlockInit.REGISTER_BLOCKS.add(this);
    }

    @Override
    public void registerModel(){
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch ((EnumFacing.Axis)state.getValue(AXIS)) {
            case X:
                return X_AABB;
            case Y:
            default:
                return Y_AABB;
            case Z:
                return Z_AABB;
        }
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public static int getMetaForAxis(EnumFacing.Axis axis) {
        return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean trySpawnPortal(World world, BlockPos pos) {
        Size size = new Size(world, pos, EnumFacing.Axis.X);

        if(ConfigInit.PORTAL_ENABLED){
            if(size.isValid() && size.portalBlockCount == 0) {
                size.placePortalBlocks();
                return true;
            } else {
                Size size1 = new Size(world, pos, EnumFacing.Axis.Z);
                if(size1.isValid() && size1.portalBlockCount == 0) {
                    size1.placePortalBlocks();
                    return true;
                }
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing.Axis axis = (EnumFacing.Axis) state.getValue(AXIS);

        if(axis == EnumFacing.Axis.X) {
            Size size = new Size(worldIn, pos, EnumFacing.Axis.X);

            if(!size.isValid() || size.portalBlockCount < size.width * size.height) {
                worldIn.setBlockToAir(pos);
            }
        } else if(axis == EnumFacing.Axis.Z) {
            Size size = new Size(worldIn, pos, EnumFacing.Axis.Z);

            if(!size.isValid() || size.portalBlockCount < size.width * size.height) {
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        pos = pos.offset(side);
        EnumFacing.Axis axis = null;

        if(blockState == this) {
            axis = (EnumFacing.Axis) blockState.getValue(AXIS);

            if(axis == null) {
                return false;
            }
            if(axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST) {
                return false;
            }
            if(axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH) {
                return false;
            }
        }

        boolean flag = blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2)).getBlock() != this,
                flag1 = blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2)).getBlock() != this,
                flag2 = blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2)).getBlock() != this,
                flag3 = blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2)).getBlock() != this,
                flag4 = flag || flag1 || axis == EnumFacing.Axis.X,
                flag5 = flag2 || flag3 || axis == EnumFacing.Axis.Z;

        if(flag4 && side == EnumFacing.WEST) {
            return true;
        } else if(flag4 && side == EnumFacing.EAST) {
            return true;
        } else if(flag5 && side == EnumFacing.NORTH) {
            return true;
        } else {
            return flag5 && side == EnumFacing.SOUTH;
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        //ICapabilityPortal capability = entityIn.getCapability(CapabilityInit.PORTAL, null);

        if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {
            /*
            if(entityIn instanceof EntityPlayerMP){
                ((EntityPlayerMP) entityIn).setPortal(pos);
            }
            */
            entityIn.setPortal(pos); //TODO Portal Check
            if(entityIn.timeUntilPortal > 0) {
                entityIn.timeUntilPortal = entityIn.getPortalCooldown();
            } else if (entityIn.dimension != DimensionInit.lightmaze.getId()) {
                entityIn.timeUntilPortal = 300;
                //if(capability != null) System.out.println("tick until teleport = " + capability.getTicksUntilTeleport());
                entityIn.changeDimension(DimensionInit.lightmaze.getId(), new TeleporterLM(server.getWorld(DimensionInit.lightmaze.getId())));
                if(entityIn instanceof EntityPlayerMP){
                    ((EntityPlayerMP) entityIn).setSpawnChunk(entityIn.getPosition(), true, DimensionInit.lightmaze.getId());
                }
            } else {
                entityIn.timeUntilPortal = 300;
                entityIn.changeDimension(0, new TeleporterLM(server.getWorld(0)));
            }
        }
    }



    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {


        if (rand.nextInt(200) == 14)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsHandler.BLOCK_LM_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        } else if (rand.nextInt(200) == 15){
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsHandler.BLOCK_LM_PORTAL_AMBIENT_2, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            ParticleSpawner.spawnParticle(EnumParticleTypesLM.PORTAL_LM, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((EnumFacing.Axis)state.getValue(AXIS)) {
                    case X:
                        return state.withProperty(AXIS, EnumFacing.Axis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumFacing.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {AXIS});
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    public BlockPattern.PatternHelper createPatternHelper(World world, BlockPos pos) {
        EnumFacing.Axis axis = EnumFacing.Axis.Z;
        Size size = new Size(world, pos, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> cache = BlockPattern.createLoadingCache(world, true);

        if(!size.isValid()) {
            axis = EnumFacing.Axis.X;
            size = new Size(world, pos, EnumFacing.Axis.Z);
        }

        if(!size.isValid()) {
            return new BlockPattern.PatternHelper(pos, EnumFacing.NORTH, EnumFacing.UP, cache, 1, 1, 1);
        } else {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing facing = size.rightDir.rotateYCCW();
            BlockPos blockpos = size.bottomLeft.up(size.getHeight() - 1);

            for(EnumFacing.AxisDirection direction : EnumFacing.AxisDirection.values()) {
                BlockPattern.PatternHelper helper = new BlockPattern.PatternHelper(facing.getAxisDirection() == direction ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), EnumFacing.getFacingFromAxis(direction, axis), EnumFacing.UP, cache, size.getWidth(), size.getHeight(), 1);

                for(int i = 0; i < size.getWidth(); ++i) {
                    for(int j = 0; j < size.getHeight(); ++j) {
                        BlockWorldState state = helper.translateOffset(i, j, 1);

                        if(state.getBlockState() != null && state.getBlockState().getMaterial() != Material.AIR) {
                            ++aint[direction.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection direction1 = EnumFacing.AxisDirection.POSITIVE;

            for(EnumFacing.AxisDirection direction2 : EnumFacing.AxisDirection.values()) {
                if(aint[direction2.ordinal()] < aint[direction1.ordinal()]) {
                    direction1 = direction2;
                }
            }

            return new BlockPattern.PatternHelper(facing.getAxisDirection() == direction1 ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), EnumFacing.getFacingFromAxis(direction1, axis), EnumFacing.UP, cache, size.getWidth(), size.getHeight(), 1);
        }
    }

    public static class Size {
        private final World world;
        private final EnumFacing.Axis axis;
        private final EnumFacing rightDir, leftDir;
        private int portalBlockCount, height, width;
        private BlockPos bottomLeft;

        public Size(World world, BlockPos pos, EnumFacing.Axis axis) {
            this.world = world;
            this.axis = axis;

            if(axis == EnumFacing.Axis.X) {
                this.leftDir = EnumFacing.EAST;
                this.rightDir = EnumFacing.WEST;
            } else {
                this.leftDir = EnumFacing.NORTH;
                this.rightDir = EnumFacing.SOUTH;
            }

            for(BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && this.isEmptyBlock(world.getBlockState(pos.down()).getBlock()); pos = pos.down()) {;}

            int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;

            if(i >= 0) {
                this.bottomLeft = pos.offset(leftDir, i);
                this.width = this.getDistanceUntilEdge(bottomLeft, rightDir);

                if(this.width < 2 || this.width > 21) {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if(this.bottomLeft != null) {
                this.height = this.calculatePortalHeight();
            }
        }

        protected int getDistanceUntilEdge(BlockPos pos, EnumFacing facing) {
            int i;

            for(i = 0; i < 22; ++i) {
                BlockPos blockpos = pos.offset(facing, i);

                if(!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != FRAME) {
                    break;
                }
            }

            Block block = this.world.getBlockState(pos.offset(facing, i)).getBlock();
            return block == FRAME ? i : 0;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        protected int calculatePortalHeight() {
            label56:

            for(this.height = 0; this.height < 21; ++this.height) {
                for(int i = 0; i < this.width; ++i) {
                    BlockPos pos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                    Block block = this.world.getBlockState(pos).getBlock();

                    if(!this.isEmptyBlock(block)) {
                        break label56;
                    }

                    if(block == BlockInit.BLOCK_PORTAL_LM) {
                        ++this.portalBlockCount;
                    }

                    if(i == 0) {
                        block = this.world.getBlockState(pos.offset(leftDir)).getBlock();
                        if(block != FRAME) {
                            break label56;
                        }
                    } else if(i == this.width - 1) {
                        block = this.world.getBlockState(pos.offset(rightDir)).getBlock();
                        if(block != FRAME) {
                            break label56;
                        }
                    }
                }
            }

            for(int j = 0; j < this.width; ++j) {
                if(this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != FRAME) {
                    this.height = 0;
                    break;
                }
            }

            if(this.height <= 21 && this.height >=3) {
                return this.height;
            } else {
                this.bottomLeft = null;
                return this.width = this.height = 0;
            }
        }

        protected boolean isEmptyBlock(Block block) {
            return block.getDefaultState().getMaterial() == Material.AIR || block == Blocks.FIRE || block == BlockInit.BLOCK_PORTAL_LM;
        }

        public boolean isValid() {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <=21;
        }

        public void placePortalBlocks() {
            for(int i = 0; i < this.width; ++i) {
                BlockPos pos = this.bottomLeft.offset(this.rightDir, i);

                for(int j = 0; j < this.height; ++j) {
                    this.world.setBlockState(pos.up(j), BlockInit.BLOCK_PORTAL_LM.getDefaultState().withProperty(BlockPortalLM.AXIS, this.axis), 2);
                }
            }
        }
    }
}
