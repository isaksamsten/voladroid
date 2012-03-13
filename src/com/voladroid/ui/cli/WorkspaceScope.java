package com.voladroid.ui.cli;

import java.util.List;

import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Configurable;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.ui.cli.args.Scope;

public class WorkspaceScope extends Scope {

	public WorkspaceScope(String name, Scope parent, Workspace space) {
		super(name, parent);

		put("workspace", space);
		add("create", 1, "<name> Create a new project");
		add("remove", 1, "<name> Remove a project");
		add("projects", 0, "List projects");
		add("enter", -1, "<name> Enter project (<name> optional)");

		alias("enter", "cd");
		alias("projects", "ls");
	}

	public void create(Scope self, List<String> args) {
		Workspace space = self.get("workspace");
		space.createProject(args.get(0));
	}

	public void remove(Scope self, List<String> args) {
		Workspace space = self.get("workspace");
		space.removeFuzzyProject(args.get(0));
	}

	public void projects(Scope self, List<String> args) {
		Workspace space = self.get("workspace");
		for (Project p : space.getProjects()) {
			String name = p.getName();
			if (space.getCurrentProject() != null
					&& space.getCurrentProject().equals(p)) {
				name += "*";
			}

			out(name);
		}

	}

	public Scope enter(Scope self, List<String> args) {
		Workspace space = self.get("workspace");
		Project p = null;
		String name = "";
		if (args.isEmpty()) {
			p = space.getCurrentProject();
		} else {
			name = args.get(0);
			p = space.getFuzzyProject(name);
		}

		if (p != null) {
			return new ProjectScope("Project", this, p);
		} else {
			out("Could not enter project '%s'\n", name);
		}

		return null;
	}

	@Override
	public List<Completion> getCompleters() {
		return null;
	}

	@Override
	public Configurable getConfigurable() {
		return get("workspace");
	}

}
