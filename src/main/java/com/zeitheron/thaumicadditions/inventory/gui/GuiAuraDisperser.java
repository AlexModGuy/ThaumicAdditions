package com.zeitheron.thaumicadditions.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.pengu.hammercore.client.utils.RenderUtil;
import com.pengu.hammercore.client.utils.UtilsFX;
import com.pengu.hammercore.color.InterpolationUtil;
import com.pengu.hammercore.utils.ColorHelper;
import com.zeitheron.thaumicadditions.InfoTAR;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.api.utils.iChangeListener;
import com.zeitheron.thaumicadditions.inventory.container.ContainerAuraDisperser;
import com.zeitheron.thaumicadditions.tiles.TileAuraDisperser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public class GuiAuraDisperser extends GuiContainer implements iChangeListener
{
	TileAuraDisperser t;
	
	public int hit;
	public int prevHit;
	
	public GuiAuraDisperser(EntityPlayer player, TileAuraDisperser t)
	{
		super(new ContainerAuraDisperser(player, t));
		this.t = t;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		t.listeners.addListener(this);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		UtilsFX.bindTexture(InfoTAR.MOD_ID, "textures/gui/aura_disperser.png");
		GL11.glColor4f(1, 1, 1, 1);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		float progress = t.tickTimer / (float) (t.secondsPerSalt * 20);
		if(t.aspects != null)
		{
			int rgb = AspectUtil.getColor(t.aspects, true);
			int red = InterpolationUtil.interpolate(rgb, 0xFF0000, .5F);
			
			float hit = prevHit + (this.hit - prevHit) * partialTicks;
			if(hit > 6)
				rgb = InterpolationUtil.interpolate(rgb, red, 1 - (hit - 6) / 2F);
			else if(hit > 0)
				rgb = InterpolationUtil.interpolate(rgb, red, hit / 6F);
			
			float r = ColorHelper.getRed(rgb), g = ColorHelper.getGreen(rgb), b = ColorHelper.getBlue(rgb);
			
			GL11.glColor4f(r, g, b, 1);
			RenderUtil.drawTexturedModalRect(guiLeft + 38, guiTop + 13 + 50 - 50 * progress, 176, 0, 8, 50 * progress);
			RenderUtil.drawTexturedModalRect(guiLeft + 38, guiTop + 13 + 50 - 50 * progress, 248, (t.ticksExisted + partialTicks) % 256, 8, 50 * progress);
		}
		GL11.glColor4f(1, 1, 1, 1);
		drawTexturedModalRect(guiLeft + 35, guiTop + 7, 190, 0, 15, 62);
	}
	
	@Override
	public void update(int id)
	{
		if(id == 11 && hit == 0)
			prevHit = hit = 8;
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		prevHit = hit;
		if(hit > 0)
			--hit;
	}
	
	@Override
	public boolean valid()
	{
		return Minecraft.getMinecraft().currentScreen == this;
	}
}