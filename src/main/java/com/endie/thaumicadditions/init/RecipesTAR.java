package com.endie.thaumicadditions.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.endie.thaumicadditions.InfoTAR;
import com.pengu.hammercore.recipeAPI.helper.RecipeRegistry;
import com.pengu.hammercore.recipeAPI.helper.RegisterRecipes;
import com.pengu.hammercore.utils.OnetimeCaller;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;

@RegisterRecipes(modid = InfoTAR.MOD_ID)
public class RecipesTAR extends RecipeRegistry
{
	public static final Map<Item, List<ResourceLocation>> FAKE_RECIPE_MAP = new HashMap<>();
	
	public static OnetimeCaller init;
	private static RecipesTAR instance;
	{
		instance = this;
		init = new OnetimeCaller(this::init);
	}
	
	public void init()
	{
		addInfuser();
	}
	
	public void addInfuser()
	{
		ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, "mithrillium_ingot"), new InfusionRecipe("TAR_MITHRILLIUM", new ItemStack(ItemsTAR.MITHRILLIUM_INGOT, 2), 5, new AspectList().add(Aspect.CRYSTAL, 30).add(Aspect.ENERGY, 15).add(Aspect.ELDRITCH, 10).add(Aspect.METAL, 60).add(Aspect.MAGIC, 10), new ItemStack(ItemsTC.ingots, 1, 1), new ItemStack(ItemsTC.amber), new ItemStack(ItemsTC.alumentum), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.amber), new ItemStack(ItemsTC.alumentum), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.salisMundus)));
		ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, "adaminite_ingot"), new InfusionRecipe("TAR_ADAMINITE", new ItemStack(ItemsTAR.ADAMINITE_INGOT, 1), 10, new AspectList().add(Aspect.LIFE, 90).add(Aspect.ALCHEMY, 30).add(Aspect.EXCHANGE, 40).add(Aspect.SOUL, 120).add(Aspect.MAGIC, 40), new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), new ItemStack(Items.NETHER_STAR), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.primordialPearl), new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.salisMundus), new ItemStack(Items.NETHER_STAR)));
	}
	
	@Override
	public void crafting()
	{
		shaped(BlocksTAR.CRAFTING_FURNACE, "c", "g", "f", 'c', Blocks.CRAFTING_TABLE, 'g', "gearIron", 'f', Blocks.FURNACE);
		shapeless(ItemsTAR.ADAMINITE_INGOT, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET);
		shapeless(ItemsTAR.MITHRILLIUM_INGOT, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET);
		shapeless(new ItemStack(ItemsTAR.ADAMINITE_NUGGET, 8), ItemsTAR.ADAMINITE_INGOT);
		shapeless(new ItemStack(ItemsTAR.MITHRILLIUM_NUGGET, 9), ItemsTAR.MITHRILLIUM_INGOT);
	}
	
	@Override
	public void smelting()
	{
	}
	
	@Override
	protected void recipe(IRecipe recipe)
	{
		super.recipe(recipe);
		Item it = recipe.getRecipeOutput().getItem();
		List<ResourceLocation> locs = FAKE_RECIPE_MAP.get(it);
		if(locs == null)
			FAKE_RECIPE_MAP.put(it, locs = new ArrayList<>());
		if(!locs.contains(recipe.getRegistryName()))
			locs.add(recipe.getRegistryName());
		ThaumcraftApi.addFakeCraftingRecipe(recipe.getRegistryName(), recipe);
	}
	
	public static String[] getFakeRecipesFor(Item it)
	{
		List<ResourceLocation> locs = FAKE_RECIPE_MAP.get(it);
		if(locs == null)
			FAKE_RECIPE_MAP.put(it, locs = new ArrayList<>());
		return locs.stream().map(l -> l.toString()).collect(Collectors.toList()).toArray(new String[locs.size()]);
	}
	
	public static String[] getFakeRecipes(Item it, String... appends)
	{
		return ArrayUtils.addAll(getFakeRecipesFor(it), appends);
	}
	
	public static String[] getFakeRecipesPre(Item it, String... prepends)
	{
		return ArrayUtils.addAll(prepends, getFakeRecipesFor(it));
	}
}