package com.zeitheron.thaumicadditions.init;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.endie.lib.utils.Joiner;
import com.pengu.hammercore.annotations.MCFBus;
import com.pengu.hammercore.color.Rainbow;
import com.pengu.hammercore.utils.OnetimeCaller;
import com.zeitheron.thaumicadditions.InfoTAR;
import com.zeitheron.thaumicadditions.TAReconstructed;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.api.CustomColoredAspect;
import com.zeitheron.thaumicadditions.api.ResearchAddendumBuilder;
import com.zeitheron.thaumicadditions.api.ResearchEntryBuilder;
import com.zeitheron.thaumicadditions.api.ResearchStageBuilder;
import com.zeitheron.thaumicadditions.tiles.TileAuraCharger;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchEntry.EnumResearchMeta;
import thaumcraft.api.research.ResearchStage.Knowledge;
import thaumcraft.common.lib.CommandThaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ResearchManager;

@MCFBus
public class KnowledgeTAR
{
	public static final OnetimeCaller init = new OnetimeCaller(KnowledgeTAR::$init);
	public static final OnetimeCaller insertAspects = new OnetimeCaller(KnowledgeTAR::$insertAspects);
	public static final OnetimeCaller clInit = new OnetimeCaller(KnowledgeTAR::$);
	
