package com.voladroid.model.compare;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.mat.snapshot.Histogram;
import org.eclipse.mat.util.IProgressListener;

import com.voladroid.model.Dump;

public class BinaryResultProducer extends ObjectResultProducer {

	@Override
	public Result compare(List<Dump> dumps, IProgressListener listener) {
		List<ByteDump> bytes = new ArrayList<ByteDump>();
		for (Dump d : dumps) {
			try {
				bytes.add(new ByteDump(FileUtils.readFileToByteArray(d
						.getLocation())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		double totalAvrage = totalAvrage(bytes);

		ArrayList<Long> samples = samples(bytes);

		double diffAvg = sampleAvrage(samples);
		double stdDev = standardDeviation(samples, diffAvg);

		// TODO Auto-generated method stub
		return new Result(diffAvg, stdDev, totalAvrage);
	}

	private ArrayList<Long> samples(List<ByteDump> bytes) {
		ArrayList<Long> samples = new ArrayList<Long>();
		BinaryComparer comparer = new BinaryComparer();
		for (int n = 0; n < bytes.size() - 1; n++) {
			ByteDump a = bytes.get(n);
			ByteDump b = bytes.get(n + 1);
			ByteDump d = (ByteDump) comparer.compare(a, b);

			samples.add(d.getLength());
		}
		return samples;
	}

	private double totalAvrage(List<ByteDump> bytes) {
		long totalLen = 0;
		for (ByteDump b : bytes) {
			totalLen += b.getData().length;
		}

		return totalLen / bytes.size();
	}

}
