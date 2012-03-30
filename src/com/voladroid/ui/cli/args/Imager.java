package com.voladroid.ui.cli.args;

import java.io.IOException;
import java.util.List;

import com.voladroid.model.Project;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.DebugBridgeAdapter;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.Process;

public class Imager {

	private static Scope self = null;

	private static DebugBridgeAdapter adapter = new DebugBridgeAdapter() {
		@Override
		public void hprofDump(Process p, byte[] data) {
			if (canImage()) {
				Project project = self.get("project");
				try {
					project.save(p, data);
				} catch (IOException e) {
					self.error(e);
				}
				self.out("Image dumped...");
			}
		}

		@Override
		public void hprofDumpFail(Process p, String reason) {
			self.error(reason);
		}
	};

	public static void setSelf(Scope self) {
		Imager.self = self;
	}

	static {
		DebugBridge.getInstance().add(adapter);
	}

	private Imager() {
	}

	public static boolean canImage() {
		return self != null;
	}
}
