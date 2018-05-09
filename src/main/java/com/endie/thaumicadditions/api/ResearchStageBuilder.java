package com.endie.thaumicadditions.api;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.api.research.ResearchStage.Knowledge;
import thaumcraft.common.lib.research.ResearchManager;

public class ResearchStageBuilder
{
	private ResearchStage entry = new ResearchStage();
	
	public static ResearchStageBuilder start()
	{
		return new ResearchStageBuilder();
	}
	
	public ResearchStageBuilder setText(String text)
	{
		entry.setText(text);
		return this;
	}
	
	public ResearchStageBuilder setRecipes(String... recipes)
	{
		return setRecipes(Arrays.stream(recipes).map(ResourceLocation::new).collect(Collectors.toList()).toArray(new ResourceLocation[0]));
	}
	
	public ResearchStageBuilder setRequiredCraft(ItemStack... items)
	{
		entry.setCraft(items);
		if(entry.getCraft() != null && entry.getCraft().length > 0)
		{
			int[] refs = new int[entry.getCraft().length];
			int q = 0;
			ItemStack[] arritemStack = entry.getCraft();
			int n = arritemStack.length;
			for(int i = 0; i < n; ++i)
			{
				ItemStack stack = arritemStack[i];
				int code = ResearchManager.createItemStackHash(stack);
				ResearchManager.craftingReferences.add(code);
				refs[q] = code;
				++q;
			}
			entry.setCraftReference(refs);
		}
		return this;
	}
	
	public ResearchStageBuilder setRecipes(ResourceLocation... recipes)
	{
		entry.setRecipes(recipes);
		return this;
	}
	
	public ResearchStageBuilder setWarp(int warp)
	{
		entry.setWarp(warp);
		return this;
	}
	
	public ResearchStageBuilder setConsumedItems(ItemStack... obtain)
	{
		entry.setObtain(obtain);
		return this;
	}
	
	public ResearchStageBuilder setCraftReference(int... craftReference)
	{
		entry.setCraftReference(craftReference);
		return this;
	}
	
	public ResearchStageBuilder setKnow(Knowledge... know)
	{
		entry.setKnow(know);
		return this;
	}
	
	public ResearchStageBuilder setResearch(String... research)
	{
		entry.setResearch(research);
		return this;
	}
	
	public ResearchStage build()
	{
		if(entry == null)
			throw new IllegalStateException("Already built!");
		ResearchStage re = entry;
		entry = null;
		return re;
	}
}