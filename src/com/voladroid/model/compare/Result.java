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

}
