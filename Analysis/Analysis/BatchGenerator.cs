using System;
using System.Linq;
using System.Threading.Tasks;

namespace Analysis
{
	public interface IBatchGenerator
	{
		Task<bool> TryGenerate(GeneratorParams paramaters);
	}

	public class BatchGenerator : IBatchGenerator
	{
		private readonly IBatchCreator batchCreator;
		private readonly IBatchWriter batchWriter;

		public BatchGenerator(IBatchCreator batchCreator, IBatchWriter batchWriter)
		{
			this.batchCreator = batchCreator;
			this.batchWriter = batchWriter;
		}

		public async Task<bool> TryGenerate(GeneratorParams parameters)
		{
			var batches = new Batch[parameters.BatchCount];

			await Task.Run(() =>
			{
				Parallel.For(0, parameters.BatchCount, i =>
				{
					batches[i] = batchCreator.CreateBatch(i.GetHashCode(), parameters);
				});
			});

			var batchesWithFileNames = batches.Select((batch, batchIndex) => new
			{
				Batch = batch,
				FilePath = MakeFinalFileName(batchIndex, batch, parameters)
			});

			return (await Task.WhenAll(batchesWithFileNames.Select(async item => new
			{
				Result = await batchWriter.TryWriteBatchFile(item.FilePath, item.Batch),
				Path = item.FilePath
			}))).All(item => item.Result);
		}

		private string MakeFinalFileName(int batchIndex, Batch batch, GeneratorParams parameters)
		{
			if (string.IsNullOrWhiteSpace(batch.FileNameFormat))
				throw new ArgumentException("Invalid batch file name format.");
			return batch.FileNameFormat
				.Replace("{i}", batchIndex.ToString())
				.Replace("{b}", parameters.BurstTimeMean.ToString())
				.Replace("{p}", parameters.PriorityMean.ToString())
				.Replace("{m}", parameters.MemoryMean.ToString());
		}
	}
}