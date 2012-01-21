package com.voladroid.service;

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

}
