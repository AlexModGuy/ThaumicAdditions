package com.zeitheron.thaumicadditions.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelCrystalBore extends ModelBase
{
	public ModelRenderer shape1;
	public ModelRenderer shape2;
	public ModelRenderer rotational;
	public ModelRenderer shape4;
	public ModelRenderer shape5;
	public ModelRenderer shape6;
	
	public ModelCrystalBore()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.shape4 = new ModelRenderer(this, 91, 11);
		this.shape4.setRotationPoint(1.5F, -4.0F, 1.5F);
		this.shape4.addBox(0.0F, 0.0F, 0.0F, 5, 4, 5, 0.0F);
		this.shape6 = new ModelRenderer(this, 12, 0);
		this.shape6.setRotationPoint(1.0F, -3.0F, 1.0F);
		this.shape6.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.rotational = new ModelRenderer(this, 84, 0);
		this.rotational.setRotationPoint(-4.0F, 15.0F, -4.0F);
		this.rotational.addBox(0.0F, 0.0F, 0.0F, 8, 3, 8, 0.0F);
		this.shape1 = new ModelRenderer(this, 0, 0);
		this.shape1.setRotationPoint(-8.0F, 22.0F, -8.0F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);
		this.shape5 = new ModelRenderer(this, 0, 0);
		this.shape5.setRotationPoint(1.0F, -2.0F, 1.0F);
		this.shape5.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
		this.shape2 = new ModelRenderer(this, 48, 0);
		this.shape2.setRotationPoint(-6.0F, 18.0F, -6.0F);
		this.shape2.addBox(0.0F, 0.0F, 0.0F, 12, 4, 12, 0.0F);
		this.rotational.addChild(this.shape4);
		this.shape5.addChild(this.shape6);
		this.shape4.addChild(this.shape5);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		this.rotational.render(scale);
		this.shape1.render(scale);
		this.shape2.render(scale);
	}
	
	public void render(float angle)
	{
		shape1.render(1 / 16F);
		shape2.render(1 / 16F);
		
		GlStateManager.pushMatrix();
		GlStateManager.rotate(angle, 0, 1, 0);
		rotational.render(1 / 16F);
		GlStateManager.popMatrix();
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
