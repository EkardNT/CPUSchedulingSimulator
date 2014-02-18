package com.jimweller.cpuscheduler;

import java.util.Iterator;

public class MaxHeap implements Iterable<Process> {

	private Process[] heap;
	private int firstEmptySlot;
	
	public MaxHeap()
	{
		heap = new Process[16];
		firstEmptySlot = 0;
	}
	
	public void add(Process process)
	{
		if(process == null)
			throw new NullPointerException();
		int destinationSlot = firstEmptySlot;
		firstEmptySlot++;
		ensureCapacity(destinationSlot);
		heap[destinationSlot] = process;
		walkUp(destinationSlot);
	}
	
	public boolean remove(Process process)
	{
		if(process == null)
			return false;
		
		if(size() == 1 && process.equals(heap[0]))
		{
			heap[0] = null;
			firstEmptySlot = 0;
			return true;
		}
		
		for(int i = 0; i < firstEmptySlot; i++)
		{
			if(heap[i].equals(process))
			{
				heap[i] = heap[firstEmptySlot - 1];
				heap[firstEmptySlot - 1] = null;
				firstEmptySlot--;
				walkUp(i);
				walkDown(i);
				return true;
			}
		}
		
		return false;
	}
	
	public Process peekMax()
	{
		if(firstEmptySlot <= 0)
			return null;
		return heap[0];
	}
	
	public Process popMax()
	{
		if(firstEmptySlot <= 0)
			return null;
		Process max = heap[0];
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
				int maxSlot = slotWithMaxPriority(current, left, right);
				if(maxSlot == current)
					return current;
				swap(current, maxSlot);
				current = maxSlot;
				left = leftChildSlot(current);
				right = leftChildSlot(current);
			}
			// Or is only the left slot full?
			else
			{
				if(heap[current].priority > heap[left].priority)
					return current;
				swap(current, left);
				return left;
			}
		}
		return current;
	}
	
	private int slotWithMaxPriority(int parentSlot, int leftSlot, int rightSlot)
	{
		if(heap[parentSlot].priority > heap[leftSlot].priority)
		{
			if(heap[parentSlot].priority > heap[rightSlot].priority)
				return parentSlot;
			else
				return rightSlot;
		}
		else if(heap[leftSlot].priority > heap[rightSlot].priority)
			return leftSlot;
		else 
			return rightSlot;
	}
	
	private void swap(int slotA, int slotB)
	{
		Process temp = heap[slotA];
		heap[slotA] = heap[slotB];
		heap[slotB] = temp;
	}
	
	private void ensureCapacity(int desiredCapacity)
	{
		if(heap.length >= desiredCapacity)
			return;
		Process[] newHeap = new Process[Math.max(desiredCapacity, heap.length * 2)];
		for(int i = 0; i < heap.length; i++)
			newHeap[i] = heap[i];
		heap = newHeap;
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
	public Iterator<Process> iterator() {
		return new ArrayIterator<Process>(heap);
	}
}
