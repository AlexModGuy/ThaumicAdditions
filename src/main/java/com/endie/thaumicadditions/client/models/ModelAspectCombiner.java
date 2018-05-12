package com.endie.thaumicadditions.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAspectCombiner extends ModelBase
{
	public ModelRenderer shape1;
	public ModelRenderer shape2;
	public ModelRenderer shape4;
	public ModelRenderer shape4_1;
	public ModelRenderer shape7;
	
	public ModelAspectCombiner()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shape2 = new ModelRenderer(this, 24, 0);
		this.shape2.setRotationPoint(-2.5F, 10.0F, -2.5F);
		this.shape2.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5, 0.0F);
		this.shape1 = new ModelRenderer(this, 0, 0);
		this.shape1.setRotationPoint(-3.0F, 8.0F, -3.0F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6, 0.0F);
		this.shape4 = new ModelRenderer(this, 44, 0);
		this.shape4.setRotationPoint(-8.0F, 14.0F, -2.0F);
		this.shape4.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
		this.shape4_1 = new ModelRenderer(this, 0, 8);
		this.shape4_1.setRotationPoint(3.0F, 14.0F, -2.0F);
		this.shape4_1.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4, 0.0F);
		this.shape7 = new ModelRenderer(this, 18, 8);
		this.shape7.setRotationPoint(-3.0F, 13.0F, -3.0F);
		this.shape7.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
//		this.shape2.render(f5);
		this.shape1.render(f5);
		this.shape4.render(f5);
		this.shape4_1.render(f5);
		this.shape7.render(f5);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}