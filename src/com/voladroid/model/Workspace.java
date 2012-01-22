package com.voladroid.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.voladroid.exception.ProjectNotFoundException;

public class Workspace implements Iterable<Project> {

	private File location;
	private List<Project> projects = new ArrayList<Project>();

	public Workspace(File path) {
		location = path;
	}

	public Iterator<Project> iterator() {
		return projects.iterator();
	}

	public boolean remove(Object arg0) {
		return projects.remove(arg0);
	}

	public boolean add(Project e) {
		return projects.add(e);
	}

	public boolean contains(Object o) {
		return projects.contains(o);
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
