package com.endie.thaumicadditions.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.essentia.TileJarFillable;

public abstract class TileAbstractJarFillable extends TileJarFillable
{
	public abstract int getCapacity();
	
	@Override
	public int addToContainer(Aspect tt, int am)
	{
		if(am == 0)
			return am;
		if(this.amount < getCapacity() && tt == aspect || amount == 0)
		{
			aspect = tt;
			int added = Math.min(am, getCapacity() - this.amount);
			amount += added;
			am -= added;
		}
		syncTile(false);
		markDirty();
		return am;
	}
	
	@Override
	public int getSuctionAmount(EnumFacing loc)
	{
		if(this.amount < getCapacity())
		{
			if(this.aspectFilter != null)
				return 64;
			return 32;
		}
		return 0;
	}
	
	int absCount;
	
	@Override
	public void update()
	{
		if(!world.isRemote && ++absCount % 5 == 0 && amount < getCapacity())
			fillAbstractJar();
	}
	
	void fillAbstractJar()
	{
		TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos, EnumFacing.UP);
		if(te != null)
		{
			IEssentiaTransport ic = (IEssentiaTransport) te;
			if(!ic.canOutputTo(EnumFacing.DOWN))
			{
				return;
			}
			Aspect ta = null;
			if(this.aspectFilter != null)
			{
				ta = this.aspectFilter;
			} else if(this.aspect != null && this.amount > 0)
			{
				ta = this.aspect;
			} else if(ic.getEssentiaAmount(EnumFacing.DOWN) > 0 && ic.getSuctionAmount(EnumFacing.DOWN) < this.getSuctionAmount(EnumFacing.UP) && this.getSuctionAmount(EnumFacing.UP) >= ic.getMinimumSuction())
			{
				ta = ic.getEssentiaType(EnumFacing.DOWN);
			}
			if(ta != null && ic.getSuctionAmount(EnumFacing.DOWN) < this.getSuctionAmount(EnumFacing.UP))
			{
				this.addToContainer(ta, ic.takeEssentia(ta, 1, EnumFacing.DOWN));
			}
		}
	}
}