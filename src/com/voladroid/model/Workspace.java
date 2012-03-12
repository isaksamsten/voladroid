package com.voladroid.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import com.voladroid.exception.ProjectNotFoundException;
import com.voladroid.service.ProjectListener;

public class Workspace implements Iterable<Project>, Configurable {
	private static Workspace workspace = null;
	private File location;

	private Project current;
	private List<Project> projects = new ArrayList<Project>();
	private Config config;

	private EventListenerList events = new EventListenerList();

	public static Workspace getWorkspace() {
		if (workspace == null) {
			File location = FileUtils.getFile(FileUtils.getUserDirectory(),
					"voladroid", "workspace");
			if (!location.exists()) {
				try {
					FileUtils.forceMkdir(location);
				} catch (IOException e) {
					return null;
				}
			}
			workspace = new Workspace(location);
			List<?> projects = workspace.getConfig().getList("projects");
			for (Object o : projects) {
				workspace.projects.add(new Project(workspace, o.toString()));
			}
		}

		return workspace;
	}

	public Workspace(File path) {
		location = path;
		try {
			config = new Config(
					FileUtils.getFile(location, Config.DEFAULT_FILE));
			if (!config.containsKey(Configs.ADB_PATH)) {
				config.addProperty(Configs.ADB_PATH,
						"/home/isak/bin/android-sdk-linux/platform-tools/adb");
			}
			if (!config.containsKey(Configs.HPROF_CONF_PATH)) {
				config.addProperty(Configs.HPROF_CONF_PATH,
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

	public Project createProject(String name) {
		Project project = getProject(name);
		if (project == null) {
			try {
				File projloc = FileUtils.getFile(getLocation(), name);
				FileUtils.forceMkdir(projloc);
				project = new Project(this, name);
				projects.add(project);

				fireProjectAdded(project);

				createConfigEntry(project);

				return project;
			} catch (IOException e) {
				throw new ProjectNotFoundException(name + " cant be created");
			}
		} else {
			return project;
		}
	}

	/**
	 * @param project
	 */
	protected void createConfigEntry(Project project) {
		List<Object> l = getConfig().getList("projects");
		l.add(project.getName());
		getConfig().setProperty("projects", l);
	}

	protected void removeConfigEntry(Project project) {
		List<Object> l = getConfig().getList("projects");
		l.remove(project.getName());
		getConfig().setProperty("projects", l);
	}

	public void removeProject(String name) {
		Project project = getProject(name);
		removeProject(project);
	}

	public void removeFuzzyProject(String name) {
		Project project = getFuzzyProject(name);
		removeProject(project);
	}

	public void removeProject(Project project) {
		if (project != null) {
			projects.remove(project);
			removeConfigEntry(project);
			try {
				FileUtils.forceDelete(project.getLocation());
				fireProjectRemove(project);
			} catch (IOException e) {
				throw new ProjectNotFoundException(e);
			}
		}
	}

	public void setCurrentProject(Project project) {
		current = project;
		fireProjectDefault(current);
	}

	public Project getCurrentProject() {
		return current;
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

	public Project getFuzzyProject(String name) {
		for (Project p : projects)
			if (p.getName().matches(name + ".*"))
				return p;
		return null;
	}

	public List<Project> getProjects() {
		return Collections.unmodifiableList(projects);
	}

	public File getLocation() {
		return location;
	}

	public void fireProjectAdded(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.projectAdded(project);
		}
	}

	public void fireProjectDefault(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.currentProject(project);
		}
	}

	public void fireProjectRemove(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.projectRemoved(project);
		}
	}

	public void fireProjectChange(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.projectRemoved(project);
		}
	}

	public void addProjectListener(ProjectListener l) {
		events.add(ProjectListener.class, l);
	}

	public void removeProjectListener(ProjectListener l) {
		events.remove(ProjectListener.class, l);
	}

	@Override
	public String toString() {
		return "Workspace [getCurrentProject()=" + getCurrentProject()
				+ ", getLocation()=" + getLocation() + "]";
	}

}
