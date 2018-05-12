package com.endie.thaumicadditions.client.runnable;

import com.pengu.hammercore.utils.ColorHelper;

import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXVisSparkle;

public class VisSparkleRunnable implements Runnable
{
	float x, y, z, x2, y2, z2;
	int color;
	
	public VisSparkleRunnable(double x, double y, double z, double x2, double y2, double z2, int color)
	{
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
		this.x2 = (float) x2;
		this.y2 = (float) y2;
		this.z2 = (float) z2;
		this.color = color;
	}
	
	@Override
	public void run()
	{
		FXVisSparkle fb = new FXVisSparkle(FXDispatcher.INSTANCE.getWorld(), x, y, z, x2, y2, z2);
		fb.setRBGColorF(ColorHelper.getRed(color), ColorHelper.getGreen(color), ColorHelper.getBlue(color));
		ParticleEngine.addEffect(FXDispatcher.INSTANCE.getWorld(), fb);
	}
}