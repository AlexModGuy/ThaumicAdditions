package com.zeitheron.thaumicadditions.tiles;

import com.pengu.hammercore.common.utils.SoundUtil;
import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.tile.TileSyncable;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.tile.iTileDroppable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.tiles.TileThaumcraft;
import thaumcraft.common.tiles.essentia.TileJarFillable;

public class TileAspectCombiner extends TileSyncableTickable implements IEssentiaTransport, IAspectContainer, iTileDroppable
{
	public Aspect inA, inB, output;
	public int craftingTime, prevCraftingTime;
	public EnumFacing front;
	
	public int vis, prevVis;
	
	public float rotation, prevRotation;
	
	public boolean prevPowered;
	public int toggle = 0;
	
	public int getMaxCraftTime()
	{
		return 100;
	}
	
	@Override
	public void tick()
	{
		front = WorldUtil.getFacing(world.getBlockState(pos));
		prevCraftingTime = craftingTime;
		prevRotation = rotation;
		
		boolean powered = gettingPower();
		boolean crafting = false;
		
		prevVis = vis;
		vis = 0;
		if(inA != null)
			++vis;
		if(inB != null)
			++vis;
		if(output != null)
			++vis;
		
		Aspect cout = getOutput(inA, inB);
		if(inA != null && inB != null && output == null && cout != null)
		{
			int max = getMaxCraftTime();
			if(craftingTime < max)
			{
				if(craftingTime == 0 && !world.isRemote)
					sendChangesToNearby();
				if(!powered)
				{
					craftingTime++;
					crafting = true;
				}
				if(atTickRate(10) && !world.isRemote && !powered)
				{
					sendChangesToNearby();
					SoundUtil.playSoundEffect(world, SoundsTC.pump.getRegistryName().toString(), pos, 1F, 1F, SoundCategory.BLOCKS);
				}
			}
			if(craftingTime >= max)
			{
				output = cout;
				craftingTime = 0;
				if(!world.isRemote)
				{
					inA = null;
					inB = null;
					sendChangesToNearby();
				}
			}
		}
		
		if(crafting != prevPowered)
			toggle = 36;
		
		float cRot = (float) Math.sqrt((!crafting ? toggle / 36F : (36 - toggle) / 36F) * 81F) / 9F * 8F;
		rotation += cRot;
		
		if(toggle != 0)
			toggle += toggle < 0 ? 1 : -1;
		
		prevPowered = crafting;
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
			TileEntity te = world.getTileEntity(pos.up());
			
			if(te instanceof TileJarFillable)
			{
				TileJarFillable tjf = (TileJarFillable) te;
				int cap = tjf instanceof TileAbstractJarFillable ? ((TileAbstractJarFillable) tjf).getCapacity() : TileAbstractJarFillable.CAPACITY;
				
				if(tjf.aspect == output && tjf.amount < cap)
				{
					tjf.amount++;
					output = null;
					sendChangesToNearby();
					tjf.syncTile(true);
				} else if(tjf.aspect == null && (tjf.aspectFilter == output || tjf.aspectFilter == null) && tjf.amount < cap)
				{
					tjf.aspect = output;
					tjf.amount++;
					output = null;
					sendChangesToNearby();
					tjf.syncTile(true);
				}
			} else
			{
				IEssentiaTransport u = (IEssentiaTransport) ThaumcraftApiHelper.getConnectableTile(world, pos, EnumFacing.UP);
				if(u != null && u.canInputFrom(EnumFacing.DOWN) && u.addEssentia(output, 1, EnumFacing.DOWN) == 1)
				{
					output = null;
					sendChangesToNearby();
					if(u instanceof TileSyncable)
						((TileSyncable) u).sync();
					else if(u instanceof TileThaumcraft)
						((TileThaumcraft) u).syncTile(true);
				}
			}
		}
	}
	
	public boolean gettingPower()
	{
		return world.isBlockIndirectlyGettingPowered(pos) > 0;
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
		nbt.setInteger("Vis", vis);
		nbt.setInteger("PrevVis", prevVis);
		nbt.setInteger("CraftingTime", craftingTime);
		nbt.setFloat("Rotation", rotation);
		nbt.setFloat("PrevRotation", prevRotation);
		nbt.setInteger("Toggle", toggle);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		output = nbt.hasKey("Output") ? Aspect.getAspect(nbt.getString("Output")) : null;
		inA = nbt.hasKey("InA") ? Aspect.getAspect(nbt.getString("InA")) : null;
		inB = nbt.hasKey("InB") ? Aspect.getAspect(nbt.getString("InB")) : null;
		vis = nbt.getInteger("Vis");
		prevVis = nbt.getInteger("PrevVis");
		craftingTime = nbt.getInteger("CraftingTime");
		rotation = nbt.getFloat("Rotation");
		prevRotation = nbt.getFloat("PrevRotation");
		toggle = nbt.getInteger("Toggle");
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
		if(canOutputTo(f) && q > 0 && output == a)
		{
			output = null;
			return 1;
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
	
	@Override
	public void createDrop(EntityPlayer player, World world, BlockPos pos)
	{
		if(!world.isRemote)
			AuraHelper.polluteAura(world, pos, vis, true);
	}
}