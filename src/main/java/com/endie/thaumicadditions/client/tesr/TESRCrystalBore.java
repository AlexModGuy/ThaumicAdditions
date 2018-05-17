package com.endie.thaumicadditions.client.tesr;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.client.models.ModelCrystalBore;
import com.endie.thaumicadditions.tiles.TileCrystalBore;
import com.pengu.hammercore.client.render.tesr.TESR;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TESRCrystalBore extends TESR<TileCrystalBore>
{
	public ResourceLocation texture = new ResourceLocation(InfoTAR.MOD_ID, "textures/models/crystal_bore.png");
	public ModelCrystalBore model = new ModelCrystalBore();
	
	@Override
	public void renderTileEntityAt(TileCrystalBore te, double x, double y, double z, float partialTicks, ResourceLocation destroyStage, float alpha)
	{
		if(te.face == null)
			return;
		GL11.glPushMatrix();
		translateFromOrientation(x, y, z, te.face.getOpposite());
		GL11.glTranslatef(0, -.5F, 0);
		for(int i = 0; i < (destroyStage != null ? 2 : 1); ++i)
		{
			bindTexture(i == 1 ? destroyStage : texture);
			model.render(te.rotator.getActualRotation(partialTicks));
		}
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderItem(ItemStack item)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(.5, 1.5, .5);
		GL11.glRotated(180, 1, 0, 0);
		bindTexture(texture);
		model.render(0);
		GL11.glPopMatrix();
	}
}