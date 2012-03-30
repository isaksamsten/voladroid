package com.voladroid.ui.cli;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Configurable;
import com.voladroid.model.Project;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.DebugBridgeAdapter;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.Process;
import com.voladroid.ui.cli.args.Scope;
import com.voladroid.ui.cli.args.Imager;
import com.voladroid.ui.cli.args.ScopeException;

public class DeviceScope extends Scope {

	public DeviceScope(Scope parent, Device device) {
		super(parent);
		put("device", device);

		add("process", 0, "List running process");
		add("info", 0, "Show information about device");
		add("image", 3, "<pid> <repeat> <interval> Create an image of "
				+ "<pid> <repeat> times each <interval> second");
		add("connect", 0, "Connect a dump listener");
		add("disconnect", 0, "Disconnect dump listener");
	}

	public void process(Scope self, List<String> args) {
		Device device = self.get("device");
		List<Process> process = device.getProcesses();
		out("pid\t\tname\n--------------------------------");
		for (Process p : process) {
			out("%s\t \t%s", p.getPid(), p.getProcessDescription());
		}
	}

	public void info(Scope self, List<String> args) {
		Device device = self.get("device");
		out("Id: %s", device.getSerialNumber());
		out("Emulator: %s", device.isEmulator() ? "yes" : "no");
	}

	public void connect(Scope self, List<String> args) {
		Imager.setSelf(self);
	}

	public void disconnect(Scope self, List<String> args) {
		Imager.setSelf(null);
	}

	public void image(Scope self, List<String> args) {
		if (!Imager.canImage())
			throw new RuntimeException("No connected dumper (issue connect)");

		String pid = args.get(0);
		Device dev = self.get("device");
		String name = dev.getProcessName(Integer.parseInt(pid));
		int repeat = Integer.parseInt(args.get(1));
		int interval = Integer.parseInt(args.get(2)) * 1000;
		Process process = dev.getProcess(name);

		for (int n = 0; n < repeat; n++) {
			process.dumpHprof();
			try {
				Thread.sleep(interval);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onExit() {
		disconnect(this, new LinkedList<String>());
	}

	@Override
	public void onEnter() {
		super.onEnter();
		connect(this, new LinkedList<String>());
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

	public Device getDevice() {
		return get("device");
	}

	@Override
	public String name() {
		return String.format("Device '%s'", getDevice().getSerialNumber());
	}

}
