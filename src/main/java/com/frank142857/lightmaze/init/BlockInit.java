package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.block.*;
import com.frank142857.lightmaze.block.teleporter.BlockPortalLM;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> REGISTER_BLOCKS = new ArrayList<Block>();

    public static final BlockPortalLM BLOCK_PORTAL_LM = new BlockPortalLM();

    public static final BlockDirtLM SURFACE_DIRT = new BlockDirtLM();
    public static final BlockGrassLM SURFACE_GRASS = new BlockGrassLM();
    public static final BlockGrassUpsideDown UPSIDE_DOWN_SURFACE_GRASS = new BlockGrassUpsideDown();
    public static final BlockStoneLM FOUNDATION_STONE = new BlockStoneLM();
    public static final BlockLM POLISHED_FOUNDATION_STONE = (BlockLM) new BlockLM(
            "polished_foundation_stone", Material.ROCK, MapColor.STONE, "pickaxe", 1, 4.5F, 0
    ).setResistance(10.0F);
    public static final BlockLM RAW_MARBLE = (BlockLM) new BlockLM(
            "raw_marble", Material.ROCK, MapColor.QUARTZ, "pickaxe", 0, 3.0F, 0
    ).setResistance(6.0F);
    public static final BlockLM MARBLE = (BlockLM) new BlockLM(
            "marble", Material.ROCK, MapColor.QUARTZ, "pickaxe", 0, 3.0F, 0
    ).setResistance(6.0F);
    public static final BlockLM MARBLE_BRICKS = (BlockLM) new BlockLM(
            "marble_bricks", Material.ROCK, MapColor.QUARTZ, "pickaxe", 0, 3.0F, 0
    ).setResistance(6.0F);
    public static final BlockPillarLM MARBLE_PILLAR = (BlockPillarLM) new BlockPillarLM(
            "marble_pillar", Material.ROCK, MapColor.QUARTZ, "pickaxe", 0, 3.0F, 0
    ).setResistance(6.0F);
    public static final BlockMudLM MINERAL_MUD = new BlockMudLM("mineral_mud");
    public static final BlockSandLM WHITE_SAND = new BlockSandLM("white_sand");

    public static final BlockOreLM IRON_ORE = new BlockOreLM("iron_ore", 1, 0);
    public static final BlockOreLM GOLD_ORE = new BlockOreLM("gold_ore", 2, 0);
    public static final BlockOreLM DIAMOND_ORE = new BlockOreLM("diamond_ore", 2, 0);
    public static final BlockOreLM REDSTONE_ORE = new BlockOreLM("redstone_ore", 2, 0);
    public static final BlockOreLM QUARTZ_ORE = new BlockOreLM("quartz_ore", 1, 0);
    public static final BlockOreLM SHADOW_METAL_ORE = new BlockOreLM("shadow_metal_ore", 2, 0);

    public static final BlockMetalLM SHADOW_METAL_BLOCK = new BlockMetalLM("shadow_metal_block", Material.IRON, 2);

    public static final BlockBushLM GLOWING_HERBS = (BlockBushLM) (new BlockBushLM().setLightLevel(0.067F));
    public static final BlockFlowerLM FLUORESCENT_FLOWER_RED = (BlockFlowerLM) new BlockFlowerLM("fluorescent_flower_red", MapColor.RED).setLightLevel(0.667F);
    public static final BlockFlowerLM FLUORESCENT_FLOWER_GREEN = (BlockFlowerLM) new BlockFlowerLM("fluorescent_flower_green", MapColor.GREEN).setLightLevel(0.667F);
    public static final BlockFlowerLM FLUORESCENT_FLOWER_BLUE = (BlockFlowerLM) new BlockFlowerLM("fluorescent_flower_blue", MapColor.BLUE).setLightLevel(0.667F);
    public static final BlockFlowerLM FLUORESCENT_FLOWER_WHITE = (BlockFlowerLM) new BlockFlowerLM("fluorescent_flower_white", MapColor.SNOW).setLightLevel(1.0F);

    public static final BlockSaplingLM SILVER_SAPLING = new BlockSaplingLM("silver_sapling");
    public static final BlockLogLM SILVER_WOOD = new BlockLogLM("silver_wood");
    public static final BlockLeavesLM SILVER_LEAVES = new BlockLeavesLM("silver_leaves");
    public static final BlockLeavesLM FLOWERING_SILVER_LEAVES = (BlockLeavesLM) (new BlockLeavesLM("flowering_silver_leaves").setLightLevel(1.0F));
    public static final BlockLM SILVER_PLANKS = (BlockLM) new BlockLM(
            "silver_planks", Material.ROCK, MapColor.SILVER,  "pickaxe", 1, 4.5F, 0
    ).setResistance(6.0F);

    public static final BlockFlowerPotLM EVERGROWTH_FLOWER_POT = new BlockFlowerPotLM();

    public static final BlockGlassLM FROSTED_GLASS = new BlockGlassLM("frosted_glass");
    public static final BlockGlassPaneLM FROSTED_GLASS_PANE = new BlockGlassPaneLM("frosted_glass_pane");

    public static final BlockCeilingLight CEILING_LIGHT = new BlockCeilingLight("ceiling_light");
    public static final BlockCeilingLight CEILING_LIGHT_FROSTED = new BlockCeilingLight("ceiling_light_frosted");
}