/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * CS 143A - Group 8
 * @author: Drake Tetreault 35571095
 * @author: Virginia McMinn 55438064
 * @author: Ling Han Meng 72933055
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
				return (int)(p1.getPriorityWeight() - p2.getPriorityWeight());
			}
		});
    }

    public String getName()
    {
    	return "Single-queue Priority";
    }
}