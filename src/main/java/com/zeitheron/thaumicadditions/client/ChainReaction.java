package com.zeitheron.thaumicadditions.client;

import java.util.function.Consumer;

public class ChainReaction
{
	int delayLeft;
	Runnable call;
	
	public ChainReaction(Runnable call, int delay)
	{
		this.call = call;
		this.delayLeft = delay;
	}
	
	public boolean update()
	{
		if(delayLeft > 0)
			--delayLeft;
		if(delayLeft == 0)
			call.run();
		return delayLeft > 0;
	}
}