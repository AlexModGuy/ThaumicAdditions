package com.endie.thaumicadditions.blocks;

import java.util.Random;

import com.endie.thaumicadditions.init.FluidsTAR;
import com.pengu.hammercore.api.iNoItemBlock;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockCrystalWater extends BlockFluidClassic implements iNoItemBlock
{
	public BlockCrystalWater()
	{
		super(FluidsTAR.CRYSTAL_WATER, Material.WATER);
		setUnlocalizedName("crystal_water");
		tickRate = 20;
	}
	
	@Override
	public Vec3d getFlowVector(IBlockAccess world, BlockPos pos)
	{
		return super.getFlowVector(world, pos);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		if(world.rand.nextInt(4000) == 0)
		{
			
		}
	}
}