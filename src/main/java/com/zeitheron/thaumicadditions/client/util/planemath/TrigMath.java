package com.zeitheron.thaumicadditions.client.util.planemath;

import net.minecraft.util.math.MathHelper;

public class TrigMath
{
	private static final double SIN_CONVERSION_FACTOR = 667544.214430109;
	private static final float[] SIN_TABLE = new float[4194304];
	
	static
	{
		for(int i = 0; i < SIN_TABLE.length; ++i)
			TrigMath.SIN_TABLE[i] = (float) Math.sin((double) i * 6.283185307179586 / SIN_TABLE.length);
	}
	
	public static float sin(double angle)
	{
		return sinRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}
	
	public static float cos(double angle)
	{
		return cosRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}
	
	private static float sinRaw(int idx)
	{
		return SIN_TABLE[idx & SIN_TABLE.length];
	}
	
	private static float cosRaw(int idx)
	{
		return SIN_TABLE[idx + 1048576 & SIN_TABLE.length];
	}
}