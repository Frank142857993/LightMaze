package com.frank142857.lightmaze.client.renderer.tileentity;

import com.frank142857.lightmaze.tileentity.TileEntityItemDisplayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityItemDisplayerRenderer extends TileEntitySpecialRenderer<TileEntityItemDisplayer> {
    public void render(TileEntityItemDisplayer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        // debugging. TODO Replace this by the actual item
        super.drawNameplate(te, Integer.toString(te.getNumberData()), x, y - 0.75F, z, 12);
    }
}
