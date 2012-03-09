package com.voladroid.ui.cli;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.eclipse.mat.util.VoidProgressListener;

import com.voladroid.model.Dump;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.IDebugBridge;
import com.voladroid.model.adb.Process;
import com.voladroid.model.compare.BinaryResultProducer;
import com.voladroid.model.compare.CompareUtils;
import com.voladroid.model.compare.ObjectResultProducer;
import com.voladroid.model.compare.Result;
import com.voladroid.model.compare.ResultProducer;
import com.voladroid.service.Services;
import com.voladroid.ui.cli.args.Argument;
import com.voladroid.ui.cli.args.ArgumentExecutor;
import com.voladroid.ui.cli.args.ArgumentStack;
import com.voladroid.ui.cli.args.ImageArgument;

public class VoladroidMain {

	private ArgumentStack stack = ArgumentStack.getInstance();
	private Scanner in = new Scanner(System.in);

	private ArgumentExecutor root = new ArgumentExecutor("Root", this);
	{
		root.put("workspace", Workspace.getWorkspace());
		root.add("help", new Argument(0, "Show this information") {
			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local().usage());
				return null;
			}
		});

		root.add("exit", new Argument(0, "Exit the current scope") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				ArgumentExecutor e = stack.pop();
				System.out.println("Leaving '" + e.name() + "'");
				return null;
			}
		});

		root.add("stack", new Argument(0, "Show the stack (* = current)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local().name() + "*");
				Iterator<ArgumentExecutor> it = stack.listIterator(1);
				while (it.hasNext()) {
					System.out.println(it.next().name());
				}
				return null;
			}
		});

		root.add("debug", new Argument(-1,
				"<true|false> Enable/Disable debugging (current context)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				if (args.size() > 0) {
					stack.local().put("debug",
							Boolean.parseBoolean(args.get(0)));
				} else {
					Boolean debug = stack.local().get("debug");
					if (debug == null) {
						debug = false;
					}
					System.out
							.println("Debugging is " + (debug ? "on" : "off"));
				}
				return null;
			}
		});

		root.add("where", new Argument(0, "Show your current location") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local());
				return null;
			}
		});

		root.add("enter", new Argument(-1,
				"<path> Enter workspace (<path> is optional)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				out("Enter workspace: '%s'", space.getLocation());
				return workspace;
			}

		});

		root.add("exit!", new Argument(0, "Quit the application") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				stack.clear();
				return null;
			}
		});

		root.alias("where", "pwd");
		root.alias("enter", "cd");
	}

	private ArgumentExecutor workspace = new ArgumentExecutor("Workspace", root);
	{

		workspace.add("create", new Argument(1, "<name> Create a new project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				space.createProject(args.get(0));
				return null;
			}
		});

		workspace.add("remove", new Argument(1, "<name> Remove a project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				space.removeFuzzyProject(args.get(0));
				return null;
			}
		});

		workspace.add("projects", new Argument(0, "List projects") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				for (Project p : space.getProjects()) {
					String name = p.getName();
					if (space.getCurrentProject() != null
							&& space.getCurrentProject().equals(p)) {
						name += "*";
					}

					out(name);
				}

				return null;
			}
		});

		workspace.add("select", new Argument(1,
				"Select a project as the current project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				String name = args.get(0);
				Workspace space = get("workspace");
				Project p = space.getProject(name);
				if (p != null) {
					space.setCurrentProject(p);
				} else {
					out("No such project");
				}
				return null;
			}
		});

		workspace.add("enter", new Argument(-1,
				"<name> Enter project (<name> optional)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				Project p = null;
				String name = "";
				if (args.isEmpty()) {
					p = space.getCurrentProject();
				} else {
					name = args.get(0);
					p = space.getFuzzyProject(name);
				}

				if (p != null) {
					project.put("project", p);
					return project;
				} else {
					out("Could not enter project '%s'\n", name);
				}

				return null;
			}
		});

		workspace.alias("projects", "ls");
		workspace.alias("enter", "cd");
		workspace.alias("remove", "rm");
		workspace.alias("create", "mk");
	}

	private ArgumentExecutor project = new ArgumentExecutor("Project",
			workspace);
	{
		project.add("devices", new Argument(0,
				"List availible android devices     ") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				IDebugBridge bridge = DebugBridge.getInstance();
				for (Device d : bridge.getDevices()) {
					System.out.println(d.getSerialNumber());
				}
				return null;
			}
		});

		project.add("enter", new Argument(1,
				"<id> Enter the scope of a device (id can be partial)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				String name = args.get(0);
				Device tmp = null;
				for (Device d : DebugBridge.getInstance().getDevices()) {
					if (d.getSerialNumber().matches(name + ".*")) {
						tmp = d;
						break;
					}
				}

				if (tmp == null) {
					out("Device '%s' not found", name);
					return null;
				}

				device.put("device", tmp);
				return device;
			}
		});

		project.add("compare", new Argument(-1,
				"<object|byte> Compare the memory images") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");
				List<Dump> dumps = p.getDumps();
				String cmp = "object";

				if (!args.isEmpty()) {
					cmp = args.get(0);
				}

				if (args.size() > 1) {
					String[] interval = args.get(1).split("-");
					int min = Integer.parseInt(interval[0]), max = Integer
							.parseInt(interval[1]);
					dumps = dumps.subList(min, max);
				}

				ResultProducer producer = null;
				if (cmp.equals("object")) {
					producer = new ObjectResultProducer();
				} else if (cmp.equals("byte")) {
					producer = new BinaryResultProducer();
				} else {
					throw new Exception();
				}

				Result res = CompareUtils.subsequent(dumps, producer,
						new VoidProgressListener());
				out(res);
				return null;
			}
		});

		project.add("info", new Argument(0, "Show information about project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");
				System.out.println(p);
				return null;
			}
		});

		project.add("images", new Argument(0, "List the memory images") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");
				int i = 0;
				for (Dump d : p.getDumps()) {
					System.out.println(i++ + " " + d.getName());
				}

				return null;
			}
		});

		project.alias("images", "ls");
		project.alias("devices", "lsd");
		project.alias("enter", "cd");
	}

	private ArgumentExecutor device = new ArgumentExecutor("Device", project);
	{

		device.add("process", new Argument(0, "List running process") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Device dev = get("device");
				List<Process> process = dev.getProcesses();
				out("pid\t\tname\n--------------------------------");
				for (Process p : process) {
					out("%s\t \t%s", p.getPid(), p.getProcessDescription());
				}

				return null;
			}
		});

		device.add("info", new Argument(0, "Show information about device") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Device dev = get("device");
				out("Id: %s", dev.getSerialNumber());
				out("Emulator: %s", dev.isEmulator() ? "yes" : "no");
				return null;
			}
		});

		device.add("image", new ImageArgument());

		device.alias("process", "ls");

	}

	public String in(String msg) {
		System.out.print(msg);
		return in.nextLine();
	}

	public void error(Exception e) {
		Boolean debug = stack.local().get("debug");
		if (debug != null && debug) {
			System.out.print(Services.getStackTrace(e));
		} else {
			System.out.println(e);
		}
	}

	public void error(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public void out(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public void out(Object o) {
		System.out.println(o);
	}

	public void start() {
		stack.push(root);
		while (true) {
			String[] cmd = in(">> ").split("\\s+");

			try {
				ArgumentExecutor executor = stack.local().execute(cmd);
				if (executor != null) {
					stack.push(executor);
				} else if (stack.empty()) {
					break;
				}
			} catch (Exception e) {
				error(e);
			}
		}
	}

}
