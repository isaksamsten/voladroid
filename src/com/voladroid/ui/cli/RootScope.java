package com.voladroid.ui.cli;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Config;
import com.voladroid.model.Configurable;
import com.voladroid.model.Workspace;
import com.voladroid.ui.cli.args.Scope;

public class RootScope extends Scope {

	private Completion completer = new Completion() {

		@Override
		public List<String> complete(String m, int arg1) {
			List<String> strs = CompletionUtils.matches(keys(), m + ".*");
			return strs;
		}
	};

	public RootScope(VoladroidCli app) {
		super(app);
		init();
	}

	private void init() {
		add("help", 0, "Show this information");
		add("enter", -1, "<path> Enter workspace (<path> is optional)");
		add("exit", 0, "Exit the current scope");
		add("stack", 0, "Show the stack (* = current)");
		add("debug", -1,
				"<true|false> Enable/Disable debugging (current context)");
		add("where", 0, "Show your current location");

		add("config", -1, "<name> <value> Set/Get configuration "
				+ "options (<name> <value> is optional)");

		add("exit!", 0, "quit", "Quit the application");
		alias("where", "pwd");
		alias("enter", "cd");
	}

	public void help(Scope self, List<String> args) {
		out(self.usage());
	}

	public Scope enter(Scope self, List<String> args) {
		Workspace space = Workspace.getWorkspace();
		return new WorkspaceScope(RootScope.this, space);
	}

	public void exit(Scope self, List<String> args) {
		Scope e = stack().pop();
		out("Leaving %s", e.name());
	}

	public void stack(Scope self, List<String> args) {
		out(stack().local().name() + "*");
		Iterator<Scope> it = stack().listIterator(1);
		while (it.hasNext()) {
			out(it.next().name());
		}
	}

	public void debug(Scope self, List<String> args) {
		if (args.size() > 0) {
			self.put("debug", Boolean.parseBoolean(args.get(0)));
		} else {
			Boolean debug = self.get("debug");
			if (debug == null) {
				debug = false;
			}
			out("Debugging is " + (debug ? "on" : "off"));
		}
	}

	public void where(Scope self, List<String> args) {
		out(stack().local());
	}

	public void config(Scope self, List<String> args) {
		Config config = self.getConfigurable().getConfig();
		if (args.size() == 2) {
			String key = args.get(0);
			String value = args.get(1);
			config.setProperty(key, value);
		} else if (args.size() == 1) {
			out(config.getProperty(args.get(0)));
		} else {
			Iterator<Object> iter = config.getKeys();
			while (iter.hasNext()) {
				Object key = iter.next();
				out("%s: %s", key, config.getProperty(key.toString()));
			}
		}
	}

	public void quit(Scope self, List<String> args) {
		stack().clear();
	}

	@Override
	public List<Completion> getCompleters() {
		return Arrays.asList(completer);
	}

	@Override
	public Configurable getConfigurable() {
		return Workspace.getWorkspace();
	}

	@Override
	public String name() {
		return "Voladroid";
	}

}
