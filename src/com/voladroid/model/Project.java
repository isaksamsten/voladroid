package com.voladroid.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Project {
	private String name;
	private Workspace workspace;

	public Project(Workspace space, String name) {
		this.workspace = space;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public File getLocation() {
		return FileUtils.getFile(getWorkspace().getLocation(), getName());
	}

	public void save(com.voladroid.model.adb.Process p, byte[] data) throws IOException {
		String name = p.getPid() + p.getProcessDescription();
		File file = FileUtils.getFile(getLocation(), name);
		FileUtils.writeByteArrayToFile(file, data);
	}
}
