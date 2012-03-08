package com.voladroid.model.compare;

import org.eclipse.mat.query.IResult;
import org.eclipse.mat.snapshot.Histogram;
import org.eclipse.mat.util.IProgressListener;

public class HistogramComparer implements IDumpComparer {

	@Override
	public IResult compare(IResult d, IResult o) {
		Histogram h1 = (Histogram) d;
		Histogram h2 = (Histogram) o;
		return h1.diffWithBaseline(h2);
	}

}
