package com.endie.thaumicadditions.blocks.base;

import com.endie.thaumicadditions.TAReconstructed;
import com.pengu.hammercore.api.iTileBlock;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.utils.InventoryUtils;

public class BlockTARTile<T extends TileEntity> extends BlockTAR implements ITileEntityProvider, iTileBlock<T>
{
	protected final Class<T> tileClass;
	protected static boolean keepInventory = false;
	protected static boolean spillEssentia = true;
	
	public BlockTARTile(Material mat, Class<T> tc, String name)
	{
		super(mat, name);
		setHardness(2.0f);
		setResistance(20.0f);
		tileClass = tc;
	}
	
	@Override
	public Class<T> getTileClass()
	{
		return tileClass;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		if(this.tileClass == null)
			return null;
		try
		{
			return this.tileClass.newInstance();
		} catch(InstantiationException e)
		{
			TAReconstructed.LOG.catching(e);
		} catch(IllegalAccessException e)
		{
			TAReconstructed.LOG.catching(e);
		}
		return null;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		int ess;
		InventoryUtils.dropItems(worldIn, pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity != null && tileentity instanceof IEssentiaTransport && spillEssentia && !worldIn.isRemote && (ess = ((IEssentiaTransport) tileentity).getEssentiaAmount(EnumFacing.UP)) > 0)
			AuraHelper.polluteAura(worldIn, pos, ess, true);
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
	{
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
}