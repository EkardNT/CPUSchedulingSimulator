using System.Windows.Forms;
using Ninject;

namespace Analysis
{
	public partial class GeneratorView : Form
	{
		[Inject]
		public IBatchGenerator BatchGenerator { get; set; }

		public GeneratorView()
		{
			InitializeComponent();
		}

		private async void generateButton_Click(object sender, System.EventArgs e)
		{
			try
			{
				generateButton.Enabled = false;
				GeneratorParams parameters;
				if (TryParseInputs(out parameters) && !(await BatchGenerator.TryGenerate(parameters)))
					MessageBox.Show(
						"An error occurred while generating the process batch files, please try again.",
						"Generation Error",
						MessageBoxButtons.OK);
			}
			finally
			{
				generateButton.Enabled = true;
			}
		}

		private bool TryParseInputs(out GeneratorParams parameters)
		{
			parameters = new GeneratorParams();

			if (!float.TryParse(burstTimeMeanTextBox.Text, out parameters.BurstTimeMean))
			{
				MessageBox.Show("Invalid burst time mean value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!float.TryParse(burstTimeStDevTextBox.Text, out parameters.BurstTimeStDev))
			{
				MessageBox.Show("Invalid burst time standard deviation value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!int.TryParse(delayMinTextBox.Text, out parameters.DelayMin))
			{
				MessageBox.Show("Invalid delay min value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!int.TryParse(delayMaxTextBox.Text, out parameters.DelayMax))
			{
				MessageBox.Show("Invalid delay max value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (parameters.DelayMin > parameters.DelayMax)
			{
				MessageBox.Show("Minimum delay must be less than or equal to maximum delay.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!float.TryParse(priorityMeanTextBox.Text, out parameters.PriorityMean))
			{
				MessageBox.Show("Invalid priority mean value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!float.TryParse(priorityStDevTextBox.Text, out parameters.PriorityStDev))
			{
				MessageBox.Show("Invalid priority standard deviation value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!float.TryParse(memoryMeanTextBox.Text, out parameters.MemoryMean))
			{
				MessageBox.Show("Invalid memory mean value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!float.TryParse(memoryStDevTextBox.Text, out parameters.MemoryStDev))
			{
				MessageBox.Show("Invalid memory standard deviation value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (!int.TryParse(burstTimeMeanTextBox.Text, out parameters.BatchCount))
			{
				MessageBox.Show("Invalid burst time mean value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			if (string.IsNullOrWhiteSpace(fileNameFormatTextBox.Text))
			{
				MessageBox.Show("Invalid file name format value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}
			parameters.FileNameFormat = fileNameFormatTextBox.Text;

			if (!int.TryParse(processCountTextBox.Text, out parameters.ProcessCount))
			{
				MessageBox.Show("Invalid process per batch value.", "Invalid Input", MessageBoxButtons.OK);
				return false;
			}

			return true;
		}
	}
}