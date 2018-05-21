package com.zeitheron.thaumicadditions.tiles;

import java.util.List;
import java.util.Map;

import com.pengu.hammercore.common.inventory.InventoryDummy;
import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.tile.iTileDroppable;
import com.zeitheron.thaumicadditions.TAReconstructed;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.api.EdibleAspect;
import com.zeitheron.thaumicadditions.api.utils.ListenerList;
import com.zeitheron.thaumicadditions.init.ItemsTAR;
import com.zeitheron.thaumicadditions.inventory.container.ContainerAuraDisperser;
import com.zeitheron.thaumicadditions.inventory.gui.GuiAuraDisperser;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class TileAuraDisperser extends TileSyncableTickable implements iTileDroppable, IInventory
{
	public final InventoryDummy inventory = new InventoryDummy(9);
	public final ListenerList<TileAuraDisperser> listeners = new ListenerList<>(this);
	
	public int tickTimer = 0;
	public int timer = 0;
	public AspectList aspects = null;
	
	public int secondsPerSalt = 20;
	
	@Override
	public void tick()
	{
		ItemStack sel = select();
		
		if(!world.isRemote && !sel.isEmpty() && (aspects == null || aspects.visSize() == 0))
		{
			aspects = ItemsTAR.SALT_ESSENCE.getAspects(sel);
			sel.shrink(1);
			timer = secondsPerSalt;
			tickTimer = timer * 20;
			sendChangesToNearby();
		}
		
		if(aspects != null && aspects.visSize() > 0 && getLocation().getRedstone() == 0)
			applyAura();
	}
	
	private boolean auraApplied;
	
	public void applyAura()
	{
		if(tickTimer > 0 && auraApplied)
			--tickTimer;
		if(!world.isRemote && timer > 0 && aspects != null && atTickRate(20))
		{
			EnumFacing face = WorldUtil.getFacing(getLocation().getState());
			AxisAlignedBB aabb = new AxisAlignedBB(pos).offset(face.getFrontOffsetX() * 5, face.getFrontOffsetY() * 4, face.getFrontOffsetZ() * 5).grow(4, 3, 4);
			List<EntityLivingBase> ents = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			boolean did = false;
			for(EntityLivingBase b : ents)
				did |= EdibleAspect.execute(b, aspects).visSize() > 0;
			if(did)
				TAReconstructed.proxy.getFX().spawnAuraDisperserFX(this);
			
			if(did)
				timer--;
			int tt = tickTimer;
			tickTimer = timer * 20;
			if(timer == 0)
				aspects = null;
			auraApplied = did;
			
			// for(int i = 0; i < 32; ++i)
			// HCNetwork.spawnParticle(world, EnumParticleTypes.CRIT, aabb.minX
			// + (aabb.maxX - aabb.minX) * getRNG().nextFloat(), aabb.minY +
			// (aabb.maxY - aabb.minY) * getRNG().nextFloat(), aabb.minZ +
			// (aabb.maxZ - aabb.minZ) * getRNG().nextFloat(), 0, 0, 0);
			if(did || tt != tickTimer)
				sendChangesToNearby();
		}
	}
	
	@Override
	public void addProperties(Map<String, Object> properties, RayTraceResult trace)
	{
		if(aspects != null)
		{
			properties.put("time_left", timer);
			properties.put("aspects", aspects.visSize());
			for(Aspect a : aspects.getAspectsSortedByName())
				properties.put(a.getName(), aspects.getAmount(a));
		}
	}
	
	@Override
	public boolean hasGui()
	{
		return true;
	}
	
	@Override
	public Object getClientGuiElement(EntityPlayer player)
	{
		return new GuiAuraDisperser(player, this);
	}
	
	@Override
	public Object getServerGuiElement(EntityPlayer player)
	{
		return new ContainerAuraDisperser(player, this);
	}
	
	public ItemStack select()
	{
		for(int i = 0; i < inventory.getSizeInventory(); ++i)
			if(!inventory.getStackInSlot(i).isEmpty() && inventory.getStackInSlot(i).getItem() == ItemsTAR.SALT_ESSENCE)
				return inventory.getStackInSlot(i);
		return ItemStack.EMPTY;
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		listeners.update(0);
		
		nbt.setTag("Items", inventory.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("Timer", timer);
		nbt.setInteger("TickTimer", tickTimer);
		nbt.setBoolean("AuraApplied", auraApplied);
		AspectUtil.writeALToNBT(aspects, nbt);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		if(nbt.hasKey("Aspects"))
		{
			aspects = new AspectList();
			aspects.readFromNBT(nbt);
		} else
			aspects = null;
		inventory.readFromNBT(nbt.getCompoundTag("Items"));
		int ptimer = timer;
		timer = nbt.getInteger("Timer");
		tickTimer = nbt.getInteger("TickTimer");
		auraApplied = nbt.getBoolean("AuraApplied");
		
		listeners.update(1);
		if(timer != ptimer)
			listeners.update(11);
	}
	
	@Override
	public void createDrop(EntityPlayer player, World world, BlockPos pos)
	{
		inventory.drop(world, pos);
	}
	
	@Override
	public String getName()
	{
		return "A.D.";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return false;
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}
	
	@Override
	public boolean isEmpty()
	{
		return inventory.isEmpty();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.getStackInSlot(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return inventory.decrStackSize(index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return inventory.removeStackFromSlot(index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inventory.setInventorySlotContents(index, stack);
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return inventory.isUsableByPlayer(player, pos);
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return !stack.isEmpty() && stack.getItem() == ItemsTAR.SALT_ESSENCE;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		inventory.clear();
	}
}