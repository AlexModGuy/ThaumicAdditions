package com.endie.thaumicadditions.api.utils;

public interface iChangeListener
{
	void update(int id);
	
	default void update(ListenerList list, int id)
	{
		if(valid())
			update(id);
	}
	
	boolean valid();
}