package com.voladroid.model.compare;

import java.util.List;

import org.eclipse.mat.util.IProgressListener;

import com.voladroid.model.Dump;

public interface ResultProducer {
	public Result compare(List<Dump> dumps, IProgressListener listener);
}
