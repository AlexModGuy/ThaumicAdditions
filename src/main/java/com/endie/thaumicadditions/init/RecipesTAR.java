package com.endie.thaumicadditions.init;

import com.endie.thaumicadditions.InfoTAR;
import com.pengu.hammercore.recipeAPI.helper.RecipeRegistry;
import com.pengu.hammercore.recipeAPI.helper.RegisterRecipes;

import net.minecraft.init.Blocks;

@RegisterRecipes(modid = InfoTAR.MOD_ID)
public class RecipesTAR extends RecipeRegistry
{
	@Override
	public void crafting()
	{
		shaped(BlocksTAR.CRAFTING_FURNACE, "c", "g", "f", 'c', Blocks.CRAFTING_TABLE, 'g', "gearIron", 'f', Blocks.FURNACE);
	}
	
	@Override
	public void smelting()
	{
	}
}