	public static final Aspect FLUCTUS = new Aspect("fluctus", 0xA8A8A8, new Aspect[] { Aspect.MOTION, Aspect.WATER }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/fluctus.png"), 1);
	public static final Aspect SONUS = new Aspect("sonus", 0xFFAA00, new Aspect[] { FLUCTUS, Aspect.AIR }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/sonus.png"), 1);
	public static final Aspect EXITIUM = new Aspect("exitium", 0x777777, new Aspect[] { Aspect.ENTROPY, Aspect.TOOL }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/exitium.png"), 1);
	public static final Aspect CAELES = new CustomColoredAspect("caeles", 0xFF0000, new Aspect[] { Aspect.MAN, Aspect.DESIRE }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/caeles.png"), 1, a -> Rainbow.doIt(a.hashCode() * a.hashCode(), 20000L));
	public static final Aspect DRACO = new Aspect("draco", 0x00BCFF, new Aspect[] { CAELES, Aspect.LIFE }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/draco.png"), 1);
	public static final Aspect INFERNUM = new Aspect("infernum", 0xFF2314, new Aspect[] { Aspect.FIRE, Aspect.DEATH }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/infernum.png"), 1);
	public static final Aspect VENTUS = new Aspect("ventus", 0xFCFCCF, new Aspect[] { Aspect.AIR, Aspect.FLIGHT }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/ventus.png"), 1);
	public static final Aspect VISUM = new Aspect("visum", 0x45CF35, new Aspect[] { Aspect.SENSES, Aspect.CRYSTAL }, new ResourceLocation(InfoTAR.MOD_ID, "textures/aspects/visum.png"), 1);
	
	@SubscribeEvent
	public void commandEvent(CommandEvent ce)
	{
		if(ce.getCommand() instanceof CommandThaumcraft && ce.getParameters().length > 0 && ce.getParameters()[0].equalsIgnoreCase("reload"))
		{
			new Thread(() ->
			{
				while(TAReconstructed.RES_CAT.research.containsKey("TAR_THAUMADDS"))
					try
					{
						Thread.sleep(10L);
					} catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				
				$init();
			}).start();
		}
	}
	
	private static void $init()
	{
		new REB().setBaseInfo("TAR_THAUMADDS", "thaumadds", 0, 0, new ResourceLocation(InfoTAR.MOD_ID, "textures/gui/thaumonomicon_icon.png")).setMeta(EnumResearchMeta.HIDDEN, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":thaumadds.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":thaumadds.2").build()).setParents("FIRSTSTEPS").buildAndRegister();
		
		new REB().setBaseInfo("TAR_ESSENCE_SALT", "essence_salt", -1, -1, AspectUtil.salt(Aspect.AURA)).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":essence_salt.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).setConsumedItems(new ItemStack(ItemsTC.crystalEssence)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":essence_salt.2").setConsumedItems(new ItemStack(ItemsTAR.SALT_ESSENCE)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":essence_salt.3").build()).setParents("TAR_THAUMADDS").buildAndRegister();
		new REB().setBaseInfo("TAR_CRYSTAL_CRUSHER", "crystal_crusher", -1, -3, new ItemStack(BlocksTAR.CRYSTAL_CRUSHER)).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_crusher.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).setConsumedItems(new ItemStack(ItemsTC.mechanismSimple)).setRequiredCraft(new ItemStack(ItemsTC.mechanismComplex)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_crusher.2").setRecipes(InfoTAR.MOD_ID + ":crystal_crusher").build()).setParents("TAR_ESSENCE_SALT", "METALLURGY@2", "BASEARTIFICE").setRewardItems(new ItemStack(ItemsTC.plate, 3, 0), new ItemStack(ItemsTC.plate, 2, 1)).buildAndRegister();
		new REB().setBaseInfo("TAR_AURA_DISPERSER", "aura_disperser", 0, -2, new ItemStack(BlocksTAR.AURA_DISPERSER)).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aura_disperser.1").setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1), new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).setConsumedItems(AspectUtil.salt((Aspect) null)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aura_disperser.2").setRecipes(InfoTAR.MOD_ID + ":aura_disperser").build()).setParents("BASEARTIFICE", "METALLURGY@2", "TAR_ESSENCE_SALT").buildAndRegister();
		
		new REB().setBaseInfo("TAR_CRYSTAL_BORE", "crystal_bore", 3, 0, new ItemStack(BlocksTAR.CRYSTAL_BORE)).setMeta(EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_bore.1").setConsumedItems(new ItemStack(ItemsTC.crystalEssence)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1), new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("GOLEMANCY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_bore.2").setRecipes(InfoTAR.MOD_ID + ":crystal_bore").build()).setParents("TAR_THAUMADDS").buildAndRegister();
		new REB().setBaseInfo("TAR_CRYSTAL_WATER", "crystal_water", -1, 1, FluidUtil.getFilledBucket(new FluidStack(FluidsTAR.CRYSTAL_WATER, Fluid.BUCKET_VOLUME))).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_water.1").setConsumedItems(new ItemStack(ItemsTC.crystalEssence), new ItemStack(Items.WATER_BUCKET)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":crystal_water.2").setRecipes(InfoTAR.MOD_ID + ":crystal_water", InfoTAR.MOD_ID + ":mb.crystal_acceleration").build()).setParents("TAR_THAUMADDS").buildAndRegister();
		new REB().setBaseInfo("TAR_ENCHANTED_GOLDEN_APPLE", "enchanted_golden_apple", -2, 1, new ItemStack(Items.GOLDEN_APPLE, 1, 1)).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":enchanted_golden_apple.1").setConsumedItems(new ItemStack(Items.GOLDEN_APPLE), AspectUtil.crystalEssence(Aspect.DESIRE)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":enchanted_golden_apple.2").setRecipes(InfoTAR.MOD_ID + ":enchanted_golden_apple").build()).setParents("TAR_THAUMADDS").buildAndRegister();
		
		new REB().setBaseInfo("TAR_MITHRILLIUM", "mithrillium", 1, -2, new ItemStack(ItemsTAR.MITHRILLIUM_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium.1").setConsumedItems(new ItemStack(ItemsTC.ingots, 1, 1)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 1), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHRILLIUM_NUGGET, RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHRILLIUM_PLATE, InfoTAR.MOD_ID + ":mithrillium_ingot"))).build()).setParents("TAR_THAUMADDS", "INFUSION").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE", "adaminite", 2, -4, new ItemStack(ItemsTAR.ADAMINITE_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite.1").setRequiredCraft(new ItemStack(ItemsTAR.MITHRILLIUM_INGOT)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 1)).setWarp(1).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.ADAMINITE_NUGGET, RecipesTAR.getFakeRecipesPre(ItemsTAR.ADAMINITE_PLATE, InfoTAR.MOD_ID + ":adaminite_ingot"))).build()).setParents("TAR_MITHRILLIUM").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHMINITE", "mithminite", 3, -6, new ItemStack(ItemsTAR.MITHMINITE_INGOT)).setMeta(EnumResearchMeta.ROUND, EnumResearchMeta.SPIKY).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite.1").setKnow(new Knowledge(EnumKnowledgeType.THEORY, TAReconstructed.RES_CAT, 1)).setRequiredCraft(new ItemStack(ItemsTAR.ADAMINITE_INGOT)).setWarp(2).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite.2").setRecipes(RecipesTAR.getFakeRecipesPre(ItemsTAR.MITHMINITE_PLATE, InfoTAR.MOD_ID + ":mithminite_ingot")).build()).setParents("TAR_ADAMINITE").buildAndRegister();
		
		new REB().setBaseInfo("TAR_ASPECT_COMBINER", "aspect_combiner", 3, -1, new ItemStack(BlocksTAR.ASPECT_COMBINER)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aspect_combiner.1").setRequiredCraft(new ItemStack(BlocksTC.centrifuge), new ItemStack(ItemsTAR.MITHRILLIUM_INGOT)).setConsumedItems(AspectUtil.phial(Aspect.EXCHANGE)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aspect_combiner.2").setRecipes(InfoTAR.MOD_ID + ":aspect_combiner").build()).setParents("CENTRIFUGE", "TAR_MITHRILLIUM").buildAndRegister();
		new REB().setBaseInfo("TAR_AURA_CHARGER", "aura_charger", 3, -3, new ItemStack(BlocksTAR.AURA_CHARGER)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aura_charger.1").setRequiredCraft(new ItemStack(BlocksTAR.ASPECT_COMBINER), new ItemStack(ItemsTAR.ADAMINITE_NUGGET)).setConsumedItems(AspectUtil.phial(TileAuraCharger.AURA)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("AUROMANCY"), 1), new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1), new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":aura_charger.2").setRecipes(InfoTAR.MOD_ID + ":aura_charger").build()).setParents("TAR_ASPECT_COMBINER", "TAR_ADAMINITE").buildAndRegister();
		
		new REB().setBaseInfo("TAR_PURIFLOWER", "puriflower", 2, 1, new ItemStack(BlocksTAR.PURIFLOWER)).setMeta(EnumResearchMeta.SPIKY, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":puriflower.1").setConsumedItems(new ItemStack(BlocksTC.vishroom)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":puriflower.2").setRecipes(InfoTAR.MOD_ID + ":puriflower").build()).setParents("TAR_THAUMADDS").buildAndRegister();
		new REB().setBaseInfo("TAR_MOB_SUMMONING", "mob_summoning", 2, -1, new ItemStack(BlocksTAR.ENTITY_SUMMONER)).setMeta(EnumResearchMeta.SPIKY, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mob_summoning.1").setConsumedItems(AspectUtil.phial(Aspect.LIFE), AspectUtil.phial(Aspect.DEATH)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("INFUSION"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mob_summoning.2").setRequiredCraft(new ItemStack(ItemsTAR.ENTITY_CELL)).setRecipes(InfoTAR.MOD_ID + ":dna_sample").build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mob_summoning.3").setRecipes(InfoTAR.MOD_ID + ":dna_sample", InfoTAR.MOD_ID + ":mob_summoner").build()).setParents("TAR_MITHRILLIUM", "TAR_PURIFLOWER").buildAndRegister();
		
		new REB().setBaseInfo("TAR_MITHRILLIUM_SMELTER", "mithrillium_smelter", 5, -1, new ItemStack(BlocksTAR.MITHRILLIUM_SMELTER)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium_smelter.1").setRequiredCraft(new ItemStack(ItemsTAR.MITHRILLIUM_PLATE)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("ALCHEMY"), 1), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium_smelter.2").setRecipes(InfoTAR.MOD_ID + ":mithrillium_smelter").build()).setParents("TAR_MITHRILLIUM", "ESSENTIASMELTER").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE_SMELTER", "adaminite_smelter", 5, -3, new ItemStack(BlocksTAR.ADAMINITE_SMELTER)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite_smelter.1").setRequiredCraft(new ItemStack(ItemsTAR.ADAMINITE_PLATE)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("ALCHEMY"), 1), new Knowledge(EnumKnowledgeType.OBSERVATION, TAReconstructed.RES_CAT, 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite_smelter.2").setRecipes(InfoTAR.MOD_ID + ":adaminite_smelter").build()).setParents("TAR_ADAMINITE", "TAR_MITHRILLIUM_SMELTER").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHMINITE_SMELTER", "mithminite_smelter", 5, -5, new ItemStack(BlocksTAR.MITHMINITE_SMELTER)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite_smelter.1").setRequiredCraft(new ItemStack(BlocksTAR.ADAMINITE_SMELTER), new ItemStack(ItemsTAR.MITHMINITE_PLATE)).setConsumedItems(new ItemStack(ItemsTC.alumentum), new ItemStack(ItemsTC.crystalEssence)).setKnow(new Knowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite_smelter.2").setRecipes(InfoTAR.MOD_ID + ":mithminite_smelter").build()).setParents("TAR_MITHMINITE", "TAR_ADAMINITE_SMELTER").buildAndRegister();
		
		new REB().setBaseInfo("TAR_BRASS_JAR", "brass_jar", 4, 5, new ItemStack(BlocksTAR.BRASS_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":brass_jar.1").setRequiredCraft(new ItemStack(BlocksTC.jarNormal)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":brass_jar.2").setRecipes(InfoTAR.MOD_ID + ":brass_jar").build()).setParents("WARDEDJARS").buildAndRegister();
		new REB().setBaseInfo("TAR_THAUMIUM_JAR", "thaumium_jar", 4, 3, new ItemStack(BlocksTAR.THAUMIUM_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":thaumium_jar.1").setRequiredCraft(new ItemStack(BlocksTAR.BRASS_JAR)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":thaumium_jar.2").setRecipes(InfoTAR.MOD_ID + ":thaumium_jar").build()).setParents("TAR_BRASS_JAR").buildAndRegister();
		new REB().setBaseInfo("TAR_ELDRITCH_JAR", "eldritch_jar", 4, 1, new ItemStack(BlocksTAR.ELDRITCH_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":eldritch_jar.1").setRequiredCraft(new ItemStack(BlocksTAR.THAUMIUM_JAR)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":eldritch_jar.2").setRecipes(InfoTAR.MOD_ID + ":eldritch_jar").build()).setParents("TAR_THAUMIUM_JAR").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHRILLIUM_JAR", "mithrillium_jar", 4, -1, new ItemStack(BlocksTAR.MITHRILLIUM_JAR)).setMeta(EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium_jar.1").setRequiredCraft(new ItemStack(BlocksTAR.ELDRITCH_JAR), new ItemStack(ItemsTAR.MITHRILLIUM_PLATE)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithrillium_jar.2").setRecipes(InfoTAR.MOD_ID + ":mithrillium_jar").build()).setParents("TAR_ELDRITCH_JAR", "TAR_MITHRILLIUM").buildAndRegister();
		new REB().setBaseInfo("TAR_ADAMINITE_JAR", "adaminite_jar", 4, -3, new ItemStack(BlocksTAR.ADAMINITE_JAR)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite_jar.1").setRequiredCraft(new ItemStack(BlocksTAR.MITHRILLIUM_JAR), new ItemStack(ItemsTAR.ADAMINITE_PLATE)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite_jar.2").setRequiredCraft(new ItemStack(BlocksTAR.ADAMINITE_JAR)).setRecipes(InfoTAR.MOD_ID + ":adaminite_jar").build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":adaminite_jar.3").setRecipes(InfoTAR.MOD_ID + ":adaminite_jar").build()).setParents("TAR_MITHRILLIUM_JAR", "TAR_ADAMINITE").buildAndRegister();
		new REB().setBaseInfo("TAR_MITHMINITE_JAR", "mithminite_jar", 4, -5, new ItemStack(BlocksTAR.MITHMINITE_JAR)).setMeta(EnumResearchMeta.HEX, EnumResearchMeta.HIDDEN).setStages(new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite_jar.1").setRequiredCraft(new ItemStack(BlocksTAR.ADAMINITE_JAR), new ItemStack(ItemsTAR.MITHMINITE_PLATE)).setKnow(new Knowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1)).build(), new RSB().setText("research_stage." + InfoTAR.MOD_ID + ":mithminite_jar.2").setRecipes(InfoTAR.MOD_ID + ":mithminite_jar").build()).setParents("TAR_ADAMINITE_JAR", "TAR_MITHMINITE").buildAndRegister();
	}
	
