using System;

namespace Analysis
{
	public static class RandomExt
	{
		/// <summary>
		/// Uses polar form of Box-Muller transform to return a random
		/// integer from a normal distribution with the specified mean
		/// and standard deviation.
		/// </summary>
		public static int NextNormal(this Random @this, float mean, float standardDeviation)
		{
			// Uniform random variables in the range [-1, 1]
			double uniformA, uniformB;
			// squaredSum = uniformA^2 + uniformB^2
			double squaredSum;

			do
			{
				uniformA = @this.NextDouble() * 2 - 1;
				uniformB = @this.NextDouble() * 2 - 1;
				squaredSum = uniformA * uniformA + uniformB * uniformB;
			} while (squaredSum == 0 || squaredSum >= 1);

			// standardNormal is a normally distributed random variable
			// with mean = 0 and standard deviation = 1. It is also possible
			// to get another one by using uniformB * ... instead of uniformA.
			double standardNormal = uniformA * Math.Sqrt(-2.0 * Math.Log(squaredSum) / squaredSum);
			return (int) Math.Round(standardNormal * standardDeviation + mean);
		}
	}
}