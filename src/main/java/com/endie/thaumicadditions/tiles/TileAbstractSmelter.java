package com.endie.thaumicadditions.tiles;

import com.endie.thaumicadditions.inventory.container.ContainerAbstractSmelter;
import com.endie.thaumicadditions.inventory.gui.GuiAbstractSmelter;
import com.pengu.hammercore.common.inventory.InventoryDummy;
import com.pengu.hammercore.tile.TileSyncableTickable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.essentia.BlockSmelter;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.tiles.devices.TileBellows;
import thaumcraft.common.tiles.essentia.TileAlembic;
import thaumcraft.common.tiles.essentia.TileSmelter;

public abstract class TileAbstractSmelter extends TileSyncableTickable implements ISidedInventory
{
	private static final int[] slots_bottom = new int[] { 1 };
	private static final int[] slots_top = new int[0];
	private static final int[] slots_sides = new int[] { 0 };
	public InventoryDummy inv = new InventoryDummy(2);
	public AspectList aspects = new AspectList();
	public int vis;
	private int maxVis = 256;
	public int smeltTime = 100;
	boolean speedBoost = false;
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	int count = 0;
	int bellows = -1;
	
	/**
	 * The efficiency of this smelter. <br>
	 * 0.8F for basic smelter (20% loss); <br>
	 * 0.85F for thaumic smelter (15% loss); <br>
	 * 0.95F for void smelter (5% loss);
	 */
	public abstract float getEfficiency();
	
