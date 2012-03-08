package com.voladroid.model.compare;

public class Result {

	private double sampleAvrage;
	private double standardDeviation;
	private double totalAvrage;
	private long totalDiff;

	public Result(double sampleAvrage, double standardDeviation,
			double totalAvrage) {
		this.sampleAvrage = sampleAvrage;
		this.standardDeviation = standardDeviation;
		this.totalAvrage = totalAvrage;
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
