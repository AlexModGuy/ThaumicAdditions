package com.endie.thaumicadditions.blocks;

import com.endie.thaumicadditions.tiles.TileAspectCombiner;
import com.pengu.hammercore.common.blocks.base.BlockDeviceHC;
import com.pengu.hammercore.common.blocks.base.iBlockHorizontal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAspectCombiner extends BlockDeviceHC<TileAspectCombiner> implements iBlockHorizontal
{
	public BlockAspectCombiner()
	{
		super(Material.WOOD, TileAspectCombiner.class, "aspect_combiner");
		setSoundType(SoundType.WOOD);
		setHardness(2F);
		setHarvestLevel("axe", 0);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}