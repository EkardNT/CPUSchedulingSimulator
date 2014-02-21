package com.jimweller.cpuscheduler;

public class MultiLevelPriotitySchedulingAlgorithm extends BaseSchedulingAlgorithm 
{
	private final RoundRobinSchedulingAlgorithm level1Queue, level2Queue;
	private final FCFSSchedulingAlgorithm level3Queue;
	private SchedulingAlgorithm activeJobQueue;
	
	public MultiLevelPriotitySchedulingAlgorithm()
	{
		level1Queue = new RoundRobinSchedulingAlgorithm();
		level1Queue.setQuantum(10);
		level2Queue = new RoundRobinSchedulingAlgorithm();
		level2Queue.setQuantum(2 * level1Queue.quantum);
		level3Queue = new FCFSSchedulingAlgorithm();
	}
	
	public boolean supportsQuantization()
	{
		return true;
	}
	
	public boolean supportsPreemption()
	{
		return true;
	}
	
	public void setQuantum(int quantum)
	{
		super.setQuantum(quantum);
		level1Queue.setQuantum(quantum);
		level2Queue.setQuantum(2 * quantum);
	}

	public void addJob(Process p)
	{
		if(p != null)
			getQueue(p).addJob(p);
	}

	public boolean removeJob(Process p) 
	{
		if(activeJob != null && activeJob.equals(p))
		{
			activeJobQueue.removeJob(activeJob);
			switchActiveJob(null);
			return true;
		}
		return level1Queue.removeJob(p) 
				|| level2Queue.removeJob(p) 
				|| level3Queue.removeJob(p);
	}

	public void transferJobsTo(SchedulingAlgorithm otherAlg) 
	{
		otherAlg.addJob(activeJob);
		level1Queue.transferJobsTo(otherAlg);
		level2Queue.transferJobsTo(otherAlg);
		level3Queue.transferJobsTo(otherAlg);
		switchActiveJob(null);
	}

	public Process getNextJob(long currentTime, long availableMemory)
	{
		Process potential = getJobFromLowestQueue(currentTime, availableMemory);
		
		if(activeJob == null)
			switchActiveJob(potential);
		else if(activeJob.isFinished())
			switchActiveJob(potential);
		else if(preemptive && potential != null)
		{
			SchedulingAlgorithm potentialQueue = getQueue(potential);
			
			// Preempt the active job if the schedulers are different
			// and the potential job's priority is lower than the active job's.
			if(activeJobQueue != potentialQueue && potential.getPriorityWeight() < activeJob.getPriorityWeight())
			{
				switchActiveJob(potential);
			}
		}
		
		return activeJob;
	}

	public String getName() 
	{
		return "Multilevel Priority";
	}
	
	private SchedulingAlgorithm getQueue(Process p)
	{
		if(p == null)
			throw new NullPointerException();
		if(p.getPriorityWeight() <= 3)
			return level1Queue;
		if(p.getPriorityWeight() <= 6)
			return level2Queue;
		return level3Queue;
	}
	
	private Process getJobFromLowestQueue(long currentTime, long availableMemory)
	{
		Process job = level1Queue.getNextJob(currentTime, availableMemory);
		if(job != null)
			return job;
		job = level2Queue.getNextJob(currentTime, availableMemory);
		if(job != null)
			return job;
		return level3Queue.getNextJob(currentTime, availableMemory);
	}
	
	private void switchActiveJob(Process nextActiveJob)
	{
		activeJob = nextActiveJob;
		activeJobQueue = nextActiveJob == null ? null : getQueue(nextActiveJob);
	}
}