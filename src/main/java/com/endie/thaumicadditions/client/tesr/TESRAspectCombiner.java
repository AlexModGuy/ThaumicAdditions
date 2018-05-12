package com.endie.thaumicadditions.client.tesr;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.client.models.ModelAspectCombiner;
import com.endie.thaumicadditions.tiles.TileAspectCombiner;
import com.pengu.hammercore.client.render.tesr.TESR;
import com.pengu.hammercore.client.utils.RenderBlocks;
import com.pengu.hammercore.utils.ColorHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class TESRAspectCombiner extends TESR<TileAspectCombiner>
{
	public ResourceLocation texture = new ResourceLocation(InfoTAR.MOD_ID, "textures/models/aspect_combiner.png");
	public ModelAspectCombiner model = new ModelAspectCombiner();
	
	public float maxVis = 3;
	
	@Override
	public void renderTileEntityAt(TileAspectCombiner te, double x, double y, double z, float partialTicks, ResourceLocation destroyStage, float alpha)
	{
		for(int i = 0; i < (destroyStage != null ? 2 : 1); ++i)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(x + .5, y + 1.5, z + .5);
			if(te.front != null && te.front.getAxis() == Axis.X)
				GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			bindTexture(i == 1 ? destroyStage : texture);
			model.render(null, 0, 0, 0, 0, 0, 1 / 16F);
			GL11.glPushMatrix();
			GL11.glRotatef(te.prevRotation + (te.rotation - te.prevRotation) * partialTicks, 0, 1, 0);
			model.shape2.render(1 / 16F);
			GL11.glRotatef(45, 0, 1, 0);
			model.shape2.render(1 / 16F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
		float vis = (te.prevVis + (te.vis - te.prevVis) * partialTicks) / 3;
		
		Aspect aa = te.inA;
		Aspect ab = te.inB;
		Integer rgb = aa != null || ab != null ? ((aa != null ? aa.getColor() : ab != null ? ab.getColor() : 0)) : null;
		
		if(te.output != null)
			rgb = te.output.getColor();
		else if(aa != null && ab != null)
		{
			int rgb1 = aa.getColor();
			int rgb2 = ab.getColor();
			
			float r1 = ColorHelper.getRed(rgb1);
			float g1 = ColorHelper.getGreen(rgb1);
			float b1 = ColorHelper.getBlue(rgb1);
			
			float r2 = ColorHelper.getRed(rgb2);
			float g2 = ColorHelper.getGreen(rgb2);
			float b2 = ColorHelper.getBlue(rgb2);
			
			rgb = ColorHelper.packRGB((r1 + r2) / 2, (g1 + g2) / 2, (b1 + b2) / 2);
			
			Aspect out = TileAspectCombiner.getOutput(aa, ab);
			if(out != null)
			{
				int rgb3 = out.getColor();
				float interp = Math.min(te.craftingTime / (float) (te.getMaxCraftTime() - 20), 1);
				
				r1 = ColorHelper.getRed(rgb) * (1 - interp);
				g1 = ColorHelper.getGreen(rgb) * (1 - interp);
				b1 = ColorHelper.getBlue(rgb) * (1 - interp);
				
				r2 = ColorHelper.getRed(rgb3) * (interp);
				g2 = ColorHelper.getGreen(rgb3) * (interp);
				b2 = ColorHelper.getBlue(rgb3) * (interp);
				
				rgb = ColorHelper.packARGB(1, (r1 + r2) / 2, (g1 + g2) / 2, (b1 + b2) / 2);
			}
		}
		
		if(rgb == null || vis <= 0)
			return;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 13.05 / 16F, y + 5.05 / 16F + (5.8 * vis) / 16, z + 2.95 / 16F);
		GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		RenderBlocks renderBlocks = RenderBlocks.forMod(InfoTAR.MOD_ID);
		GL11.glDisable(2896);
		Tessellator t = Tessellator.getInstance();
		renderBlocks.setRenderBounds(0, 0, 0, 5.8 / 16F, (5.8 * vis) / 16F, 5.8 / 16F);
		t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
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
	
	@Override
	public void renderItem(ItemStack item)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(.5, 1.5, .5);
		// GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(180, 1, 0, 0);
		bindTexture(texture);
		model.render(null, 0, 0, 0, 0, 0, 1 / 16F);
		model.shape2.render(1 / 16F);
		GL11.glPopMatrix();
	}
}