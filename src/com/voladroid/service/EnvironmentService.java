package com.voladroid.service;

import java.io.File;
import java.io.IOException;

import javax.swing.event.EventListenerList;

import org.apache.commons.io.FileUtils;

import com.voladroid.exception.ProjectNotFoundException;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;

public class EnvironmentService {

	private Workspace workspace = null;
	private EventListenerList events = new EventListenerList();
	private Project project;

	EnvironmentService() {
	}

	public Workspace getWorkspace() {
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
			for (File p : location.listFiles()) {
				workspace.add(new Project(workspace, p.getName()));
			}
		}

		return workspace;
	}

	public Project addProject(String name) {
		Project project = getWorkspace().getProject(name);
		if (project == null) {
			try {
				File projloc = FileUtils.getFile(getWorkspace().getLocation(),
						name);
				FileUtils.forceMkdir(projloc);
				File config = FileUtils.getFile(projloc, ".config");
				FileUtils.write(config, "name=" + name);

				project = new Project(getWorkspace(), name);
				getWorkspace().add(project);

				fireProjectAdded(project);
				return project;
			} catch (IOException e) {
				throw new ProjectNotFoundException(name + " cant be created");
			}
		} else {
			return project;
		}
	}

	public void removeProject(String name) {
		Project project = getWorkspace().getProject(name);
		if (project != null) {
			getWorkspace().remove(project);
			try {
				FileUtils.forceDelete(project.getLocation());
				fireProjectRemove(project);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setCurrentProject(Project project) {
		this.project = project;
		fireProjectDefault(project);
	}

	public Project getCurrentProject() {
		return this.project;
	}

	protected void fireProjectAdded(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.projectAdded(project);
		}
	}

	protected void fireProjectDefault(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.defaultProject(project);
		}
	}

	protected void fireProjectRemove(Project project) {
		for (ProjectListener p : events.getListeners(ProjectListener.class)) {
			p.projectRemoved(project);
		}
	}

	public void addProjectEvent(ProjectListener l) {
		events.add(ProjectListener.class, l);
	}

	public void removeProjectListener(ProjectListener l) {
		events.remove(ProjectListener.class, l);
	}
}
