/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrioritySchedulingAlgorithm extends BasePrioritizedSchedulingAlgorithm 
{
    PrioritySchedulingAlgorithm()
    {
    	super(new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				return (int)(p2.getPriorityWeight() - p1.getPriorityWeight());
			}
		});
    }

    public String getName()
    {
    	return "Single-queue Priority";
    }
}