package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.InfoTAR;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CommonProxy
{
	public static final ResourceLocation TEXTURE_THAUMONOMICON_BG = new ResourceLocation(InfoTAR.MOD_ID, "textures/builting/texture_thaumonomicon_bg");
	
	protected FXHandler fx;
	
	public void preInit()
	{
	}
	
	public void init()
	{
	}
	
	public void postInit()
	{
	}
	
	protected FXHandler createFX()
	{
		return new FXHandler();
	}
	
	public FXHandler getFX()
	{
		if(fx == null)
			fx = createFX();
		return fx;
	}
	
	public int getItemColor(ItemStack stack, int layer)
	{
		return 0xFFFFFF;
	}
}