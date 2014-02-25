package com.jimweller.cpuscheduler;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

//import Process;
import java.awt.geom.*;

/**
 * A process panel is a thin tall (about 115x100) rectangle that consists
 * of a cpu meter and a priority indicator. The burst panel shows if a process
 * is arrived or active. It also shows a progress bar relating a processes initial
 * cpu burst and it's remaining burst times. The priority at the bottom shows the 
 * weight a process is given in some scheduling algorithms
 */
class ProcessPanel extends JPanel{

    /** The process this panel sharkfishes from */ // Sharkfishes ??!?!
    Process proc;

    /** The width of the process panel */
    static final int PPWIDTH  = 10;

    /** The height of the process panel */
    static final int PPHEIGHT = 200;
        
    /** The label to show the priority. */
    private final JLabel priLbl;
    private final JPanel burstBarSpacer, memoryBarSpacer;

    private long totalMemory, availableMemory;
    
    /** Do you want to see unarrived processes? Look into the future.  */
    static boolean showHidden=false;
    
    /**
     * Articulate constructor.
     * param p the process to base this panel on. 
     */
    ProcessPanel(Process p, long totalMemory, long availableMemory){
		this.proc = p;
		this.totalMemory = Math.max(1, totalMemory);
		this.availableMemory = Math.max(1, availableMemory);

		setBackground(Color.WHITE);
		setSize(PPWIDTH, PPHEIGHT);
		setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		priLbl = new JLabel(Long.toString(proc.getPriorityWeight()));
		priLbl.setBackground(Color.WHITE);
		priLbl.setToolTipText("Once a process has arrived this shows its priority. (0 High and 9 Low)");
		priLbl.setSize(PPWIDTH, (int)(0.20 * PPHEIGHT));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 0;
		add(priLbl, c);
		
		burstBarSpacer = new JPanel();
		burstBarSpacer.setToolTipText("Displays the process' burst status.");
		burstBarSpacer.setSize(PPWIDTH, (int)(0.40 * PPHEIGHT));
		burstBarSpacer.setOpaque(false);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.VERTICAL;
		add(burstBarSpacer, c);
		
		memoryBarSpacer = new JPanel();
		memoryBarSpacer.setToolTipText("Displays the process' memory requirements.");
		memoryBarSpacer.setSize(PPWIDTH, (int)(0.40 * PPHEIGHT));
		memoryBarSpacer.setOpaque(false);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.VERTICAL;
		add(memoryBarSpacer, c);
    }

    /**
     * If the process is done remove it. Otherwise update the burst meter.
     */
    public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		paintBurstBar(burstBarSpacer.getBounds(), g2);
		paintMemoryBar(memoryBarSpacer.getBounds(), g2);
    }
    
    private void paintBurstBar(Rectangle bounds, Graphics2D g)
    {
    	g.setColor(Color.BLACK);
    	g.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
    	double fillFactor = proc.getBurstTime() / (double)Process.MAX_BURST_TIME;
    	g.setColor(Color.RED);
    	g.fillRect(bounds.x + 1, bounds.height - (int)(fillFactor * bounds.height) - 1, bounds.width - 2, (int)(fillFactor * bounds.height));
    }
    
    private void paintMemoryBar(Rectangle bounds, Graphics2D g)
    {
    	if(proc.isStarted() || proc.getMemory() <= availableMemory)
    		g.setColor(Color.GREEN);
    	else
    		g.setColor(Color.RED);
    	g.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
    	double fillFactor = proc.getMemory() / (double)totalMemory;
    	if(fillFactor > 1)
    	{
    		fillFactor = 1;
    		g.setColor(Color.ORANGE);
    	}
    	else
    		g.setColor(Color.YELLOW);
    	g.fillRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, (int)(fillFactor * (bounds.height - 2)));
    }


    /**
     * Get the value of proc.
     * @return Value of proc.
     */
    public Process getProc() {return proc;}

    
    /**
     * Set the value of proc.
     * @param v  Value to assign to proc.
     */
    public void setProc(Process  v) {this.proc = v;}
    
	
    public Dimension getPreferredSize(){
    	return ( new Dimension(PPWIDTH,PPHEIGHT));
    }
   
    public void setMemoryCapacity(long totalMemory, long availableMemory)
    {
    	this.totalMemory = Math.max(1, totalMemory);
    	this.availableMemory = availableMemory;
    }
    
    /**
       * Get the value of showHidden.
       * @return Value of showHidden.
       */
    public static boolean getShowHidden() {return showHidden;}
    
    /**
       * Set the value of showHidden.
       * @param v  Value to assign to showHidden.
       */
    public static void setShowHidden(boolean  v) { showHidden = v;}

} // ENDS  ProcessPanel

