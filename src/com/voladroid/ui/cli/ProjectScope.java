package com.voladroid.ui.cli;

import java.util.List;

import org.eclipse.mat.util.VoidProgressListener;
import org.eclipse.swt.widgets.Display;
import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Configurable;
import com.voladroid.model.Dump;
import com.voladroid.model.Project;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.IDebugBridge;
import com.voladroid.model.compare.BinaryResultProducer;
import com.voladroid.model.compare.CompareUtils;
import com.voladroid.model.compare.ObjectResultProducer;
import com.voladroid.model.compare.Result;
import com.voladroid.model.compare.ResultProducer;
import com.voladroid.ui.cli.args.Scope;

public class ProjectScope extends Scope {

	public ProjectScope(String name, Scope parent, Project project) {
		super(name, parent);

		put("project", project);
		add("devices", 0, "List availible android devices");
		add("inspect", 0, "Inspect a project in the class browser");
		add("enter", 1, "<id> Enter the scope of a device (id can be partial)");
		add("compare", -1, "<object|byte> Compare the memory images");
		add("info", 0, "Show information about project");
		add("images", 0, "List the memory images");

		alias("images", "ls");
		alias("devices", "lsd");
		alias("enter", "cd");
	}

	public void images(Scope self, List<String> args) {
		Project project = self.get("project");
		int i = 0;
		for (Dump d : project.getDumps()) {
			System.out.println(i++ + " " + d.getName());
		}
	}

	public void info(Scope self, List<String> args) {
		Project project = self.get("project");
		out(project);
	}

	public void compare(Scope self, List<String> args) {
		Project project = self.get("project");
		List<Dump> dumps = project.getDumps();
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
		}

		Result res = CompareUtils.subsequent(dumps, producer,
				new VoidProgressListener());
		out(res);
	}

	public Scope enter(Scope self, List<String> args) {
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
		return new DeviceScope("Device", this, tmp);
	}

	public void inspect(Scope self, List<String> args) {
		final Project project = self.get("project");
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				app().inspector().open(project);
			}
		});
	}

	public void devices(Scope self, List<String> args) {
		IDebugBridge bridge = DebugBridge.getInstance();
		for (Device d : bridge.getDevices()) {
			System.out.println(d.getSerialNumber());
		}
	}

	@Override
	public Configurable getConfigurable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Completion> getCompleters() {
		// TODO Auto-generated method stub
		return null;
	}

}
