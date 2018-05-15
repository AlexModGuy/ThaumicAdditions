package com.endie.thaumicadditions.client;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.endie.thaumicadditions.api.AttributesTAR;
import com.endie.thaumicadditions.api.EdibleAspect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ClientChainReactor
{
	private final List<LinkedList<ChainReaction>> CHAINS = new ArrayList<>();
	public static final ClientChainReactor REACTOR = new ClientChainReactor();
	
	public void addChain(ChainReaction... sequence)
	{
		CHAINS.add(new LinkedList<>(Arrays.asList(sequence)));
	}
	
	public void addChain(Collection<ChainReaction> sequence)
	{
		CHAINS.add(new LinkedList<>(sequence));
	}
	
	public void updateChains()
	{
		if(Minecraft.getMinecraft().world == null)
			CHAINS.clear();
		
		for(int i = 0; i < CHAINS.size(); ++i)
		{
			LinkedList<ChainReaction> ll = CHAINS.get(i);
			if(ll.isEmpty())
				CHAINS.remove(i);
			else
				while(!ll.isEmpty() && !ll.getFirst().update())
					ll.removeFirst();
		}
	}
	
	@SubscribeEvent
	public void clientTick(ClientTickEvent cte)
	{
		if(!Minecraft.getMinecraft().isGamePaused() && cte.phase == Phase.START)
			updateChains();
	}
	
	/**
	 * SoundEvent handling, used to make sounds louder with a special potion
	 * effect.
	 */
	@SubscribeEvent
	public void soundEvent(PlaySoundEvent e)
	{
		if(e.getSound() instanceof PositionedSound)
		{
			PositionedSound ps = (PositionedSound) e.getSound();
			Field volume = PositionedSound.class.getDeclaredFields()[4];
			volume.setAccessible(true);
			if(volume.getType() == float.class)
				try
				{
					float f = volume.getFloat(ps);
					
					// Adjust volume
					if(Minecraft.getMinecraft().player != null)
					{
						IAttributeInstance ss = Minecraft.getMinecraft().player.getEntityAttribute(AttributesTAR.SOUND_SENSIVITY);
						f *= (float) ss.getAttributeValue();
					}
					
					volume.setFloat(ps, f);
				} catch(Throwable err)
				{
					err.printStackTrace();
				}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void tooltipEvent(ItemTooltipEvent e)
	{
		ItemStack stack = e.getItemStack();
		
		AspectList salt;
		if(!stack.isEmpty() && stack.getItem() instanceof ItemFood && (salt = EdibleAspect.getSalt(stack)).visSize() > 0)
		{
			e.getToolTip().add("Vis: " + salt.visSize() + "/" + EdibleAspect.MAX_ESSENTIA);
			for(Aspect a : salt.getAspectsSortedByName())
				e.getToolTip().add(a.getName() + " x" + salt.getAmount(a));
		}
	}
}