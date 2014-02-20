/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class SJFSchedulingAlgorithm extends BasePrioritizedSchedulingAlgorithm
{
    SJFSchedulingAlgorithm()
    {
    	super(new Comparator<Process>() {
    		public int compare(Process p1, Process p2) {
    			return (int)(p1.getBurstTime() - p2.getBurstTime());
    		}
    	});
    }
    
    public String getName()
    {
    	return "Shortest job first";
    }
}