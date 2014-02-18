package com.jimweller.cpuscheduler;

/**
 * Represents an ordering algorithm for a heap.
 * @author Drake Tetreault
 * @param <T> The type of element in the heap.
 */
public interface HeapOrderer<T> {
	/**
	 * Tests whether two heap elements are correctly ordered.
	 * @param expectedBefore The element that is expected to come first in the ordering.
	 * @param expectedAfter The element that is expected to come last in the ordering.
	 * @return True if the expectedBefore does belong before (or in the same position as)
	 * the expectedAfter element, false if the two elements are incorrectly ordered.
	 */
	boolean isOrdered(T expectedBefore, T expectedAfter);
}
