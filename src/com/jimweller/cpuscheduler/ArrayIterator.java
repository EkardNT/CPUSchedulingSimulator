package com.jimweller.cpuscheduler;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {

	private final T[] array;
	private int position;
	
	public ArrayIterator(T[] array)
	{
		this.array = array;
		position = -1;
	}
	
	@Override
	public boolean hasNext() {
		return position < array.length - 1;
	}

	@Override
	public T next() {
		if(hasNext())
			return array[++position];
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
