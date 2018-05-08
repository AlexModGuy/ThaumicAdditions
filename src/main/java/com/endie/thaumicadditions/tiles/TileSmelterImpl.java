package com.endie.thaumicadditions.tiles;

import net.minecraft.nbt.NBTTagCompound;

public class TileSmelterImpl extends TileAbstractSmelter
{
	public float efficiency;
	public int speed;
	
	public TileSmelterImpl(float efficiency, int speed)
	{
		this.efficiency = efficiency;
		this.speed = speed;
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
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		super.readNBT(nbt);
		this.speed = nbt.getInteger("Speed");
		this.efficiency = nbt.getFloat("Efficiency");
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
}