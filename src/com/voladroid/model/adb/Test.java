package com.voladroid.model.adb;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Test {
	private static Device device = null;

	public static void main(String[] args) throws InterruptedException,
			IOException {
		// IDebugBridge adb = DebugBridge.getInstance();
		// adb.add(new DebugBridgeListener() {
		//
		// @Override
		// public void deviceDisconnected(Device d) {
		//
		// }
		//
		// @Override
		// public void deviceConnected(Device d) {
		// device = d;
		// }
		//
		// @Override
		// public void processChanged(Process p, int x) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void deviceChanged(Device d, int x) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void hprofDumpFail(Process p, String reason) {
		// System.out.println(p + " failed: " + reason);
		//
		// }
		//
		// @Override
		// public void hprofDump(Process p, byte[] data) {
		// System.out.println(p + " hprof dumped to " + data.length);
		//
		// }
		// });
		//
		// while (true) {
		// if (device != null) {
		// FileListingService file = device.getFileListingService();
		// List<Process> clients = device.getProcesses();
		// System.out.println(device.getSerialNumber());
		// System.out.println(device.hasProcesses());
		// System.out.println(file.getRoot());
		// for (Process c : clients) {
		// System.out.println(c.toString());
		// c.dumpHprof();
		// System.out.println(c.hasPendingHprofDump());
		// break;
		// }
		// break;
		// }
		// System.out.println("Retry..");
		//
		// Thread.sleep(1000);
		// }
		//
		// adb.terminate();

		File dump = new File("/home/isak/test.hprof");
		byte[] bytes = FileUtils.readFileToByteArray(dump);
		byte[] b = Hprof.convert(bytes);
		FileUtils.writeByteArrayToFile(new File("/home/isak/test.hprof-2"), b);

	}

}
