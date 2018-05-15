package com.endie.thaumicadditions.api;

import java.util.function.IntSupplier;

import com.endie.lib.utils.function.ObjToIntFunction;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class CustomColoredAspect extends Aspect
{
	public final ObjToIntFunction<CustomColoredAspect> getColor;
	
	public CustomColoredAspect(String tag, int color, Aspect[] components, ResourceLocation image, int blend, ObjToIntFunction<CustomColoredAspect> getcolor)
	{
		super(tag, color, components, image, blend);
		this.getColor = getcolor;
	}
	
	@Override
	public int getColor()
	{
		return getColor.applyAsInt(this);
	}
}