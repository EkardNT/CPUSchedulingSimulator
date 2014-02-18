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

public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    // Processes ordered with highest priority first.
    private PriorityQueue<Process> maxHeap;

    PrioritySchedulingAlgorithm(){
    	preemptive = false;
    	maxHeap = new PriorityQueue<Process>(10, new Comparator<Process>() {
    		public int compare(Process p1, Process p2) {
    			return (int)(p2.priority - p1.priority);
    		}
    	});
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	if(p != null)
    		maxHeap.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if(activeJob != null && activeJob.equals(p))
    	{
    		activeJob = null;
    		return true;
    	}
    	return maxHeap.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	otherAlg.addJob(activeJob);
    	for(Process p : maxHeap)
    		otherAlg.addJob(p);
    	maxHeap.clear();
    	activeJob = null;
    }


    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(activeJob == null || preemptive || activeJob.isFinished())
    		activeJob = maxHeap.poll();
    	return activeJob;
    }

    public String getName(){
    	return "Single-queue Priority";
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