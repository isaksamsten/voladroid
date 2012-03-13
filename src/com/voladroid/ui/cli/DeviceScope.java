package com.voladroid.ui.cli;

import java.util.List;

import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Configurable;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.Process;
import com.voladroid.ui.cli.args.Scope;
import com.voladroid.ui.cli.args.ImageArgument;

public class DeviceScope extends Scope {

	public DeviceScope(Scope parent, Device device) {
		super(parent);
		put("device", device);

		add("process", 0, "List running process");
		add("info", 0, "Show information about device");
		add("image", new ImageArgument());
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
