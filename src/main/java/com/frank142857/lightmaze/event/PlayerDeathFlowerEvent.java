package com.frank142857.lightmaze.event;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.block.BlockFlowerPotLM;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.DimensionInit;
import com.frank142857.lightmaze.tileentity.TileEntityFlowerPotLM;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LightMaze.MODID)
public class PlayerDeathFlowerEvent {

    public PlayerDeathFlowerEvent(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onPlayerDeathDetected(LivingDeathEvent event){
        Entity entity = event.getEntity();
        World worldIn = entity.getEntityWorld();
        BlockPos deadPos = new BlockPos(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());
        if (!worldIn.isRemote && worldIn.provider.getDimension() == DimensionInit.lightmaze.getId()){
            if((worldIn.getBlockState(deadPos.down()).getBlock()) instanceof IGrowable){
                if(worldIn.getBlockState(deadPos).getBlock().isReplaceable(worldIn, deadPos)
                        && !(worldIn.getBlockState(deadPos).getBlock() instanceof BlockLiquid)){
                    worldIn.setBlockState(deadPos, BlockInit.FLUORESCENT_FLOWER_WHITE.getDefaultState());
                }
            } else {
                if(worldIn.getBlockState(deadPos).getBlock().isReplaceable(worldIn, deadPos)
                        && !(worldIn.getBlockState(deadPos).getBlock() instanceof BlockLiquid)){
                    worldIn.setBlockState(deadPos, BlockInit.EVERGROWTH_FLOWER_POT.getDefaultState().withProperty(BlockFlowerPotLM.CONTENTS, BlockFlowerPotLM.EnumFlowerType.FLUORESCENT_FLOWER_WHITE).withProperty(BlockFlowerPotLM.POWERED, false), 2);
                    TileEntity tileEntity = worldIn.getTileEntity(deadPos);
                    if (tileEntity != null){
                        if (tileEntity instanceof TileEntityFlowerPotLM) {
                            tileEntity.validate();
                            ((TileEntityFlowerPotLM) tileEntity).setItemStack(new ItemStack(Item.getItemFromBlock(BlockInit.FLUORESCENT_FLOWER_WHITE)));
                            worldIn.setTileEntity(deadPos, tileEntity);
                            //System.out.println("Tile Entity Detected");
                        }
                    }
                }
            }
        }
    }
}
