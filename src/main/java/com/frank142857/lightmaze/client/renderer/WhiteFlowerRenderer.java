package com.frank142857.lightmaze.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WhiteFlowerRenderer {

    public void render(double x, double y, double z, float partialTicks, int destroyStage, float alpha){
        GlStateManager.disableFog();
        GlStateManager.alphaFunc(516, 0.1F);
        //this.
    }
}