	/**
	 * The speed of this smelter. <br>
	 * 15 for basic smelter; <br>
	 * 10 for thaumic smelter; <br>
	 * 15 for void smelter;
	 */
	public abstract int getSpeed();
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		this.speedBoost = nbt.getBoolean("speedBoost");
		this.furnaceCookTime = nbt.getShort("CookTime");
		this.aspects.readFromNBT(nbt);
		this.vis = this.aspects.visSize();
		this.furnaceBurnTime = nbt.getShort("BurnTime");
		this.currentItemBurnTime = nbt.getShort("CurrentBurnTime");
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setShort("BurnTime", (short) this.furnaceBurnTime);
		nbt.setShort("CurrentBurnTime", (short) this.currentItemBurnTime);
		nbt.setBoolean("speedBoost", this.speedBoost);
		nbt.setShort("CookTime", (short) this.furnaceCookTime);
		this.aspects.writeToNBT(nbt);
	}
	
	@Override
	public boolean hasGui()
	{
		return true;
	}
	
	@Override
	public Object getClientGuiElement(EntityPlayer player)
	{
		return new GuiAbstractSmelter(player.inventory, this);
	}
	
	@Override
	public Object getServerGuiElement(EntityPlayer player)
	{
		return new ContainerAbstractSmelter(player.inventory, this);
	}
	
	@Override
	public void tick()
	{
		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;
		++this.count;
		if(this.furnaceBurnTime > 0)
		{
			--this.furnaceBurnTime;
		}
		if(this.world != null && !this.world.isRemote)
		{
			if(this.bellows < 0)
			{
				this.checkNeighbours();
			}
			int speed = this.getSpeed();
			if(this.speedBoost)
				speed = (int) (speed * 0.8);
			if(this.count % (speed / 2) == 0 && this.aspects.size() > 0)
			{
				for(Aspect aspect : this.aspects.getAspects())
				{
					if(this.aspects.getAmount(aspect) <= 0 || !processAlembics(this.getWorld(), this.getPos(), aspect))
						continue;
					this.takeFromContainer(aspect, 1);
					break;
				}
				block1: for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					IBlockState aux = this.world.getBlockState(getPos().offset(face));
					if(aux.getBlock() != BlocksTC.smelterAux || BlockStateUtils.getFacing(aux) != face.getOpposite())
						continue;
					for(Aspect aspect : aspects.getAspects())
					{
						if(aspects.getAmount(aspect) <= 0 || !processAlembics(getWorld(), getPos().offset(face), aspect))
							continue;
						this.takeFromContainer(aspect, 1);
						continue block1;
					}
				}
			}
			if(this.furnaceBurnTime == 0)
			{
				if(this.canSmelt())
				{
					this.currentItemBurnTime = this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(1));
					if(this.furnaceBurnTime > 0)
					{
						BlockSmelter.setFurnaceState(this.world, this.getPos(), true);
						flag1 = true;
						this.speedBoost = false;
						if(this.getStackInSlot(1) != null)
						{
							if(this.getStackInSlot(1).isItemEqual(new ItemStack(ItemsTC.alumentum)))
							{
								this.speedBoost = true;
							}
							this.getStackInSlot(1).shrink(1);
							if(this.getStackInSlot(1).getCount() == 0)
							{
								this.setInventorySlotContents(1, this.getStackInSlot(1).getItem().getContainerItem(this.getStackInSlot(1)));
							}
						}
					} else
					{
						BlockSmelter.setFurnaceState(this.world, this.getPos(), false);
					}
				} else
				{
					BlockSmelter.setFurnaceState(this.world, this.getPos(), false);
				}
			}
			if(BlockStateUtils.isEnabled(this.getBlockMetadata()) && this.canSmelt())
			{
				++this.furnaceCookTime;
				if(this.furnaceCookTime >= this.smeltTime)
				{
					this.furnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			} else
			{
				this.furnaceCookTime = 0;
			}
			if(flag != this.furnaceBurnTime > 0)
			{
				flag1 = true;
			}
		}
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	private boolean canSmelt()
	{
		if(this.getStackInSlot(0).isEmpty())
		{
			return false;
		}
		AspectList al = ThaumcraftCraftingManager.getObjectTags(this.getStackInSlot(0));
		if(al == null || al.size() == 0)
		{
			return false;
		}
		int vs = al.visSize();
		if(vs > this.maxVis - this.vis)
		{
			return false;
		}
		this.smeltTime = (int) (vs * 2 * (1.0f - 0.125f * this.bellows));
		return true;
	}
	
	public void checkNeighbours()
	{
		EnumFacing[] faces = EnumFacing.HORIZONTALS;
		try
		{
			if(BlockStateUtils.getFacing(this.getBlockMetadata()) == EnumFacing.NORTH)
				faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };
			if(BlockStateUtils.getFacing(this.getBlockMetadata()) == EnumFacing.SOUTH)
				faces = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
			if(BlockStateUtils.getFacing(this.getBlockMetadata()) == EnumFacing.EAST)
				faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.WEST };
			if(BlockStateUtils.getFacing(this.getBlockMetadata()) == EnumFacing.WEST)
				faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.NORTH };
		} catch(Exception exception)
		{
			// empty catch block
		}
		this.bellows = TileBellows.getBellows(this.world, this.pos, faces);
	}
	
	public void smeltItem()
	{
		if(this.canSmelt())
		{
			int flux = 0;
			AspectList al = ThaumcraftCraftingManager.getObjectTags(this.getStackInSlot(0));
			for(Aspect a : al.getAspects())
			{
				if(this.getEfficiency() < 1.0f)
				{
					int qq = al.getAmount(a);
					for(int q = 0; q < qq; ++q)
					{
						if(this.world.rand.nextFloat() <= (a == Aspect.FLUX ? this.getEfficiency() * 0.66f : this.getEfficiency()))
							continue;
						al.reduce(a, 1);
						++flux;
					}
				} else if(this.getEfficiency() > 1.0F) // Gain extra vis?
				{
					int qq = al.getAmount(a);
					for(int q = 0; q < qq; ++q)
					{
						if(this.world.rand.nextFloat() + 1 >= (a == Aspect.FLUX ? this.getEfficiency() * 0.66f : this.getEfficiency()))
							continue;
						al.add(a, 1 + world.rand.nextInt(qq));
					}
				}
				this.aspects.add(a, al.getAmount(a));
			}
			if(flux > 0)
			{
				int pp = 0;
				block2: for(int c = 0; c < flux; ++c)
				{
					for(EnumFacing face : EnumFacing.HORIZONTALS)
					{
						IBlockState vent = this.world.getBlockState(this.getPos().offset(face));
						if(vent.getBlock() != BlocksTC.smelterVent || BlockStateUtils.getFacing(vent) != face.getOpposite() || this.world.rand.nextFloat() >= 0.333)
							continue;
						this.world.addBlockEvent(this.getPos(), this.getBlockType(), 1, face.getOpposite().ordinal());
						continue block2;
					}
					++pp;
				}
				AuraHelper.polluteAura(getWorld(), getPos(), pp, true);
			}
			vis = aspects.visSize();
			getStackInSlot(0).shrink(1);
			if(getStackInSlot(0).getCount() <= 0)
				setInventorySlotContents(0, ItemStack.EMPTY);
		}
	}
	
	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return TileEntityFurnace.getItemBurnTime(par0ItemStack) > 0;
	}
	
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack stack2)
	{
		AspectList al;
		if(par1 == 0 && (al = ThaumcraftCraftingManager.getObjectTags(stack2)) != null && al.size() > 0)
			return true;
		return par1 == 1 ? TileSmelter.isItemFuel(stack2) : false;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing par1)
	{
		return par1 == EnumFacing.DOWN ? slots_bottom : (par1 == EnumFacing.UP ? slots_top : slots_sides);
	}
	
	@Override
	public boolean canInsertItem(int par1, ItemStack stack2, EnumFacing par3)
	{
		return par3 == EnumFacing.UP ? false : this.isItemValidForSlot(par1, stack2);
	}
	
	@Override
	public boolean canExtractItem(int par1, ItemStack stack2, EnumFacing par3)
	{
		return par3 != EnumFacing.UP || par1 != 1 || stack2.getItem() == Items.BUCKET;
	}
	
	public boolean takeFromContainer(Aspect tag, int amount)
	{
		if(this.aspects != null && this.aspects.getAmount(tag) >= amount)
		{
			this.aspects.remove(tag, amount);
			this.vis = this.aspects.visSize();
			this.markDirty();
			return true;
		}
		return false;
	}
	
	@SideOnly(value = Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		if(this.smeltTime <= 0)
		{
			this.smeltTime = 1;
		}
		return this.furnaceCookTime * par1 / this.smeltTime;
	}
	
	@SideOnly(value = Side.CLIENT)
	public int getVisScaled(int par1)
	{
		return this.vis * par1 / this.maxVis;
	}
	
	@SideOnly(value = Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if(this.currentItemBurnTime == 0)
			this.currentItemBurnTime = 200;
		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		if(this.world != null)
			this.world.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
	}
	
	@Override
	public boolean receiveClientEvent(int i, int j)
	{
		if(i == 1)
		{
			if(this.world.isRemote)
			{
				EnumFacing d = EnumFacing.VALUES[j];
				this.world.playSound(this.getPos().getX() + 0.5 + d.getOpposite().getFrontOffsetX(), this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5 + d.getOpposite().getFrontOffsetZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.25f, 2.6f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8f, true);
				for(int a = 0; a < 4; ++a)
				{
					float fx = 0.1f - this.world.rand.nextFloat() * 0.2f;
					float fz = 0.1f - this.world.rand.nextFloat() * 0.2f;
					float fy = 0.1f - this.world.rand.nextFloat() * 0.2f;
					float fx2 = 0.1f - this.world.rand.nextFloat() * 0.2f;
					float fz2 = 0.1f - this.world.rand.nextFloat() * 0.2f;
					float fy2 = 0.1f - this.world.rand.nextFloat() * 0.2f;
					int color = 11184810;
					FXDispatcher.INSTANCE.drawVentParticles(this.getPos().getX() + 0.5f + fx + d.getOpposite().getFrontOffsetX(), this.getPos().getY() + 0.5f + fy, this.getPos().getZ() + 0.5f + fz + d.getOpposite().getFrontOffsetZ(), d.getOpposite().getFrontOffsetX() / 4.0f + fx2, d.getOpposite().getFrontOffsetY() / 4.0f + fy2, d.getOpposite().getFrontOffsetZ() / 4.0f + fz2, color);
				}
			}
			return true;
		}
		return super.receiveClientEvent(i, j);
	}
	
	public static boolean processAlembics(World world, BlockPos pos, Aspect aspect)
	{
		TileEntity te;
		TileAlembic alembic;
		int deep = 1;
		while((te = world.getTileEntity(pos.up(deep))) != null && te instanceof TileAlembic)
		{
			alembic = (TileAlembic) te;
			if(alembic.amount > 0 && alembic.aspect == aspect && alembic.addToContainer(aspect, 1) == 0)
			{
				return true;
			}
			++deep;
		}
		deep = 1;
		while((te = world.getTileEntity(pos.up(deep))) != null && te instanceof TileAlembic)
		{
			alembic = (TileAlembic) te;
			if((alembic.aspectFilter == null || alembic.aspectFilter == aspect) && alembic.addToContainer(aspect, 1) == 0)
			{
				return true;
			}
			++deep;
		}
		return false;
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
}