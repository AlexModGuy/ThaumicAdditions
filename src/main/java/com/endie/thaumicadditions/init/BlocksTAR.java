package com.endie.thaumicadditions.init;

import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar;
import com.endie.thaumicadditions.blocks.BlockAbstractSmelter;
import com.endie.thaumicadditions.blocks.BlockAspectCombiner;
import com.endie.thaumicadditions.blocks.BlockAuraCharger;
import com.endie.thaumicadditions.blocks.BlockAuraDisperser;
import com.endie.thaumicadditions.blocks.BlockCraftingFurnace;
import com.endie.thaumicadditions.blocks.BlockCrystalBore;
import com.endie.thaumicadditions.blocks.BlockCrystalCrusher;
import com.endie.thaumicadditions.blocks.BlockCrystalWater;
import com.endie.thaumicadditions.blocks.BlockEntitySummoner;
import com.endie.thaumicadditions.blocks.BlockPurishroom;
import com.endie.thaumicadditions.tiles.jars.TileAdaminiteJar;
import com.endie.thaumicadditions.tiles.jars.TileBrassJar;
import com.endie.thaumicadditions.tiles.jars.TileEldritchJar;
import com.endie.thaumicadditions.tiles.jars.TileMithminiteJar;
import com.endie.thaumicadditions.tiles.jars.TileMithrilliumJar;
import com.endie.thaumicadditions.tiles.jars.TileThaumiumJar;

public class BlocksTAR
{
	public static final BlockCraftingFurnace CRAFTING_FURNACE = new BlockCraftingFurnace();
	public static final BlockAbstractSmelter MITHRILLIUM_SMELTER = new BlockAbstractSmelter("mithrillium_smelter", 1F, 20, 1000);
	public static final BlockAbstractSmelter ADAMINITE_SMELTER = new BlockAbstractSmelter("adaminite_smelter", 1.2F, 15, 2000);
	public static final BlockAbstractSmelter MITHMINITE_SMELTER = new BlockAbstractSmelter("mithminite_smelter", 1.5F, 10, 4000);
	
	public static final BlockCrystalWater CRYSTAL_WATER = new BlockCrystalWater();
	public static final BlockAuraDisperser AURA_DISPERSER = new BlockAuraDisperser();
	public static final BlockAspectCombiner ASPECT_COMBINER = new BlockAspectCombiner();
	public static final BlockAuraCharger AURA_CHARGER = new BlockAuraCharger();
	public static final BlockCrystalCrusher CRYSTAL_CRUSHER = new BlockCrystalCrusher();
	public static final BlockCrystalBore CRYSTAL_BORE = new BlockCrystalBore();
	public static final BlockEntitySummoner ENTITY_SUMMONER = new BlockEntitySummoner();
	public static final BlockPurishroom PURIFLOWER = new BlockPurishroom();
	
	public static final BlockAbstractEssentiaJar<TileBrassJar> BRASS_JAR = new BlockAbstractEssentiaJar<>(TileBrassJar.class, 275, "jar_brass");
	public static final BlockAbstractEssentiaJar<TileThaumiumJar> THAUMIUM_JAR = new BlockAbstractEssentiaJar<>(TileThaumiumJar.class, 350, "jar_thaumium");
	public static final BlockAbstractEssentiaJar<TileEldritchJar> ELDRITCH_JAR = new BlockAbstractEssentiaJar<>(TileEldritchJar.class, 500, "jar_eldritch");
	public static final BlockAbstractEssentiaJar<TileMithrilliumJar> MITHRILLIUM_JAR = new BlockAbstractEssentiaJar<>(TileMithrilliumJar.class, 1000, "jar_mithrillium");
	public static final BlockAbstractEssentiaJar<TileAdaminiteJar> ADAMINITE_JAR = new BlockAbstractEssentiaJar<>(TileAdaminiteJar.class, 2000, "jar_adaminite");
	public static final BlockAbstractEssentiaJar<TileMithminiteJar> MITHMINITE_JAR = new BlockAbstractEssentiaJar<>(TileMithminiteJar.class, 4000, "jar_mithminite");
}