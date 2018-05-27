package com.zeitheron.thaumicadditions.items;

import com.pengu.hammercore.utils.iRegisterListener;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMaterial extends Item implements iRegisterListener
{
	private final String[] OD;
	
	public ItemMaterial(String name)
	{
		this(name, new String[0]);
	}
	
	public ItemMaterial(String name, String... ods)
	{
		setUnlocalizedName(name);
		this.OD = ods;
	}
	
	@Override
	public void onRegistered()
	{
		if(OD != null)
			for(String o : OD)
				OreDictionary.registerOre(o, this);
	}
}