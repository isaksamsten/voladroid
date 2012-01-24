package com.voladroid.service;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.voladroid.model.Workspace;

public class EnvironmentService {
	EnvironmentService() {
	}

	public File getConfig() {
		return FileUtils.getFile(Workspace.getWorkspace().getLocation(),
				"default.properties");
	}
}
