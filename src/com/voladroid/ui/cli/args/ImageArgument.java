package com.voladroid.ui.cli.args;

import java.io.IOException;
import java.util.List;

import com.voladroid.model.Project;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.DebugBridgeAdapter;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.Process;

public class ImageArgument extends Argument {
	private Scope self = null;

	DebugBridgeAdapter adapter = new DebugBridgeAdapter() {
		@Override
		public void hprofDump(Process p, byte[] data) {
			Project project = self.get("project");
			try {
				project.save(p, data);
			} catch (IOException e) {
				self.error(e);
			}
			System.out.println("Image dumped...");
		}

		@Override
		public void hprofDumpFail(Process p, String reason) {
			self.error(reason);
		}
	};

	public ImageArgument() {
		super(3, "<pid> <repeat> <interval> Create an image of <pid> <repeat> "
				+ "times each <interval> second");

	}

	@Override
	public Scope execute(Scope self, List<String> args) throws Exception {
		this.self = self;
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
		// One dump can take up to 10 sec.. Lets wait until all dumps are saved
		Thread.sleep(10 * repeat - interval * repeat);

		return null;
	}

	@Override
	public void initialize() {
		DebugBridge.getInstance().add(adapter);
	}

	@Override
	public void dispose() {
		DebugBridge.getInstance().remove(adapter);
	}
}
