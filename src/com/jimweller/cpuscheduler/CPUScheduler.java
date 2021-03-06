package com.jimweller.cpuscheduler;

import java.util.*;
import java.io.*;
//import Process;
import java.text.*;

/**
 * CPUScheduler runs a simulation of one of four different scheduling algorithms
 * (FCFS,SJF,ROUNDROBIN,PRIORITY). It can be set to run the whole simulation
 * automatically in one fell swoop, or the programmer can imcrement on a step by
 * step basis.
 * 
 * CS 143A - Group 8
 * @author: Drake Tetreault 35571095
 * @author: Virginia McMinn 55438064
 * @author: Ling Han Meng 72933055
 * @version 0.50
 */

public class CPUScheduler {
	private SchedulingAlgorithm executingScheduler, arrivedScheduler;

	/**
	 * The default number of processes to randomly generate. The programmer can
	 * use the articulate constructor to build their own process set of any
	 * length.
	 */
	private static final int DEF_PROC_COUNT = 100;

	/** This simulates elapsed time. */
	private long currentTime = 0;

	/** The amount of elapsed idle time. */
	private long idle = 0;

	/** The amount of elapsed time that the CPU was kept busy. */
	private long busy = 0;

	/** The number of jobs submitted for execution. */
	private int procsIn = 0;

	/** the number of jobs that have been executed to completion. */
	private int procsOut = 0;

	/** Whether to use priority weights for the round robin algorithm. */
	private boolean priority = false;
	
	/** The total amount of memory available to the system. */
	private long totalMemory = 0;
	
	/** The amount of memory that is currently in use by processes
	 * which have started but not ended. */
	private long usedMemory = 0;

	/**
	 * The collection of all processes involved in this simulation. Extraneous
	 * now but handy for debugging.
	 */
	private ArrayList<Process> allProcs = new ArrayList<Process>(DEF_PROC_COUNT);

	/** The collection of all jobs that will be used */
	private ArrayList<Process> jobQueue = new ArrayList<Process>(DEF_PROC_COUNT);

	/** The collection of all jobs that have arrived and require CPU time. */
	private ArrayList<Process> readyQueue = new ArrayList<Process>(DEF_PROC_COUNT);

	private final ArrayList<Process> newlyArrivedJobs = new ArrayList<Process>();
	private final ArrayList<Process> newlyFinishedJobs = new ArrayList<Process>();
	
	/**
	 * A reference to the currently active job. The cpu changes this reference
	 * to different jobs in the ready queue using the respective algorithm's
	 * criteria
	 */
	private Process activeJob = null;

	/*
	 * Variables to store harvested statistics on wait, response and turnaround
	 * time
	 */
	private int minWait = 0, maxWait = 0;
	private double meanWait = 0.0, sDevWait = 0.0;

	private int minResponse = 0, maxResponse = 0;
	private double meanResponse = 0.0, sDevResponse = 0.0;

	private int minTurn = 0, maxTurn = 0;
	private double meanTurn = 0.0, sDevTurn = 0.0;

	/**
	 * Default constructor which builds DEF_PROC_COUNT randomly generated
	 * processes and loads them into the job queue
	 */
	CPUScheduler() {
		buildRandomQueue();
		executingScheduler = new RandomSchedulingAlgorithm();
		arrivedScheduler = new RandomSchedulingAlgorithm();
	}	

	/** Empty and populate a CPUScheduler */
	void buildRandomQueue() {
		activeJob = null;
		jobQueue.clear();
		allProcs.clear();
		Process p;
		Random r = new Random();
		for (int i = 0; i < DEF_PROC_COUNT; i++) {
			p = new Process(r.nextLong());
			allProcs.add(p);
		}
		LoadJobQueue(allProcs);
	}

	/**
	 * Articulate constructor that allows the programmer to design his/her own
	 * Vector of processes and use them in the scheduler
	 */
	CPUScheduler(ArrayList<Process> ap) {
		activeJob = null;
		allProcs = ap;
		LoadJobQueue(ap);
	}

