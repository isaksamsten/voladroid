package com.voladroid.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.voladroid.exception.ProjectNotFoundException;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;

public class EnvironmentService {

	private Workspace workspace = null;
	private EventListenerList events = new EventListenerList();
	private Project project;

	EnvironmentService() {
	}

	public File getConfig() {
		return FileUtils.getFile(getWorkspace().getLocation(),
				"default.properties");
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
			List<?> projects = workspace.getConfig().getList("projects");
			for (Object o : projects) {
				workspace.add(new Project(workspace, o.toString()));
			}
		}

		return workspace;
	}

	public Project createProject(String name) {
		Project project = getWorkspace().getProject(name);
		if (project == null) {
			try {
				File projloc = FileUtils.getFile(getWorkspace().getLocation(),
						name);
				FileUtils.forceMkdir(projloc);
				project = new Project(getWorkspace(), name);
				getWorkspace().add(project);
				fireProjectAdded(project);

				List<Object> l = getWorkspace().getConfig().getList("projects");
				l.add(project.getName());
				getWorkspace().getConfig().setProperty("projects", l);

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

	public void convertHprof(File from, File to) throws IOException,
			InterruptedException {
		try {
			Process p = Runtime.getRuntime().exec(
					Services.getConfig().getHprofConvPath()
							+ " "
							+ FilenameUtils.separatorsToSystem(from
									.getAbsolutePath())
							+ " "
							+ FilenameUtils.separatorsToSystem(to
									.getAbsolutePath()));
			int out = p.waitFor();
			if(out != 0)
				throw new IOException("Invalid return of call");
		} catch (IOException e) {
			throw e;
		} catch (InterruptedException e) {
			throw e;
		} finally {
			FileUtils.forceDelete(from);
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
			p.currentProject(project);
		}
	}

	protected void fireProjectRemove(Project project) {
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
}
