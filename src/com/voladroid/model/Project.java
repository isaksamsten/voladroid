package com.voladroid.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import com.voladroid.model.adb.Process;

public class Project {

	public static final String DUMP_LOCATION = "memory";

	public static final String DUMP_LOCATION_KEY = "dump-location";
	public static final String DUMPS_KEY = "dumps";

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
		for (Object name : getConfig().getList(DUMPS_KEY)) {
			dumps.add(new Dump(this, (String) name));
		}

		return dumps;
	}

	/**
	 * Bad.
	 * 
	 * @param name
	 * @return
	 */
	public Dump getDump(String name) {
		for (Dump d : getDumps())
			if (d.getName().equals(name))
				return d;

		return null;
	}

	/**
	 * Bad
	 * 
	 * @param id
	 * @return
	 */
	public Dump getDump(int id) {
		int i = 0;
		List<Dump> dumps = getDumps();
		for (Dump d : dumps) {
			if (i == id) {
				return d;
			}
			i++;
		}

		return null;
	}

	public File getDumpLocation() {
		String dump = DUMP_LOCATION;
		if (getConfig().containsKey(DUMP_LOCATION_KEY)) {
			dump = getConfig().getString(DUMP_LOCATION_KEY);
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

		File file = FileUtils.getFile(getDumpLocation(), name + ".hprof");
		FileUtils.writeByteArrayToFile(file, data);
		List<Object> dumps = getConfig().getList("dumps");
		dumps.add(name);
		getConfig().setProperty("dumps", dumps);

		workspace.fireProjectChange(this);
	}

	@Override
	public String toString() {
		return "Project [getName()=" + getName() + ", getDumpLocation()="
				+ getDumpLocation() + ", getLocation()=" + getLocation() + "]";
	}

}
