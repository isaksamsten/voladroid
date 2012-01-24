package com.voladroid.model.adb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.Client;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IDevice.DeviceState;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.SyncService;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.log.LogReceiver;

public class Device {
	private IDevice device;

	public Device(IDevice device) {
		this.device = device;
	}

	public String getAvdName() {
		return device.getAvdName();
	}

	public Process getProcess(String client) {
		return new Process(device.getClient(client));
	}

	public String getProcessName(int arg0) {
		return device.getClientName(arg0);
	}

	public List<Process> getProcesses() {
		List<Process> process = new ArrayList<Process>();
		for (Client c : device.getClients())
			process.add(new Process(c));

		return process;
	}

	public FileListingService getFileListingService() {
		return device.getFileListingService();
	}

	public String getMountPoint(String arg0) {
		return device.getMountPoint(arg0);
	}

	public Map<String, String> getProperties() {
		return device.getProperties();
	}

	public String getProperty(String arg0) {
		return device.getProperty(arg0);
	}

	public int getPropertyCount() {
		return device.getPropertyCount();
	}

	public ImageData getScreenshot() throws TimeoutException,
			AdbCommandRejectedException, IOException {
		RawImage img = device.getScreenshot();
		PaletteData pdata = new PaletteData(img.getRedMask(),
				img.getGreenMask(), img.getBlueMask());
		ImageData imgData = new ImageData(img.width, img.height, img.bpp,
				pdata, 1, img.data);

		return imgData;
	}

	public String getSerialNumber() {
		return device.getSerialNumber();
	}

	public DeviceState getState() {
		return device.getState();
	}

	public SyncService getSyncService() throws TimeoutException,
			AdbCommandRejectedException, IOException {
		return device.getSyncService();
	}

	public boolean hasProcesses() {
		return device.hasClients();
	}

	public boolean isBootLoader() {
		return device.isBootLoader();
	}

	public boolean isEmulator() {
		return device.isEmulator();
	}

	public boolean isOffline() {
		return device.isOffline();
	}

	public boolean isOnline() {
		return device.isOnline();
	}

	public void runEventLogService(LogReceiver arg0) throws TimeoutException,
			AdbCommandRejectedException, IOException {
		device.runEventLogService(arg0);
	}

}
