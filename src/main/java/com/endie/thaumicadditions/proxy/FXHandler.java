package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.net.fxh.FXHPacket;
import com.endie.thaumicadditions.tiles.TileAuraDisperser;
import com.pengu.hammercore.net.HCNetwork;

import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class FXHandler
{
	public void spawnAuraDisperserFX(TileAuraDisperser tile)
	{
		if(tile == null)
			return;
		HCNetwork.manager.sendToAllAround(new FXHPacket(tile.getPos(), 0), new TargetPoint(tile.getWorld().provider.getDimension(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), 32));
	}
}