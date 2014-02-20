package com.jimweller.cpuscheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class BasePrioritizedSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm { 
	private boolean preemptive;
    private final PriorityQueue<Process> jobQueue;
    private final Comparator<Process> orderer;

    BasePrioritizedSchedulingAlgorithm(Comparator<Process> orderer){
    	preemptive = false;
    	this.orderer = orderer;
    	jobQueue = new PriorityQueue<Process>(10, orderer);
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	if(p != null)
    		jobQueue.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if(activeJob != null && activeJob.equals(p))
    	{
    		activeJob = null;
    		return true;
    	}
    	return jobQueue.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	otherAlg.addJob(activeJob);
    	for(Process p : jobQueue)
    		otherAlg.addJob(p);
    	jobQueue.clear();
    	activeJob = null;
    }


    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(activeJob == null)
    		activeJob = jobQueue.poll();
    	else if(activeJob.isFinished())
    		activeJob = jobQueue.poll();
    	else if(preemptive
    			&& jobQueue.size() > 0
    			&& orderer.compare(jobQueue.element(), activeJob) < 0)
    	{
    		jobQueue.add(activeJob);
    		activeJob = jobQueue.element();
    	}
    	
    	return activeJob;
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
