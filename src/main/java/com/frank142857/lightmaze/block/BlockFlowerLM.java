package com.frank142857.lightmaze.block;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.*;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import com.frank142857.lightmaze.util.particle.ParticleSpawner;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockFlowerLM extends BlockBush implements IHasModel {
    public BlockFlowerLM(String name, MapColor color){
        super(Material.GRASS, color);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(CreativeTabInit.TAB_LM);
        BlockInit.REGISTER_BLOCKS.add(this);
        ItemInit.REGISTER_ITEMS.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void registerModel() {
        LightMaze.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        if (this == BlockInit.FLUORESCENT_FLOWER_RED) tooltip.add("X");
        else if (this == BlockInit.FLUORESCENT_FLOWER_GREEN) tooltip.add("Y");
        else if (this == BlockInit.FLUORESCENT_FLOWER_BLUE) tooltip.add("Z");
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (this == BlockInit.FLUORESCENT_FLOWER_WHITE) return 0;
        return super.quantityDropped(state, fortune, random);
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (this == BlockInit.FLUORESCENT_FLOWER_WHITE) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.10D, 0.0D);
        }
    }
}
