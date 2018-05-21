package com.zeitheron.thaumicadditions.client.tesr;

import org.lwjgl.opengl.GL11;

import com.pengu.hammercore.client.render.tesr.TESR;
import com.pengu.hammercore.utils.FrictionRotator;
import com.zeitheron.thaumicadditions.InfoTAR;
import com.zeitheron.thaumicadditions.client.models.ModelAuraCharger;
import com.zeitheron.thaumicadditions.tiles.TileAuraCharger;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TESRAuraCharger extends TESR<TileAuraCharger>
{
	public ResourceLocation texture = new ResourceLocation(InfoTAR.MOD_ID, "textures/models/aura_charger.png");
	public ModelAuraCharger model = new ModelAuraCharger();
	
	@Override
	public void renderTileEntityAt(TileAuraCharger te, double x, double y, double z, float partialTicks, ResourceLocation destroyStage, float alpha)
	{
		FrictionRotator cr = te.rotator;
		for(int i = 0; i < (destroyStage != null ? 2 : 1); ++i)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(x + .5, y + 1.5, z + .5);
			// GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			bindTexture(i == 1 ? destroyStage : texture);
			model.shape1.render(1 / 16F);
			GL11.glPushMatrix();
			GL11.glRotatef(cr.getActualRotation(partialTicks), 0, 1, 0);
			model.shape2.render(1 / 16F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public void renderItem(ItemStack item)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(.5, 2.25, .5);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(1.5, 1.5, 1.5);
		bindTexture(texture);
		model.render(null, 0, 0, 0, 0, 0, 1 / 16F);
		GL11.glPopMatrix();
	}
}