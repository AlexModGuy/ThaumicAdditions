package com.endie.thaumicadditions.effect;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.InfoTAR;
import com.pengu.hammercore.client.utils.RenderUtil;
import com.pengu.hammercore.client.utils.UtilsFX;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBaseTAR extends Potion
{
	public int tx, ty;
	
	public PotionBaseTAR(boolean bad, int color, String name, int tx, int ty)
	{
		super(bad, color);
		this.tx = tx;
		this.ty = ty;
		setPotionName("potion." + InfoTAR.MOD_ID + ":" + name);
		setRegistryName(InfoTAR.MOD_ID, name);
	}
	
	public String getTex()
	{
		int x = tx / 14;
		int y = ty / 14;
		return "textures/gui/potion_sheets/" + x + "_" + y + ".png";
	}
	
	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		render(x - 3, y - 3, effect, mc, alpha);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
	{
		render(x, y, effect, mc, 1F);
	}
	
	@SideOnly(Side.CLIENT)
	protected void render(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		drawPotionIcon(tx / 8, ty / 8, tx % 8 * 18, ty % 8 * 18, x, y, 32, 32);
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawPotionIcon(int sx, int sy, int tx, int ty, int x, int y, int width, int height)
	{
		UtilsFX.bindTexture(InfoTAR.MOD_ID, "textures/gui/potion_sheets/" + sx + "_" + sy + ".png");
		GL11.glPushMatrix();
		GL11.glTranslatef(x + 6, y + 6, 0);
		GL11.glScalef(18F / width, 18F / height, 1);
		RenderUtil.drawTexturedModalRect(0, 0, tx, ty, width, height);
		GL11.glPopMatrix();
	}
}