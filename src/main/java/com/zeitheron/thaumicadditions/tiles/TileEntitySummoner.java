package com.zeitheron.thaumicadditions.tiles;

import java.util.UUID;

import com.pengu.hammercore.common.utils.WorldUtil;
import com.pengu.hammercore.tile.TileSyncableTickable;
import com.pengu.hammercore.tile.iTileDroppable;
import com.pengu.hammercore.utils.FrictionRotator;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.init.ItemsTAR;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.common.lib.events.EssentiaHandler;

public class TileEntitySummoner extends TileSyncableTickable implements IAspectContainer, iTileDroppable
{
	public ItemStack sample = ItemStack.EMPTY;
	public AspectList accumulated = new AspectList();
	public AspectList missing = new AspectList();
	public int cooldown = 0;
	private Entity cachedEntity;
	
	public final FrictionRotator rotator = new FrictionRotator();
	
	@SideOnly(Side.CLIENT)
	public Entity getCachedEntity()
	{
		boolean cs = canSpawn();
		if(cachedEntity == null && cs)
		{
			NBTTagCompound nbt = sample.getTagCompound().getCompoundTag("Entity").getCompoundTag("Data");
			
			// Reset mob's properties
			nbt.removeTag("Pos");
			nbt.removeTag("Motion");
			nbt.removeTag("Rotation");
			nbt.removeTag("FallDistance");
			nbt.removeTag("Fire");
			nbt.removeTag("UUID");
			nbt.removeTag("Health");
			
			nbt.setString("id", sample.getTagCompound().getCompoundTag("Entity").getString("Id"));
			cachedEntity = AnvilChunkLoader.readWorldEntity(nbt, world, false);
			if(nbt.getSize() == 1 && this.cachedEntity instanceof EntityLiving)
				((EntityLiving) cachedEntity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(cachedEntity)), null);
		}
		if(!cs)
			cachedEntity = null;
		return cachedEntity;
	}
	
	@Override
	public void tick()
	{
		rotator.friction = .25F;
		rotator.update();
		
		if(cooldown > 0)
			--cooldown;
		
		if(accumulated == null)
			accumulated = new AspectList();
		AspectList requirements = null;
		
		rotator.speedup(.2F);
		
		if(canSpawn() && (requirements = getAspectsForDNA(sample)).visSize() > 0)
		{
			missing = AspectUtil.getMissing(accumulated, requirements);
			
			if(missing.visSize() == 0)
			{
				if(cooldown == 0 && !world.isRemote && atTickRate(20) && performSpawn())
				{
					cooldown += 180 + getRNG().nextInt(200);
					accumulated.remove(requirements);
					
					rotator.speedup(10F);
					sendChangesToNearby();
				}
			} else if(!world.isRemote && missing != null && missing.size() > 0 && atTickRate(3))
			{
				Aspect[] as = missing.getAspects();
				Aspect a = as[as.length - 1];
				
				if(EssentiaHandler.drainEssentia(this, a, null, 8, 1))
				{
					accumulated.add(a, 1);
					rotator.speedup(1F);
					sendChangesToNearby();
				}
			}
		} else
			missing = new AspectList();
	}
	
	@Override
	public void createDrop(EntityPlayer player, World world, BlockPos pos)
	{
		if(!sample.isEmpty())
			WorldUtil.spawnItemStack(world, pos, sample);
		AuraHelper.polluteAura(world, pos, accumulated.visSize(), true);
	}
	
	public boolean performSpawn()
	{
		int spawnCount = 1 + getRNG().nextInt(4);
		double spawnRange = 4;
		int maxNearbyEntities = 4;
		
		int spawned = 0;
		
		for(int i = 0; i < spawnCount; ++i)
		{
			NBTTagCompound nbttagcompound = sample.getTagCompound().getCompoundTag("Entity").getCompoundTag("Data");
			
			// Reset mob's properties
			nbttagcompound.removeTag("Pos");
			nbttagcompound.removeTag("Motion");
			nbttagcompound.removeTag("Rotation");
			nbttagcompound.removeTag("FallDistance");
			nbttagcompound.removeTag("Fire");
			nbttagcompound.removeTag("UUID");
			nbttagcompound.removeTag("Health");
			
			nbttagcompound.setString("id", sample.getTagCompound().getCompoundTag("Entity").getString("Id"));
			UUID uuid = UUID.randomUUID();
			nbttagcompound.setString("UUID", uuid.toString());
			
			NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);
			int j = nbttaglist.tagCount();
			double d0 = j >= 1 ? nbttaglist.getDoubleAt(0) : (double) pos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double) spawnRange + 0.5D;
			double d1 = j >= 2 ? nbttaglist.getDoubleAt(1) : (double) (pos.getY() + world.rand.nextInt(3) - 1);
			double d2 = j >= 3 ? nbttaglist.getDoubleAt(2) : (double) pos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double) spawnRange + 0.5D;
			Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0, d1, d2, false);
			
			if(entity == null)
				return false;
			
			entity.setUniqueId(uuid);
			
			WorldServer ws = WorldUtil.cast(world, WorldServer.class);
			for(int k = 0; k < 16 && ws != null && ws.getEntityFromUuid(entity.getUniqueID()) != null; ++k)
				entity.setUniqueId(UUID.randomUUID());
			
			int k = world.getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(pos).grow(spawnRange)).size();
			
			if(k >= maxNearbyEntities)
				break;
			
			EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;
			entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextFloat() * 360.0F, 0.0F);
			
			if(entityliving != null)
				entityliving.spawnExplosionParticle();
			
			if(!world.isRemote)
			{
				AnvilChunkLoader.spawnEntity(entity, world);
				
				++spawned;
			}
		}
		
		if(spawned > 0)
			world.playEvent(2004, pos, 0);
		
		return spawned > 0;
	}
	
	public static AspectList getAspectsForDNA(ItemStack sample)
	{
		if(!sample.isEmpty() && sample.getItem() == ItemsTAR.ENTITY_CELL && sample.hasTagCompound() && sample.getTagCompound().hasKey("Entity", NBT.TAG_COMPOUND))
		{
			NBTTagCompound nbt = sample.getTagCompound().getCompoundTag("Entity");
			EntityEntry entry = GameRegistry.findRegistry(EntityEntry.class).getValue(new ResourceLocation(nbt.getString("Id")));
			NBTTagCompound tc = nbt.getCompoundTag("Data");
			
			AspectList tags = new AspectList();
			if(entry != null)
				for(ThaumcraftApi.EntityTags et : CommonInternals.scanEntities)
				{
					if(!et.entityName.equals(entry.getName()))
						continue;
					
					if(et.nbts == null || et.nbts.length == 0)
					{
						tags = et.aspects;
						continue;
					}
					
					for(ThaumcraftApi.EntityTagsNBT enbt : et.nbts)
					{
						if(!tc.hasKey(enbt.name) || !ThaumcraftApiHelper.getNBTDataFromId(tc, tc.getTagId(enbt.name), enbt.name).equals(enbt.value))
							continue;
					}
					
					tags.add(et.aspects);
				}
			
			return tags;
		} else
			return new AspectList();
	}
	
	public boolean canSpawn()
	{
		return !sample.isEmpty() && sample.getItem() == ItemsTAR.ENTITY_CELL && sample.hasTagCompound() && sample.getTagCompound().hasKey("Entity", NBT.TAG_COMPOUND);
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt)
	{
		nbt.setTag("Accum", AspectUtil.writeALToNBT(accumulated, new NBTTagCompound()));
		nbt.setTag("Sample", sample.serializeNBT());
		nbt.setInteger("Cooldown", cooldown);
		nbt.setTag("Rotator", writeFrictionRotatorToNBT(rotator, new NBTTagCompound()));
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt)
	{
		readFrictionRotatorFromNBT(nbt.getCompoundTag("Rotator"), rotator);
		cooldown = nbt.getInteger("Cooldown");
		accumulated = new AspectList();
		if(nbt.hasKey("Accum"))
			accumulated.readFromNBT(nbt.getCompoundTag("Accum"));
		sample = new ItemStack(nbt.getCompoundTag("Sample"));
	}
	
	@Override
	public AspectList getAspects()
	{
		return missing != null && missing.size() > 0 ? missing : null;
	}
	
	@Override
	public void setAspects(AspectList var1)
	{
	}
	
	@Override
	public boolean doesContainerAccept(Aspect var1)
	{
		return false;
	}
	
	@Override
	public int addToContainer(Aspect a, int q)
	{
		if(missing != null)
		{
			int max = Math.min(missing.getAmount(a), q);
			if(accumulated != null)
			{
				accumulated.add(a, max);
				return max;
			}
		}
		return 0;
	}
	
	@Override
	public boolean takeFromContainer(Aspect var1, int var2)
	{
		return false;
	}
	
	@Override
	public boolean takeFromContainer(AspectList var1)
	{
		return false;
	}
	
	@Override
	public boolean doesContainerContainAmount(Aspect var1, int var2)
	{
		return false;
	}
	
	@Override
	public boolean doesContainerContain(AspectList var1)
	{
		return false;
	}
	
	@Override
	public int containerContains(Aspect var1)
	{
		return 0;
	}
	
	public static FrictionRotator readFrictionRotatorFromNBT(NBTTagCompound nbt, FrictionRotator rotator)
	{
		rotator.currentSpeed = nbt.getFloat("CurrentSpeed");
		rotator.degree = nbt.getFloat("Degree");
		rotator.friction = nbt.getFloat("Friction");
		rotator.prevDegree = nbt.getFloat("PrevDegree");
		rotator.speed = nbt.getFloat("Speed");
		return rotator;
	}
	
	public static NBTTagCompound writeFrictionRotatorToNBT(FrictionRotator rotator, NBTTagCompound nbt)
	{
		nbt.setFloat("CurrentSpeed", rotator.currentSpeed);
		nbt.setFloat("Degree", rotator.degree);
		nbt.setFloat("Friction", rotator.friction);
		nbt.setFloat("PrevDegree", rotator.prevDegree);
		nbt.setFloat("Speed", rotator.speed);
		
		return nbt;
	}
}