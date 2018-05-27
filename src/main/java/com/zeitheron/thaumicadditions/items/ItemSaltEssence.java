package com.zeitheron.thaumicadditions.items;

import java.util.ArrayList;
import java.util.List;

import com.zeitheron.thaumicadditions.api.AspectUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

public class ItemSaltEssence extends Item implements IEssentiaContainerItem
{
	public ItemSaltEssence()
	{
		setUnlocalizedName("salt_essence");
		setMaxStackSize(64);
		setHasSubtypes(true);
	}
	
	public int getItemColor(ItemStack stack, int layer)
	{
		if(layer == 0)
		{
			AspectList al = getAspects(stack);
			return AspectUtil.getColor(al, true);
		}
		
		return 0xFFFFFF;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		List<String> taken = new ArrayList<>();
		if(isInCreativeTab(tab))
			for(Aspect tag : Aspect.aspects.values())
			{
				if(taken.contains(tag.getTag()))
					continue;
				taken.add(tag.getTag());
				ItemStack i = new ItemStack(this);
				setAspects(i, new AspectList().add(tag, 1));
				items.add(i);
			}
	}
	
	@Override
	public AspectList getAspects(ItemStack itemstack)
	{
		if(itemstack.hasTagCompound())
		{
			AspectList aspects = new AspectList();
			aspects.readFromNBT(itemstack.getTagCompound());
			return aspects.size() > 0 ? aspects : null;
		}
		return null;
	}
	
	@Override
	public void setAspects(ItemStack itemstack, AspectList aspects)
	{
		if(!itemstack.hasTagCompound())
			itemstack.setTagCompound(new NBTTagCompound());
		aspects.writeToNBT(itemstack.getTagCompound());
		itemstack.getTagCompound().removeTag("Color");
	}
	
	@Override
	public boolean ignoreContainedAspects()
	{
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!world.isRemote && !stack.hasTagCompound())
		{
			Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
			this.setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], 1));
		}
		super.onUpdate(stack, world, entity, par4, par5);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote && !stack.hasTagCompound())
		{
			Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
			this.setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], 1));
		}
	}
}