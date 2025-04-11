package com.frank142857.lightmaze.tileentity;

import com.frank142857.lightmaze.block.BlockFlowerPotLM;
import com.frank142857.lightmaze.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityFlowerPotLM extends TileEntity implements ITickable {
    private Item flowerPotItem;
    private int flowerPotData;

    public TileEntityFlowerPotLM(){

    }

    public TileEntityFlowerPotLM(Item item, int data){
        this.flowerPotItem = item;
        this.flowerPotData = data;
    }

    public static void registerFixesFlowerPotLM(DataFixer fixer)
    {
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(this.flowerPotItem);
        compound.setString("Item", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setInteger("Data", this.flowerPotData);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Item", 8))
        {
            this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
        }
        else
        {
            this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
        }

        this.flowerPotData = compound.getInteger("Data");
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    public void setItemStack(ItemStack stack)
    {
        this.flowerPotItem = stack.getItem();
        this.flowerPotData = stack.getMetadata();
    }

    public ItemStack getFlowerItemStack()
    {
        return this.flowerPotItem == null ? ItemStack.EMPTY : new ItemStack(this.flowerPotItem, 1, this.flowerPotData);
    }

    @Nullable
    public Item getFlowerPotItem()
    {
        return this.flowerPotItem;
    }

    public int getFlowerPotData()
    {
        return this.flowerPotData;
    }

    public void update(){

        if (this.world != null && !this.world.isRemote){
            this.blockType = this.getBlockType();

            if (this.blockType instanceof BlockFlowerPotLM){
                if(this.flowerPotItem == Item.getItemFromBlock(BlockInit.FLUORESCENT_FLOWER_RED)){
                    ((BlockFlowerPotLM) this.blockType).turnOnPower(world, pos);
                }
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
