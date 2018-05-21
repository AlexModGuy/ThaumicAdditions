package com.zeitheron.thaumicadditions.recipes;

import java.util.Objects;

import com.zeitheron.thaumicadditions.api.EdibleAspect;
import com.zeitheron.thaumicadditions.init.ItemsTAR;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import thaumcraft.api.aspects.AspectList;

public class RecipeApplySalt extends Impl<IRecipe> implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemFood food = null;
		NBTTagCompound nbt = null;
		int foodq = 0;
		int dmg = 0;
		AspectList aspects = new AspectList();
		int l = inv.getSizeInventory();
		for(int i = 0; i < l; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			if(stack.getItem() != ItemsTAR.SALT_ESSENCE)
			{
				if(stack.getItem() instanceof ItemFood)
				{
					if(food != null)
					{
						if(stack.getItem() != food || stack.getItemDamage() != dmg || !nbtEqual(nbt, stack.getTagCompound()))
							return false;
						else
							foodq++;
					}
					food = (ItemFood) stack.getItem();
					dmg = stack.getItemDamage();
					nbt = stack.getTagCompound();
					foodq++;
				}
			}
			AspectList al = ItemsTAR.SALT_ESSENCE.getAspects(stack);
			if(al != null)
				aspects.add(al);
		}
		
		if(aspects.size() > 0 && food != null && foodq > 0)
		{
			ItemStack s = new ItemStack(food, foodq, dmg);
			if(nbt != null)
				s.setTagCompound(nbt);
			ItemStack res = EdibleAspect.applyToFoodStack(s, aspects);
			return EdibleAspect.getSalt(res).visSize() <= EdibleAspect.MAX_ESSENTIA;
		}
		
		return false;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemFood food = null;
		NBTTagCompound nbt = null;
		int foodq = 0;
		int dmg = 0;
		AspectList aspects = new AspectList();
		int l = inv.getSizeInventory();
		for(int i = 0; i < l; ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			if(stack.getItem() != ItemsTAR.SALT_ESSENCE)
			{
				if(stack.getItem() instanceof ItemFood)
				{
					if(food != null)
					{
						if(stack.getItem() != food || stack.getItemDamage() != dmg || !nbtEqual(nbt, stack.getTagCompound()))
							return ItemStack.EMPTY;
						else
							foodq++;
					}
					food = (ItemFood) stack.getItem();
					dmg = stack.getItemDamage();
					nbt = stack.getTagCompound();
					foodq++;
				}
			}
			AspectList al = ItemsTAR.SALT_ESSENCE.getAspects(stack);
			if(al != null)
				aspects.add(al);
		}
		if(aspects.size() > 0 && food != null && foodq > 0)
		{
			ItemStack s = new ItemStack(food, foodq, dmg);
			if(nbt != null)
				s.setTagCompound(nbt);
			ItemStack res = EdibleAspect.applyToFoodStack(s, aspects);
			return EdibleAspect.getSalt(res).visSize() <= EdibleAspect.MAX_ESSENTIA ? res : ItemStack.EMPTY;
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
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean isHidden()
	{
		return true;
	}
	
	private static boolean nbtEqual(NBTTagCompound a, NBTTagCompound b)
	{
		return Objects.equals(a, b);
	}
}