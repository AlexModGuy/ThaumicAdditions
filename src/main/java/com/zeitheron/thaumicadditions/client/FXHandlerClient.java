package com.zeitheron.thaumicadditions.client;

import java.util.Random;

import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.utils.FrictionRotator;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.proxy.FXHandler;
import com.zeitheron.thaumicadditions.tiles.TileAuraDisperser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class FXHandlerClient extends FXHandler
{
	@Override
	public void spawnAuraDisperserFX(TileAuraDisperser tile)
	{
		if(tile == null)
			return;
		
		if(!tile.getWorld().isRemote)
		{
			// Invoke server-side code.
			super.spawnAuraDisperserFX(tile);
			return;
		}
		
		Random random = tile.getRNG();
		EnumFacing face = WorldUtil.getFacing(tile.getLocation().getState());
		BlockPos blockPosIn = tile.getPos();
		
		int data = AspectUtil.getColor(tile.aspects, true);
		
		float f5 = (float) (data >> 16 & 255) / 255.0F;
		float f = (float) (data >> 8 & 255) / 255.0F;
		float f1 = (float) (data >> 0 & 255) / 255.0F;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		AxisAlignedBB aabb = new AxisAlignedBB(blockPosIn).offset(face.getFrontOffsetX() * 5, face.getFrontOffsetY() * 4, face.getFrontOffsetZ() * 5).grow(4, 3, 4);
		
		for(int j3 = 0; j3 < 100; ++j3)
		{
			double d18 = random.nextDouble() * 8D;
			double d21 = Math.toRadians((j3 / 100D * 360D) % 360D);
			
			double sin = Math.sin(d21) / 2.25;
			double cos = Math.cos(d21) / 2.25;
			
			double x = aabb.minX + (aabb.maxX - aabb.minX) * random.nextDouble();
			double y = aabb.minY + (aabb.maxY - aabb.minY) * random.nextDouble();
			double z = aabb.minZ + (aabb.maxZ - aabb.minZ) * random.nextDouble();
			
			Particle particle1 = mc.effectRenderer.spawnEffectParticle((random.nextBoolean() ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.SPELL).getParticleID(), x, y, z, 0, 0, 0);
			
			if(particle1 != null)
			{
				double d3 = particle1.posX - mc.player.posX;
				double d4 = particle1.posY - mc.player.posY;
				double d5 = particle1.posZ - mc.player.posZ;
				if(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)
					particle1.setExpired();
				
				float f4 = 0.75F + random.nextFloat() * 0.25F;
				particle1.setRBGColorF(f5 * f4, f * f4, f1 * f4);
				particle1.multiplyVelocity((float) d18);
			}
		}
	}
	
	@Override
	public void renderMob(Entity entity, FrictionRotator rotator, double posX, double posY, double posZ, float partialTicks)
	{
		if(entity != null)
		{
			GlStateManager.pushMatrix();
			float f = 0.53125F / Math.max(entity.width, entity.height);
			GlStateManager.translate(0.0F, 0.4F, 0.0F);
			GlStateManager.rotate(rotator.getActualRotation(partialTicks), 0F, 1F, 0F);
			GlStateManager.translate(0F, -.2F, 0F);
			GlStateManager.rotate(-30F, 1F, 0F, 0F);
			GlStateManager.scale(f, f, f);
			entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
			Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
			GlStateManager.popMatrix();
		}
	}
}