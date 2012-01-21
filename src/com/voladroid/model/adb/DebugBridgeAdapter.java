package com.voladroid.model.adb;

public abstract class DebugBridgeAdapter implements DebugBridgeListener{

	@Override
	public void deviceConnected(Device d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deviceDisconnected(Device d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deviceChanged(Device d, int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processChanged(Process p, int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hprofDump(Process p, byte[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hprofDumpFail(Process p, String reason) {
		// TODO Auto-generated method stub
		
	}

}
