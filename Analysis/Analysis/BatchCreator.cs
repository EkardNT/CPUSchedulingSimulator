using System;

namespace Analysis
{
	public interface IBatchCreator
	{
		Batch CreateBatch(int randSeed, GeneratorParams parameters);
	}

	public class BatchCreator : IBatchCreator
	{
		public Batch CreateBatch(int randSeed, GeneratorParams parameters)
		{
			var rand = new Random(randSeed);
			var processeDescs = new ProcessDesc[parameters.ProcessCount];
			for (int j = 0; j < parameters.ProcessCount; j++)
			{
				processeDescs[j] = new ProcessDesc(
					cpuBurstTime: Math.Max(0, rand.NextNormal(parameters.BurstTimeMean, parameters.BurstTimeStDev)),
					delay: Math.Max(0, rand.Next(parameters.DelayMin, parameters.DelayMax)),
					priority: Math.Max(0, rand.NextNormal(parameters.PriorityMean, parameters.PriorityStDev)),
					memory: Math.Max(0, rand.NextNormal(parameters.MemoryMean, parameters.MemoryStDev)));
			}
			return new Batch(parameters.FileNameFormat, processeDescs);
		}
	}
}