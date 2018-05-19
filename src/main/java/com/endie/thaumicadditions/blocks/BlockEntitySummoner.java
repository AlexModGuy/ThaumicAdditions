package com.endie.thaumicadditions.blocks;

import com.endie.thaumicadditions.init.ItemsTAR;
import com.endie.thaumicadditions.tiles.TileEntitySummoner;
import com.pengu.hammercore.common.blocks.base.BlockDeviceHC;
import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.net.HCNetwork;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEntitySummoner extends BlockDeviceHC<TileEntitySummoner>
{
	public BlockEntitySummoner()
	{
		super(Material.IRON, TileEntitySummoner.class, "entity_summoner");
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntitySummoner s = WorldUtil.cast(worldIn.getTileEntity(pos), TileEntitySummoner.class);
		if(s != null && hand == EnumHand.MAIN_HAND)
		{
			ItemStack hit = playerIn.getHeldItem(hand);
			ItemStack tile = s.sample;
			
			if(!tile.isEmpty())
			{
				if(!playerIn.addItemStackToInventory(tile))
					WorldUtil.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, tile);
				s.sample = ItemStack.EMPTY;
				s.sendChangesToNearby();
				HCNetwork.swingArm(playerIn, hand);
			} else if(!hit.isEmpty() && hit.getItem() == ItemsTAR.ENTITY_CELL && hit.hasTagCompound() && hit.getTagCompound().hasKey("Entity", NBT.TAG_COMPOUND))
			{
				s.sample = hit.copy();
				s.sample.setCount(1);
				hit.shrink(1);
				s.sendChangesToNearby();
				HCNetwork.swingArm(playerIn, hand);
			}
		}
		
		return false;
	}
}