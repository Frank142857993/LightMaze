package com.frank142857.lightmaze.event;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LightMaze.MODID)
public class PlayerThrowPearlEvent {
    public PlayerThrowPearlEvent(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onTeleport(ProjectileImpactEvent.Throwable event){
        RayTraceResult ray = event.getRayTraceResult();
        if (ray.entityHit == null && event.getThrowable() instanceof EntityEnderPearl && !event.getThrowable().world.isRemote) {
            EntityEnderPearl entityEnderPearl = (EntityEnderPearl)event.getThrowable();
            BlockPos hitPos = ray.getBlockPos();
            EnumFacing facing = ray.sideHit;
            int x = hitPos.getX(), y = hitPos.getY(), z = hitPos.getZ();
            if(facing != null) switch(facing.getIndex()){
                case 0:
                default:
                    y--;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    z--;
                    break;
                case 3:
                    z++;
                    break;
                case 4:
                    x--;
                    break;
                case 5:
                    x++;
                    break;
            }

            IBlockState location = entityEnderPearl.world.getBlockState(new BlockPos(x, y, z));

            if(location == Blocks.AIR.getDefaultState()) {
                if(!BlockInit.BLOCK_PORTAL_LM.trySpawnPortal(entityEnderPearl.world, new BlockPos(x, y, z))){
                    //TODO add stuff
                } else {
                    BlockInit.BLOCK_PORTAL_LM.trySpawnPortal(entityEnderPearl.world, new BlockPos(x, y, z));
                }
            }
            //}
            //else return;
        }
    }
}
