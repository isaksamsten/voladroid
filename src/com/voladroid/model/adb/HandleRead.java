package com.voladroid.model.adb;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

class HandleRead implements Runnable {
	private BufferedInputStream in;
	public byte[] data;

	HandleRead(BufferedInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		try {
			data = IOUtils.toByteArray(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
