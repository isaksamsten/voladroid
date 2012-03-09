package com.voladroid.model.compare;

public class Result {

	private double sampleAvrage;
	private double standardDeviation;
	private double totalAvrage;
	private long totalDiff;
	private Long[] samples;

	public Result(double sampleAvrage, double standardDeviation,
			double totalAvrage, Long[] samples) {
		this.sampleAvrage = sampleAvrage;
		this.standardDeviation = standardDeviation;
		this.totalAvrage = totalAvrage;
		this.samples = samples;
	}

	public Long[] samples() {
		return samples;
	}

	public double getSampleAvrage() {
		return sampleAvrage;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public double getTotalAvrage() {
		return totalAvrage;
	}

	public long getTotalDiff() {
		return totalDiff;
	}

	public double getRelativeSampleAvrage() {
		return (getSampleAvrage() / getTotalAvrage()) * 100;
	}

	public double getRelativeStandardDeviation() {
		return (getStandardDeviation() / getSampleAvrage()) * 100;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Total avrage: %f \n", getTotalAvrage()));
		builder.append("------------ \n");
		for (int i = 0; i < samples().length; i++) {
			builder.append(String.format(" Sample delta %d, %d: %d \n", i,
					i + 1, samples()[i]));
		}
		builder.append("\n");
		builder.append(String
				.format("Sample average: %f\n", getSampleAvrage()));
		builder.append(String.format("Relative sample average: %f \n",
				getRelativeSampleAvrage()));
		builder.append(String.format("Standard deviation: %f \n",
				getStandardDeviation()));
		builder.append(String.format("Relative standard deviation: %f \n",
				getRelativeStandardDeviation()));
		builder.append(String.format("Uncertainty: %f±%f",
				getRelativeSampleAvrage(), getRelativeStandardDeviation()));

		return builder.toString();

	}
}
