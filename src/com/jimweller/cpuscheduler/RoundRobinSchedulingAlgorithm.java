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

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    /** the timeslice each process gets */
    private int quantum;
    // Round robin queue.
    private final Queue<Process> processes;
    // Time at which the active job started its quantum.
    private long activeStartTime;

    RoundRobinSchedulingAlgorithm() {
    	quantum = 10;
    	processes = new LinkedList<Process>();
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
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() {
	return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     *            Value to assign to quantum.
     */
    public void setQuantum(int v) {
	this.quantum = v;
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