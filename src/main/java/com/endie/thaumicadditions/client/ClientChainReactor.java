package com.endie.thaumicadditions.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.endie.thaumicadditions.api.EdibleAspect;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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