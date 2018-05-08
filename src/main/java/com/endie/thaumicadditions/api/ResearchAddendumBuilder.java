package com.endie.thaumicadditions.api;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchAddendum;

public class ResearchAddendumBuilder
{
	private ResearchAddendum entry = new ResearchAddendum();
	
	public static ResearchAddendumBuilder start()
	{
		return new ResearchAddendumBuilder();
	}
	
	public ResearchAddendumBuilder setText(String text)
	{
		entry.setText(text);
		return this;
	}
	
	public ResearchAddendumBuilder setRecipes(String... recipes)
	{
		return setRecipes(Arrays.stream(recipes).map(ResourceLocation::new).collect(Collectors.toList()).toArray(new ResourceLocation[0]));
	}
	
	public ResearchAddendumBuilder setRecipes(ResourceLocation... recipes)
	{
		entry.setRecipes(recipes);
		return this;
	}
	
	public ResearchAddendumBuilder setResearch(String... research)
	{
		entry.setResearch(research);
		return this;
	}
	
	public ResearchAddendum build()
	{
		if(entry == null)
			throw new IllegalStateException("Already built!");
		ResearchAddendum re = entry;
		entry = null;
		return re;
	}
}