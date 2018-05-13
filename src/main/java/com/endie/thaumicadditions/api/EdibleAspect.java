package com.endie.thaumicadditions.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants.NBT;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.potions.PotionFluxTaint;

public class EdibleAspect
{
	public static final int MAX_ESSENTIA = 32;
	public static final Map<Aspect, BiConsumer<EntityPlayerMP, Integer>> EAT_FUNCTIONS = new HashMap<>();
	
	static
	{
		addEatCall(Aspect.WATER, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 10 + (count * count) / 2, (int) Math.cbrt(count) - 1, false, false)));
		addEatCall(Aspect.TRAP, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.LIGHT, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 40 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.DARKNESS, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.FLUX, (player, count) -> player.addPotionEffect(new PotionEffect(PotionFluxTaint.instance, 10 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.ALCHEMY, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 10 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.ENERGY, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.TOOL, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 20 + (count * count) / 2, 1, false, false)));
		addEatCall(Aspect.DEATH, (player, count) -> player.attackEntityFrom(DamageSource.MAGIC, 1 + (float) Math.sqrt(count)));
		addEatCall(Aspect.LIFE, (player, count) -> player.heal(1 + (float) Math.sqrt(count)));
		addEatCall(Aspect.MOTION, (player, count) -> player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10 + (count * count) / 2, 0, false, false)));
	}
	
	public static void addEatCall(Aspect asp, BiConsumer<EntityPlayerMP, Integer> c)
	{
		BiConsumer<EntityPlayerMP, Integer> ef = EAT_FUNCTIONS.get(asp);
		if(ef != null)
			c = ef.andThen(c);
		EAT_FUNCTIONS.put(asp, c);
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