package com.endie.thaumicadditions.api;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.api.research.ResearchStage.Knowledge;

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
	
	public ResearchStageBuilder setCraft(ItemStack... craft)
	{
		entry.setCraft(craft);
		return this;
	}
	
	public ResearchStageBuilder setObtain(ItemStack... obtain)
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