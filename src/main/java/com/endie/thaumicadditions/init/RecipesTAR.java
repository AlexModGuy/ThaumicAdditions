package com.endie.thaumicadditions.init;

import com.endie.thaumicadditions.InfoTAR;
import com.pengu.hammercore.recipeAPI.helper.RecipeRegistry;
import com.pengu.hammercore.recipeAPI.helper.RegisterRecipes;
import com.pengu.hammercore.utils.OnetimeCaller;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;

@RegisterRecipes(modid = InfoTAR.MOD_ID)
public class RecipesTAR extends RecipeRegistry
{
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
		ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(InfoTAR.MOD_ID, "adaminite_ingot"), new InfusionRecipe("TAR_ADAMINITE", new ItemStack(ItemsTAR.ADAMINITE_INGOT, 1), 10, new AspectList().add(Aspect.LIFE, 90).add(Aspect.ALCHEMY, 30).add(Aspect.EXCHANGE, 40).add(Aspect.SOUL, 120).add(Aspect.MAGIC, 40), new ItemStack(ItemsTAR.MITHMINITE_INGOT), new ItemStack(Items.NETHER_STAR), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.primordialPearl), new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.salisMundus)));
	}
	
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