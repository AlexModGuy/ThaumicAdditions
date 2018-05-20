package com.endie.thaumicadditions.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.endie.thaumicadditions.init.KnowledgeTAR;
import com.endie.thaumicadditions.init.PotionsTAR;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants.NBT;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.potions.PotionFluxTaint;

public class EdibleAspect
{
	public static final int MAX_ESSENTIA = 32;
	public static final Map<Aspect, BiFunction<EntityLivingBase, Integer, Boolean>> EAT_FUNCTIONS = new HashMap<>();
	public static final Map<AspectList, BiFunction<EntityLivingBase, AspectList, Boolean>> COMPLEX_FUNCTIONS = new HashMap<>();
	
	static
	{
		addEatCall(Aspect.WATER, (player, count) -> addPotionEffect(player, MobEffects.WATER_BREATHING, 10 + (count * count), 0));
		addEatCall(Aspect.TRAP, (player, count) -> addPotionEffect(player, MobEffects.SLOWNESS, 10 + (count * count), 1));
		addEatCall(Aspect.SENSES, (player, count) -> addPotionEffect(player, MobEffects.NIGHT_VISION, 400 + count * count, 0));
		addEatCall(Aspect.DARKNESS, (player, count) -> addPotionEffect(player, MobEffects.BLINDNESS, 10 + (count * count), 0));
		addEatCall(Aspect.FLUX, (player, count) -> addPotionEffect(player, PotionFluxTaint.instance, 10 + (count * count), 0));
		addEatCall(Aspect.ALCHEMY, (player, count) -> addPotionEffect(player, MobEffects.NAUSEA, 10 + (count * count), 0));
		addEatCall(Aspect.ENERGY, (player, count) -> addPotionEffect(player, MobEffects.STRENGTH, 20 + (count * count), (int) Math.sqrt(count)));
		addEatCall(Aspect.TOOL, (player, count) -> addPotionEffect(player, MobEffects.HASTE, 20 + (count * count), (int) Math.sqrt(count)));
		addEatCall(Aspect.DEATH, (player, count) -> player.attackEntityFrom(DamageSource.MAGIC, 1 + (float) Math.sqrt(count)));
		addEatCall(Aspect.MOTION, (player, count) -> addPotionEffect(player, MobEffects.SPEED, 10 + (count * count), count * 3 / MAX_ESSENTIA));
		addEatCall(KnowledgeTAR.SONUS, (player, count) -> addPotionEffect(player, PotionsTAR.SOUND_SENSIVITY, 120 + (count * count), count * 21 / MAX_ESSENTIA));
		addEatCall(Aspect.DESIRE, (player, count) -> addPotionEffect(player, MobEffects.HUNGER, 100 + (count * count), count * 5 / MAX_ESSENTIA));
		addEatCall(Aspect.PROTECT, (player, count) -> addPotionEffect(player, MobEffects.RESISTANCE, 200 + (count * count) * 2, count * 10 / MAX_ESSENTIA));
		
		addEatCall(Aspect.LIFE, (player, count) ->
		{
			boolean healthy = player.getMaxHealth() - player.getHealth() < .5F;
			if(!healthy)
				player.heal(1 + (float) Math.sqrt(count));
			return !healthy;
		});
	}
	
	public static boolean addPotionEffect(EntityLivingBase elb, Potion potion, int timeInSec, int amplifier)
	{
		PotionEffect pe = elb.getActivePotionEffect(potion);
		ret: if(pe != null)
		{
			if(potion == MobEffects.NIGHT_VISION && pe.getDuration() < 300)
				break ret;
			if(pe.getDuration() <= 40)
				break ret;
			return false;
		}
		elb.addPotionEffect(new PotionEffect(potion, timeInSec, amplifier, false, false));
		return true;
	}
	
	public static AspectList execute(EntityLivingBase ent, AspectList al)
	{
		if(ent == null || al == null || al.visSize() == 0)
			return new AspectList();
		final AspectList alt = al = al.copy();
		AspectList origin = al.copy();
		AspectList used = new AspectList();
		
		// First, process all combinations, and remove their aspects
		COMPLEX_FUNCTIONS.keySet().stream().filter(a -> AspectUtil.containsAll(alt, a)).forEach(a ->
		{
			BiFunction<EntityLivingBase, AspectList, Boolean> bi = COMPLEX_FUNCTIONS.get(alt);
			if(bi != null)
			{
				if(Objects.equals(bi.apply(ent, alt), Boolean.TRUE))
					used.add(a);
				alt.remove(a);
			}
		});
		
		// Second, process all leftovers from processing combinations.
		for(Aspect a : alt.getAspectsSortedByAmount())
		{
			BiFunction<EntityLivingBase, Integer, Boolean> cons = EdibleAspect.EAT_FUNCTIONS.get(a);
			if(cons != null && Objects.equals(cons.apply(ent, alt.getAmount(a)), Boolean.TRUE))
				used.add(a, alt.getAmount(a));
		}
		
		return used;
	}
	
	public static void addEatCall(Aspect asp, BiFunction<EntityLivingBase, Integer, Boolean> c)
	{
		BiFunction<EntityLivingBase, Integer, Boolean> ef = EAT_FUNCTIONS.get(asp);
		if(ef != null)
			ef = aor(c, ef);
		EAT_FUNCTIONS.put(asp, c);
	}
	
	private static <A1, A2> BiFunction<A1, A2, Boolean> aor(BiFunction<A1, A2, Boolean> a, BiFunction<A1, A2, Boolean> b)
	{
		return (k, u) -> a.apply(k, u) || b.apply(k, u);
	}
	
	public static ItemStack withoutSalt(ItemStack stack)
	{
		stack = stack.copy();
		
		if(stack.isEmpty())
			return stack;
		if(!(stack.getItem() instanceof ItemFood))
			return stack;
		
		if(stack.hasTagCompound())
			stack.getTagCompound().removeTag("TARSalt");
		
		if(stack.getTagCompound().hasNoTags())
			stack.setTagCompound(null);
		
		return stack;
	}
	
	/**
	 * Creates a copy of food stack and applies given aspects (or sums them with
	 * those aspects who were previously added)
	 */
	public static ItemStack applyToFoodStack(ItemStack stack, AspectList aspects)
	{
		stack = stack.copy();
		
		if(stack.isEmpty())
			return stack;
		if(!(stack.getItem() instanceof ItemFood))
			return stack;
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		AspectList fin = new AspectList();
		fin.add(aspects);
		fin.add(getSalt(stack));
		NBTTagCompound anbt = new NBTTagCompound();
		fin.writeToNBT(anbt);
		stack.getTagCompound().setTag("TARSalt", anbt);
		
		return stack;
	}
	
	public static AspectList getSalt(ItemStack stack)
	{
		NBTTagCompound anbt = stack.getTagCompound();
		if(anbt != null)
		{
			if(!anbt.hasKey("TARSalt", NBT.TAG_COMPOUND))
				return new AspectList();
			anbt = anbt.getCompoundTag("TARSalt");
			AspectList nal = new AspectList();
			nal.readFromNBT(anbt);
			return nal;
		}
		return new AspectList();
	}
}