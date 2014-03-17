/** RandomSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * CS 143A - Group 8
 * @author: Drake Tetreault 35571095
 * @author: Virginia McMinn 55438064
 * @author: Ling Han Meng 72933055
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RandomSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    private Vector<Process> jobs;
    private Random rand;

    RandomSchedulingAlgorithm(){
	activeJob = null;
	rand = new Random();
	jobs = new Vector<Process>();
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
	jobs.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
	return jobs.remove(p);
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime)
    {
		return activeJob = jobs.size() == 0 ? null : jobs.get(rand.nextInt(jobs.size()));
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
	for (int i = jobs.size()-1; i >= 0; i--) {
	    Process job = this.jobs.get(0);
	    this.removeJob(job);
	    otherAlg.addJob(job);
	}
    }


    public String getName(){
	return "Random Job";
    }
}