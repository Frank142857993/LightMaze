package com.frank142857.lightmaze.world;

import com.frank142857.lightmaze.block.teleporter.BlockPortalLM;
import com.frank142857.lightmaze.init.BlockInit;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterLM extends Teleporter {
    public TeleporterLM(WorldServer world) {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        if (this.world.provider.getDimensionType().getId() != 1) {
            if (!this.placeInExistingPortal(entityIn, rotationYaw)) {
                this.makePortal(entityIn);
                this.placeInExistingPortal(entityIn, rotationYaw);
            }
        } else {
            int i = MathHelper.floor(entityIn.posX);
            int j = MathHelper.floor(entityIn.posY) - 1;
            int k = MathHelper.floor(entityIn.posZ);
            int l = 1;
            int i1 = 0;

            for(int j1 = -2; j1 <= 2; ++j1) {
                for(int k1 = -2; k1 <= 2; ++k1) {
                    for(int l1 = -1; l1 < 3; ++l1) {
                        int i2 = i + k1 * 1 + j1 * 0;
                        int j2 = j + l1;
                        int k2 = k + k1 * 0 - j1 * 1;
                        boolean flag = l1 < 0;
                        this.world.setBlockState(new BlockPos(i2, j2, k2), flag ? BlockPortalLM.FRAME.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }

            entityIn.setLocationAndAngles((double)i, (double)j, (double)k, entityIn.rotationYaw, 0.0F);
            entityIn.motionX = 0.0D;
            entityIn.motionY = 0.0D;
            entityIn.motionZ = 0.0D;
        }

    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        int i = 128;
        double d0 = -1.0D;
        int j = MathHelper.floor(entity.posX);
        int k = MathHelper.floor(entity.posZ);
        boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);
        if (this.destinationCoordinateCache.containsKey(l)) {
            Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.get(l);
            d0 = 0.0D;
            blockpos = teleporter$portalposition;
            teleporter$portalposition.lastUpdateTime = this.world.getTotalWorldTime();
            flag = false;
        } else {
            BlockPos blockpos3 = new BlockPos(entity);

            for(int i1 = -i; i1 <= i; ++i1) {
                BlockPos blockpos2;
                for(int j1 = -i; j1 <= i; ++j1) {
                    for(BlockPos blockpos1 = blockpos3.add(i1, this.world.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
                        blockpos2 = blockpos1.down();
                        if (this.world.getBlockState(blockpos1).getBlock() == BlockInit.BLOCK_PORTAL_LM) {
                            for(blockpos2 = blockpos1.down(); this.world.getBlockState(blockpos2).getBlock() == BlockInit.BLOCK_PORTAL_LM; blockpos2 = blockpos2.down()) {
                                blockpos1 = blockpos2;
                            }

                            double d1 = blockpos1.distanceSq(blockpos3);
                            if (d0 < 0.0D || d1 < d0) {
                                d0 = d1;
                                blockpos = blockpos1;
                            }
                        }
                    }
                }
            }
        }

        //debug
        System.out.println(blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ());

        if (d0 >= 0.0D) {
            if (flag) {
                this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition((BlockPos)blockpos, this.world.getTotalWorldTime()));
            }

            double d5 = (double)((BlockPos)blockpos).getX() + 0.5D;
            double d7 = (double)((BlockPos)blockpos).getZ() + 0.5D;
            BlockPattern.PatternHelper blockpattern$patternhelper = BlockInit.BLOCK_PORTAL_LM.createPatternHelper(this.world, (BlockPos)blockpos);
            boolean flag1 = blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double d2 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();

            double d1 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? d7 : d5; //TODO Study
            d1 = Math.abs(MathHelper.pct(d1 - (double)(blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), d2, d2 - (double)blockpattern$patternhelper.getWidth()));
            double d22 = MathHelper.pct(blockpos.getY() - 1.0D, (double)blockpattern$patternhelper.getFrontTopLeft().getY(), (double)(blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));

            double d6 = (double)(blockpattern$patternhelper.getFrontTopLeft().getY() + 1) - new Vec3d(d1, d22, 0.0D).y * (double)blockpattern$patternhelper.getHeight();
            if (flag1) {
                ++d2;
            }

            if (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) {
                d7 = d2 + (1.0D - new Vec3d(d1, d22, 0.0D).x) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
            } else {
                d5 = d2 + (1.0D - new Vec3d(d1, d22, 0.0D).x) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
            }

            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;
            if (blockpattern$patternhelper.getForwards().getOpposite() == entity.getTeleportDirection()) {
                f = 1.0F;
                f1 = 1.0F;
            } else if (blockpattern$patternhelper.getForwards().getOpposite() == entity.getTeleportDirection().getOpposite()) {
                f = -1.0F;
                f1 = -1.0F;
            } else if (blockpattern$patternhelper.getForwards().getOpposite() == entity.getTeleportDirection().rotateY()) {
                f2 = 1.0F;
                f3 = -1.0F;
            } else {
                f2 = -1.0F;
                f3 = 1.0F;
            }

            double d3 = entity.motionX;
            double d4 = entity.motionZ;
            entity.motionX = d3 * (double)f + d4 * (double)f3;
            entity.motionZ = d3 * (double)f2 + d4 * (double)f1;
            entity.rotationYaw = rotationYaw - (float)(entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float)(blockpattern$patternhelper.getForwards().getHorizontalIndex() * 90);
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP)entity).connection.setPlayerLocation(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
            } else {
                entity.setLocationAndAngles(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
            }
            //debug
            System.out.println(d5 + ", " + d6 + ", " + d7);
            System.out.println(entity.getPosition().getX() + ", " + entity.getPosition().getY() + ", " + entity.getPosition().getZ());

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entityIn) {
        int i = 1;
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posY);
        int l = MathHelper.floor(entityIn.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        int l5;
        double d3;
        int j6;
        double d4;
        int i7;
        int k7;
        int j8;
        int j9;
        int j10;
        int j11;
        int j12;
        int i13;
        int j13;
        double d6;
        double d8;
        for(l5 = j - 16; l5 <= j + 16; ++l5) {
            d3 = (double)l5 + 0.5D - entityIn.posX;

            for(j6 = l - 16; j6 <= l + 16; ++j6) {
                d4 = (double)j6 + 0.5D - entityIn.posZ;

                label293:
                for(i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7) {
                    if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6))) {
                        while(i7 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6))) {
                            --i7;
                        }

                        for(k7 = i2; k7 < i2 + 4; ++k7) {
                            j8 = k7 % 2;
                            j9 = 1 - j8;
                            if (k7 % 4 >= 2) {
                                j8 = -j8;
                                j9 = -j9;
                            }

                            for(j10 = 0; j10 < 3; ++j10) {
                                for(j11 = 0; j11 < 4; ++j11) {
                                    for(j12 = -1; j12 < 4; ++j12) {
                                        i13 = l5 + (j11 - 1) * j8 + j10 * j9;
                                        j13 = i7 + j12;
                                        int k5 = j6 + (j11 - 1) * j9 - j10 * j8;
                                        blockpos$mutableblockpos.setPos(i13, j13, k5);
                                        if (j12 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j12 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label293;
                                        }
                                    }
                                }
                            }

                            d6 = (double)i7 + 0.5D - entityIn.posY;
                            d8 = d3 * d3 + d6 * d6 + d4 * d4;
                            if (d0 < 0.0D || d8 < d0) {
                                d0 = d8;
                                i1 = l5;
                                j1 = i7;
                                k1 = j6;
                                l1 = k7 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for(l5 = j - 16; l5 <= j + 16; ++l5) {
                d3 = (double)l5 + 0.5D - entityIn.posX;

                for(j6 = l - 16; j6 <= l + 16; ++j6) {
                    d4 = (double)j6 + 0.5D - entityIn.posZ;

                    label229:
                    for(i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7) {
                        if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6))) {
                            while(i7 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6))) {
                                --i7;
                            }

                            for(k7 = i2; k7 < i2 + 2; ++k7) {
                                j8 = k7 % 2;
                                j9 = 1 - j8;

                                for(j10 = 0; j10 < 4; ++j10) {
                                    for(j11 = -1; j11 < 4; ++j11) {
                                        j12 = l5 + (j10 - 1) * j8;
                                        i13 = i7 + j11;
                                        j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);
                                        if (j11 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label229;
                                        }
                                    }
                                }

                                d6 = (double)i7 + 0.5D - entityIn.posY;
                                d8 = d3 * d3 + d6 * d6 + d4 * d4;
                                if (d0 < 0.0D || d8 < d0) {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        l5 = i1;
        int k2 = j1;
        int k6 = k1;
        j6 = l1 % 2;
        int i3 = 1 - j6;
        if (l1 % 4 >= 2) {
            j6 = -j6;
            i3 = -i3;
        }

        if (d0 < 0.0D) {
            j1 = MathHelper.clamp(j1, 70, this.world.getActualHeight() - 10);
            k2 = j1;

            for(int j7 = -1; j7 <= 1; ++j7) {
                for(i7 = 1; i7 < 3; ++i7) {
                    for(k7 = -1; k7 < 3; ++k7) {
                        j8 = l5 + (i7 - 1) * j6 + j7 * i3;
                        j9 = k2 + k7;
                        j10 = k6 + (i7 - 1) * i3 - j7 * j6;
                        boolean flag = k7 < 0;
                        this.world.setBlockState(new BlockPos(j8, j9, j10), flag ? BlockPortalLM.FRAME.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = BlockInit.BLOCK_PORTAL_LM.getDefaultState().withProperty(BlockPortal.AXIS, j6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for(i7 = 0; i7 < 4; ++i7) {
            for(k7 = 0; k7 < 4; ++k7) {
                for(j8 = -1; j8 < 4; ++j8) {
                    j9 = l5 + (k7 - 1) * j6;
                    j10 = k2 + j8;
                    j11 = k6 + (k7 - 1) * i3;
                    boolean flag1 = k7 == 0 || k7 == 3 || j8 == -1 || j8 == 3;
                    this.world.setBlockState(new BlockPos(j9, j10, j11), flag1 ? BlockPortalLM.FRAME.getDefaultState() : iblockstate, 2);
                }
            }

            for(k7 = 0; k7 < 4; ++k7) {
                for(j8 = -1; j8 < 4; ++j8) {
                    j9 = l5 + (k7 - 1) * j6;
                    j10 = k2 + j8;
                    j11 = k6 + (k7 - 1) * i3;
                    BlockPos blockpos = new BlockPos(j9, j10, j11);
                    this.world.notifyNeighborsOfStateChange(blockpos, this.world.getBlockState(blockpos).getBlock(), false);
                }
            }
        }

        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {
        if (worldTime % 100L == 0L) {
            long i = worldTime - 300L;
            ObjectIterator objectiterator = this.destinationCoordinateCache.values().iterator();

            while(true) {
                Teleporter.PortalPosition teleporter$portalposition;
                do {
                    if (!objectiterator.hasNext()) {
                        return;
                    }

                    teleporter$portalposition = (Teleporter.PortalPosition)objectiterator.next();
                } while(teleporter$portalposition != null && teleporter$portalposition.lastUpdateTime >= i);

                objectiterator.remove();
            }
        }
    }
}
