package com.voladroid.model;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class Dump {
	private Project project;
	private String name;

	public Dump(Project p, String name) {
		this.project = p;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getLocation() {
		return FileUtils.getFile(project.getDumpLocation(), getName()
				+ ".hprof");
	}

}
