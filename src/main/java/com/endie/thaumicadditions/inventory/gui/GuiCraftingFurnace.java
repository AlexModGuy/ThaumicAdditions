package com.endie.thaumicadditions.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.inventory.container.ContainerCraftingFurnace;
import com.endie.thaumicadditions.tiles.TileCraftingFurnace;
import com.pengu.hammercore.client.gui.GuiWidgets;
import com.pengu.hammercore.client.texture.gui.DynGuiTex;
import com.pengu.hammercore.client.texture.gui.GuiTexBakery;
import com.pengu.hammercore.client.texture.gui.theme.GuiTheme;
import com.pengu.hammercore.client.utils.RenderUtil;
import com.pengu.hammercore.client.utils.UtilsFX;
import com.pengu.hammercore.utils.ColorHelper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class GuiCraftingFurnace extends GuiContainer
{
	public GuiCraftingFurnace(EntityPlayer player, TileCraftingFurnace tile)
	{
		super(new ContainerCraftingFurnace(player, tile));
	}
	
	DynGuiTex tex;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		GuiTexBakery b = GuiTexBakery.start();
		b.body(0, 0, xSize, ySize);
		for(Slot s : inventorySlots.inventorySlots)
			b.slot(s.xPos - 1, s.yPos - 1);
		tex = b.bake();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		tex.render(guiLeft, guiTop);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		UtilsFX.bindTexture("textures/gui/def_widgets.png");
		
		ContainerCraftingFurnace furn = (ContainerCraftingFurnace) inventorySlots;
		TileCraftingFurnace t = furn.t;
		int col = GuiTheme.current().slotColor;
		GL11.glColor4f(ColorHelper.getRed(col), ColorHelper.getGreen(col), ColorHelper.getBlue(col), 1);
		RenderUtil.drawTexturedModalRect(guiLeft + 91, guiTop + 37, 43, 0, 13, 13);
		
		if(t.isBurning())
		{
			double k = (t.furnaceBurnTime / (float) t.currentItemBurnTime) * 13;
			
			RenderUtil.drawTexturedModalRect(guiLeft + 91, guiTop + 36, 14, 0, 14, 14);
			
			GL11.glColor4f(1, 1, 1, 1);
			RenderUtil.drawTexturedModalRect(guiLeft + 90, guiTop + 37 + 12 - k, 0, 12 - k, 14, k + 1);
		}
		
		double l = (t.cookTime / (float) t.totalCookTime) * 24;
		GuiWidgets.drawFurnaceArrow(guiLeft + 112.5F, guiTop + 35, l);
		
		GL11.glColor4f(ColorHelper.getRed(col), ColorHelper.getGreen(col), ColorHelper.getBlue(col), 1);
		UtilsFX.bindTexture(InfoTAR.MOD_ID, "textures/gui/widgets.png");
		RenderUtil.drawTexturedModalRect(guiLeft + 65, guiTop + 37, 0, 0, 16, 16);
		if(furn.craftResult != null && !furn.craftResult.getStackInSlot(0).isEmpty())
		{
			col = GuiTheme.current().slotCoverLU;
			GL11.glColor4f(ColorHelper.getRed(col), ColorHelper.getGreen(col), ColorHelper.getBlue(col), 1);
			RenderUtil.drawTexturedModalRect(guiLeft + 64, guiTop + 36, 0, 0, 16, 16);
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GL11.glColor4f(1, 1, 1, 1);
		drawDefaultBackground();
		GL11.glColor4f(1, 1, 1, 1);
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		GL11.glColor4f(1, 1, 1, 1);
	}
}