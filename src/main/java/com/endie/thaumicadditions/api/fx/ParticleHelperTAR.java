package com.endie.thaumicadditions.api.fx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.lang3.ArrayUtils;

import com.pengu.hammercore.net.HCNetwork;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ParticleHelperTAR
{
	public static void spawnItemCrack(World world, double x, double y, double z, double motionX, double motionY, double motionZ, ItemStack stack)
	{
		HCNetwork.spawnParticle(world, TARParticleTypes.COLORED_ITEM_CRACK, x, y, z, motionX, motionY, motionZ, toIA(stack.serializeNBT()));
	}
	
	public static int[] toIA(NBTTagCompound comp)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			CompressedStreamTools.write(comp, new DataOutputStream(baos));
		} catch(IOException e)
		{
			// Shouldn't happen, be silent.
		}
		byte[] data = baos.toByteArray();
		while(data.length % 4 != 0)
			data = ArrayUtils.add(data, (byte) 0);
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		int[] ret = new int[data.length / 4];
		buffer.asIntBuffer().get(ret);
		return ret;
	}
	
	public static NBTTagCompound toNBT(int[] ia)
	{
		ByteBuffer buffer = ByteBuffer.allocate(ia.length * 4).order(ByteOrder.LITTLE_ENDIAN);
		buffer.asIntBuffer().put(ia);
		buffer.flip();
		byte[] data = buffer.array();
		try
		{
			return CompressedStreamTools.read(new DataInputStream(new ByteArrayInputStream(data)));
		} catch(IOException e)
		{
			// Shouldn't happen, but return new tag compound.
			return new NBTTagCompound();
		}
	}
}