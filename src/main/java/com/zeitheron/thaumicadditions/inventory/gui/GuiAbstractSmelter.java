package com.zeitheron.thaumicadditions.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.pengu.hammercore.client.utils.RenderUtil;
import com.zeitheron.thaumicadditions.inventory.container.ContainerAbstractSmelter;
import com.zeitheron.thaumicadditions.tiles.TileAbstractSmelter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAbstractSmelter extends GuiContainer
{
	private TileAbstractSmelter furnaceInventory;
	ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_smelter.png");
	
	public GuiAbstractSmelter(InventoryPlayer par1InventoryPlayer, TileAbstractSmelter par2TileEntityFurnace)
	{
		super(new ContainerAbstractSmelter(par1InventoryPlayer, par2TileEntityFurnace));
		this.furnaceInventory = par2TileEntityFurnace;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(this.tex);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		GL11.glEnable(3042);
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		
		float i1;
		if(this.furnaceInventory.getBurnTimeRemainingScaled(20) > 0)
		{
			i1 = this.furnaceInventory.getBurnTimeRemainingScaled(20);
			RenderUtil.drawTexturedModalRect(k + 80, l + 26 + 20 - i1, 176, 20 - i1, 16, i1);
		}
		
		i1 = this.furnaceInventory.getCookProgressScaled(46);
		RenderUtil.drawTexturedModalRect(k + 106, l + 13 + 46 - i1, 216, 46 - i1, 9, i1);
		
		i1 = this.furnaceInventory.getVisScaled(48);
		RenderUtil.drawTexturedModalRect(k + 61, l + 12 + 48 - i1, 200, 48 - i1, 8, i1);
		this.drawTexturedModalRect(k + 60, l + 8, 232, 0, 10, 55);
	}
}