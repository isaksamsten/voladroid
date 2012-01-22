package com.voladroid.service;

import com.voladroid.model.Project;

import java.util.EventListener;

public interface ProjectListener extends EventListener {

	void defaultProject(Project p);

	void projectRemoved(Project project);

	void projectAdded(Project project);
}
