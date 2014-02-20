/** BaseSchedulingAlgorithm.java
 * 
 * An abstract scheduling algorithm for others to inherit from.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public abstract class BaseSchedulingAlgorithm implements SchedulingAlgorithm {
	/** Whether preemption is enabled. */
	protected boolean preemptive;
	/** The current quantization period. */
	protected int quantum;	
    /** The currently running process, null if none. */
    protected Process activeJob;
    
    /** Add the new job to the correct queue.*/
    public abstract void addJob(Process p);
    
    /** Returns true if the job was present and was removed. */
    public abstract boolean removeJob(Process p);

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public abstract void transferJobsTo(SchedulingAlgorithm otherAlg);

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public abstract Process getNextJob(long currentTime);

    /** Return a human-readable name for the algorithm. */
    public abstract String getName();
    
    public boolean supportsQuantization()
    {
    	return false;
    }
    
    public boolean supportsPreemption()
    {
    	return false;
    }
    
    public void setQuantum(int quantum)
    {
    	this.quantum = quantum;
    }
    
    public void setPreemptive(boolean preemptive)
    {
    	this.preemptive = preemptive;
    }

    /** Returns true if the current job is finished or there is no such job. */
    public boolean isJobFinished(){
	if (activeJob != null)
	    return activeJob.isFinished();
	else
	    return true;
    }
}
