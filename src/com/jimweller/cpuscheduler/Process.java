package com.jimweller.cpuscheduler;

import java.io.*;
import java.util.Random;

/** 
 *   An aggregate data type to represent a process to schedule. It will
 *   maintain all of it's state internally.
 * CS 143A - Group 8
 * @author: Drake Tetreault 35571095
 * @author: Virginia McMinn 55438064
 * @author: Ling Han Meng 72933055
 */
public class Process{
    
	public static final long 
		MAX_BURST_TIME = 100,
		MAX_DELAY_TIME = 70,
		MAX_PRIORITY = 9;
	
    /** The process' identification number. It must be unique. */
    private long PID=0;      // [ 0 - sizeof(long) ]

    /** Store the value of the next pid to use. There is no garbage
	collection. I just take the next available number. It is the same
	variable in *all* instances of a process.
    */
    private static long nextPID = 0; // [ 0 - sizeof(long) ]


    /** Process' CPU burst time. The total amount of CPU time that a process
	needs in seconds. [0 - 100]. */
    private long burst=0;    // [0 - 100]

    /** Store original burst state. To show total an remaining I want to   
	know what the original CPU burst of a process was. The variable
	'burst' will store the "active" state 
    */
    private long initBurst=0;

    /** Delay of arrival. How private long after the previous process arrival
	does this process arrive? It is a time offset in seconds. 
	[0 - 95]*/
    private long delay=0;    // [0 - 95]

    /** Execution priority. The weight that this process has. It aids
	the scheduler in some premptive algorithms. [0(low) - 9 (high)] */
    private long priority=0; // [0(low) - 9 (high)]

    /** How much memory this process requires in order to run. */
    private long memory = 0; // Randomly generated in [0, 50].
    
    /** The actual time the process arrived. This will be set by the 
	scheduler. */
    private long arrival=0;  // set by scheduler

    /** The time that the process firsts begins execution. This will be
	set by the scheduler. */
    private long start=0;   

    /** The time that the process ends execution. This will be
	set by the scheduler. */
    private long finish=0;  

    /** The total amount of time the process spent waiting. Measured
	from the time it arrives to the time it finishes */
    private long wait=0;   
    
    /** The measure of time after arrival that it took to begin execution */
    private long response=0; 

    /** Measure of the total time a process was in the clutches of the
	scheduler. Whether ready or waiting or running the full lifecycle
	of this process */
    private long lifetime = 0;
    
    /** a way to check if a process has occured yet */
    private boolean arrived = false;

    /** A way to check if a process has started running yet. */
    private boolean started=false;

    /** A way to see if a process is complete */
    private boolean finished=false;

    /** A way to see if a process is scheduled to run this cycle */
    private boolean active = false;    
  
    /** Default constructor. Randomly generate a process and fills in fields
	using the bounds specified above. */
    public Process(long seed)
    {
    	Random rand = new Random(seed);
		nextPID++;
		PID = nextPID;
		burst = rand.nextInt((int)MAX_BURST_TIME + 1);
		initBurst = burst;
		delay = rand.nextInt((int)MAX_DELAY_TIME + 1);
		priority = rand.nextInt((int)MAX_PRIORITY + 1);
		memory = rand.nextInt((int)9 + 1);
    }


    /** Articulate constructor. No random generation is done on the
	programmers behalf. It is assumed that all values will be
	within the above parameters.  This method is useful for
	building a queue of process where the data comes from a file
	or other source
	@param b The burst time of the process.
	@param d The delay in process arrival mesured from the 
	         arrival of the previous process.
	@param p The priority weight of the process.
	@param memory The memory consumption of the process.
    */
    Process(long b, long d, long p, long memory){
		nextPID++;
		PID = nextPID;
		burst = b;
		initBurst = burst;
		delay = d;
		priority = p;
		this.memory = memory;
    }

    public boolean equals(Object object)
    {
    	return object instanceof Process && ((Process)object).PID == PID;
    }

    /**
     * Go through the motions of running one cycle on a process. 
     * uses current time to check if certain events have occured
     * (e.g. arrival, start, finish) and then sets the state of those
     * events booleans.
     */
    public  synchronized void executing(long timeNow)
    {	
    	active=true;
	
		arrived = timeNow >= arrival;
	    
		if( burst == initBurst){
		    started  = true;
		    start    = timeNow;
		    response = start - arrival;
		}
		    
		burst--;
		lifetime++;
		    
		if( burst <= 0){
		    finished = true;
		    finish = timeNow;
		}
    }


