package com.endie.thaumicadditions.tiles;

import com.endie.thaumicadditions.client.ChainReaction;
import com.endie.thaumicadditions.client.ClientChainReactor;
import com.endie.thaumicadditions.client.runnable.ThunderRunnable;
import com.endie.thaumicadditions.client.runnable.VisSparkleRunnable;
import com.endie.thaumicadditions.init.KnowledgeTAR;
import com.endie.thaumicadditions.net.PacketBlockEvent;
import com.pengu.hammercore.HammerCore;
import com.pengu.hammercore.net.pkt.thunder.Thunder.Layer;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.utils.ColorHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXVisSparkle;
import thaumcraft.common.world.aura.AuraHandler;

public class TileAuraCharger extends TileSyncableTickable implements IEssentiaTransport, IAspectContainer
{
	public static final Aspect AURA = Aspect.AURA;
	
	protected int capacity = 10;
	public int amount;
	
	public int getCapacity()
	{
		return capacity;
	}
	
	@Override
	public void tick()
	{
		float base = AuraHandler.getAuraBase(world, pos);
		float vis = AuraHandler.getVis(world, pos);
		
		if(amount > 0 && vis < base * 2)
		{
			if(world.isRemote && atTickRate(10))
				PacketBlockEvent.performBlockEvent(world, pos, 1, 0);
			
			if(atTickRate(10))
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
				
				for(int i = 0; i < rays; ++i)
				{
					float deg = (System.currentTimeMillis() % 3600L / 10F + (i * (360F / rays))) % 360F;
					float rad = (float) Math.toRadians(deg);
					
					float sin = MathHelper.sin(rad) * 2;
					float cos = MathHelper.cos(rad) * 2;
					
					visSparkle(pos.getX() + .5F, pos.getY(), pos.getZ() + .5F, pos.getX() + .5F + sin * getRNG().nextFloat() * 6, pos.getY() + 1.5F + getRNG().nextFloat() * 8, pos.getZ() + .5F + cos * getRNG().nextFloat() * 6, AURA.getColor());
					
					if(getRNG().nextBoolean())
					{
						Vec3d veca = new Vec3d(pos.getX() + .5F + cos, pos.getY() + .5F + getRNG().nextFloat(), pos.getZ() + .5F + sin);
						Vec3d vecb = new Vec3d(pos.getX() + .5F + cos, veca.y + 1.5 + getRNG().nextFloat(), pos.getZ() + .5F + sin);
						Vec3d vecc = new Vec3d(pos.getX() + .5F + cos * .1, pos.getY() + .25F, pos.getZ() + .5F + sin * .1);
						
						ChainReaction ra = new ChainReaction(new ThunderRunnable(world, vecc, veca, getRNG().nextLong(), 10, 0F, new Layer(771, AURA.getColor(), true), new Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 0);
						
						ChainReaction rb = new ChainReaction(new ThunderRunnable(world, veca, vecb, getRNG().nextLong(), 10, 0F, new Layer(771, AURA.getColor(), true), new Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 7);
						
						ChainReaction rc = new ChainReaction(new ThunderRunnable(world, vecb, vecc, getRNG().nextLong(), 10, 1F, new Layer(771, AURA.getColor(), true), new Layer(771, KnowledgeTAR.CAELES.getColor(), true)), 7);
						ChainReaction rc_a = new ChainReaction(new VisSparkleRunnable(vecb.x, vecb.y, vecb.z, vecb.x, vecb.y + 2, vecb.z, KnowledgeTAR.CAELES.getColor()), 0);
						
						ClientChainReactor.REACTOR.addChain(ra, rb, rc, rc_a);
					}
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
}