package com.zeitheron.thaumicadditions.items;

import java.util.ArrayList;
import java.util.List;

import com.pengu.hammercore.common.utils.SoundUtil;
import com.pengu.hammercore.event.FoodEatenEvent;
import com.zeitheron.thaumicadditions.api.AspectUtil;
import com.zeitheron.thaumicadditions.api.EdibleAspect;
import com.zeitheron.thaumicadditions.init.ItemsTAR;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.SoundsTC;

public class ItemSaltEssence extends Item implements IEssentiaContainerItem
{
	public ItemSaltEssence()
	{
		setUnlocalizedName("salt_essence");
		setMaxStackSize(64);
		setHasSubtypes(true);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void foodEaten(FoodEatenEvent e)
	{
		if(e.getEntityPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) e.getEntityPlayer();
			ItemStack item = e.getOriginStack();
			AspectList al;
			if(!item.isEmpty() && item.getItem() instanceof ItemFood && (al = EdibleAspect.getSalt(item)).visSize() > 0)
				EdibleAspect.execute(mp, al);
		}
	}
	
	@SubscribeEvent
	public void itemPickupEvent(EntityItemPickupEvent ev)
	{
		float fall = ev.getEntityPlayer().fallDistance;
		if(ev.getEntityPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) ev.getEntityPlayer();
			if(mp.getEntityBoundingBox() != null && fall >= 3F && ThaumcraftCapabilities.knowsResearch(mp, "TAR_ESSENCE_SALT@2"))
			{
				EntityItem e = ev.getItem();
				ItemStack stack = e.getItem();
				if(!stack.isEmpty() && stack.getItem() == ItemsTC.crystalEssence)
				{
					NBTTagCompound nbt = e.getEntityData();
					float crack = nbt.getFloat("CrystalCrack");
					crack += fall - 1;
					
					int shrinks = 0;
					while(crack > 4 && !stack.isEmpty())
					{
						++shrinks;
						crack -= 4;
						stack.shrink(1);
						ItemStack salt = new ItemStack(ItemsTAR.SALT_ESSENCE);
						salt.setTagCompound(stack.getTagCompound().copy());
						EntityItem ni = new EntityItem(e.world, e.posX, e.posY, e.posZ, salt);
						ni.motionX = e.motionX;
						ni.motionY = e.motionY;
						ni.motionZ = e.motionZ;
						if(stack.isEmpty())
						{
							e.setDead();
							return;
						}
						e.world.spawnEntity(ni);
						SoundUtil.playSoundEffect(e.world, SoundsTC.crystal.getRegistryName().toString(), e.getPosition(), 1F, .8F, SoundCategory.PLAYERS);
					}
					
					nbt.setFloat("CrystalCrack", crack);
					
					if(shrinks == 0)
						SoundUtil.playSoundEffect(e.world, SoundsTC.crystal.getRegistryName().toString(), e.getPosition(), 1F, .2F, SoundCategory.PLAYERS);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void handleFlyingFall(PlayerFlyableFallEvent e)
	{
		// if(e.getEntityPlayer() instanceof EntityPlayerMP)
		// invokeFall((EntityPlayerMP) e.getEntityPlayer(), e.getDistance());
	}
	
	@SubscribeEvent
	public void handleFall(LivingFallEvent e)
	{
		// if(e.getEntityLiving() instanceof EntityPlayerMP)
		// invokeFall((EntityPlayerMP) e.getEntityLiving(), e.getDistance());
	}
	
	public void invokeFall(EntityPlayerMP mp, float fall)
	{
		if(mp.getEntityBoundingBox() != null && fall >= 3F && ThaumcraftCapabilities.knowsResearch(mp, "TAR_ESSENCE_SALT@2"))
		{
			List<EntityItem> items = mp.world.getEntitiesWithinAABB(EntityItem.class, mp.getEntityBoundingBox().grow(1));
			
			if(items != null)
				eic: for(EntityItem e : items)
				{
					ItemStack stack = e.getItem();
					if(!stack.isEmpty() && stack.getItem() == ItemsTC.crystalEssence)
					{
						NBTTagCompound nbt = e.getEntityData();
						float crack = nbt.getFloat("CrystalCrack");
						crack += fall - 1;
						e.setPickupDelay(200);
						
						int shrinks = 0;
						while(crack > 4 && !stack.isEmpty())
						{
							++shrinks;
							crack -= 4;
							stack.shrink(1);
							ItemStack salt = new ItemStack(ItemsTAR.SALT_ESSENCE);
							salt.setTagCompound(stack.getTagCompound().copy());
							EntityItem ni = new EntityItem(e.world, e.posX, e.posY, e.posZ, salt);
							ni.motionX = e.motionX;
							ni.motionY = e.motionY;
							ni.motionZ = e.motionZ;
							if(stack.isEmpty())
							{
								e.setDead();
								continue eic;
							}
							e.world.spawnEntity(ni);
							SoundUtil.playSoundEffect(e.world, SoundsTC.crystal.getRegistryName().toString(), e.getPosition(), 1F, .8F, SoundCategory.PLAYERS);
						}
						
						e.setItem(stack);
						nbt.setFloat("CrystalCrack", crack);
						
						if(shrinks == 0)
							SoundUtil.playSoundEffect(e.world, SoundsTC.crystal.getRegistryName().toString(), e.getPosition(), 1F, .2F, SoundCategory.PLAYERS);
					}
				}
		}
	}
	
	public int getItemColor(ItemStack stack, int layer)
	{
		if(layer == 0)
		{
			AspectList al = getAspects(stack);
			return AspectUtil.getColor(al, true);
		}
		
		return 0xFFFFFF;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		List<String> taken = new ArrayList<>();
		if(isInCreativeTab(tab))
			for(Aspect tag : Aspect.aspects.values())
			{
				if(taken.contains(tag.getTag()))
					continue;
				taken.add(tag.getTag());
				ItemStack i = new ItemStack(this);
				setAspects(i, new AspectList().add(tag, 1));
				items.add(i);
			}
	}
	
	@Override
	public AspectList getAspects(ItemStack itemstack)
	{
		if(itemstack.hasTagCompound())
		{
			AspectList aspects = new AspectList();
			aspects.readFromNBT(itemstack.getTagCompound());
			return aspects.size() > 0 ? aspects : null;
		}
		return null;
	}
	
	@Override
	public void setAspects(ItemStack itemstack, AspectList aspects)
	{
		if(!itemstack.hasTagCompound())
			itemstack.setTagCompound(new NBTTagCompound());
		aspects.writeToNBT(itemstack.getTagCompound());
		itemstack.getTagCompound().removeTag("Color");
	}
	
	@Override
	public boolean ignoreContainedAspects()
	{
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!world.isRemote && !stack.hasTagCompound())
		{
			Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
			this.setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], 1));
		}
		super.onUpdate(stack, world, entity, par4, par5);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote && !stack.hasTagCompound())
		{
			Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
			this.setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], 1));
		}
	}
}