package com.voladroid.model.adb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IClientChangeListener;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.ClientData.IHprofDumpHandler;
import com.android.ddmlib.ClientData.IMethodProfilingHandler;
import com.android.ddmlib.IDevice;
import com.voladroid.service.Services;

public class DebugBridge implements IDebugBridge {
	private static String adbpath = Services.getConfig().getAdbPath();
	private static IDebugBridge instance;

	public static IDebugBridge getInstance() {
		if (instance == null) {
			instance = new DebugBridge();
		}

		return instance;
	}

	private AndroidDebugBridge adb = null;
	private boolean initialized = false;
	private EventListenerList listeners = new EventListenerList();

	private DebugBridge() {
		AndroidDebugBridge.addClientChangeListener(new IClientChangeListener() {

			@Override
			public void clientChanged(Client c, int x) {
				DebugBridge.this.fire(c, x);

			}
		});

		AndroidDebugBridge.addDeviceChangeListener(new IDeviceChangeListener() {

			@Override
			public void deviceDisconnected(IDevice d) {
				DebugBridge.this.fireD(d);
			}

			@Override
			public void deviceConnected(IDevice d) {
				DebugBridge.this.fireC(d);
			}

			@Override
			public void deviceChanged(IDevice d, int x) {
				DebugBridge.this.fire(d, x);

			}
		});

		ClientData.setHprofDumpHandler(new IHprofDumpHandler() {

			@Override
			public void onSuccess(byte[] file, Client c) {
				DebugBridge.this.fireDump(Hprof.convert(file), c);

			}

			@Override
			public void onSuccess(String path, Client c) {
				DebugBridge.this.fireDump(path, c);

			}

			@Override
			public void onEndFailure(Client c, String reason) {
				DebugBridge.this.fireDumpFail(reason, c);
			}
		});

		ClientData.setMethodProfilingHandler(new IMethodProfilingHandler() {

			@Override
			public void onSuccess(byte[] arg0, Client arg1) {

			}

			@Override
			public void onSuccess(String arg0, Client arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartFailure(Client arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onEndFailure(Client arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void init(boolean b) {
		AndroidDebugBridge.init(b);
		adb = AndroidDebugBridge.createBridge(adbpath, b);
		initialized = true;
	}

	public boolean isInit() {
		return initialized;
	}

	protected void fireDump(byte[] file, Client c) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.hprofDump(new Process(c), file);
		}
	}

	protected void fireDumpFail(String reason, Client c) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.hprofDumpFail(new Process(c), reason);
		}

	}

	protected void fireDump(String path, Client c) {
		System.out.println(path);
	}

	protected void fire(IDevice d, int x) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.deviceChanged(new Device(d), x);
		}

	}

	protected void fireC(IDevice d) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.deviceConnected(new Device(d));
		}

	}

	protected void fireD(IDevice d) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.deviceDisconnected(new Device(d));
		}

	}

	protected void fire(Client c, int x) {
		for (DebugBridgeListener l : listeners
				.getListeners(DebugBridgeListener.class)) {
			l.processChanged(new Process(c), x);
		}

	}

	@Override
	public void terminate() {
		AndroidDebugBridge.terminate();
	}

	@Override
	public void add(DebugBridgeListener l) {
		listeners.add(DebugBridgeListener.class, l);
	}

	@Override
	public List<Device> getDevices() {
		List<Device> devices = new ArrayList<Device>();
		IDevice[] tmp = adb.getDevices();
		for (int n = 0; n < tmp.length; n++) {
			devices.add(new Device(tmp[0]));
		}

		return Collections.unmodifiableList(devices);
	}

	@Override
	public int getConnectionAttemptCount() {
		return adb.getConnectionAttemptCount();
	}

	@Override
	public int getRestartAttemptCount() {
		return adb.getRestartAttemptCount();
	}

	@Override
	public boolean hasInitialDeviceList() {
		return adb.hasInitialDeviceList();
	}

	@Override
	public boolean isConnected() {
		return adb.isConnected();
	}

	@Override
	public boolean restart() {
		return adb.restart();
	}
}
