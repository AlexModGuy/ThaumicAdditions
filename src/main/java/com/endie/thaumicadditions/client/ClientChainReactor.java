package com.endie.thaumicadditions.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ClientChainReactor
{
	private final List<LinkedList<ChainReaction>> CHAINS = new ArrayList<>();
	public static final ClientChainReactor REACTOR = new ClientChainReactor();
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
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
}