	/**
	 * Articulate constructor that reads the process data from a file.
	 * 
	 * @param filename
	 *            a string containing the file to open
	 */
	CPUScheduler(String filename) {
		activeJob = null;
		Process proc = null;
		String s = null;
		long b = 0, d = 0, p = 0, memory = 0;
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			while ((s = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s);
				b = Long.parseLong(st.nextToken());
				d = Long.parseLong(st.nextToken());
				p = Long.parseLong(st.nextToken());
				memory = Long.parseLong(st.nextToken());
				proc = new Process(b, d, p, memory);
				allProcs.add(proc);
			}

		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}
		LoadJobQueue(allProcs);
	}

	/**
	 * Articulate constructor that reads the process data from a file.
	 * 
	 * @param filename
	 *            A File object to read data from
	 */
	CPUScheduler(File filename) {
		activeJob = null;
		Process proc = null;
		String s = null;
		long burst = 0, delay = 0, priority = 0, memory = 0;
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			while ((s = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s);
				burst = Long.parseLong(st.nextToken());
				if(burst < 1)
					burst = 1;
				else if(burst > Process.MAX_BURST_TIME)
					burst = Process.MAX_BURST_TIME;
				delay = Long.parseLong(st.nextToken());
				if(delay < 0)
					delay = 0;
				priority = Long.parseLong(st.nextToken());
				if(priority < 0)
					priority = 0;
				else if(priority > 9)
					priority = 9;
				memory = st.hasMoreTokens() ? Long.parseLong(st.nextToken()) : 0;
				if(memory < 0)
					memory = 0;
				proc = new Process(burst, delay, priority, memory);
				allProcs.add(proc);
			}
			input.close();

		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}
		LoadJobQueue(allProcs);
	}

	/**
	 * Use the appropriate scheduler to choose the next process. Then dispatch
	 * the process.
	 */
	public void Schedule() 
	{
		Process potential = arrivedScheduler.getNextJob(currentTime);
		// If its already started, then we know it has already reserved its
		// memory.
		if(potential == null || potential.isStarted())
			activeJob = potential;
		// Otherwise, if it needs to reserve memory...
		else
		{
			// ... but there isn't enough memory available,
			// then we have to pick from the set of jobs that
			// have already reserved their memory.
			if(potential.getMemory() > getAvailableMemory())
			{
				activeJob = executingScheduler.getNextJob(currentTime);
			}
			// ... and there is enough memory available, reserve that
			// memory, add the potential job to the scheduler for executing
			// jobs, and switch to the new job.
			else
			{
				usedMemory += potential.getMemory();
				executingScheduler.addJob(potential);
				activeJob = potential;
			}
		}
		Dispatch();
	}

	/** Actually run the active job and wait the rest of them */
	void Dispatch() {
		for(Process p : readyQueue)
		{			
			if(p.equals(activeJob))
				p.executing(currentTime);
			else
				p.waiting(currentTime);
			
			if(p.getArrivalTime() == currentTime)
				newlyArrivedJobs.add(p);
			if(p.getFinishTime() == currentTime)
			{
				newlyFinishedJobs.add(p);
				usedMemory -= p.getMemory();
			}
		}
	}

	/**
	 * Loop through the job queue and grab important statistics
	 */
	private void harvestStats() {
		int allWaited = 0, allResponded = 0, allTurned = 0;
		int sDevWaited = 0, sDevWaitedSquared = 0;
		int sDevTurned = 0, sDevTurnedSquared = 0;
		int sDevResponded = 0, sDevRespondedSquared = 0;
		int startedCount = 0, finishedCount = 0;
		Process p = null;
		int i = 0;

		for (i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);
			if (p.isStarted()) {
				startedCount++;
				int responded = (int) p.getResponseTime();
				allResponded += responded;
				sDevResponded += responded;
				sDevRespondedSquared += responded * responded;
				if (responded < minResponse || i == 0) {
					minResponse = responded;
				} else if (responded > maxResponse || i == 0) {
					maxResponse = responded;
				}
			}
		}

		if (startedCount > 0) {
			meanResponse = ((double) allResponded) / ((double) startedCount);
			if (startedCount > 1) {
				double sdev = (double) sDevRespondedSquared;
				sdev -= (double) (sDevResponded * sDevResponded)
						/ (double) startedCount;
				sdev /= (double) (startedCount - 1);
				sDevResponse = Math.sqrt(sdev);
			} else {
				sDevResponse = 0.0;
			}

		} else {
			meanResponse = 0.0;
			sDevResponse = 0.0;
		}

		for (i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);

			if (p.isFinished()) {
				finishedCount++;
				int waited = (int) p.getWaitTime();
				int turned = (int) p.getLifetime();
				allWaited += waited;
				sDevWaited += waited;
				sDevWaitedSquared += waited * waited;
				allTurned += turned;
				sDevTurned += turned;
				sDevTurnedSquared += turned * turned;

				if (waited < minWait || i == 0) {
					minWait = waited;
				} else if (waited > maxWait || i == 0) {
					maxWait = waited;
				}

				if (turned < minTurn || i == 0) {
					minTurn = turned;
				} else if (turned > maxTurn || i == 0) {
					maxTurn = turned;
				}

			}
		}

		if (finishedCount > 0) {
			meanWait = (double) allWaited / (double) finishedCount;
			meanTurn = (double) allTurned / (double) finishedCount;

			if (finishedCount > 1) {
				double sdev = (double) sDevWaitedSquared;
				sdev -= (double) (sDevWaited * sDevWaited)
						/ (double) finishedCount;
				sdev /= (double) (finishedCount - 1);
				sDevWait = Math.sqrt(sdev);
				sdev = 0.0;
				sdev = (double) sDevTurnedSquared;
				sdev -= (double) (sDevTurned * sDevTurned)
						/ (double) finishedCount;
				sdev /= (double) (finishedCount - 1);
				sDevTurn = Math.sqrt(sdev);
			} else {
				sDevWait = 0.0;
				sDevTurn = 0.0;
			}
		} else {
			meanWait = 0.0;
			meanTurn = 0.0;
		}

	}

	/** Check for new jobs. */
	void LoadReadyQueue() {
		Process p;
		for (int i = 0; i < jobQueue.size(); i++) {
			p =  jobQueue.get(i);
			if (p.getArrivalTime() == currentTime) {
				readyQueue.add(p);
				arrivedScheduler.addJob(p);
				procsIn++;
			}
		}

	}

	/** Remove finished jobs. */
	void PurgeReadyQueue() {
		Process p;
		for (int i = 0; i < readyQueue.size(); i++) {
			p =  readyQueue.get(i);
			if (p.isFinished() == true) {
				readyQueue.remove(i);
				arrivedScheduler.removeJob(p);
				executingScheduler.removeJob(p);
				procsOut++;
			}
		}
	}

	/** Get rid of jobs that are done */
	void PurgeJobQueue() 
	{
		Process p;
		for (int i = 0; i < jobQueue.size(); i++) 
		{
			p = jobQueue.get(i);
			if (p.isFinished())
			{
				jobQueue.remove(i);
				arrivedScheduler.removeJob(p);
				executingScheduler.removeJob(p);
			}
		}
	}

	/** Load all the jobs into the job queue and setup their arrival times */
	public void LoadJobQueue(ArrayList<Process> jobs) {
	        arrivedScheduler = new RandomSchedulingAlgorithm();
	        executingScheduler = new RandomSchedulingAlgorithm();
	        Process p;
		long arTime = 0;
		for (int i = 0; i < jobs.size(); i++) {
			p = jobs.get(i);
			arTime += p.getDelayTime();
			p.setArrivalTime(arTime);
			jobQueue.add(p);
		}
	}

	/** Dump to terminal. */
	public void print() {
		Process p;
		for (int i = 0; i < allProcs.size(); i++) {
			p = allProcs.get(i);
			p.print();
			System.out.println("---------------");
		}
	}

	/** Dump ready queue to terminal. */
	public void printReadyQueue() {
		Process p;
		for (int i = 0; i < readyQueue.size(); i++) {
			p =  readyQueue.get(i);
			p.print();
			System.out.println("---------------");
		}
	}

	/** kindof nice looking table. Java sucks for text formatting. Printf? */
	public void printTable() {
		Process p;
		for (int i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);
			p.println();
		}
	}

	/** kindof ugly table to import into spreadsheet. */
	public void printCSV() {
		Process p;
		System.out.println(getAlgorithmName() + ","
				   + (getPriority() ? "Priority," : ","));
				   //+ (getPreemption() ? "Preemptive" : ""));
		System.out.println("\"PID\"," + "\"Burst\"," + "\"Priority\","
				+ "\"Arrival\"," + "\"Start\"," + "\"Finish\"," + "\"Wait\","
				+ "\"Response\"," + "\"Turnaround\"");

		for (int i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);
			p.printCSV();
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);

		System.out.println(",,,,,,,,");
		System.out.println(",,,,," + "Min," + minWait + "," + minResponse + ","
				+ minTurn);
		System.out.println(",,,,," + "Mean," + nf.format(meanWait) + ","
				+ nf.format(meanResponse) + "," + nf.format(meanTurn));
		System.out.println(",,,,," + "Max," + maxWait + "," + maxResponse + ","
				+ maxTurn);
		System.out.println(",,,,," + "StdDev," + nf.format(sDevWait) + ","
				+ nf.format(sDevResponse) + "," + nf.format(sDevTurn));
	}

	/** kindof ugly table to import into spreadsheet. */
	public void printCSV(PrintWriter pw) {
		Process p;
		pw.println(getAlgorithmName() + ","
			   + (getPriority() ? "Priority," : ","));
			   //+ (getPreemption() ? "Preemptive" : ""));
		pw.println("\"PID\"," + "\"Burst\"," + "\"Priority\"," + "\"Arrival\","
				+ "\"Start\"," + "\"Finish\"," + "\"Wait\"," + "\"Response\","
				+ "\"Turnaround\"");

		for (int i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);
			p.printCSV(pw);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);

		pw.println(",,,,,,,,");
		pw.println(",,,,," + "Min," + minWait + "," + minResponse + ","
				+ minTurn);
		pw.println(",,,,," + "Mean," + nf.format(meanWait) + ","
				+ nf.format(meanResponse) + "," + nf.format(meanTurn));
		pw.println(",,,,," + "Max," + maxWait + "," + maxResponse + ","
				+ maxTurn);
		pw.println(",,,,," + "StdDev," + nf.format(sDevWait) + ","
				+ nf.format(sDevResponse) + "," + nf.format(sDevTurn));
	}

	/**
	 * Set the value of algorithm.
	 * 
	 * @param algo
	 *            The algorithm to use for this simulation.
	 */
	public void setAlgorithm(SchedulingAlgorithm algo) {
		Class<? extends SchedulingAlgorithm> newAlgoClass = algo.getClass();
		SchedulingAlgorithm oldAlg = arrivedScheduler;
		try
		{
			arrivedScheduler = (SchedulingAlgorithm)newAlgoClass.newInstance();
			arrivedScheduler.setPreemptive(algo.getPreemptive());
			arrivedScheduler.setQuantum(algo.getQuantum());
			executingScheduler = (SchedulingAlgorithm)newAlgoClass.newInstance();
			executingScheduler.setPreemptive(algo.getPreemptive());
			executingScheduler.setQuantum(algo.getQuantum());			
		}
		catch(Exception e) // Ugh
		{
			throw new RuntimeException(e);
		}		
		oldAlg.transferJobsTo(arrivedScheduler);
	}

	/**
	 * Get the current algorithm.
	 * 
	 * @return The algorithm.
	 */
	public SchedulingAlgorithm getAlgorithm() {
		return arrivedScheduler;
	}

	/**
	 * Get the number of idle cpu cycles.
	 * 
	 * @return Number of idle cpu cycles.
	 */
	public long getIdleTime() {
		return idle;
	}

	/**
	 * Get the total time this simulation has been running.
	 * 
	 * @return total running time.
	 */
	public long getTotalTime() {
		return currentTime;
	}

	/**
	 * Get the amount of time the CPU has been used so far.
	 * 
	 * @return Busy cpu cycles.
	 */
	public long getBusyTime() {
		return busy;
	}

	/**
	 * Get the value of priority.
	 * 
	 * @return Value of priority.
	 */
	public boolean getPriority() {
		return priority;
	}

	/**
	 * Set the value of priority.
	 * 
	 * @param v
	 *            Value to assign to priority.
	 */
	public void setPriority(boolean v) {
		this.priority = v;
	}

	/**
	 * Get the number of completed processes .
	 * 
	 * @return Value of procsOut.
	 */
	public long getProcsOut() {
		return procsOut;
	}

	/**
	 * Get the number of recieved jobs.
	 * 
	 * @return Value of procsOut.
	 */
	public long getProcsIn() {
		return procsOut;
	}

	/**
	 * Get the system load.
	 * 
	 * @return The current sysetm load which is input processes over output
	 *         processes.
	 */
	public double getLoad() {
		return ((double) procsIn / (double) procsOut);
	}

	/** Get the Process that is actively being executed */
	public Process getActiveProcess() {
		return activeJob;
	}

	/** Run the whole simulation in one while loop */
	public void Simulate() {
		while (nextCycle())
			;
	}

	/**
	 * Just run one cycle of the simulation. This represents one time unit.
	 * 
	 * @return a boolean that is true if more cycles remain to be run.
	 */
	public boolean nextCycle() {
		newlyArrivedJobs.clear();
		newlyFinishedJobs.clear();
		boolean moreCycles = false;
		if (jobQueue.isEmpty()) {
			moreCycles = false;
		} else {
			LoadReadyQueue();
			moreCycles = true;
			if (readyQueue.isEmpty()) {
				idle++;
			} else {
				Schedule();
				busy++;
				cleanUp();
			}
			currentTime++;
		}
		harvestStats();
		return moreCycles;
	}

	/**
	 * Purge the runtime queues
	 */
	void cleanUp() {
		PurgeJobQueue();
		PurgeReadyQueue();
	}
	
	/**
	 * Creates a new algorithm of the current type to reset it.
	 */
	public void resetAlgorithm() {
		Class<? extends SchedulingAlgorithm> theAlg = arrivedScheduler.getClass();
		try
		{
			SchedulingAlgorithm newAlg = theAlg.newInstance();
			newAlg.setPreemptive(arrivedScheduler.getPreemptive());
			newAlg.setQuantum(arrivedScheduler.getQuantum());
			setAlgorithm(newAlg);
		} catch (Exception e) {
			System.out.println("Error creating new algorithm!");
		}
	}

	/**
	 * Restore time and statisitic variables to their defaults. Also restores
	 * all processes to original state and reloads the queues. Leavs algorithm
	 * configurations alone an other state variables.
	 */
	public void restore() {
		Process p;

		activeJob = null;
		currentTime = 0;
		busy = 0;
		idle = 0;
		procsIn = 0;
		procsOut = 0;

		minWait = 0;
		meanWait = 0;
		maxWait = 0;
		sDevWait = 0;

		minResponse = 0;
		meanResponse = 0;
		maxResponse = 0;
		sDevResponse = 0;

		minTurn = 0;
		meanTurn = 0;
		maxTurn = 0;
		sDevTurn = 0;

		for (int i = 0; i < allProcs.size(); i++) {
			p =  allProcs.get(i);
			p.restore();
		}

		resetAlgorithm();
		
		jobQueue.clear();
		readyQueue.clear();
		LoadJobQueue(allProcs);
	}

	/**
	 * Get all jobs
	 * 
	 * @return Vector of all Processes
	 */
	public ArrayList<Process> getJobs() {
		return allProcs;
	}

	/**
	 * Get the mean process wait time
	 * 
	 * @return an int containting the mean wait
	 */
	public double getMeanWait() {
		return meanWait;
	}

	/**
	 * Get the minimum process wait time
	 * 
	 * @return an int containting the minimum wait
	 */
	public int getMinWait() {
		return minWait;
	}

	/**
	 * Get the maximum process wait time
	 * 
	 * @return an int containting the maximum wait
	 */
	public int getMaxWait() {
		return maxWait;
	}

	/**
	 * Get the standard deviation in process wait time
	 * 
	 * @return an int containting the standard deviation for wait
	 */
	public double getStdDevWait() {
		return sDevWait;
	}

	/**
	 * Get the mean process response time
	 * 
	 * @return an int containting the mean response
	 */
	public double getMeanResponse() {
		return meanResponse;
	}

	/**
	 * Get the minimum process response time
	 * 
	 * @return an int containting the minimum response
	 */
	public int getMinResponse() {
		return minResponse;
	}

	/**
	 * Get the maximum process response time
	 * 
	 * @return an int containting the maximum response
	 */
	public int getMaxResponse() {
		return maxResponse;
	}

	/**
	 * Get the standard deviation in process response time
	 * 
	 * @return an int containting the standard deviation for response
	 */
	public double getStdDevResponse() {
		return sDevResponse;
	}

	/**
	 * Get the mean process turn around time
	 * 
	 * @return an int containting the mean turn around
	 */
	public double getMeanTurn() {
		return meanTurn;
	}

	/**
	 * Get the minimum process turn around time
	 * 
	 * @return an int containting the minimum turn around
	 */
	public int getMinTurn() {
		return minTurn;
	}

	/**
	 * Get the maximum process turn around time
	 * 
	 * @return an int containting the maximum turn around
	 */
	public int getMaxTurn() {
		return maxTurn;
	}

	/**
	 * Get the standard deviation in process turn around time
	 * 
	 * @return an int containing the standard deviation for turn around
	 */
	public double getStdDevTurn() {
		return sDevTurn;
	}

	/**
	 * Get a string with the current algorithm's name
	 * 
	 * @return String containing the currently running algorithm's name
	 */
	public String getAlgorithmName() {
		return arrivedScheduler.getName();
	}
	
	/** Gets the total amount of memory in the system. */
	public long getTotalMemory() { return totalMemory; }
	
	/** Sets the total amount of memory in the system. */
	public void setTotalMemory(long totalMemory) { this.totalMemory = totalMemory; }
	
	/** Gets the amount of memory currently in use by started but not ended processes. */
	public long getUsedMemory() { return usedMemory; }
	
	/** Gets the amount of memory currently available for use by nonstarted processes. */
	public long getAvailableMemory() { return totalMemory - usedMemory; }
	
	public Iterable<Process> getNewlyArrivedProcesses()
	{
		return newlyArrivedJobs;
	}
	
	public Iterable<Process> getNewlyFinishedProcesses()
	{
		return newlyFinishedJobs;
	}

}// ENDS class CPUScheduler
