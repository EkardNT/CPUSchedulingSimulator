/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {
	// FIFO queue.
	private final Queue<Process> processes;

    public FCFSSchedulingAlgorithm(){
    	processes = new LinkedList<Process>();
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	if(p != null)
    		processes.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if(activeJob != null && activeJob.equals(p))
    	{
    		activeJob = null;
    		return true;
    	}
    	return processes.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	otherAlg.addJob(activeJob);
    	for(Process p : processes)
    		otherAlg.addJob(p);
    	processes.clear();
    	activeJob = null;
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(activeJob == null || activeJob.isFinished())
    		activeJob = processes.poll();    	
    	return activeJob;
    }

    public String getName(){
    	return "First-come first-served";
    }
}