package com.voladroid.model.adb;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;

public interface IDebugBridge {
	
	void addDeviceListener(IDeviceChangeListener l);
	IDevice[] getDevices();
	void terminate();
}
