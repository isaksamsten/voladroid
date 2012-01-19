package com.voladroid.ui;

import java.io.File;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.ConsoleProgressListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class VoladroidDisplay {

	private String title = "Voladroid";

	public Shell start(final Display display) {
		final Shell shell = new Shell(display);
		shell.setText(title);
		shell.setLayout(new GridLayout(2, false));

		Button open = new Button(shell, SWT.PUSH);
		open.setText("Open Dialog");
		Button close = new Button(shell, SWT.PUSH);
		close.setText("Close");
		
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData d = new GridData();
		d.horizontalSpan = 2;
		d.horizontalAlignment = SWT.FILL;
		d.horizontalAlignment = SWT.FILL;
		d.grabExcessHorizontalSpace = true;
		d.verticalAlignment = SWT.FILL;
		d.grabExcessVerticalSpace = true;
		list.setLayoutData(d);
		
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = list.getSelectionIndex();
				System.out.println(list.getItem(selected));
			}
		});

		open.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				final String filename = fd.open();
				display.asyncExec(new Runnable() {

					@Override
					public void run() {
						list.removeAll();
						ConsoleProgressListener listener = new ConsoleProgressListener(
								System.out);
						ISnapshot snapshot = null;
						try {
							snapshot = SnapshotFactory.openSnapshot(new File(
									filename), listener);
							shell.setText(snapshot.getSnapshotInfo().toString());
							for (IClass c : snapshot.getClasses()) {
								list.add(c.getName() + " have "
										+ c.getNumberOfObjects()
										+ " object instances.");
							}
						} catch (SnapshotException ex) {

						} finally {
							listener.done();
						}

					}
				});

			}
		});

		return shell;
	}
}
