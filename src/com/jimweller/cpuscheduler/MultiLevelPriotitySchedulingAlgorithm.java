package com.jimweller.cpuscheduler;

public class MultiLevelPriotitySchedulingAlgorithm extends BaseSchedulingAlgorithm 
{
	private final SchedulingAlgorithm level1Queue, level2Queue, level3Queue;
	private SchedulingAlgorithm activeJobQueue;
	
	public MultiLevelPriotitySchedulingAlgorithm()
	{
		level1Queue = new RoundRobinSchedulingAlgorithm();
		level2Queue = new RoundRobinSchedulingAlgorithm();
		level3Queue = new FCFSSchedulingAlgorithm();
		setQuantum(10);
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
			setActiveJob(null);
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
		setActiveJob(null);
	}

	public Process getNextJob(long currentTime)
	{
		if(activeJob == null || activeJob.isFinished() || preemptive)
			setActiveJob(getJobFromLowestQueue(currentTime));
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
	
	private Process getJobFromLowestQueue(long currentTime)
	{
		Process job = level1Queue.getNextJob(currentTime);
		if(job != null)
			return job;
		job = level2Queue.getNextJob(currentTime);
		if(job != null)
			return job;
		return level3Queue.getNextJob(currentTime);
	}
	
	private void setActiveJob(Process nextActiveJob)
	{
		activeJob = nextActiveJob;
		activeJobQueue = nextActiveJob == null ? null : getQueue(nextActiveJob);
	}
}