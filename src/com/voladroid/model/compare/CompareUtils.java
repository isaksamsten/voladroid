package com.voladroid.model.compare;

import java.util.List;

import org.eclipse.mat.util.IProgressListener;

import com.voladroid.model.Dump;

public class CompareUtils {

	public static Result subsequent(List<Dump> dumps, ResultProducer producer,
			IProgressListener listener) {
		return producer.compare(dumps, listener);
	}
}
