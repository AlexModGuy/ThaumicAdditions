package com.endie.thaumicadditions.init;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.endie.lib.utils.Joiner;
import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.TAReconstructed;
import com.endie.thaumicadditions.api.ResearchAddendumBuilder;
import com.endie.thaumicadditions.api.ResearchEntryBuilder;
import com.endie.thaumicadditions.api.ResearchStageBuilder;
import com.pengu.hammercore.color.Rainbow;
import com.pengu.hammercore.utils.OnetimeCaller;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchEntry.EnumResearchMeta;
import thaumcraft.api.research.ResearchStage.Knowledge;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ResearchManager;

public class KnowledgeTAR
{
	public static final OnetimeCaller init = new OnetimeCaller(KnowledgeTAR::$init);
	public static final OnetimeCaller insertAspects = new OnetimeCaller(KnowledgeTAR::$insertAspects);
	
	public static final Aspect GODLIKE = new Aspect("godium", 0xFF0000, new Aspect[] { Aspect.MAN, Aspect.DESIRE }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/godium.png"), 1)
	{
		@Override
		public int getColor()
		{
			return Rainbow.doIt(hashCode() * hashCode(), 20000L);
		}
	};
	
	private static void $init()
	{
		new REB().setBaseInfo("TAR_THAUMADDS", "thaumadds", 0, 0, new ResourceLocation(InfoTAR.MOD_ID, "textures/gui/thaumonomicon_icon.png")).setMeta(EnumResearchMeta.HIDDEN, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage.thaumadds.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage.thaumadds.2").build()).setParents("FIRSTSTEPS").buildAndRegister();
		
		new REB().setBaseInfo("TAR_MITHRILLIUM", "mithrillium", 1, -2, new ItemStack(ItemsTAR.MITHRILLIUM_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage.mithrillium.1").setConsumedItems(new ItemStack(Items.IRON_INGOT)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 2), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 4)).build(), new RSB().setText("research_stage.mithrillium.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHRILLIUM_NUGGET, RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHRILLIUM_PLATE, InfoTAR.MOD_ID + ":mithrillium_ingot"))).build()).setParents("TAR_THAUMADDS", "INFUSION").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE", "adaminite", 2, -4, new ItemStack(ItemsTAR.ADAMINITE_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage.adaminite.1").setConsumedItems(new ItemStack(Items.GOLD_INGOT)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 4)).setWarp(4).build(), new RSB().setText("research_stage.adaminite.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.ADAMINITE_NUGGET, RecipesTAR.getFakeRecipesPre(ItemsTAR.ADAMINITE_PLATE, InfoTAR.MOD_ID + ":adaminite_ingot"))).build()).setParents("TAR_MITHRILLIUM", "INFUSION").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHMINITE", "mithminite", 3, -5, new ItemStack(ItemsTAR.MITHMINITE_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage.mithminite.1").setConsumedItems(new ItemStack(Items.GOLD_INGOT)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 2)).setWarp(2).build(), new RSB().setText("research_stage.mithminite.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHMINITE_PLATE, InfoTAR.MOD_ID + ":mithminite_ingot")).build()).setParents("TAR_ADAMINITE").buildAndRegister();
		
		new REB().setBaseInfo("TAR_MITHRILLIUM_SMELTER", "mithrillium_smelter", 3, -1, new ItemStack(BlocksTAR.MITHRILLIUM_SMELTER)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.mithrillium_smelter.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 2), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 4)).build(), new RSB().setText("research_stage.mithrillium_smelter.2").setRecipes(InfoTAR.MOD_ID + ":mithrillium_smelter").build()).setParents("TAR_MITHRILLIUM", "ESSENTIASMELTER").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE_SMELTER", "adaminite_smelter", 3, -3, new ItemStack(BlocksTAR.ADAMINITE_SMELTER)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.adaminite_smelter.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 2), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 4)).build(), new RSB().setText("research_stage.adaminite_smelter.2").setRecipes(InfoTAR.MOD_ID + ":adaminite_smelter").build()).setParents("TAR_ADAMINITE", "TAR_MITHRILLIUM_SMELTER").buildAndRegister();
		
		new REB().setBaseInfo("TAR_BRASS_JAR", "brass_jar", 4, 5, new ItemStack(BlocksTAR.BRASS_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.brass_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.brass_jar.2").setRecipes(InfoTAR.MOD_ID + ":brass_jar").build()).setParents("WARDEDJARS").buildAndRegister();
		new REB().setBaseInfo("TAR_THAUMIUM_JAR", "thaumium_jar", 4, 3, new ItemStack(BlocksTAR.THAUMIUM_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.thaumium_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.thaumium_jar.2").setRecipes(InfoTAR.MOD_ID + ":thaumium_jar").build()).setParents("TAR_BRASS_JAR").buildAndRegister();
		new REB().setBaseInfo("TAR_ELDRITCH_JAR", "eldritch_jar", 4, 1, new ItemStack(BlocksTAR.ELDRITCH_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.eldritch_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.eldritch_jar.2").setRecipes(InfoTAR.MOD_ID + ":eldritch_jar").build()).setParents("TAR_THAUMIUM_JAR").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHRILLIUM_JAR", "mithrillium_jar", 4, -1, new ItemStack(BlocksTAR.MITHRILLIUM_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.mithrillium_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.mithrillium_jar.2").setRecipes(InfoTAR.MOD_ID + ":mithrillium_jar").build()).setParents("TAR_ELDRITCH_JAR", "TAR_MITHRILLIUM").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE_JAR", "adaminite_jar", 4, -3, new ItemStack(BlocksTAR.ADAMINITE_JAR)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.adaminite_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.adaminite_jar.2").setRequiredCraft(new ItemStack(BlocksTAR.ADAMINITE_JAR)).setRecipes(InfoTAR.MOD_ID + ":adaminite_jar").build(), new RSB().setText("research_stage.adaminite_jar.3").setRecipes(InfoTAR.MOD_ID + ":adaminite_jar").build()).setParents("TAR_MITHRILLIUM_JAR", "TAR_ADAMINITE").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHMINITE_JAR", "mithminite_jar", 4, -5, new ItemStack(BlocksTAR.MITHMINITE_JAR)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.mithminite_jar.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage.mithminite_jar.2").setRecipes(InfoTAR.MOD_ID + ":mithminite_jar").build()).setParents("TAR_ADAMINITE_JAR", "TAR_MITHMINITE").buildAndRegister();
	}
	
	private static void $insertAspects()
	{
		appendAspects("ingotAdaminite", new AspectList().add(GODLIKE, 16));
		
		removeAspects(new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), Aspect.TRAP);
		appendAspects("ingotMithrillium", new AspectList().add(GODLIKE, 6));
		
		String prefix = addIfPresent("thaumictinkerer:ichor", new AspectList().add(GODLIKE, 5), "");
		prefix = addIfPresent("thaumictinkerer:ichorium", new AspectList().add(GODLIKE, 8), prefix);
		prefix = addIfPresent("draconicevolution:draconic_ingot", new AspectList().add(GODLIKE, 6), prefix);
		prefix = addIfPresent("draconicevolution:draconic_block", new AspectList().add(GODLIKE, 54), prefix);
		prefix = addIfPresent("draconicevolution:chaos_shard", new AspectList().add(GODLIKE, 16), prefix);
	}
	
	private static void appendAspects(String oreDict, AspectList toAdd)
	{
		List<ItemStack> ores = ThaumcraftApiHelper.getOresWithWildCards(oreDict);
		if(toAdd == null)
			toAdd = new AspectList();
		if(ores != null && ores.size() > 0)
			for(ItemStack ore : ores)
				try
				{
					ItemStack oc = ore.copy();
					oc.setCount(1);
					appendAspects(oc, toAdd);
				} catch(Exception oc)
				{
				}
	}
	
	private static void appendAspects(ItemStack stack, AspectList toAdd)
	{
		toAdd = toAdd.copy();
		
		// Finds item's aspects, and if there are any, adds them to appended
		// aspects
		{
			AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
			if(al != null)
				toAdd = toAdd.add(al);
		}
		
		CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
	}
	
	private static void removeAspects(ItemStack stack, Aspect... aspects)
	{
		AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
		if(al != null)
		{
			for(Aspect a : aspects)
				al.remove(a);
			CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), al);
		}
	}
	
	private static String addIfPresent(String item, AspectList al, String prefix)
	{
		Item it = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(item));
		if(it != null)
		{
			List<String> fullAspectList = Arrays.stream(al.getAspectsSortedByAmount()).map(a -> al.getAmount(a) + "x " + a.getName()).collect(Collectors.toList());
			TAReconstructed.LOG.info("I " + prefix + "found " + item + " and I added some aspects to it! " + Joiner.on(", ").join(fullAspectList));
			if(prefix.isEmpty())
				prefix = "also ";
			appendAspects(new ItemStack(it), al);
		}
		return prefix;
	}
	
	private static class RAB extends ResearchAddendumBuilder
	{
	}
	
	private static class RSB extends ResearchStageBuilder
	{
	}
	
	private static class REB extends ResearchEntryBuilder
	{
		public ResearchEntryBuilder setBaseInfo(String key, String name, int x, int y, Object... icons)
		{
			return super.setBaseInfo(key, "THAUMADDITIONS", "research_name." + InfoTAR.MOD_ID + ":" + name, x, y, icons);
		}
	}
	
	private static Method addResearchToCategory = null;
	
	public static void addResearchToCategory(ResearchEntry ri)
	{
		if(addResearchToCategory == null)
			try
			{
				addResearchToCategory = ResearchManager.class.getDeclaredMethod("addResearchToCategory", ResearchEntry.class);
				addResearchToCategory.setAccessible(true);
			} catch(NoSuchMethodException | SecurityException e)
			{
				TAReconstructed.LOG.error(e);
			}
		
		try
		{
			addResearchToCategory.invoke(null, ri);
		} catch(Throwable e)
		{
			TAReconstructed.LOG.error(e);
		}
	}
}