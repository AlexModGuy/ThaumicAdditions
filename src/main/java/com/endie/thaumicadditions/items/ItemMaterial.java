package com.endie.thaumicadditions.items;

import com.pengu.hammercore.utils.iRegisterListener;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMaterial extends Item implements iRegisterListener
{
	private final String OD;
	
	public ItemMaterial(String name)
	{
		this(name, null);
	}
	
	public ItemMaterial(String name, String od)
	{
		setUnlocalizedName(name);
		this.OD = od;
	}
	
	@Override
	public void onRegistered()
	{
		if(OD != null)
			OreDictionary.registerOre(OD, this);
	}
}