package com.voladroid.service;

import com.voladroid.model.Config;

/**
 * Configuring the workspace
 * 
 * @author isak
 * 
 */
public class ConfigService {

	private Config config;

	ConfigService() {
		config = Services.getEnvironment().getWorkspace().getConfig();
	}

	public String getAdbPath() {
		return (String) config.getProperty("adb-path");
	}
	
	public String getHprofConvPath() {
		return config.getString("hprof-conv-path");
	}
}
