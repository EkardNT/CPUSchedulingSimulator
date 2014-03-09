using System.Collections.Generic;

namespace Analysis
{
	public class Batch
	{
		public string FileNameFormat { get; private set; }
		public IEnumerable<ProcessDesc> ProcessDescs { get; private set; }

		public Batch(string fileNameFormat, IEnumerable<ProcessDesc> processDescs)
		{
			FileNameFormat = fileNameFormat;
			ProcessDescs = processDescs;
		}
	}
}