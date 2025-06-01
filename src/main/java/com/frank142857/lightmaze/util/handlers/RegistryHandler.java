package com.frank142857.lightmaze.util.handlers;

import com.frank142857.lightmaze.block.teleporter.BlockPortalLM;
import com.frank142857.lightmaze.client.renderer.tileentity.TileEntityItemDisplayerRenderer;
import com.frank142857.lightmaze.init.BlockInit;
import com.frank142857.lightmaze.init.ItemInit;
import com.frank142857.lightmaze.tileentity.TileEntityItemDisplayer;
import com.frank142857.lightmaze.util.interfaces.IHasModel;
import com.frank142857.lightmaze.world.gen.WorldGenOreLM;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(BlockInit.REGISTER_BLOCKS.toArray(new Block[0]));
    }
    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event){
        event.getRegistry().register(new ItemBlock(BlockInit.BLOCK_PORTAL_LM).setRegistryName(BlockPortalLM.name));
        event.getRegistry().registerAll(ItemInit.REGISTER_ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (Block block : BlockInit.REGISTER_BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModel();
            }
        }
        for(Item item : ItemInit.REGISTER_ITEMS){
            if(item instanceof IHasModel){
                ((IHasModel)item).registerModel();
            }
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemDisplayer.class, new TileEntityItemDisplayerRenderer());
    }
    public static void addSmelting(Item input, Item output, float xp){
        addSmelting(input, new ItemStack(output), xp);
    }

    public static void addSmelting(Item input, ItemStack output, float xp){
        GameRegistry.addSmelting(input, output, xp);
    }

    public static void registerSmeltingRecipe(){
        addSmelting(Item.getItemFromBlock(BlockInit.IRON_ORE), Items.IRON_INGOT, 0.15F);
        addSmelting(Item.getItemFromBlock(BlockInit.GOLD_ORE), Items.GOLD_INGOT, 0.2F);
        addSmelting(Item.getItemFromBlock(BlockInit.DIAMOND_ORE), Items.DIAMOND, 0.3F);
        addSmelting(Item.getItemFromBlock(BlockInit.REDSTONE_ORE), Items.REDSTONE, 0.15F);
        addSmelting(Item.getItemFromBlock(BlockInit.QUARTZ_ORE), Items.QUARTZ, 0.15F);
        addSmelting(Item.getItemFromBlock(BlockInit.SHADOW_METAL_ORE), ItemInit.SHADOW_METAL_INGOT, 0.55F);
        addSmelting(ItemInit.MINERAL_MUD_BALL, ItemInit.MINERAL_MUD_BRICK, 0.15F);
        addSmelting(Item.getItemFromBlock(BlockInit.WHITE_SAND), Item.getItemFromBlock(BlockInit.FROSTED_GLASS), 0.15F);
    }

    public static void worldGenRegistries(){
        GameRegistry.registerWorldGenerator(new WorldGenOreLM(), 0);
    }
}
