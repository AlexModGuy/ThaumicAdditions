package com.endie.thaumicadditions.tiles;

import java.util.Random;

import com.endie.thaumicadditions.api.AspectUtil;
import com.pengu.hammercore.common.utils.ItemStackUtil;
import com.pengu.hammercore.common.utils.SoundUtil;
import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.utils.FrictionRotator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import thaumcraft.common.blocks.world.ore.BlockCrystal;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.world.aura.AuraHandler;

public class TileCrystalBore extends TileSyncableTickable
{
	public int minCrystals = 1;
	public final FrictionRotator rotator = new FrictionRotator();
	{
		rotator.degree = new Random().nextFloat() * 360;
	}
	
	public EnumFacing face;
	public EnumFacing oldFace;
	
	public int boring = 0;
	
	@Override
	public void tick()
	{
		rotator.friction = .25F;
		rotator.update();
		
		oldFace = face;
		face = WorldUtil.getFacing(loc.getState());
		
		if(canBore())
		{
			boolean isDraneable = AuraHandler.drainVis(world, pos, 1F, true) >= .02F;
			if(isDraneable)
			{
				++boring;
				rotator.speedup(.5F);
				AuraHandler.drainVis(world, pos, .02F, false);
				if(world.isRemote && atTickRate(3))
					SoundUtil.playSoundEffect(loc, "thaumcraft:grind", .5F, 1F, SoundCategory.BLOCKS);
			}
			
			if(boring >= 100)
			{
				boring = 0;
				ItemStack bored = bore();
				if(!bored.isEmpty())
				{
					EnumFacing of = face.getOpposite();
					BlockPos opos = pos.offset(of);
					if(!(bored = ItemStackUtil.inject(bored, world.getTileEntity(opos), face)).isEmpty())
					{
						Random rand = world.rand;
						if(!world.isRemote)
						{
							EntityItem ei = new EntityItem(world, pos.getX() + .5 + of.getFrontOffsetX() * .6, pos.getY() + .5 + of.getFrontOffsetY() * .6, pos.getZ() + .5 + of.getFrontOffsetZ() * .6, bored.copy());
							ei.motionX = of.getFrontOffsetX() * rand.nextDouble() * 0.35;
							ei.motionY = of.getFrontOffsetY() * rand.nextDouble() * 0.35;
							ei.motionZ = of.getFrontOffsetZ() * rand.nextDouble() * 0.35;
							world.spawnEntity(ei);
						}
					}
				}
				sendChangesToNearby();
			}
		}
	}
	
	public boolean canBore()
	{
		IBlockState state = world.getBlockState(pos.offset(face));
		return getLocation().getRedstone() == 0 && state.getBlock() instanceof BlockCrystal && state.getValue(BlockCrystal.SIZE) >= minCrystals;
	}
	
	public ItemStack bore()
	{
		IBlockState state = world.getBlockState(pos.offset(face));
		int co;
		if(state.getBlock() instanceof BlockCrystal && (co = state.getValue(BlockCrystal.SIZE)) >= minCrystals - 1)
		{
			state = state.withProperty(BlockCrystal.SIZE, co - 1);
			world.setBlockState(pos.offset(face), state);
			SoundUtil.playSoundEffect(world, SoundsTC.crystal.getRegistryName().toString(), pos, .3F, .8F, SoundCategory.BLOCKS);
			return AspectUtil.crystalEssence(((BlockCrystal) state.getBlock()).aspect);
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("MinCrystals", minCrystals);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		minCrystals = nbt.getInteger("MinCrystals");
	}
}