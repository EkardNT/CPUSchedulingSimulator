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


public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    // Processes ordered with lowest burst time first.
    private final PriorityQueue<Process> minHeap;

    SJFSchedulingAlgorithm(){
    	preemptive = false;
    	minHeap = new PriorityQueue<Process>(10, new Comparator<Process>() {
    		public int compare(Process p1, Process p2) {
    			return (int)(p1.getBurstTime() - p2.getBurstTime());
    		}
    	});
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	if(p != null)
    		minHeap.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if(activeJob != null && activeJob.equals(p))
    	{
    		activeJob = null;
    		return true;
    	}
    	return minHeap.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	otherAlg.addJob(activeJob);
    	for(Process p : minHeap)
    		otherAlg.addJob(p);
    	minHeap.clear();
    	activeJob = null;
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(activeJob == null)
    		activeJob = minHeap.poll();
    	else if(activeJob.isFinished())
    		activeJob = minHeap.poll();
    	else if(preemptive
    			&& (minHeap.size() > 0) 
    			&& (activeJob.getBurstTime() > minHeap.element().getBurstTime()))
    	{
    		minHeap.add(activeJob);
    		activeJob = minHeap.poll();
    	}

    	return activeJob;
    }

    public String getName(){
    	return "Shortest job first";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
    	return preemptive;
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
    	preemptive = v;
    }
}