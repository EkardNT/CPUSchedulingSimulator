namespace Analysis
{
	public class ProcessDesc
	{
		public int CpuBurstTime { get; private set; }
		public int Delay { get; private set; }
		public int Priority { get; private set; }
		public int Memory { get; private set; }

		public ProcessDesc(int cpuBurstTime, int delay, int priority, int memory)
		{
			CpuBurstTime = cpuBurstTime;
			Delay = delay;
			Priority = priority;
			Memory = memory;
		}
	}
}