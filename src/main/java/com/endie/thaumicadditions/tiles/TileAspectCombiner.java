package com.endie.thaumicadditions.tiles;

import com.pengu.hammercore.common.utils.SoundUtil;
import com.pengu.hammercore.tile.TileSyncableTickable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.BlockStateUtils;

public class TileAspectCombiner extends TileSyncableTickable implements IEssentiaTransport, IAspectContainer
{
	public Aspect inA, inB, output;
	public int craftingTime = 0;
	public EnumFacing front;
	
	public int getMaxCraftTime()
	{
		return 200;
	}
	
	@Override
	public void tick()
	{
		front = BlockStateUtils.getFacing(world.getBlockState(pos));
		
		Aspect cout = getOutput(inA, inB);
		if(inA != null && inB != null && output == null && cout != null)
		{
			int max = getMaxCraftTime();
			if(craftingTime < max)
			{
				++craftingTime;
				if(craftingTime == 1)
					sync();
				if(atTickRate(10))
					SoundUtil.playSoundEffect(world, SoundsTC.pump.getRegistryName().toString(), pos, 1F, 1F, SoundCategory.BLOCKS);
			}
			if(craftingTime >= max)
			{
				output = cout;
				inA = null;
				inB = null;
				craftingTime = 0;
			}
		}
		
		if(world.isRemote)
			return;
		
		if(atTickRate(20))
			sendChangesToNearby();
		
		EnumFacing lf = front.rotateY();
		EnumFacing rf = front.rotateYCCW();
		
		IEssentiaTransport l = (IEssentiaTransport) ThaumcraftApiHelper.getConnectableTile(world, pos, lf);
		IEssentiaTransport r = (IEssentiaTransport) ThaumcraftApiHelper.getConnectableTile(world, pos, rf);
		
		if(l != null && l.canOutputTo(rf))
		{
			Aspect lasp = l.getEssentiaType(rf);
			if(canAccept(lasp))
			{
				if(inA == null)
				{
					if(l.takeEssentia(lasp, 1, rf) == 1)
					{
						inA = lasp;
						sendChangesToNearby();
					}
				}
			}
		}
		
		if(r != null && r.canOutputTo(lf))
		{
			Aspect lasp = r.getEssentiaType(lf);
			if(canAccept(lasp))
			{
				if(inB == null)
				{
					if(r.takeEssentia(lasp, 1, lf) == 1)
					{
						inB = lasp;
						sendChangesToNearby();
					}
				}
			}
		}
		
		if(output != null)
		{
			IEssentiaTransport u = (IEssentiaTransport) ThaumcraftApiHelper.getConnectableTile(world, pos, EnumFacing.UP);
			if(u != null && u.canInputFrom(EnumFacing.DOWN) && u.addEssentia(output, 1, EnumFacing.DOWN) == 1)
			{
				output = null;
				sendChangesToNearby();
			}
		}
	}
	
	public void insert(IEssentiaTransport l, EnumFacing rf)
	{
		
	}
	
	public boolean canAccept(Aspect type)
	{
		if(inA == null && inB == null)
			return isUsable(type);
		if(inA == null && inB != null)
			return getOutput(inB, type) != null;
		if(inA != null && inB == null)
			return getOutput(inA, type) != null;
		return false;
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		if(output != null)
			nbt.setString("Output", output.getTag());
		if(inA != null)
			nbt.setString("InA", inA.getTag());
		if(inB != null)
			nbt.setString("InB", inB.getTag());
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		output = nbt.hasKey("Output") ? Aspect.getAspect(nbt.getString("Output")) : null;
		inA = nbt.hasKey("InA") ? Aspect.getAspect(nbt.getString("InA")) : null;
		inB = nbt.hasKey("InB") ? Aspect.getAspect(nbt.getString("InB")) : null;
	}
	
