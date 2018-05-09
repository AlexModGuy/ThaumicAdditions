package com.endie.thaumicadditions.init;

import java.lang.reflect.Method;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.TAReconstructed;
import com.endie.thaumicadditions.api.ResearchAddendumBuilder;
import com.endie.thaumicadditions.api.ResearchEntryBuilder;
import com.endie.thaumicadditions.api.ResearchStageBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchEntry.EnumResearchMeta;
import thaumcraft.api.research.ResearchStage.Knowledge;
import thaumcraft.common.lib.research.ResearchManager;

public class KnowledgeTAR
{
	private static Method addResearchToCategory = null;
	
	public static void init()
	{
		new REB().setBaseInfo("TAR_THAUMADDS", "thaumadds", 0, 0, new ResourceLocation(InfoTAR.MOD_ID, "textures/gui/thaumonomicon_icon.png")).setMeta(EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage.thaumadds.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage.thaumadds.2").build()).setParents("FIRSTSTEPS").buildAndRegister();
		
		new REB().setBaseInfo("TAR_MITHRILLIUM", "mithrillium", 1, -2, new ItemStack(ItemsTAR.MITHRILLIUM_INGOT)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.mithrillium.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 2), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 4)).build(), new RSB().setText("research_stage.mithrillium.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHRILLIUM_NUGGET, InfoTAR.MOD_ID + ":mithrillium_ingot")).build()).setParents("TAR_THAUMADDS", "INFUSION").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE", "adaminite", 2, -4, new ItemStack(ItemsTAR.ADAMINITE_INGOT)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage.adaminite.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 4)).setWarp(4).build(), new RSB().setText("research_stage.adaminite.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.ADAMINITE_NUGGET, InfoTAR.MOD_ID + ":adaminite_ingot")).build()).setParents("TAR_MITHRILLIUM", "INFUSION").buildAndRegister();
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