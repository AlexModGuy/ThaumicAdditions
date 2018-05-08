package com.endie.thaumicadditions.inventory.container;

import com.endie.thaumicadditions.tiles.TileAbstractSmelter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.container.slot.SlotLimitedHasAspects;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.essentia.TileSmelter;

public class ContainerAbstractSmelter extends Container
{
	private TileAbstractSmelter furnace;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	private int lastVis;
	private int lastSmelt;
	private int lastFlux;
	
	public ContainerAbstractSmelter(InventoryPlayer par1InventoryPlayer, TileAbstractSmelter tileEntity)
	{
		int i;
		this.furnace = tileEntity;
		this.addSlotToContainer(new SlotLimitedHasAspects(tileEntity, 0, 80, 8));
		this.addSlotToContainer(new Slot(tileEntity, 1, 80, 48));
		for(i = 0; i < 3; ++i)
		{
			for(int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.furnace);
		listener.sendWindowProperty(this, 0, this.furnace.furnaceCookTime);
		listener.sendWindowProperty(this, 1, this.furnace.furnaceBurnTime);
		listener.sendWindowProperty(this, 2, this.furnace.currentItemBurnTime);
		listener.sendWindowProperty(this, 3, this.furnace.vis);
		listener.sendWindowProperty(this, 4, this.furnace.smeltTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icrafting = this.listeners.get(i);
			if(this.lastCookTime != this.furnace.furnaceCookTime)
			{
				icrafting.sendWindowProperty(this, 0, this.furnace.furnaceCookTime);
			}
			if(this.lastBurnTime != this.furnace.furnaceBurnTime)
			{
				icrafting.sendWindowProperty(this, 1, this.furnace.furnaceBurnTime);
			}
			if(this.lastItemBurnTime != this.furnace.currentItemBurnTime)
			{
				icrafting.sendWindowProperty(this, 2, this.furnace.currentItemBurnTime);
			}
			if(this.lastVis != this.furnace.vis)
			{
				icrafting.sendWindowProperty(this, 3, this.furnace.vis);
			}
			if(this.lastSmelt == this.furnace.smeltTime)
				continue;
			icrafting.sendWindowProperty(this, 4, this.furnace.smeltTime);
		}
		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastBurnTime = this.furnace.furnaceBurnTime;
		this.lastItemBurnTime = this.furnace.currentItemBurnTime;
		this.lastVis = this.furnace.vis;
		this.lastSmelt = this.furnace.smeltTime;
	}
	
	@Override
	@SideOnly(value = Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == 0)
		{
			this.furnace.furnaceCookTime = par2;
		}
		if(par1 == 1)
		{
			this.furnace.furnaceBurnTime = par2;
		}
		if(par1 == 2)
		{
			this.furnace.currentItemBurnTime = par2;
		}
		if(par1 == 3)
		{
			this.furnace.vis = par2;
		}
		if(par1 == 4)
		{
			this.furnace.smeltTime = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.furnace.isUsableByPlayer(par1EntityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(par2 != 1 && par2 != 0)
			{
				AspectList al = ThaumcraftCraftingManager.getObjectTags(itemstack1);
				if(TileSmelter.isItemFuel(itemstack1) ? !this.mergeItemStack(itemstack1, 1, 2, false) && !this.mergeItemStack(itemstack1, 0, 1, false) : (al != null && al.size() > 0 ? !this.mergeItemStack(itemstack1, 0, 1, false) : (par2 >= 2 && par2 < 29 ? !this.mergeItemStack(itemstack1, 29, 38, false) : par2 >= 29 && par2 < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))))
				{
					return ItemStack.EMPTY;
				}
			} else if(!this.mergeItemStack(itemstack1, 2, 38, false))
			{
				return ItemStack.EMPTY;
			}
			if(itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			} else
			{
				slot.onSlotChanged();
			}
			if(itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}