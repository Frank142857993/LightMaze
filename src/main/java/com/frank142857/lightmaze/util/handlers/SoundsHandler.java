package com.frank142857.lightmaze.util.handlers;

import com.frank142857.lightmaze.LightMaze;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {
    public static SoundEvent BLOCK_LM_PORTAL_AMBIENT;
    public static SoundEvent BLOCK_LM_PORTAL_AMBIENT_2;

    public static void registerSounds(){
        BLOCK_LM_PORTAL_AMBIENT = registerSound("block.lm_portal.ambient");
        BLOCK_LM_PORTAL_AMBIENT_2 = registerSound("block.lm_portal.ambient2");
    }

    private static SoundEvent registerSound(String name){
        ResourceLocation location = new ResourceLocation(LightMaze.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
