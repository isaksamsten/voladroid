package com.voladroid.model.compare;

import org.eclipse.mat.query.IResult;
import org.eclipse.mat.util.IProgressListener;

import com.voladroid.model.Dump;

public interface IDumpComparer {

	IResult compare(IResult d, IResult o);
}
