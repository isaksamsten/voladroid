package com.voladroid.ui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.voladroid.model.Project;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.model.adb.DebugBridgeAdapter;
import com.voladroid.model.adb.Device;
import com.voladroid.model.adb.Process;
import com.voladroid.service.ProjectListener;
import com.voladroid.service.Services;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ProcessList extends org.eclipse.swt.widgets.Composite {
	private Composite composite1;
	private Table table1;
	private Button dump;
	private Canvas canvas1;
	private TableColumn name;
	private TableColumn pid;
	private Button refresh;

	private boolean deviceConnected = false;
	private Device device = null;

	private boolean hasProject = false;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	@Override
	protected void checkSubclass() {
	}

	public ProcessList(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();

		Services.getEnvironment().addProjectListener(new ProjectListener() {

			@Override
			public void projectRemoved(Project project) {
				// TODO Auto-generated method stub

			}

			@Override
			public void projectAdded(Project project) {
				// TODO Auto-generated method stub

			}

			@Override
			public void currentProject(Project p) {
				hasProject = true;
				dump.setEnabled(hasProject);
			}
		});

		DebugBridge.getInstance().add(new DebugBridgeAdapter() {

			@Override
			public void hprofDump(final Process p, final byte[] data) {
				try {
					Services.getEnvironment().getCurrentProject().save(p, data);
				} catch (final IOException e) {
					e.printStackTrace();

					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							MessageBox box = new MessageBox(getShell(), SWT.OK);
							box.setMessage(e.getMessage());
							box.open();
						}
					});

				}
				System.out.println(p + " dumped");

			}

			@Override
			public void deviceDisconnected(Device d) {
				deviceConnected = false;
				device = null;
			}

			@Override
			public void deviceConnected(Device d) {
				deviceConnected = true;
				device = d;
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						try {
							ImageData data = device.getScreenshot();
							Image img = new Image(Display.getDefault(), data);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
			}
		});
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.horizontalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(493, 270);
			{
				composite1 = new Composite(this, SWT.NONE);
				RowLayout composite1Layout = new RowLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					refresh = new Button(composite1, SWT.PUSH | SWT.CENTER);
					RowData button1LData = new RowData();
					refresh.setLayoutData(button1LData);
					refresh.setText("Refresh");
					refresh.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							refreshWidgetSelected(evt);
						}
					});
				}
				{
					dump = new Button(composite1, SWT.PUSH | SWT.CENTER);
					dump.setEnabled(hasProject);
					RowData dumpLData = new RowData();
					dump.setLayoutData(dumpLData);
					dump.setText("Dump memory");
					dump.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							dumpWidgetSelected(evt);
						}
					});
				}
			}
			{
				GridData table1LData = new GridData();
				table1LData.grabExcessHorizontalSpace = true;
				table1LData.grabExcessVerticalSpace = true;
				table1LData.horizontalAlignment = GridData.FILL;
				table1LData.verticalAlignment = GridData.FILL;
				table1 = new Table(this, SWT.NONE);
				table1.setLayoutData(table1LData);
				table1.setHeaderVisible(true);
				{
					pid = new TableColumn(table1, SWT.NONE);
					pid.setText("Pid");
					pid.setWidth(60);
				}
				{
					name = new TableColumn(table1, SWT.NONE);
					name.setText("Name");
					name.setWidth(60);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshWidgetSelected(SelectionEvent evt) {
		table1.removeAll();
		if (deviceConnected) {
			for (Process p : device.getProcesses()) {
				TableItem item = new TableItem(table1, SWT.NONE);
				item.setText(0, String.valueOf(p.getPid()));
				if (p.getProcessDescription() != null)
					item.setText(1, p.getProcessDescription());
			}
		}
	}

	private void dumpWidgetSelected(SelectionEvent evt) {
		String pid = table1.getSelection()[0].getText(1);
		Process process = device.getProcess(pid);
		if (process.isValid())
			process.dumpHprof();
	}

}
