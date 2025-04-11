package com.frank142857.lightmaze.world.dimension.lightmaze;

import com.frank142857.lightmaze.init.ConfigInit;
import com.frank142857.lightmaze.init.DimensionInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderLM extends WorldProvider {
    @Override
    protected void init(){
        this.biomeProvider = new BiomeProviderLM(this.world.getSeed());
        this.nether = false;
        this.world.setSeaLevel(63);
        System.out.println("Sea level = " + this.world.getSeaLevel());
    }

    @Override
    public DimensionType getDimensionType(){
        return DimensionInit.lightmaze;
    }

    @Override
    public IChunkGenerator createChunkGenerator(){
        return new ChunkGeneratorLM(this.world, this.world.getSeed(), true);
    }

    @Override
    public void setAllowedSpawnTypes(boolean hostile, boolean animal){
        super.setAllowedSpawnTypes(false, true);
    }


    @SuppressWarnings("deprecation")
    @Override
    public void resetRainAndThunder() {

        WorldInfo worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo) world.getWorldInfo(), "theWorldInfo", "field_76115_a");

        worldInfo.setRainTime(0);
        worldInfo.setRaining(false);
        worldInfo.setThunderTime(0);
        worldInfo.setThundering(false);

        if(this.world.getGameRules().getBoolean("doDaylightCycle"))
        {
            long i = worldInfo.getWorldTime() + 24000L;
            worldInfo.setWorldTime(i - i % 24000L);

        }
    }

    @Override
    public WorldProvider.WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos){
        return WorldSleepResult.ALLOW;
    }

    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return true;
    }

    public boolean canRespawnHere()
    {
        return true;
    }

    @Override
    public boolean isSurfaceWorld(){
        return true;
    }

    @Override
    public boolean shouldMapSpin(String entityName, double x, double z, double rotation) {
        return false;
    }


    @Override
    public float getCloudHeight(){
        return 165F;
    }

    @Override
    public double getHorizon() {
        return 1.0D;
    }

    @Override
    public boolean hasSkyLight(){
        return true;
    }

    @Override
    public boolean doesWaterVaporize(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor(){
        return 0.03125D;
    }

    @Override
    public Vec3d getFogColor(float f, float f1) {

        float bright = MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        bright = MathHelper.clamp(bright, 0.0F, 1.0F);
        float red = 0.84705883F;
        float green = 0.84705883F;
        float blue = 0.84705883F;
        red = red * (bright * 0.94F + 0.06F);
        green = green * (bright * 0.94F + 0.06F);
        blue = blue * (bright * 0.91F + 0.09F);
        if (ConfigInit.USING_FOG_COLOR) return new Vec3d((double)red, (double)green, (double)blue);
        else return super.getFogColor(f, f1);

        /*
        float bright = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (bright < 0.0F) {
            bright = 0.0F;
        }
        if (bright > 1.0F) {
            bright = 1.0F;
        }
        float red = 0.84705883F;
        float green = 0.84705883F;
        float blue = 0.84705883F;
        red *= bright * 0.94F + 0.06F;
        green *= bright * 0.94F + 0.06F;
        blue *= bright * 0.91F + 0.06F;
        if (ConfigInit.USING_FOG_COLOR) return new Vec3d(red, green, blue);
        else return super.getFogColor(f, f1);
        */
    }
}
