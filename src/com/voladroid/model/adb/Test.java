package com.voladroid.model.adb;

import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		IDebugBridge adb = DebugBridge.getDebugBridge();
		adb.addDeviceListener(new IDeviceChangeListener() {

			@Override
			public void deviceDisconnected(IDevice arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void deviceConnected(IDevice arg0) {
				System.out.println("* " + arg0.getSerialNumber());

			}

			@Override
			public void deviceChanged(IDevice arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

}
