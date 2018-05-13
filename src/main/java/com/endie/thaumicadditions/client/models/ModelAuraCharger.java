package com.endie.thaumicadditions.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAuraCharger extends ModelBase
{
	// Sprinkler Pipe
	public ModelRenderer shape1;
	
	// Sprinkler Base
	public ModelRenderer shape2;
	
	// Sprinkler A
	public ModelRenderer shape3;
	public ModelRenderer shape4;
	public ModelRenderer shape5;
	
	// Sprinkler B
	public ModelRenderer shape3_1;
	public ModelRenderer shape4_1;
	public ModelRenderer shape5_1;
	
	public ModelAuraCharger()
	{
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.shape2 = new ModelRenderer(this, 12, 0);
		this.shape2.setRotationPoint(-2.0F, 18.0F, -2.0F);
		this.shape2.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
		this.shape5 = new ModelRenderer(this, 9, 0);
		this.shape5.setRotationPoint(-4.0F, -2.0F, 1.0F);
		this.shape5.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.shape5_1 = new ModelRenderer(this, 24, 0);
		this.shape5_1.setRotationPoint(6.0F, -2.0F, 2.0F);
		this.shape5_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.shape1 = new ModelRenderer(this, 0, 0);
		this.shape1.setRotationPoint(-1.5F, 20.0F, -1.5F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 3, 4, 3, 0.0F);
		this.shape3 = new ModelRenderer(this, 10, 6);
		this.shape3.setRotationPoint(-2.0F, 0.0F, 1.0F);
		this.shape3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
		this.shape4 = new ModelRenderer(this, 18, 6);
		this.shape4.setRotationPoint(-4.0F, -1.0F, 1.0F);
		this.shape4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
		this.shape3_1 = new ModelRenderer(this, 0, 7);
		this.shape3_1.setRotationPoint(4.0F, 0.0F, 1.0F);
		this.shape3_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
		this.shape4_1 = new ModelRenderer(this, 6, 9);
		this.shape4_1.setRotationPoint(5.0F, -1.0F, 1.0F);
		this.shape4_1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
		this.shape2.addChild(this.shape5);
		this.shape2.addChild(this.shape5_1);
		this.shape2.addChild(this.shape3);
		this.shape2.addChild(this.shape4);
		this.shape2.addChild(this.shape3_1);
		this.shape2.addChild(this.shape4_1);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.shape2.render(f5);
		this.shape1.render(f5);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}