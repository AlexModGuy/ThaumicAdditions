package com.endie.thaumicadditions.tiles.jars;

import com.endie.thaumicadditions.tiles.TileAbstractJarFillable;

import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;

public class TileMithminiteJar extends TileAbstractJarFillable
{
	@Override
	public int getCapacity()
	{
		return 4000;
	}
	
	@Override
	public int addToContainer(Aspect tt, int am)
	{
		boolean up = this.amount < getCapacity();
		if(am == 0)
			return am;
		if(tt == this.aspect || this.amount == 0)
		{
			this.aspect = tt;
			this.amount += am;
			am = 0;
			if(amount > getCapacity())
			{
				if(this.world.rand.nextInt(250 - amount + getCapacity()) == 0)
					AuraHelper.polluteAura(getWorld(), getPos(), 1F, true);
				amount = getCapacity();
			}
		}
		if(up)
		{
			syncTile(false);
			markDirty();
		}
		return am;
	}
	
	@Override
	public int getMinimumSuction()
	{
		return aspectFilter != null ? 48 : 32;
	}
	
	@Override
	public int getSuctionAmount(EnumFacing loc)
	{
		if(aspectFilter != null && amount < 250)
			return 48;
		return 32;
	}
}