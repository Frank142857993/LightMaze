package com.frank142857.lightmaze.init;

import com.frank142857.lightmaze.item.ItemIngotLM;
import com.frank142857.lightmaze.item.ItemLM;
import com.frank142857.lightmaze.item.ItemSilverBlossom;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> REGISTER_ITEMS = new ArrayList<Item>();

    public static final ItemLM SILVER_WOOD_STICK = new ItemLM("silver_wood_stick");
    public static final ItemLM MINERAL_MUD_BALL = new ItemLM("mineral_mud_ball");
    public static final ItemIngotLM SHADOW_METAL_INGOT = new ItemIngotLM("shadow_metal_ingot");
    public static final ItemIngotLM MINERAL_MUD_BRICK = new ItemIngotLM("mineral_mud_brick");
    public static final ItemSilverBlossom SILVER_BLOSSOM = new ItemSilverBlossom();
}
