package com.voladroid.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.ConsoleProgressListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.voladroid.model.Dump;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.service.ProjectListener;

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
public class ClassBrowser extends org.eclipse.swt.widgets.Composite {
	private static final Log logger = LogFactory.getLog(ClassBrowser.class);

	private Table classes;
	private Button filterBtn;
	private Text filterTxt;
	private Composite composite1;
	private Combo dumps;
	private TableColumn instCount;
	private TableColumn name;
	private TableColumn address;

	private Project currentProject;
	private ISnapshot currentSnapshot;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */

	public ClassBrowser(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
		Workspace.getWorkspace().addProjectListener(new ProjectListener() {

			@Override
			public void projectRemoved(Project project) {
				// TODO Auto-generated method stub

			}

			@Override
			public void projectChange(Project p) {
				dumps.removeAll();
				for (Dump d : p.getDumps()) {
					dumps.add(d.getName());
				}
			}

			@Override
			public void projectAdded(Project project) {
				// TODO Auto-generated method stub

			}

			@Override
			public void currentProject(Project p) {
				currentProject = p;
				dumps.removeAll();
				for (Dump d : p.getDumps()) {
					dumps.add(d.getName());
				}

			}
		});

	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(576, 403);
			{
				{
					composite1 = new Composite(this, SWT.NONE);
					RowLayout composite1Layout = new RowLayout(
							org.eclipse.swt.SWT.HORIZONTAL);
					composite1Layout.wrap = false;
					GridData composite1LData = new GridData();
					composite1LData.heightHint = 29;
					composite1LData.grabExcessHorizontalSpace = true;
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1.setLayoutData(composite1LData);
					composite1.setLayout(composite1Layout);
					composite1.setSize(353, 29);
				}
				{
					RowData dumpsLData = new RowData();
					dumps = new Combo(composite1, SWT.NONE);
					dumps.setLayoutData(dumpsLData);
					dumps.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							dumpsWidgetSelected(evt);
						}
					});
				}
				{
					filterTxt = new Text(composite1, SWT.NONE);
					RowData filterLData = new RowData();
					filterLData.width = 215;
					filterLData.height = 21;
					filterTxt.setLayoutData(filterLData);
					filterTxt.setText("");
				}
				{
					filterBtn = new Button(composite1, SWT.PUSH | SWT.CENTER);
					RowData filterBtnLData = new RowData();
					filterBtn.setLayoutData(filterBtnLData);
					filterBtn.setText("Filter");
					filterBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							filterBtnWidgetSelected(evt);
						}
					});
				}
				GridData classesLData = new GridData();
				classesLData.grabExcessHorizontalSpace = true;
				classesLData.grabExcessVerticalSpace = true;
				classesLData.horizontalAlignment = GridData.FILL;
				classesLData.verticalAlignment = GridData.FILL;
				classes = new Table(this, SWT.NONE);
				classes.setLayoutData(classesLData);
				classes.setHeaderVisible(true);
				classes.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						classesWidgetSelected(evt);
					}
				});
				{
					address = new TableColumn(classes, SWT.NONE);
					address.setText("Address");
					address.setWidth(60);
				}
				{
					name = new TableColumn(classes, SWT.NONE);
					name.setText("Name");
					name.setWidth(260);
				}
				{
					instCount = new TableColumn(classes, SWT.NONE);
					instCount.setText("Instance count");
					instCount.setWidth(100);
				}
			}

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dumpsWidgetSelected(SelectionEvent evt) {
		if (currentProject != null) {
			classes.removeAll();

			String text = ((Combo) evt.widget).getText();
			Dump d = currentProject.getDump(text);
			try {
				currentSnapshot = d.getSnapshot(new ConsoleProgressListener(
						System.out));

				for (IClass c : currentSnapshot.getClasses()) {
					TableItem item = new TableItem(classes, SWT.NONE);
					item.setText(0, "" + c.getObjectAddress());
					item.setText(1, c.getName());
					item.setText(2, "" + c.getNumberOfObjects());
				}
			} catch (SnapshotException e) {
				e.printStackTrace();
			}
		}
	}

	private void filterBtnWidgetSelected(SelectionEvent evt) {
		if (currentSnapshot != null) {
			classes.removeAll();
			try {
				for (IClass c : currentSnapshot.getClasses()) {
					if (c.getName().matches(filterTxt.getText())) {
						TableItem item = new TableItem(classes, SWT.NONE);
						item.setText(0, "" + c.getObjectAddress());
						item.setText(1, c.getName());
						item.setText(2, "" + c.getNumberOfObjects());
					}
				}
			} catch (Exception e) {
				MessageBox box = new MessageBox(getShell(), SWT.ICON_ERROR);
				box.setMessage(e.getMessage());
				box.setText("Error");
				box.open();
				logger.error(e, e);
			}
		}

	}
	
	private void classesWidgetSelected(SelectionEvent evt) {
		int idx = classes.getSelectionIndex();
		String name = classes.getItem(idx).getText(1);
		IClass clazz = null;
		try {
			for(IClass c : currentSnapshot.getClassesByName(name, false)) {
				clazz = c;
				break;
			}
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClassDialog dialog = new ClassDialog(getShell(), clazz, SWT.NONE);
		dialog.open();
	}

}
