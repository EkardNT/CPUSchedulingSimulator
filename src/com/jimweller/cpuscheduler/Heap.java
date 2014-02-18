package com.jimweller.cpuscheduler;

import java.util.Iterator;

public class Heap<T> implements Iterable<T> {

	private final HeapOrderer<T> orderer;
	private Object[] backingArray;
	private int firstEmptySlot;
	
	public Heap(HeapOrderer<T> orderer)
	{
		this.orderer = orderer;
		backingArray = new Object[16]; // Curse you type erasure!!!
		firstEmptySlot = 0;
	}
	
	public void add(T T)
	{
		if(T == null)
			throw new NullPointerException();
		int destinationSlot = firstEmptySlot;
		firstEmptySlot++;
		ensureCapacity(destinationSlot);
		backingArray[destinationSlot] = T;
		walkUp(destinationSlot);
	}
	
	public boolean remove(T T)
	{
		if(T == null)
			return false;
		
		if(size() == 1 && T.equals(backingArray[0]))
		{
			backingArray[0] = null;
			firstEmptySlot = 0;
			return true;
		}
		
		for(int i = 0; i < firstEmptySlot; i++)
		{
			if(backingArray[i].equals(T))
			{
				backingArray[i] = backingArray[firstEmptySlot - 1];
				backingArray[firstEmptySlot - 1] = null;
				firstEmptySlot--;
				walkUp(i);
				walkDown(i);
				return true;
			}
		}
		
		return false;
	}
	
	public T peekMax()
	{
		if(firstEmptySlot <= 0)
			return null;
		return (T)backingArray[0];
	}
	
	public T popMax()
	{
		if(firstEmptySlot <= 0)
			return null;
		T max = (T)backingArray[0];
		remove(max);
		return max;
	}
	
	public int size()
	{
		return firstEmptySlot;
	}
	
	public void clear()
	{
		firstEmptySlot = 0;
	}
	
	private int walkUp(int initialSlot)
	{
		int current = initialSlot, 
			parent = parentSlot(initialSlot);
		while(parent >= 0)
		{
			swap(current, parent);
			current = parent;
			parent = parentSlot(current);
		}
		return current;
	}
	
	private int walkDown(int initialSlot)
	{
		int current = initialSlot,
			left = leftChildSlot(current),
			right = rightChildSlot(current);
		while(left < firstEmptySlot)
		{
			// Are both child slots full?
			if(right < firstEmptySlot)
			{
				int orderedFirstSlot = findSlotThatShouldBeOrderedFirst(current, left, right);
				if(orderedFirstSlot == current)
					return current;
				swap(current, orderedFirstSlot);
				current = orderedFirstSlot;
				left = leftChildSlot(current);
				right = leftChildSlot(current);
			}
			// Or is only the left slot full?
			else
			{
				if(orderer.isOrdered((T)backingArray[current], (T)backingArray[left]))
					return current;
				swap(current, left);
				return left;
			}
		}
		return current;
	}
	
	private int findSlotThatShouldBeOrderedFirst(int parentSlot, int leftSlot, int rightSlot)
	{
		if(orderer.isOrdered((T)backingArray[parentSlot], (T)backingArray[leftSlot]))
		{
			if(orderer.isOrdered((T)backingArray[parentSlot], (T)backingArray[rightSlot]))
				return parentSlot;
			else
				return rightSlot;
		}
		else if(orderer.isOrdered((T)backingArray[leftSlot], (T)backingArray[rightSlot]))
			return leftSlot;
		else 
			return rightSlot;
	}
	
	private void swap(int slotA, int slotB)
	{
		Object temp = backingArray[slotA];
		backingArray[slotA] = backingArray[slotB];
		backingArray[slotB] = temp;
	}
	
	private void ensureCapacity(int desiredCapacity)
	{
		if(backingArray.length >= desiredCapacity)
			return;
		Object[] newHeap = new Object[Math.max(desiredCapacity, backingArray.length * 2)];
		for(int i = 0; i < backingArray.length; i++)
			newHeap[i] = backingArray[i];
		backingArray = newHeap;
	}
	
	private int parentSlot(int childSlot)
	{
		return (childSlot - 1) / 2;
	}
	
	private int leftChildSlot(int parentSlot)
	{
		return parentSlot * 2 + 1;
	}
	
	private int rightChildSlot(int parentSlot)
	{
		return leftChildSlot(parentSlot) + 1;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<T>((T[])backingArray);
	}
}
