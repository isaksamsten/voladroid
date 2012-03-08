package com.voladroid.model.compare;

import org.eclipse.mat.query.IResult;
import org.eclipse.mat.query.ResultMetaData;

public class ByteDump implements IResult {

	private byte[] data;
	private long length;

	public ByteDump(byte[] data) {
		this.data = data;
		length = data.length;
	}

	public ByteDump(long len) {
		length = len;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public ResultMetaData getResultMetaData() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}
