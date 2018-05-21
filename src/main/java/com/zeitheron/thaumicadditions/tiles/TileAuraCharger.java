package com.zeitheron.thaumicadditions.tiles;

import java.util.Random;

import com.pengu.hammercore.net.pkt.thunder.Thunder.Layer;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.tile.iTileDroppable;
import com.pengu.hammercore.utils.ColorHelper;
import com.pengu.hammercore.utils.FrictionRotator;
import com.zeitheron.thaumicadditions.client.ChainReaction;
import com.zeitheron.thaumicadditions.client.ClientChainReactor;
import com.zeitheron.thaumicadditions.client.runnable.ThunderRunnable;
import com.zeitheron.thaumicadditions.client.runnable.VisSparkleRunnable;
import com.zeitheron.thaumicadditions.init.KnowledgeTAR;
import com.zeitheron.thaumicadditions.net.PacketBlockEvent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXVisSparkle;
import thaumcraft.common.world.aura.AuraHandler;

public class TileAuraCharger extends TileSyncableTickable implements IEssentiaTransport, IAspectContainer, iTileDroppable
{
	public static final Aspect AURA = Aspect.AURA;
	
	protected int capacity = 10;
	public int amount;
	
	public final FrictionRotator rotator = new FrictionRotator();
	{
		rotator.degree = new Random().nextFloat() * 360;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	@Override
	public void tick()
	{
		rotator.friction = .25F;
		if(world.isRemote)
			rotator.update();
		
		float base = AuraHandler.getAuraBase(world, pos);
		float vis = AuraHandler.getVis(world, pos);
		
		if(world.isBlockIndirectlyGettingPowered(pos) > 0)
			return;
		
		if(amount > 0 && vis < base * 2.5)
		{
			if(world.isRemote)
				PacketBlockEvent.performBlockEvent(world, pos, 1, 0);
			
			if(atTickRate(100))
			{
				if(!world.isRemote)
				{
					AuraHandler.addVis(world, pos, 10);
					--amount;
					sendChangesToNearby();
				}
			}
			
			if(atTickRate(40))
				sendChangesToNearby();
		}
		
		if(!world.isRemote)
		{
			EnumFacing rf = EnumFacing.UP;
			IEssentiaTransport l = (IEssentiaTransport) ThaumcraftApiHelper.getConnectableTile(world, pos, EnumFacing.DOWN);
			
			if(l != null && l.canOutputTo(rf))
			{
				Aspect lasp = l.getEssentiaType(rf);
				if(lasp == AURA)
				{
					int amt = capacity - amount;
					int taken;
					if(amt > 0 && (taken = l.takeEssentia(lasp, amt, rf)) > 0)
					{
						amount += taken;
						sendChangesToNearby();
					}
				}
			}
		}
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("Amount", amount);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		amount = nbt.getInteger("Amount");
	}
	
	@Override
	public AspectList getAspects()
	{
		return new AspectList().add(AURA, amount);
	}
	
	@Override
	public void setAspects(AspectList asp)
	{
		amount = asp.getAmount(AURA);
		sendChangesToNearby();
	}
	
	@Override
	public boolean doesContainerAccept(Aspect a)
	{
		return a == AURA;
	}
	
	@Override
	public int addToContainer(Aspect a, int q)
	{
		if(a == AURA)
		{
			int ma = Math.min(q, capacity - amount);
			amount += ma;
			sendChangesToNearby();
			return ma;
		}
		return 0;
	}
	
	@Override
	public boolean takeFromContainer(Aspect a, int q)
	{
		return false;
	}
	
	@Override
	public boolean takeFromContainer(AspectList al)
	{
		return false;
	}
	
	@Override
	public boolean doesContainerContainAmount(Aspect a, int q)
	{
		return a == AURA && amount >= q;
	}
	
	@Override
	public boolean doesContainerContain(AspectList al)
	{
		return amount >= al.getAmount(AURA);
	}
	
	@Override
	public int containerContains(Aspect a)
	{
		return a == AURA ? amount : 0;
	}
	
	@Override
	public boolean isConnectable(EnumFacing f)
	{
		return canInputFrom(f);
	}
	
	@Override
	public boolean canInputFrom(EnumFacing f)
	{
		return f == EnumFacing.DOWN;
	}
	
	@Override
	public boolean canOutputTo(EnumFacing f)
	{
		return false;
	}
	
	@Override
	public void setSuction(Aspect var1, int var2)
	{
	}
	
	@Override
	public Aspect getSuctionType(EnumFacing f)
	{
		return canInputFrom(f) ? AURA : null;
	}
	
	@Override
	public int getSuctionAmount(EnumFacing f)
	{
		return canInputFrom(f) ? 128 : 0;
	}
	
	@Override
	public int takeEssentia(Aspect a, int q, EnumFacing f)
	{
		return 0;
	}
	
	@Override
	public int addEssentia(Aspect a, int q, EnumFacing f)
	{
		return f == EnumFacing.DOWN ? addToContainer(a, q) : 0;
	}
	
	@Override
	public Aspect getEssentiaType(EnumFacing f)
	{
		return canInputFrom(f) ? AURA : null;
	}
	
	@Override
	public int getEssentiaAmount(EnumFacing f)
	{
		return canInputFrom(f) ? amount : 0;
	}
	
	@Override
	public int getMinimumSuction()
	{
		return 0;
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if(id == 1)
		{
			if(world.isRemote)
			{
				int rays = 2;
				
				rotator.slowdown(.5F);
				
				for(int i = 0; i < rays; ++i)
				{
					float deg = rotator.getActualRotation(1) - 5 + i * (360F / rays);
					float rad = (float) Math.toRadians(deg);
					
					float sin = MathHelper.sin(rad);
					float cos = MathHelper.cos(rad);
					
					visSparkle(pos.getX() + cos * .3F + .5F, pos.getY() + .5F, pos.getZ() + sin * .3F + .5F, pos.getX() + .5F + cos * getRNG().nextFloat() * 6, pos.getY() + 1.5F + getRNG().nextFloat() * 4, pos.getZ() + .5F + sin * getRNG().nextFloat() * 6, AURA.getColor());
					
					// if(getRNG().nextInt(100) == 0)
					// {
					// Vec3d veca = new Vec3d(pos.getX() + .5F + cos, pos.getY()
					// + .5F + getRNG().nextFloat(), pos.getZ() + .5F + sin);
					// Vec3d vecb = new Vec3d(pos.getX() + .5F + cos, veca.y +
					// 1.5 + getRNG().nextFloat(), pos.getZ() + .5F + sin);
					// Vec3d vecc = new Vec3d(pos.getX() + .5F + cos * .3,
					// pos.getY() + .5F, pos.getZ() + .5F + sin * .3);
					//
					// Vec3d vecd = veca.add(veca.subtract(vecc));
					//
					// ChainReaction ra = new ChainReaction(new
					// ThunderRunnable(world, vecc, veca, getRNG().nextLong(),
					// 10, 10F, new Layer(771, AURA.getColor(), true), new
					// Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 0);
					//
					// ChainReaction rb = new ChainReaction(new
					// ThunderRunnable(world, veca, vecb, getRNG().nextLong(),
					// 10, 10F, new Layer(771, AURA.getColor(), true), new
					// Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 7);
					// ChainReaction rb_a = new ChainReaction(new
					// VisSparkleRunnable(veca.x, veca.y, veca.z, vecd.x,
					// vecd.y, vecd.z, KnowledgeTAR.CAELES.getColor()), 0);
					//
					// ChainReaction rc = new ChainReaction(new
					// ThunderRunnable(world, vecb, vecc, getRNG().nextLong(),
					// 10, 10F, new Layer(771, AURA.getColor(), true), new
					// Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 7);
					// ChainReaction rc_a = new ChainReaction(new
					// VisSparkleRunnable(vecb.x, vecb.y, vecb.z, vecb.x, vecb.y
					// + 2, vecb.z, KnowledgeTAR.CAELES.getColor()), 0);
					//
					// ClientChainReactor.REACTOR.addChain(ra, rb, rb_a, rc,
					// rc_a);
					// }
				}
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void visSparkle(float x, float y, float z, float x2, float y2, float z2, int color)
	{
		FXVisSparkle fb = new FXVisSparkle(FXDispatcher.INSTANCE.getWorld(), x, y, z, x2, y2, z2);
		fb.setRBGColorF(ColorHelper.getRed(color), ColorHelper.getGreen(color), ColorHelper.getBlue(color));
		ParticleEngine.addEffect(FXDispatcher.INSTANCE.getWorld(), fb);
	}
	
	@Override
	public void createDrop(EntityPlayer player, World world, BlockPos pos)
	{
		if(!world.isRemote)
			AuraHelper.polluteAura(world, pos, amount, true);
	}
}