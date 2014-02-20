/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm 
{
    // Round robin queue.
    private final Queue<Process> processes;
    // Time at which the active job started its quantum.
    private long activeStartTime;

    RoundRobinSchedulingAlgorithm() {
    	processes = new LinkedList<Process>();
    }
    
    public boolean supportsQuantization()
    {
    	return true;
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) {
    	if(p != null)
    		processes.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) {
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

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) {
    	// If there's no currently active job, don't worry about
    	// timing, just get the next available job if any.
    	if(activeJob == null)
    	{
    		activeJob = processes.poll();
    		activeStartTime = currentTime;
    	}
    	// If the current job is finished, go to the next one
    	// regardless of its quantum.
    	else if(activeJob.isFinished())
    	{
    		activeJob = processes.poll();
    		activeStartTime = currentTime;
    	}
    	// Otherwise, if the active job's quantum is over,
    	// add it back to the end of the queue and take the front.
    	// This works even if the active job is the only one
    	// in the scheduler.
    	else if(currentTime - activeStartTime >= quantum)
    	{
    		processes.add(activeJob);
    		activeJob = processes.poll();
    		activeStartTime = currentTime;
    	}
    	
    	return activeJob;
    }

    public String getName() {
    	return "Round Robin";
    }
}