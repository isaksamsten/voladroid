package com.voladroid.service;

import com.voladroid.model.Project;

import java.util.EventListener;

public interface ProjectListener extends EventListener {

	void currentProject(Project p);

	void projectRemoved(Project project);

	void projectAdded(Project project);
}
