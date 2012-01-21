package com.voladroid.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class VoladroidDisplay {

	private String title = "Voladroid";

	public Shell start(final Display display) {
		final Shell shell = new Shell(display);
		shell.setText(title);
		shell.setLayout(new GridLayout(1, false));
		final ProcessList pc = new ProcessList(shell, SWT.NONE);
		GridData d = new GridData();
		d.horizontalSpan = 2;
		d.horizontalAlignment = SWT.FILL;
		d.horizontalAlignment = SWT.FILL;
		d.grabExcessHorizontalSpace = true;
		d.verticalAlignment = SWT.FILL;
		d.grabExcessVerticalSpace = true;
		pc.setLayoutData(d);
		pc.layout();

		return shell;
	}
}
