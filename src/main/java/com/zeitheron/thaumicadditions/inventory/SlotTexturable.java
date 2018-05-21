package com.zeitheron.thaumicadditions.inventory;

import java.util.function.Predicate;

import com.google.common.base.Predicates;
import com.pengu.hammercore.client.texture.TextureAtlasSpriteFull;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotTexturable extends Slot
{
	public SlotTexturable(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	protected ResourceLocation tex;
	public Predicate<ItemStack> validator = Predicates.alwaysTrue();
	
	public SlotTexturable setValidator(Predicate<ItemStack> validator)
	{
		this.validator = validator;
		return this;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return validator.test(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return TextureAtlasSpriteFull.sprite;
	}
	
	public SlotTexturable setBackgroundTexture(ResourceLocation texture)
	{
		tex = texture;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getBackgroundLocation()
	{
		return tex;
	}
}