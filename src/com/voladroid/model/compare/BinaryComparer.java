package com.voladroid.model.compare;

import org.eclipse.mat.query.IResult;

public class BinaryComparer implements IDumpComparer {

	@Override
	public IResult compare(IResult d, IResult o) {
		byte[] a = ((ByteDump) d).getData();
		byte[] b = ((ByteDump) o).getData();

		if (a.length > b.length) {
			byte[] tmp = a;
			a = b;
			b = tmp;
		}

		int length = 0;
		for (int n = 0; n < a.length; n++) {
			if (a[n] != b[n])
				length++;
		}
		length += b.length - a.length;
		return new ByteDump(length);
	}

}
