package com.zeitheron.thaumicadditions.tiles;

import net.minecraft.nbt.NBTTagCompound;

public class TileSmelterImpl extends TileAbstractSmelter
{
	public float efficiency;
	public int speed;
	public int maxVis;
	
	public TileSmelterImpl(float efficiency, int speed, int maxVis)
	{
		this.efficiency = efficiency;
		this.speed = speed;
		this.maxVis = maxVis;
	}
	
	public TileSmelterImpl()
	{
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		super.writeNBT(nbt);
		nbt.setInteger("Speed", this.speed);
		nbt.setFloat("Efficiency", this.efficiency);
		nbt.setInteger("MaxVis", this.maxVis);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		super.readNBT(nbt);
		this.speed = nbt.getInteger("Speed");
		this.efficiency = nbt.getFloat("Efficiency");
		this.maxVis = nbt.getInteger("MaxVis");
	}
	
	@Override
	public float getEfficiency()
	{
		return efficiency;
	}
	
	@Override
	public int getSpeed()
	{
		return speed;
	}
	
	@Override
	public int getCapacity()
	{
		return maxVis;
	}
}