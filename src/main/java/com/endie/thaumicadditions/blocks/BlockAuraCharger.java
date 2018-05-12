package com.endie.thaumicadditions.blocks;

import com.endie.thaumicadditions.blocks.base.BlockTARDevice;
import com.endie.thaumicadditions.tiles.TileAuraCharger;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

public class BlockAuraCharger extends BlockTARDevice<TileAuraCharger>
{
	public BlockAuraCharger()
	{
		super(Material.ROCK, TileAuraCharger.class, "aura_charger");
		setSoundType(SoundType.STONE);
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