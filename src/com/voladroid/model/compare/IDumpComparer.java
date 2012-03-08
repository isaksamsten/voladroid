package com.voladroid.model.compare;

import org.eclipse.mat.query.IResult;

public interface IDumpComparer {

	IResult compare(IResult d, IResult o);
}
