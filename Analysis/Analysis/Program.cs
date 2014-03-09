using System;
using System.Windows.Forms;
using Ninject;

namespace Analysis
{
	internal static class Program
	{
		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		private static void Main()
		{
			using (var kernel = CompositionRoot())
			{
				Application.EnableVisualStyles();
				Application.SetCompatibleTextRenderingDefault(false);
				Application.Run(kernel.Get<GeneratorView>());
			}
		}

		private static IKernel CompositionRoot()
		{
			var kernel = new StandardKernel();

			kernel.Bind<IBatchGenerator>().To<BatchGenerator>().InSingletonScope();
			kernel.Bind<IBatchCreator>().To<BatchCreator>().InSingletonScope();
			kernel.Bind<IBatchWriter>().To<BatchWriter>().InSingletonScope();

			return kernel;
		}
	}
}