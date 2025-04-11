package com.frank142857.lightmaze.world.gen;

import com.frank142857.lightmaze.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenGrassLM extends WorldGenerator {
    private final IBlockState grassState;

    public WorldGenGrassLM()
    {
        this.grassState = BlockInit.GLOWING_HERBS.getDefaultState();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && BlockInit.GLOWING_HERBS.canBlockStay(worldIn, blockpos, this.grassState))
            {
                worldIn.setBlockState(blockpos, this.grassState, 2);
            }
        }

        return true;
    }
}
