/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    // Processes ordered with highest priority first.
    private Heap<Process> maxHeap;

    PrioritySchedulingAlgorithm(){
    	preemptive = false;
    	maxHeap = new Heap<Process>(new HeapOrderer<Process>() { 
			public boolean isOrdered(Process expectedBefore, Process expectedAfter) {
				return expectedBefore.priority >= expectedAfter.priority;
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
    		activeJob = maxHeap.popMax();
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