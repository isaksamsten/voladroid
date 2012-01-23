package com.voladroid.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import com.voladroid.service.EnvironmentService;

public class Workspace implements Iterable<Project> {

	private File location;
	private List<Project> projects = new ArrayList<Project>();
	private Config config;

	public Workspace(File path) {
		location = path;
		try {
			config = new Config(
					FileUtils.getFile(location, Config.DEFAULT_FILE));
			if (!config.containsKey("adb-path")) {
				config.addProperty("adb-path",
						"/home/isak/bin/android-sdk-linux/platform-tools/adb");
			}
			if (!config.containsKey("hprof-conv-path")) {
				config.addProperty("hprof-conv-path",
						"/home/isak/bin/android-sdk-linux/tools/hprof-conv");
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Iterator<Project> iterator() {
		return projects.iterator();
	}

	public boolean remove(Object arg0) {
		return projects.remove(arg0);
	}

	/**
	 * Wont create a project use
	 * {@link EnvironmentService#createProject(String)}
	 * 
	 * @param e
	 * @return
	 */
	public boolean add(Project e) {
		return projects.add(e);
	}

	public boolean contains(Object o) {
		return projects.contains(o);
	}

	public Config getConfig() {
		return config;
	}

	public Project getProject(String name) {
		for (Project p : projects)
			if (p.getName().equals(name))
				return p;
		return null;
	}

	public File getLocation() {
		return location;
	}
}