	public static Aspect getOutput(Aspect a, Aspect b)
	{
		if(a == null || b == null)
			return null;
		return Aspect.aspects.values().stream().filter(s -> !s.isPrimal() && s.getComponents() != null && s.getComponents().length == 2 && contains(s.getComponents(), a) && contains(s.getComponents(), b)).findAny().orElse(null);
	}
	
	public static boolean isUsable(Aspect a)
	{
		return Aspect.aspects.values().stream().filter(s -> !s.isPrimal() && s.getComponents() != null && s.getComponents().length == 2 && contains(s.getComponents(), a)).findAny().isPresent();
	}
	
	public static boolean contains(Aspect[] co, Aspect a)
	{
		for(Aspect s : co)
			if(s == a)
				return true;
		return false;
	}
	
	@Override
	public boolean isConnectable(EnumFacing face)
	{
		return canInputFrom(face) || canOutputTo(face);
	}
	
	@Override
	public boolean canInputFrom(EnumFacing f)
	{
		if(front != null)
			return f == front.rotateY() || f == front.rotateYCCW();
		return false;
	}
	
	@Override
	public boolean canOutputTo(EnumFacing f)
	{
		return f == EnumFacing.UP;
	}
	
	@Override
	public void setSuction(Aspect var1, int var2)
	{
	}
	
	@Override
	public Aspect getSuctionType(EnumFacing f)
	{
		return f == EnumFacing.UP ? output : null;
	}
	
	@Override
	public int getSuctionAmount(EnumFacing f)
	{
		return canInputFrom(f) && getEssentiaType(f) == null ? 128 : 0;
	}
	
	@Override
	public int takeEssentia(Aspect a, int q, EnumFacing f)
	{
		if(canOutputTo(f) && q > 0)
		{
			if(output == a)
			{
				output = null;
				return 1;
			}
		}
		return 0;
	}
	
	@Override
	public int addEssentia(Aspect a, int q, EnumFacing f)
	{
		if(canInputFrom(f) && canAccept(a))
		{
			if(inA == null)
			{
				inA = a;
				return 1;
			} else if(inB == null)
			{
				inB = a;
				return 1;
			}
		}
		return 0;
	}
	
	@Override
	public Aspect getEssentiaType(EnumFacing f)
	{
		if(front == null)
			return null;
		return f == EnumFacing.UP ? output : f == front.rotateY() ? inA : f == front.rotateYCCW() ? inB : null;
	}
	
	@Override
	public int getEssentiaAmount(EnumFacing f)
	{
		return getEssentiaType(f) != null ? 1 : 0;
	}
	
	@Override
	public int getMinimumSuction()
	{
		return 0;
	}
	
	@Override
	public AspectList getAspects()
	{
		AspectList aspects = new AspectList();
		if(output != null)
			aspects.add(output, 1);
		if(inA != null)
			aspects.add(inA, 1);
		if(inB != null)
			aspects.add(inB, 1);
		if(inA != null && inB != null)
		{
			Aspect r = getOutput(inA, inB);
			if(r != null)
				aspects.add(r, 0);
		}
		return aspects;
	}
	
	@Override
	public void setAspects(AspectList var1)
	{
	}
	
	@Override
	public boolean doesContainerAccept(Aspect asp)
	{
		return false;
	}
	
	@Override
	public int addToContainer(Aspect var1, int var2)
	{
		return 0;
	}
	
	@Override
	public boolean takeFromContainer(Aspect a, int q)
	{
		return q == 1 && takeEssentia(a, q, EnumFacing.UP) == 1;
	}
	
	@Override
	public boolean takeFromContainer(AspectList al)
	{
		return false;
	}
	
	@Override
	public boolean doesContainerContainAmount(Aspect a, int q)
	{
		return q == 1 && a == output;
	}
	
	@Override
	public boolean doesContainerContain(AspectList al)
	{
		return false;
	}
	
	@Override
	public int containerContains(Aspect a)
	{
		return a == output ? 1 : 0;
	}
}