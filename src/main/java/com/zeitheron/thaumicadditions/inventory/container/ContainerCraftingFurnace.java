package com.zeitheron.thaumicadditions.inventory.container;

import com.pengu.hammercore.core.gui.container.ItemTransferHelper.TransferableContainer;
import com.zeitheron.thaumicadditions.tiles.TileCraftingFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;

public class ContainerCraftingFurnace extends TransferableContainer<TileCraftingFurnace>
{
	public InventoryCrafting craftMatrix;
	public InventoryCraftResult craftResult;
	
	public ContainerCraftingFurnace(EntityPlayer player, TileCraftingFurnace tile)
	{
		super(player, tile, 8, 84);
	}
	
	@Override
	protected void addCustomSlots()
	{
		craftMatrix = new InventoryCrafting(this, 3, 3);
		craftResult = new InventoryCraftResult();
		
		addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 64, 17));
		
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 3; ++j)
				addSlotToContainer(new Slot(craftMatrix, j + i * 3, 8 + j * 18, 17 + i * 18));
			
		addSlotToContainer(new Slot(t.inv, 0, 90, 17));
		addSlotToContainer(new SlotFurnaceFuel(t.inv, 1, 90, 53));
		addSlotToContainer(new SlotFurnaceOutput(player, t.inv, 2, 140, 35));
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		slotChangedCraftingGrid(t.getWorld(), player, craftMatrix, craftResult);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		
		if(!t.getWorld().isRemote)
			clearContainer(playerIn, t.getWorld(), craftMatrix);
	}
}