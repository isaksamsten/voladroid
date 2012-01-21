package com.voladroid.model.adb;

import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;

public class Process {
	private Client client;

	public Process(Client client) {
		this.client = client;
	}

	public void dumpHprof() {
		client.dumpHprof();
	}

	public void enableAllocationTracker(boolean enable) {
		client.enableAllocationTracker(enable);
	}

	protected ClientData getClientData() {
		return client.getClientData();
	}

	public int getPid() {
		return getClientData().getPid();
	}

	public String getProcessDescription() {
		return getClientData().getClientDescription();
	}

	public boolean hasPendingHprofDump() {
		return getClientData().hasPendingHprofDump();
	}

	public Device getDevice() {
		return new Device(client.getDevice());
	}

	public boolean isDdmAware() {
		return client.isDdmAware();
	}

	public boolean isDebuggerAttached() {
		return client.isDebuggerAttached();
	}

	public boolean isHeapUpdateEnabled() {
		return client.isHeapUpdateEnabled();
	}

	public boolean isSelectedClient() {
		return client.isSelectedClient();
	}

	public boolean isThreadUpdateEnabled() {
		return client.isThreadUpdateEnabled();
	}

	public boolean isValid() {
		return client != null && client.isValid();
	}

	public void requestAllocationDetails() {
		client.requestAllocationDetails();
	}

	public void requestAllocationStatus() {
		client.requestAllocationStatus();
	}

	public void requestMethodProfilingStatus() {
		client.requestMethodProfilingStatus();
	}

	public boolean requestNativeHeapInformation() {
		return client.requestNativeHeapInformation();
	}

	public void requestThreadStackTrace(int threadId) {
		client.requestThreadStackTrace(threadId);
	}

	public void requestThreadUpdate() {
		client.requestThreadUpdate();
	}

	public void setAsSelectedClient() {
		client.setAsSelectedClient();
	}

	public void setHeapUpdateEnabled(boolean arg0) {
		client.setHeapUpdateEnabled(arg0);
	}

	public void setThreadUpdateEnabled(boolean arg0) {
		client.setThreadUpdateEnabled(arg0);
	}

	public void toggleMethodProfiling() {
		client.toggleMethodProfiling();
	}

	@Override
	public String toString() {
		return "Process: " + getClientData().getPid() + " "
				+ getClientData().getClientDescription() + " at "
				+ getDevice().getSerialNumber();
	}
}