	private static void $insertAspects()
	{
		appendAspects("ingotAdaminite", new AspectList().add(CAELES, 16));
		appendAspects(new ItemStack(BlocksTC.jarNormal), new AspectList().add(Aspect.VOID, 4).add(Aspect.ALCHEMY, 8));
		
		removeAspects(new ItemStack(ItemsTAR.MITHRILLIUM_INGOT), Aspect.TRAP);
		appendAspects("ingotMithrillium", new AspectList().add(CAELES, 6));
		appendAspects(new ItemStack(Items.EGG), new AspectList().add(Aspect.EXCHANGE, 6));
		
		appendAspects(new ItemStack(BlocksTC.vishroom), new AspectList().add(Aspect.FLUX, 2));
		appendAspects(new ItemStack(Blocks.NOTEBLOCK), new AspectList().add(SONUS, 8));
		appendAspects(new ItemStack(Blocks.JUKEBOX), new AspectList().add(SONUS, 12));
		appendAspects(new ItemStack(BlocksTC.arcaneEar), new AspectList().add(SONUS, 12));
		appendAspects(new ItemStack(BlocksTC.levitator), new AspectList().add(FLUCTUS, 12).add(VENTUS, 20));
		appendAspects(new ItemStack(Items.WATER_BUCKET), new AspectList().add(FLUCTUS, 4));
		appendAspects(new ItemStack(Blocks.CHEST), new AspectList().add(Aspect.VOID, 6));
		appendAspects(new ItemStack(Blocks.TNT), new AspectList().add(EXITIUM, 50));
		appendAspects(new ItemStack(Items.GUNPOWDER), new AspectList().add(EXITIUM, 10));
		appendAspects(new ItemStack(Blocks.DRAGON_EGG), new AspectList().add(DRACO, 100));
		appendAspects(new ItemStack(Items.DRAGON_BREATH), new AspectList().add(DRACO, 25));
		appendAspects(new ItemStack(ItemsTC.goggles), new AspectList().add(VISUM, 20));
		appendAspects(new ItemStack(Items.CARROT), new AspectList().add(VISUM, 10));
		appendAspects(new ItemStack(Blocks.NETHER_BRICK), new AspectList().add(INFERNUM, 15));
		appendAspects(new ItemStack(Blocks.NETHER_BRICK_STAIRS), new AspectList().add(INFERNUM, 10));
		appendAspects(new ItemStack(Blocks.NETHER_BRICK_FENCE), new AspectList().add(INFERNUM, 5));
		appendAspects(new ItemStack(Blocks.PISTON), new AspectList().add(FLUCTUS, 10).add(VENTUS, 10));
		appendAspects(new ItemStack(ItemsTAR.ZEITH_SCALES), new AspectList().add(Aspect.MIND, 15).add(DRACO, 15).add(Aspect.LIFE, 15).add(CAELES, 2).add(Aspect.AURA, 20));
		
		String prefix = addIfPresent("thaumictinkerer:ichor", new AspectList().add(CAELES, 5), "");
		prefix = addIfPresent("thaumictinkerer:ichorium", new AspectList().add(CAELES, 8), prefix);
		prefix = addIfPresent("draconicevolution:draconic_ingot", new AspectList().add(CAELES, 6).add(DRACO, 18), prefix);
		prefix = addIfPresent("draconicevolution:dragon_heart", new AspectList().add(CAELES, 20).add(DRACO, 100), prefix);
		prefix = addIfPresent("draconicevolution:draconic_block", new AspectList().add(CAELES, 54).add(DRACO, 18 * 9), prefix);
		prefix = addIfPresent("draconicevolution:chaos_shard", new AspectList().add(CAELES, 16).add(EXITIUM, 96), prefix);
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
	
	private static void $()
	{
		
	}
}