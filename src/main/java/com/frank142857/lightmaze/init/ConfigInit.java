package com.frank142857.lightmaze.init;


import com.frank142857.lightmaze.LightMaze;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigInit {
    public static Configuration config;

    /** Dimension IDs **/
    @Config.RequiresWorldRestart
    public static int LIGHT_MAZE = 8;

    //Is portal enabled
    public static boolean PORTAL_ENABLED = true;

    //Set LM dimension fog color into 0xcccccc
    public static boolean USING_FOG_COLOR = true;

    /** Terrain **/
    //@Config.RequiresWorldRestart
    //public static float COORDINATE_SCALE = 684.412F;

    public static void init(File file){
        config = new Configuration(file);
        String category = "general";

        config.addCustomCategoryComment(category, "Set the dimension ID for the Light Maze dimension, default:8");
        LIGHT_MAZE = config.getInt("Light Maze Dimension ID", category, 8, 2, 255, "Dim ID of the Light Maze dimension", "set_dim_id");

        config.addCustomCategoryComment(category, "Decide if player is able to create LM portal, default:true");
        PORTAL_ENABLED = config.getBoolean("Enable creating portal", category, true, "Enable creating LM portal", "enable_portal");

        config.addCustomCategoryComment(category, "Decide if dimension sky & fog color is applied, default:true");
        USING_FOG_COLOR = config.getBoolean("Using dimension sky & fog color", category, true, "Stylize LM dimension sky & fog color", "fog_color");

        config.setCategoryComment(category, "General configurations");

        /*
        //TODO test: terrain gen settings by config. Add category!
        category = "terrain";
        config.addCustomCategoryComment(category, "Set coordinate scale, default:684.412F");
        COORDINATE_SCALE = config.getFloat("Terrain generation settings: coordinate scale", category, 684.412F, 1.0F, 6000.0F, "Terrain generation settings: coordinate scale", "coordinate_scale");

        config.setCategoryComment(category, "Terrain generation settings");
        */

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event){
        LightMaze.config = new File(event.getModConfigurationDirectory() + "/" + "LightMaze");
        LightMaze.config.mkdirs();
        init(new File(LightMaze.config.getPath(), "LightMaze.cfg"));
    }
}
