package com.endie.thaumicadditions.blocks;

import com.endie.thaumicadditions.blocks.base.BlockTARDevice;
import com.endie.thaumicadditions.tiles.TileAspectCombiner;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import thaumcraft.common.blocks.IBlockFacingHorizontal;

public class BlockAspectCombiner extends BlockTARDevice<TileAspectCombiner> implements IBlockFacingHorizontal
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