    /** 
     * The inverse of executing.  Go through the motions of waiting
     * for cpu time. Use current time to check if this was our arrival time.
     */
    public synchronized void waiting(long timeNow){
	 	active=false;
		lifetime++;
		wait++;
		arrived = timeNow >= arrival;
    }

    
    /** Show state of process on the terminal
     */
    public void print(){
    	System.out.println("PID     : " + PID + "\n" +
			   "Burst   : " + burst + "\n" +
			   "IBurst  : " + initBurst + "\n" +
			   "Memory  : " + memory + "\n" +
			   "Delay   : " + delay + "\n" +
			   "Priority: " + priority + "\n" +
			   "Arrival : " + arrival + "\n" +
			   "Start   : " + start + "\n" +
			   "Finish  : " + finish + "\n" +
			   "Wait    : " + wait + "\n" +
			   "Response: " + response);
    }
    

    /** Show state on a line. For tabular formats. Must figure out how to get table
     *  formatting. Oh, to say %8ld in java.
     */
    public void println(){
	System.out.println("PID " + PID + " b" + burst + "mem " + memory + " p" +
			   priority +  " a" +
			   arrival + " s" + start +  " f" +
			   finish + " w" + wait +  " r" +
			   response);
			 
    }


    /** Print comma seperated values to the terminal
     */
    public void printCSV(){
	System.out.println(PID + "," + initBurst + "," + memory + "," +
			   priority +  "," + arrival + "," + 
			   start +  "," + finish + ","  +
			    wait +  "," + response + "," + lifetime);
			 
    }

    /**
     * Print comma seperated values list to a PrintWriter object.
     */
    public void printCSV(PrintWriter pw){
	pw.println(PID + "," + initBurst + "," + memory + "," +
			   priority +  "," + arrival + "," + 
			   start +  "," + finish + ","  +
			    wait +  "," + response + "," + lifetime);
			 
    }


    /**
     * Get the value of response time.
     * @return Value of response time .
     */
    public long getResponseTime() {return response;}
    
    /**
     * Get the value of wait.
     * @return Value of wait.
     */
    public long getWaitTime() {
    	//System.out.println("arrival: " + getArrivalTime() + ", wait: " + wait);
    	return wait;}
    
    
    /**
     * Get the value of finish.
     * @return Value of finish.
     */
    public long getFinishTime() {return finish;}
    
    /**
     * Get the value of start.
     * @return Value of start.
     */
    public long getStartTime() {return start;}
    
    /**
     * Set the value of start.
     * @param v  Value to assign to start.
     */
    //public void setStartTime(long  v) {this.start = v;}
    
    /**
     * Get the value of arrival.
     * @return Value of arrival.
     */
    public long getArrivalTime() {return arrival;}
    
    /**
     * Set the value of arrival.
     * @param v  Value to assign to arrival.
     */
    public void setArrivalTime(long  v) {this.arrival = v;}
    
    /**
     * Get the value of priority.
     * @return Value of priority.
     */
    public long getPriorityWeight() {return priority;}

   /**
     * Get the value of delay.
     * @return Value of delay.
     */
    public long getDelayTime() {return delay;}
    
    /**
     * Get the value of burst.
     * @return Value of burst.
     */
    public long getBurstTime() {return burst;}
    
    /**
     * Get the initial burst value of this process.
     * @return Value of initBurst.
     */
    public long getInitBurstTime() {return initBurst;}
    
    
    /**
     * Get the value of PID.
     * @return Value of PID.
     */
    public long getPID() {return PID;}
    

    /**
     * Get the value of lifetime
     * @return current lifetime in the scheduling queue
     */
    public long getLifetime(){ return lifetime; };


    /**
     * Gets the memory required for the process to execute.
     * @return The required amount of memory.
     */
    public long getMemory() { return memory; };
    
    /** Restores the process to it's original state. For rerunning
	a data set maybe under different circumstances*/
    public void restore(){
		burst = initBurst;
		lifetime = 0;
		response = 0;
		start    = 0;
		wait     = 0;
		active   = false;
		started  = false;
		finished = false;
		arrived  = false;
		memory = 0;
    }

    /**
     * Get the value of active.
     * @return Value of active.
     */
    public boolean isActive() {return active;}
    

    /**
     * Get the value of finished.
     * @return Value of finished.
     */
    public boolean isFinished() {return finished;}

    
    /**
     * Get the value of started.
     * @return Value of started.
     */
    public boolean isStarted() {return started;}
    

    /**
     * Get the value of arrived.
     * @return Value of arrived.
     */
    public boolean isArrived() { return arrived; }

} // ENDS class Process