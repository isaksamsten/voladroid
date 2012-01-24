package com.voladroid.model.adb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voladroid.service.Services;

public final class Hprof {
	private static final Log log = LogFactory.getLog(Hprof.class);

	/**
	 * Convert hprof (android) to hprof (java)
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] convert(byte[] data) {
		try {
			ProcessBuilder process = new ProcessBuilder(Services.getConfig()
					.getHprofConvPath(), "-", "-");

			java.lang.Process p = process.start();
			BufferedOutputStream out = new BufferedOutputStream(
					p.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());

			HandleRead reader = new HandleRead(in);
			HandleWrite write = new HandleWrite(out, data);
			Thread r = new Thread(reader);
			Thread w = new Thread(write);
			r.start();
			w.start();

			int value = p.waitFor();
			r.join();
			w.join();

			if (value == 0) {
				return reader.data;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}
}
