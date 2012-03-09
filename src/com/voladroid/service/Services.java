package com.voladroid.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class Services {

	private static ConfigService config = null;

	public static ConfigService getConfig() {
		if (config == null)
			config = new ConfigService();
		return config;
	}

	private static EnvironmentService environment = null;

	public static EnvironmentService getEnvironment() {
		if (environment == null)
			environment = new EnvironmentService();
		return environment;
	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

}
