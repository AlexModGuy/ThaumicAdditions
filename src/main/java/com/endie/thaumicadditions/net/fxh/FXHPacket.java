package com.endie.thaumicadditions.net.fxh;

import com.endie.thaumicadditions.TAReconstructed;
import com.endie.thaumicadditions.tiles.TileAuraDisperser;
import com.pengu.hammercore.HammerCore;
import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.net.packetAPI.iPacket;
import com.pengu.hammercore.net.packetAPI.iPacketListener;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class FXHPacket implements iPacket, iPacketListener<FXHPacket, iPacket>
{
	BlockPos pos;
	int sub;
	
	public FXHPacket(BlockPos pos, int sub)
	{
		this.pos = pos;
		this.sub = sub;
	}
	
	public FXHPacket()
	{
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("p", pos.toLong());
		nbt.setInteger("s", sub);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		pos = BlockPos.fromLong(nbt.getLong("p"));
		sub = nbt.getInteger("s");
	}
	
	@Override
	public iPacket onArrived(FXHPacket packet, MessageContext context)
	{
		if(context.side == Side.CLIENT)
		{
			BlockPos pos = packet.pos;
			EntityPlayer ep = HammerCore.renderProxy.getClientPlayer();
			
			switch(packet.sub)
			{
			case 0:
				TileAuraDisperser tad = WorldUtil.cast(ep.world.getTileEntity(pos), TileAuraDisperser.class);
				if(tad != null)
					TAReconstructed.proxy.getFX().spawnAuraDisperserFX(tad);
			break;
		
			default:
			break;
			}
		}
		
		return null;
	}
}