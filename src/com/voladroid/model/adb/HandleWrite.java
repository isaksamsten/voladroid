package com.voladroid.model.adb;

import java.io.BufferedOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

class HandleWrite implements Runnable {
	private BufferedOutputStream out;
	private byte[] data;

	HandleWrite(BufferedOutputStream out, byte[] data) {
		this.out = out;
		this.data = data;
	}

	@Override
	public void run() {
		try {
			IOUtils.write(data, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
