package com.frank142857.lightmaze.client.gui;

import com.frank142857.lightmaze.LightMaze;
import com.frank142857.lightmaze.init.ConfigInit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiMessageDialog;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GuiConfigLM extends GuiConfig {
    public GuiConfigLM(GuiScreen parentScreen){
        super(
                parentScreen,
                new ConfigElement(ConfigInit.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                LightMaze.MODID,
                false,
                false,
                "General Configurations"
        );
    }
    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        if (button.id == 2000) {
            System.out.println("Pressed DONE button");
            boolean flag = true;
            try {
                if ((configID != null || parentScreen == null || !(parentScreen instanceof GuiConfig)) && (entryList.hasChangedEntry(true))) {
                    System.out.println("Saving config elements");
                    boolean requiresMcRestart = entryList.saveConfigElements();

                    if (Loader.isModLoaded(modID)) {
                        ConfigChangedEvent event = new ConfigChangedEvent.OnConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart);
                        FMLCommonHandler.instance().bus().post(event);
                        if (!event.getResult().equals(Event.Result.DENY))
                            FMLCommonHandler.instance().bus().post(new ConfigChangedEvent.PostConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart));

                        if (requiresMcRestart) {
                            flag = false;
                            mc.displayGuiScreen(new GuiMessageDialog(parentScreen, "fml.configgui.gameRestartTitle",
                                    new TextComponentString(I18n.format("fml.configgui.gameRestartRequired")), "fml.configgui.confirmRestartMessage"));
                        }

                        if (parentScreen instanceof GuiConfig)
                            ((GuiConfig) parentScreen).needsRefresh = true;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            if (flag) mc.displayGuiScreen(parentScreen);
        }
    }
}
