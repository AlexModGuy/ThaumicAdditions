package com.endie.thaumicadditions.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockTAR extends Block
{
	public BlockTAR(Material material, String name)
	{
		super(material);
		setUnlocalizedName(name);
		setResistance(2.0f);
		setHardness(1.5f);
	}
	
	public BlockTAR(Material mat, String name, SoundType st)
	{
		this(mat, name);
		this.setSoundType(st);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
}