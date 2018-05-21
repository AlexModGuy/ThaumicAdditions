package com.zeitheron.thaumicadditions.api.utils;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ListenerList<T> extends ArrayList<iChangeListener> implements iChangeListener
{
	public final T holder;
	
	public ListenerList(T holder)
	{
		this.holder = holder;
	}
	
	@Override
	public Stream<iChangeListener> stream()
	{
		validate();
		return super.stream();
	}
	
	public void validate()
	{
		removeIf(l -> !l.valid());
	}
	
	public void addListener(iChangeListener listener)
	{
		if(!contains(listener))
			add(listener);
	}
	
	@Override
	public boolean contains(Object o)
	{
		return this == o || super.contains(o);
	}
	
	@Override
	public void update(int id)
	{
		validate();
		forEach(l -> l.update(ListenerList.this, id));
	}
	
	@Override
	public boolean valid()
	{
		return false;
	}
}