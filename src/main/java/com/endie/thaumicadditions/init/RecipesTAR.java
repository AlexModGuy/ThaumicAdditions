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

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
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
	
	private void init()
	{
		infusing();
		arcaneCrafting();
	}
	
	private void infusing()
	{
		addInfusionRecipe("mithrillium_ingot", new ItemStack(ItemsTAR.MITHRILLIUM_INGOT, 2), "TAR_MITHRILLIUM", 5, new ItemStack(ItemsTC.ingots, 1, 1), new AspectList().add(Aspect.CRYSTAL, 30).add(Aspect.ENERGY, 15).add(Aspect.ELDRITCH, 10).add(Aspect.METAL, 30).add(Aspect.MAGIC, 10), new ItemStack(ItemsTC.amber), new ItemStack(ItemsTC.alumentum), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.amber), new ItemStack(ItemsTC.alumentum), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.salisMundus));
		addInfusionRecipe("adaminite_ingot", new ItemStack(ItemsTAR.ADAMINITE_INGOT, 1), "TAR_ADAMINITE", 10, new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), new AspectList().add(Aspect.LIFE, 45).add(Aspect.ALCHEMY, 30).add(Aspect.EXCHANGE, 40).add(Aspect.METAL, 40).add(Aspect.SOUL, 120).add(Aspect.MAGIC, 40), new ItemStack(Items.NETHER_STAR), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.primordialPearl), new ItemStack(Items.NETHER_STAR));
		addInfusionRecipe("mithminite_ingot", new ItemStack(ItemsTAR.MITHMINITE_INGOT, 3), "TAR_MITHMINITE", 8, new ItemStack(ItemsTAR.ADAMINITE_INGOT), new AspectList().add(KnowledgeTAR.GODLIKE, 10).add(Aspect.METAL, 60).add(Aspect.LIFE, 90).add(Aspect.MAGIC, 120), new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), new ItemStack(ItemsTC.quicksilver));
	}
	
	private void arcaneCrafting()
	{
		addShapedArcaneRecipe("mithrillium_smelter", "TAR_MITHRILLIUM_SMELTER", 1000, new AspectList().add(Aspect.FIRE, 6).add(Aspect.WATER, 2), new ItemStack(BlocksTAR.MITHRILLIUM_SMELTER), "bsb", "mcm", "mmm", 'b', new ItemStack(ItemsTC.plate, 1, 0), 's', BlocksTC.smelterVoid, 'm', ItemsTAR.MITHRILLIUM_PLATE, 'c', BlocksTC.metalAlchemicalAdvanced);
		addShapedArcaneRecipe("adaminite_smelter", "TAR_ADAMINITE_SMELTER", 1200, new AspectList().add(Aspect.FIRE, 12).add(Aspect.WATER, 6), new ItemStack(BlocksTAR.ADAMINITE_SMELTER), "bsb", "mcm", "mmm", 'b', new ItemStack(ItemsTC.plate, 1, 0), 's', BlocksTAR.MITHRILLIUM_SMELTER, 'm', ItemsTAR.ADAMINITE_PLATE, 'c', BlocksTC.metalAlchemicalAdvanced);
		addShapedArcaneRecipe("brass_jar", "TAR_BRASS_JAR", 7, new AspectList(), new ItemStack(BlocksTAR.BRASS_JAR), "gpg", "gjg", "ggg", 'g', "paneGlass", 'p', new ItemStack(ItemsTC.plate, 1, 0), 'j', BlocksTC.jarNormal);
		addShapedArcaneRecipe("thaumium_jar", "TAR_THAUMIUM_JAR", 15, new AspectList().add(Aspect.WATER, 2), new ItemStack(BlocksTAR.THAUMIUM_JAR), "gpg", "gjg", "ggg", 'g', "paneGlass", 'p', new ItemStack(ItemsTC.plate, 1, 2), 'j', BlocksTAR.BRASS_JAR);
		addShapedArcaneRecipe("eldritch_jar", "TAR_ELDRITCH_JAR", 150, new AspectList().add(Aspect.WATER, 6), new ItemStack(BlocksTAR.ELDRITCH_JAR), "gpg", "gjg", "ggg", 'g', "paneGlass", 'p', new ItemStack(ItemsTC.plate, 1, 3), 'j', BlocksTAR.THAUMIUM_JAR);
		addShapedArcaneRecipe("mithrillium_jar", "TAR_MITHRILLIUM_JAR", 750, new AspectList().add(Aspect.WATER, 12), new ItemStack(BlocksTAR.MITHRILLIUM_JAR), "gpg", "gjg", "ggg", 'g', "paneGlass", 'p', new ItemStack(ItemsTAR.MITHRILLIUM_PLATE), 'j', BlocksTAR.ELDRITCH_JAR);
		addShapedArcaneRecipe("adaminite_jar", "TAR_ADAMINITE_JAR@2", 1000, new AspectList().add(Aspect.WATER, 24), new ItemStack(BlocksTAR.ADAMINITE_JAR), "gpg", "gjg", "ggg", 'g', "paneGlass", 'p', new ItemStack(ItemsTAR.ADAMINITE_PLATE), 'j', BlocksTAR.MITHRILLIUM_JAR);
	}
	
	private static void addInfusionRecipe(String path, Object output, String research, int instability, Object catalyst, AspectList aspects, Object... inputs)
	{
		ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, path), new InfusionRecipe(research, output, instability, aspects, catalyst, inputs));
	}
	
	static ResourceLocation defaultGroup = new ResourceLocation("");
	
	private static void addShapedArcaneRecipe(String path, String res, int vis, AspectList crystals, ItemStack result, Object... recipe)
	{
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, path), new ShapedArcaneRecipe(defaultGroup, res, vis, crystals, result, recipe));
	}
	
	private static void addShapedArcaneRecipe(String path, String res, int vis, AspectList crystals, Item result, Object... recipe)
	{
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, path), new ShapedArcaneRecipe(defaultGroup, res, vis, crystals, result, recipe));
	}
	
	private static void addShapedArcaneRecipe(String path, String res, int vis, AspectList crystals, Block result, Object... recipe)
	{
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, path), new ShapedArcaneRecipe(defaultGroup, res, vis, crystals, result, recipe));
	}
	
	@Override
	public void crafting()
	{
		shaped(BlocksTAR.CRAFTING_FURNACE, "c", "g", "f", 'c', Blocks.CRAFTING_TABLE, 'g', "gearIron", 'f', Blocks.FURNACE);
		shapeless(ItemsTAR.ADAMINITE_INGOT, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET, ItemsTAR.ADAMINITE_NUGGET);
		shapeless(ItemsTAR.MITHRILLIUM_INGOT, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET, ItemsTAR.MITHRILLIUM_NUGGET);
		shapeless(new ItemStack(ItemsTAR.ADAMINITE_NUGGET, 8), ItemsTAR.ADAMINITE_INGOT);
		shapeless(new ItemStack(ItemsTAR.MITHRILLIUM_NUGGET, 9), ItemsTAR.MITHRILLIUM_INGOT);
		shaped(new ItemStack(ItemsTAR.MITHRILLIUM_PLATE, 3), "ppp", 'p', ItemsTAR.MITHRILLIUM_INGOT);
		shaped(new ItemStack(ItemsTAR.ADAMINITE_PLATE, 3), "ppp", 'p', ItemsTAR.ADAMINITE_INGOT);
		shaped(new ItemStack(ItemsTAR.MITHMINITE_PLATE, 3), "ppp", 'p', ItemsTAR.MITHMINITE_INGOT);
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