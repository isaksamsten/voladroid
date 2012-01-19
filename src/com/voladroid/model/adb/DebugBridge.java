package com.voladroid.model.adb;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;

public class DebugBridge implements IDebugBridge {
	private static String adb = "/home/isak/bin/android-sdk-linux/platform-tools/adb";
	private static IDebugBridge instance;

	public static IDebugBridge getDebugBridge() {
		if (instance == null) {
			instance = new DebugBridge();
		}

		return instance;
	}

	private DebugBridge() {
		AndroidDebugBridge.init(false);
		AndroidDebugBridge.createBridge(adb, true);
	}

	@Override
	public void terminate() {
		AndroidDebugBridge.terminate();
	}

	@Override
	public void addDeviceListener(IDeviceChangeListener l) {
		AndroidDebugBridge.addDeviceChangeListener(l);
	}

	@Override
	public IDevice[] getDevices() {
		// TODO Auto-generated method stub
		return null;
	}
}
