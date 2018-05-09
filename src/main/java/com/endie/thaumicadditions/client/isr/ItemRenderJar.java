package com.endie.thaumicadditions.client.isr;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.endie.thaumicadditions.client.tesr.TESRAbstractJar;
import com.pengu.hammercore.client.render.item.iItemRender;
import com.pengu.hammercore.client.utils.RenderBlocks;
import com.pengu.hammercore.utils.ColorHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.blocks.essentia.BlockJarItem;
import thaumcraft.common.config.ModConfig;

public class ItemRenderJar implements iItemRender
{
	@Override
	public void renderItem(ItemStack stack)
	{
		int amount = 0;
		int cap = 250;
		Aspect aspect = null, aspectFilter = null;
		
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("AspectFilter"))
			aspectFilter = Aspect.getAspect(stack.getTagCompound().getString("AspectFilter"));
		
		if(stack.getItem() instanceof BlockJarItem)
		{
			BlockJarItem it = (BlockJarItem) stack.getItem();
			AspectList al = it.getAspects(stack);
			if(al != null && al.size() > 0)
			{
				aspect = al.getAspectsSortedByAmount()[0];
				amount = al.getAmount(aspect);
			}
		} else if(stack.getItem() instanceof BlockAbstractJarItem)
		{
			BlockAbstractJarItem it = (BlockAbstractJarItem) stack.getItem();
			AspectList al = it.getAspects(stack);
			if(al != null && al.size() > 0)
			{
				aspect = al.getAspectsSortedByAmount()[0];
				amount = al.getAmount(aspect);
				cap = it.block.capacity;
			}
		}
		
		renderJarEssentia(amount, cap, aspect);
		
		if(aspectFilter != null)
		{
			GL11.glPushMatrix();
			GlStateManager.disableBlend();
			GL11.glTranslatef(.5F, .4F, -.135F);
			GL11.glBlendFunc(770, 771);
			float rot = aspectFilter.getTag().hashCode() % 4 - 2;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 0, 0.315f);
			if(ModConfig.CONFIG_GRAPHICS.crooked)
				GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
			UtilsFX.renderQuadCentered(new ResourceLocation("thaumcraft", "textures/models/label.png"), 0.5f, 1.0f, 1.0f, 1.0f, -99, 771, 1.0f);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 0, 0.316f);
			if(ModConfig.CONFIG_GRAPHICS.crooked)
				GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
			GL11.glScaled(-0.021, -0.021, -0.021);
			UtilsFX.drawTag(-8, -8, aspectFilter);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
			GlStateManager.enableBlend();
			
			GL11.glColor4f(1, 1, 1, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		}
	}
	
	public static void renderJarEssentia(int amount, int cap, Aspect aspect)
	{
		if(amount > 0 && cap > 0 && aspect != null)
		{
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glTranslatef(.5F, .01F, .5F);
			GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(2896);
			renderLiquidInItem(amount, aspect, cap, 0, 0, 0);
			GL11.glPopMatrix();
		}
	}
	
	public static void renderLiquidInItem(int amount, Aspect aspect, int cap, double x, double y, double z)
	{
		GL11.glPushMatrix();
		GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		RenderBlocks renderBlocks = RenderBlocks.forMod(InfoTAR.MOD_ID);
		GL11.glDisable(2896);
		float level = amount / ((float) cap) * 0.625f;
		Tessellator t = Tessellator.getInstance();
		renderBlocks.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.0625 + level, 0.75);
		t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
		int rgb = aspect.getColor();
		TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/animatedglow");
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		float r = ColorHelper.getRed(rgb);
		float g = ColorHelper.getGreen(rgb);
		float b = ColorHelper.getBlue(rgb);
		renderBlocks.renderFaceYNeg(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		renderBlocks.renderFaceYPos(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		renderBlocks.renderFaceZNeg(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		renderBlocks.renderFaceZPos(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		renderBlocks.renderFaceXNeg(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		renderBlocks.renderFaceXPos(-0.5, 0.0, -0.5, icon, r, g, b, 200);
		t.draw();
		GL11.glEnable(2896);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}