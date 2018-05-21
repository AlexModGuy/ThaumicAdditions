package com.zeitheron.thaumicadditions.fluids;

import com.zeitheron.thaumicadditions.InfoTAR;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidCrystalWater extends Fluid
{
	public FluidCrystalWater()
	{
		super("crystal_water", new ResourceLocation(InfoTAR.MOD_ID, "blocks/crystal_water_still"), new ResourceLocation(InfoTAR.MOD_ID, "blocks/crystal_water_flow"));
		setUnlocalizedName(InfoTAR.MOD_ID + ":crystal_water");
	}
	
	@Override
	public int getColor()
	{
		return 0xFF3D9999;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "fluid." + InfoTAR.MOD_ID + ":crystal_water";
	}
}