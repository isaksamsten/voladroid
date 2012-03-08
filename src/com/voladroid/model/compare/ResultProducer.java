package com.voladroid.model.compare;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mat.query.IResult;
import org.eclipse.mat.query.ResultMetaData;
import org.eclipse.mat.snapshot.Histogram;
import org.eclipse.mat.util.IProgressListener;

import com.voladroid.model.Dump;

public interface ResultProducer {
	public Result compare(List<Dump> dumps, IProgressListener listener);
}
