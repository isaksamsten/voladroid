package com.voladroid.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import com.voladroid.model.adb.Process;
import com.voladroid.service.Services;

public class Project {

	public static final String DUMP_LOCATION = "memory";

	private String name;

	private Config config;
	private Workspace workspace;

	public Project(Workspace space, String name) {
		this.workspace = space;
		this.name = name;
		try {
			this.config = new Config(FileUtils.getFile(getLocation(),
					Config.DEFAULT_FILE));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dump> getDumps() {
		List<Dump> dumps = new LinkedList<Dump>();
		for (Object name : getConfig().getList("dumps")) {
			dumps.add(new Dump(this, (String) name));
		}

		return dumps;
	}

	public File getDumpLocation() {
		String dump = DUMP_LOCATION;
		if (getConfig().containsKey("dump-location")) {
			dump = getConfig().getString("dump-location");
		}

		File location = FileUtils.getFile(getLocation(), dump);
		if (!location.exists()) {
			try {
				FileUtils.forceMkdir(location);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return location;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public Config getConfig() {
		return config;
	}

	public File getLocation() {
		return FileUtils.getFile(getWorkspace().getLocation(), getName());
	}

	public void save(Process p, byte[] data) throws IOException {
		String name = p.getPid() + p.getProcessDescription()
				+ new Date().getTime();

		File file = FileUtils.getFile(getDumpLocation(), name + ".tmp");
		File to = FileUtils.getFile(getDumpLocation(), name + ".hprof");
		FileUtils.writeByteArrayToFile(file, data);

		try {
			Services.getEnvironment().convertHprof(file, to);
			List<Object> dumps = getConfig().getList("dumps");
			dumps.add(name);
			getConfig().setProperty("dumps", dumps);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
