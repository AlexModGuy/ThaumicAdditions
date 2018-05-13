package com.endie.thaumicadditions.recipes;

import com.endie.thaumicadditions.api.EdibleAspect;
import com.endie.thaumicadditions.init.ItemsTAR;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import thaumcraft.api.aspects.AspectList;

public class RecipeMixSalts extends Impl<IRecipe> implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		AspectList aspects = new AspectList();
		int salts = 0;
		int l = inv.getSizeInventory();
		for(int i = 0; i < l; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			if(stack.getItem() != ItemsTAR.SALT_ESSENCE)
				return false;
			++salts;
			AspectList al = ItemsTAR.SALT_ESSENCE.getAspects(stack);
			if(al != null)
				aspects.add(al);
		}
		return aspects.visSize() > 1 && aspects.visSize() <= EdibleAspect.MAX_ESSENTIA && salts > 1;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		AspectList aspects = new AspectList();
		int salts = 0;
		int l = inv.getSizeInventory();
		for(int i = 0; i < l; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			if(stack.getItem() != ItemsTAR.SALT_ESSENCE)
				return ItemStack.EMPTY;
			++salts;
			AspectList al = ItemsTAR.SALT_ESSENCE.getAspects(stack);
			if(al != null)
				aspects.add(al);
		}
		
		if(aspects.visSize() > 1 && aspects.visSize() <= EdibleAspect.MAX_ESSENTIA && salts > 1)
		{
			ItemStack res = new ItemStack(ItemsTAR.SALT_ESSENCE, 1);
			ItemsTAR.SALT_ESSENCE.setAspects(res, aspects);
			return res;
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canFit(int width, int height)
	{
		return width * height > 1;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(ItemsTAR.SALT_ESSENCE);
	}
	
	@Override
	public boolean isHidden()
	{
		return true;
	}
}