package com.voladroid.model.adb;

import java.util.List;

public interface IDebugBridge {

	List<Device> getDevices();

	void init(boolean b);

	void terminate();

	public abstract boolean restart();

	public abstract boolean isConnected();

	public abstract boolean hasInitialDeviceList();

	public abstract int getRestartAttemptCount();

	public abstract int getConnectionAttemptCount();

	void add(DebugBridgeListener l);

	boolean isInit();
}
