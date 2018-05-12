package com.endie.thaumicadditions.net;

import com.pengu.hammercore.HammerCore;
import com.pengu.hammercore.net.HCNetwork;
import com.pengu.hammercore.net.packetAPI.iPacket;
import com.pengu.hammercore.net.packetAPI.iPacketListener;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketBlockEvent implements iPacket, iPacketListener<PacketBlockEvent, iPacket>
{
	public int id, type;
	public long pos;
	
	public static void performBlockEvent(World world, BlockPos pos, int id, int type)
	{
		if(world.isRemote)
			world.addBlockEvent(pos, world.getBlockState(pos).getBlock(), id, type);
		else
		{
			PacketBlockEvent bpe = new PacketBlockEvent();
			bpe.id = id;
			bpe.type = type;
			bpe.pos = pos.toLong();
			HCNetwork.manager.sendToAllAround(bpe, new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128));
		}
	}
	
	@Override
	public iPacket onArrived(PacketBlockEvent packet, MessageContext context)
	{
		if(context.side == Side.CLIENT)
		{
			EntityPlayer player = HammerCore.renderProxy.getClientPlayer();
			if(player != null)
			{
				World wc = player.world;
				BlockPos pos = BlockPos.fromLong(packet.pos);
				wc.addBlockEvent(pos, wc.getBlockState(pos).getBlock(), packet.id, packet.type);
			}
		}
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("a", id);
		nbt.setInteger("b", type);
		nbt.setLong("c", pos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		id = nbt.getInteger("a");
		type = nbt.getInteger("b");
		pos = nbt.getLong("c");
	}
}