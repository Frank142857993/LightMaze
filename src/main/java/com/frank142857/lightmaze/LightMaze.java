package com.frank142857.lightmaze;

import com.frank142857.lightmaze.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = LightMaze.MODID,
        name = LightMaze.NAME,
        version = LightMaze.VERSION,
        acceptedMinecraftVersions = LightMaze.ACCEPTED_MINECRAFT_VERSIONS,
        guiFactory = "com.frank142857." + LightMaze.MODID + ".client.gui.GuiFactory",
        dependencies = LightMaze.DEPENDENCIES)
public class LightMaze
{
    public static final String MODID = "lightmaze";
    public static final String NAME = "Light Maze";
    public static final String VERSION = "0.0.1";
    public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12,1.13)";
    public static final String DEPENDENCIES = "after:aether_legacy";

    public static File config;

    private static Logger logger;

    @SidedProxy(clientSide = "com.frank142857.lightmaze.proxy.ClientProxy", serverSide = "com.frank142857.lightmaze.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("LM Mod loaded");
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public void sop(Object obj){
        System.out.println(obj);
    }

    static {
        FluidRegistry.enableUniversalBucket();
    }
}
