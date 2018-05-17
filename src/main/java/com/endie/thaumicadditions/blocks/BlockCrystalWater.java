package com.endie.thaumicadditions.blocks;

import java.util.Random;

import com.endie.thaumicadditions.api.fx.TARParticleTypes;
import com.endie.thaumicadditions.init.BlocksTAR;
import com.endie.thaumicadditions.init.FluidsTAR;
import com.pengu.hammercore.api.iNoItemBlock;
import com.pengu.hammercore.net.HCNetwork;

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
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		
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
			
			for(int x = -1; x <= 1; x += 2)
				for(int z = -1; z <= 1; z += 2)
				{
					BlockPos ppos = pos.add(x, -1, z);
					IBlockState ibs = world.getBlockState(ppos);
					if(ibs.getBlock() != BlocksTAR.CRYSTAL_WATER || ibs.getValue(LEVEL) > 0)
						return false;
				}
			
			return size < 3;
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
			BlockPos bp = pos.add(sx, -1, sz);
			world.setBlockToAir(bp);
			world.setBlockState(pos, state.withProperty(BlockCrystal.SIZE, size + 1), 3);
			
			HCNetwork.spawnParticle(world, TARParticleTypes.COLOR_CLOUD, bp.getX() + .5, bp.getY() + .7, bp.getZ() + .5, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 255, 255, 255, 1);
		}
	}
}