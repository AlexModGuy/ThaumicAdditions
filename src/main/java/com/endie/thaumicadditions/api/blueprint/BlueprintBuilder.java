package com.endie.thaumicadditions.api.blueprint;

import java.util.Objects;
import java.util.Random;

import com.pengu.hammercore.common.blocks.base.iBlockHorizontal;
import com.pengu.hammercore.common.utils.ItemStackUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import thaumcraft.api.ThaumcraftApi.BluePrint;
import thaumcraft.api.crafting.Part;

public class BlueprintBuilder
{
	public final Part[][][] parts;
	
	public BlueprintBuilder(int xSize, int ySize, int zSize)
	{
		parts = new Part[ySize][][];
		for(int i = 0; i < ySize; ++i)
		{
			parts[i] = new Part[xSize][];
			for(int j = 0; j < xSize; ++j)
				parts[i][j] = new Part[zSize];
		}
	}
	
	public int cx, cy, cz;
	
	public BlueprintBuilder center(int x, int y, int z)
	{
		cx = x;
		cy = y;
		cz = z;
		
		return this;
	}
	
	public BlueprintBuilder part(int x, int y, int z, Object in, Object out)
	{
		parts[parts.length - (y + cy) - 1][x + cx][z + cz] = new Part(in, out);
		return this;
	}
	
	public BluePrint build(String research, ItemStack display)
	{
		NonNullList<ItemStack> ings = NonNullList.create();
		for(Part[][] p1 : parts)
			for(Part[] p2 : p1)
				for(Part p : p2)
					if(p != null)
					{
						Object o = p.getSource();
						if(o instanceof ItemStack)
							ings.add((ItemStack) o);
						else if(o instanceof Block)
						{
							if(o instanceof BlockFluidBase)
								ings.add(FluidUtil.getFilledBucket(new FluidStack(((BlockFluidBase) o).getFluid(), Fluid.BUCKET_VOLUME)));
							else
								ings.add(new ItemStack((Block) o));
						} else if(o instanceof IBlockState)
							((IBlockState) o).getBlock().getItemDropped((IBlockState) o, new Random(), 0);
					}
		stackTogether(ings);
		return new BluePrint(research, display, parts, ings.toArray(new ItemStack[ings.size()]));
	}
	
	/**
	 * Merges same items into one unlimited stack-size stack.
	 */
	public static void stackTogether(NonNullList<ItemStack> stacks)
	{
		for(int i = 0; i < stacks.size(); ++i)
		{
			ItemStack given = stacks.get(i);
			if(given.isEmpty())
			{
				stacks.remove(0);
				i = 0;
				continue;
			} else for(int j = 0; j < i; ++j)
			{
				ItemStack loc = stacks.get(j);
				
				if(loc.isItemEqual(given) && Objects.equals(loc.getTagCompound(), given.getTagCompound()))
				{
					given.grow(loc.getCount());
					stacks.remove(j);
					i = 0;
					break;
				}
			}
		}
	}
}