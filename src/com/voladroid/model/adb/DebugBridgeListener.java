package com.voladroid.model.adb;

import java.util.EventListener;

public interface DebugBridgeListener extends EventListener {

	void deviceConnected(Device d);

	void deviceDisconnected(Device d);

	void deviceChanged(Device d, int x);

	void processChanged(Process p, int x);

	void hprofDump(Process p, byte[] data);

	void hprofDumpFail(Process p, String reason);

}
