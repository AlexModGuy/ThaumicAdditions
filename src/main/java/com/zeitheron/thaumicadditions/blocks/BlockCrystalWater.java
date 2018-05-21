package com.zeitheron.thaumicadditions.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pengu.hammercore.api.iNoItemBlock;
import com.pengu.hammercore.net.HCNetwork;
import com.zeitheron.thaumicadditions.api.fx.TARParticleTypes;
import com.zeitheron.thaumicadditions.init.BlocksTAR;
import com.zeitheron.thaumicadditions.init.FluidsTAR;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import thaumcraft.common.blocks.world.ore.BlockCrystal;

public class BlockCrystalWater extends BlockFluidClassic implements iNoItemBlock
{
	public BlockCrystalWater()
	{
		super(FluidsTAR.CRYSTAL_WATER, Material.WATER);
		setUnlocalizedName("crystal_water");
		tickRate = 20;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		
		if(!world.isUpdateScheduled(pos, this))
			world.scheduleUpdate(pos, this, tickRate);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		
		// Skip non-source blocks.
		if(state.getValue(LEVEL) > 0)
			return;
		
		if(!world.isUpdateScheduled(pos, this))
			world.scheduleUpdate(pos, this, tickRate);
		
		if(!world.isRemote && rand.nextInt(50) == 0)
			for(int x = -1; x <= 1; x += 2)
				for(int z = -1; z <= 1; z += 2)
				{
					BlockPos ppos = pos.add(x, 1, z);
					if(isCrystalGrowable(world, ppos))
					{
						growCrystal(world, ppos);
						return;
					}
				}
	}
	
	public static boolean isCrystalGrowable(World world, BlockPos pos)
	{
		if(world.getBlockState(pos).getBlock() instanceof BlockCrystal)
		{
			IBlockState state = world.getBlockState(pos);
			int size = state.getValue(BlockCrystal.SIZE);
			
			int sources = 0;
			for(int x = -1; x <= 1; x += 2)
				for(int z = -1; z <= 1; z += 2)
				{
					BlockPos ppos = pos.add(x, -1, z);
					IBlockState ibs = world.getBlockState(ppos);
					if(ibs.getBlock() != BlocksTAR.CRYSTAL_WATER)
						return false;
					if(ibs.getValue(LEVEL) == 0)
						sources++;
				}
			
			return size < 3 && sources >= 2;
		}
		return false;
	}
	
	public static void growCrystal(World world, BlockPos pos)
	{
		if(isCrystalGrowable(world, pos))
		{
			IBlockState state = world.getBlockState(pos);
			int size = state.getValue(BlockCrystal.SIZE);
			int sx = world.rand.nextInt(2) * 2 - 1;
			int sz = world.rand.nextInt(2) * 2 - 1;
			
			List<BlockPos> poses = new ArrayList<>();
			for(int x = -1; x <= 1; x += 2)
				for(int z = -1; z <= 1; z += 2)
				{
					BlockPos ppos = pos.add(x, -1, z);
					IBlockState ibs = world.getBlockState(ppos);
					if(ibs.getBlock() == BlocksTAR.CRYSTAL_WATER && ibs.getValue(LEVEL) == 0)
						poses.add(ppos);
				}
			
			if(poses.isEmpty())
				return;
			
			BlockPos bp = poses.remove(world.rand.nextInt(poses.size()));
			if(world.rand.nextBoolean())
				world.setBlockToAir(bp);
			world.setBlockState(pos, state.withProperty(BlockCrystal.SIZE, size + 1), 3);
			
			HCNetwork.spawnParticle(world, TARParticleTypes.COLOR_CLOUD, bp.getX() + .5, bp.getY() + .7, bp.getZ() + .5, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 255, 255, 255, 1);
		}
	}
}