package com.zeitheron.thaumicadditions.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystalCrusher extends ModelBase
{
	public ModelRenderer shape1;
	public ModelRenderer shape3;
	public ModelRenderer shape2;
	
	public ModelCrystalCrusher()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shape2 = new ModelRenderer(this, 0, 17);
		this.shape2.setRotationPoint(-6.0F, -3.0F, -6.0F);
		this.shape2.addBox(0.0F, 0.0F, 0.0F, 14, 3, 14, 0.0F);
		this.shape1 = new ModelRenderer(this, 0, 0);
		this.shape1.setRotationPoint(-7.0F, 21.0F, -7.0F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 14, 3, 14, 0.0F);
		this.shape3 = new ModelRenderer(this, 0, 0);
		this.shape3.setRotationPoint(-1.0F, 19.0F, -1.0F);
		this.shape3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
		this.shape3.addChild(this.shape2);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.shape1.render(f5);
		GL11.glTranslatef(0, 1 / 16F, 0);
		this.shape3.render(f5);
		GL11.glTranslatef(0, -1 / 16F, 0);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}