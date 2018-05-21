package com.zeitheron.thaumicadditions.tiles;

import com.pengu.hammercore.common.inventory.InventoryDummy;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.zeitheron.thaumicadditions.blocks.BlockCraftingFurnace;
import com.zeitheron.thaumicadditions.inventory.container.ContainerCraftingFurnace;
import com.zeitheron.thaumicadditions.inventory.gui.GuiCraftingFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.MathHelper;

public class TileCraftingFurnace extends TileSyncableTickable
{
	public final InventoryDummy inv = new InventoryDummy(3);
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int cookTime;
	public int totalCookTime;
	public String furnaceCustomName;
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setTag("Items", inv.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("BurnTime", furnaceBurnTime);
		nbt.setInteger("CurrentBurnTime", currentItemBurnTime);
		nbt.setInteger("CookTime", cookTime);
		nbt.setInteger("TotalCookTime", totalCookTime);
		if(furnaceCustomName != null && !furnaceCustomName.isEmpty())
			nbt.setString("CustomName", furnaceCustomName);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		inv.readFromNBT(nbt.getCompoundTag("Items"));
		furnaceBurnTime = nbt.getInteger("BurnTime");
		currentItemBurnTime = nbt.getInteger("CurrentBurnTime");
		cookTime = nbt.getInteger("CookTime");
		totalCookTime = nbt.getInteger("TotalCookTime");
		if(nbt.hasKey("CustomName"))
			furnaceCustomName = nbt.getString("CustomName");
	}
	
	@Override
	public boolean hasGui()
	{
		return true;
	}
	
	@Override
	public Object getClientGuiElement(EntityPlayer player)
	{
		return new GuiCraftingFurnace(player, this);
	}
	
	@Override
	public Object getServerGuiElement(EntityPlayer player)
	{
		return new ContainerCraftingFurnace(player, this);
	}
	
	@Override
	public void tick()
	{
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(isBurning())
			--furnaceBurnTime;
		
		if(isBurning() && canSmelt())
			++cookTime;
		else
			this.cookTime = 0;
		
		if(!world.isRemote)
		{
			ItemStack itemstack = inv.getStackInSlot(1);
			
			if(isBurning() || !itemstack.isEmpty() && !inv.getStackInSlot(0).isEmpty())
			{
				if(!isBurning() && canSmelt())
				{
					furnaceBurnTime = TileEntityFurnace.getItemBurnTime(itemstack);
					currentItemBurnTime = furnaceBurnTime;
					
					if(isBurning())
					{
						flag1 = true;
						
						if(!itemstack.isEmpty())
						{
							Item item = itemstack.getItem();
							itemstack.shrink(1);
							
							if(itemstack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack);
								inv.setInventorySlotContents(1, item1);
							}
						}
					}
					
					sendChangesToNearby();
				}
				
				if(cookTime >= totalCookTime)
				{
					cookTime = 0;
					totalCookTime = this.getCookTime(inv.getStackInSlot(0));
					smeltItem();
					flag1 = true;
					sendChangesToNearby();
				}
			} else if(!this.isBurning() && this.cookTime > 0)
			{
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			
			if(flag != this.isBurning())
			{
				flag1 = true;
				BlockCraftingFurnace.setState(this.isBurning(), this.world, this.pos);
			}
		}
		
		if(flag1)
			markDirty();
	}
	
	public int getCookTime(ItemStack stack)
	{
		return 200;
	}
	
	public boolean isBurning()
	{
		return this.furnaceBurnTime > 0;
	}
	
	private boolean canSmelt()
	{
		if(inv.getStackInSlot(0).isEmpty())
			return false;
		else
		{
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(inv.getStackInSlot(0));
			
			if(itemstack.isEmpty())
				return false;
			else
			{
				ItemStack itemstack1 = inv.getStackInSlot(2);
				
				if(itemstack1.isEmpty())
					return true;
				else if(!itemstack1.isItemEqual(itemstack))
					return false;
				else if(itemstack1.getCount() + itemstack.getCount() <= inv.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())
					return true;
				else
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
			}
		}
	}
	
	public void smeltItem()
	{
		if(canSmelt())
		{
			ItemStack itemstack = inv.getStackInSlot(0);
			ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
			ItemStack itemstack2 = inv.getStackInSlot(2);
			if(itemstack2.isEmpty())
				inv.setInventorySlotContents(2, itemstack1.copy());
			else if(itemstack2.getItem() == itemstack1.getItem())
				itemstack2.grow(itemstack1.getCount());
			if(itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !inv.getStackInSlot(1).isEmpty() && inv.getStackInSlot(1).getItem() == Items.BUCKET)
				inv.setInventorySlotContents(1, new ItemStack(Items.WATER_BUCKET));
			itemstack.shrink(1);
		}
	}
	
	public void setCustomInventoryName(String displayName)
	{
		this.furnaceCustomName = displayName;
	}
}