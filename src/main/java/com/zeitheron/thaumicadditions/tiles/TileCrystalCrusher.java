package com.zeitheron.thaumicadditions.tiles;

import java.util.Objects;
import java.util.Random;

import com.pengu.hammercore.common.inventory.InventoryDummy;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.utils.FrictionRotator;
import com.zeitheron.thaumicadditions.api.fx.ParticleHelperTAR;
import com.zeitheron.thaumicadditions.init.ItemsTAR;
import com.zeitheron.thaumicadditions.inventory.container.ContainerCrystalCrusher;
import com.zeitheron.thaumicadditions.inventory.gui.GuiCrystalCrusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.events.EssentiaHandler;

public class TileCrystalCrusher extends TileSyncableTickable implements ISidedInventory
{
	public InventoryDummy inv = new InventoryDummy(2);
	public int crushes, craftTime;
	
	public final FrictionRotator rotator = new FrictionRotator();
	{
		rotator.degree = new Random().nextFloat() * 360;
	}
	
	@Override
	public void tick()
	{
		rotator.friction = .5F;
		if(world.isRemote)
			rotator.update();
		
		if(world.isBlockIndirectlyGettingPowered(pos) > 0)
			return;
		
		if(crushes <= 0 && EssentiaHandler.drainEssentia(this, Aspect.MECHANISM, null, 12, 1))
			crushes += 3;
		
		if(canCraft())
		{
			++craftTime;
			if(world.isRemote)
			{
				rotator.speedup(1F);
				ParticleHelperTAR.spawnItemCrack(world, pos.getX() + .1 + getRNG().nextFloat() * .8, pos.getY() + 4 / 16D, pos.getZ() + .1 + getRNG().nextFloat() * .8, (getRNG().nextFloat() - getRNG().nextFloat()) * .3, 0, (getRNG().nextFloat() - getRNG().nextFloat()) * .3, inv.getStackInSlot(0));
			}
			if(!world.isRemote && atTickRate(10))
				sync();
			if(!world.isRemote && craftTime >= 100)
				craft();
		} else if(craftTime > 0)
			--craftTime;
	}
	
	public boolean canCraft()
	{
		ItemStack in = inv.getStackInSlot(0);
		ItemStack out = inv.getStackInSlot(1);
		if(crushes <= 0)
			return false;
		if(in.isEmpty() || in.getItem() != ItemsTC.crystalEssence)
			return false;
		if(out.isEmpty() || (out.getItem() == ItemsTAR.SALT_ESSENCE && Objects.equals(in.getTagCompound(), out.getTagCompound())))
			return true;
		return false;
	}
	
	public void craft()
	{
		NBTTagCompound nbt = inv.getStackInSlot(0).getTagCompound();
		if(nbt != null)
			nbt = nbt.copy();
		
		if(inv.getStackInSlot(1).isEmpty())
		{
			inv.getStackInSlot(0).shrink(1);
			ItemStack stack = new ItemStack(ItemsTAR.SALT_ESSENCE);
			stack.setTagCompound(nbt);
			inv.setInventorySlotContents(1, stack);
			crushes--;
		} else if(inv.getStackInSlot(1).getItem() == ItemsTAR.SALT_ESSENCE && Objects.equals(inv.getStackInSlot(0).getTagCompound(), inv.getStackInSlot(1).getTagCompound()))
		{
			inv.getStackInSlot(0).shrink(1);
			inv.getStackInSlot(1).grow(1);
			crushes--;
		}
		
		craftTime = 0;
		sync();
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("Fuel", crushes);
		nbt.setInteger("CraftTime", craftTime);
		nbt.setTag("Items", inv.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		crushes = nbt.getInteger("Fuel");
		craftTime = nbt.getInteger("CraftTime");
		inv.readFromNBT(nbt.getCompoundTag("Items"));
	}
	
	@Override
	public boolean hasGui()
	{
		return true;
	}
	
	@Override
	public Object getClientGuiElement(EntityPlayer player)
	{
		return new GuiCrystalCrusher(player, this);
	}
	
	@Override
	public Object getServerGuiElement(EntityPlayer player)
	{
		return new ContainerCrystalCrusher(player, this);
	}
	
	@Override
	public int getSizeInventory()
	{
		return inv.getSizeInventory();
	}
	
	@Override
	public boolean isEmpty()
	{
		return inv.isEmpty();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inv.getStackInSlot(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return inv.decrStackSize(index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return inv.removeStackFromSlot(index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inv.setInventorySlotContents(index, stack);
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return inv.getInventoryStackLimit();
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return inv.isUsableByPlayer(player, pos);
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
		return index == 0 ? stack.getItem() == ItemsTC.crystalEssence : false;
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
		inv.clear();
	}
	
	@Override
	public String getName()
	{
		return "";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return false;
	}
	
	private final int[] slots = new int[] { 0, 1 };
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return slots;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return index == 1;
	}
}