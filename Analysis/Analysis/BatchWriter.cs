using System.IO;
using System.Threading.Tasks;

namespace Analysis
{
	public interface IBatchWriter
	{
		Task<bool> TryWriteBatchFile(string finalFilePath, Batch batch);
	}

	public class BatchWriter : IBatchWriter
	{
		public async Task<bool> TryWriteBatchFile(string finalFilePath, Batch batch)
		{
			try
			{
				using (var writer = File.CreateText(finalFilePath))
				{
					foreach (var desc in batch.ProcessDescs)
						await writer.WriteLineAsync(string.Format(
							"{0} {1} {2} {3}",
							desc.CpuBurstTime,
							desc.Delay,
							desc.Priority,
							desc.Memory));
					return true;
				}
			}
			catch
			{
				return false;
			}
		}
	}
}
