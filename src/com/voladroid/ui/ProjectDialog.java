package com.voladroid.ui;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
public class ProjectDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button rmProject;
	private Label label1;
	private Text projectNameTxt;
	private Button projectNewBtn;
	private Group group1;
	private List projectList;
	private Button cancel;
	private Label sep;
	private Button loadProject;

	private Project selectedProject;

	private ProjectListener listener = new ProjectListener() {

		@Override
		public void projectRemoved(Project project) {
			projectList.remove(project.getName());
		}

		@Override
		public void projectAdded(Project project) {
			projectList.add(project.getName());
		}

		@Override
		public void currentProject(Project p) {
			// TODO Auto-generated method stub

		}

		@Override
		public void projectChange(Project project) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */

	public ProjectDialog(Shell parent, int style) {
		super(parent, style);

		Workspace.getWorkspace().addProjectListener(listener);
	}

	public Project open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(283, 364);
			dialogShell.setText("Project workspace");
			dialogShell.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent evt) {
					dialogShellWidgetDisposed(evt);
				}
			});
			{
				group1 = new Group(dialogShell, SWT.NONE);
				FormLayout group1Layout = new FormLayout();
				group1.setLayout(group1Layout);
				FormData group1LData = new FormData();
				group1LData.left = new FormAttachment(0, 1000, 12);
				group1LData.top = new FormAttachment(0, 1000, 207);
				group1LData.width = 255;
				group1LData.height = 62;
				group1.setLayoutData(group1LData);
				group1.setText("New Project");
				{
					FormData projectNameTxtLData = new FormData();
					projectNameTxtLData.left = new FormAttachment(0, 1000, 48);
					projectNameTxtLData.top = new FormAttachment(0, 1000, 11);
					projectNameTxtLData.width = 192;
					projectNameTxtLData.height = 13;
					projectNameTxt = new Text(group1, SWT.NONE);
					projectNameTxt.setLayoutData(projectNameTxtLData);
				}
				{
					label1 = new Label(group1, SWT.NONE);
					FormData label1LData = new FormData();
					label1LData.left = new FormAttachment(0, 1000, 10);
					label1LData.top = new FormAttachment(0, 1000, 11);
					label1LData.width = 32;
					label1LData.height = 13;
					label1.setLayoutData(label1LData);
					label1.setText("Name");
				}
				{
					projectNewBtn = new Button(group1, SWT.PUSH | SWT.CENTER);
					FormData projectNewBtnLData = new FormData();
					projectNewBtnLData.left = new FormAttachment(0, 1000, 167);
					projectNewBtnLData.top = new FormAttachment(0, 1000, 34);
					projectNewBtnLData.width = 78;
					projectNewBtnLData.height = 23;
					projectNewBtn.setLayoutData(projectNewBtnLData);
					projectNewBtn.setText("New project");
					projectNewBtn.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent evt) {
							projectNewBtnWidgetSelected(evt);
						}
					});
				}
			}
			{
				FormData projectListLData = new FormData();
				projectListLData.left = new FormAttachment(0, 1000, 12);
				projectListLData.top = new FormAttachment(0, 1000, 12);
				projectListLData.width = 259;
				projectListLData.height = 189;
				projectList = new List(dialogShell, SWT.NONE);
				projectList.setLayoutData(projectListLData);

				for (Project p : Workspace.getWorkspace())
					projectList.add(p.getName());
			}
			{
				cancel = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData cancelLData = new FormData();
				cancelLData.left = new FormAttachment(0, 1000, 127);
				cancelLData.top = new FormAttachment(0, 1000, 309);
				cancelLData.width = 46;
				cancelLData.height = 23;
				cancel.setLayoutData(cancelLData);
				cancel.setText("Cancel");
				cancel.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						cancelWidgetSelected(evt);
					}
				});
			}
			{
				sep = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				FormData sepLData = new FormData();
				sepLData.top = new FormAttachment(0, 1000, 290);
				sepLData.width = 467;
				sepLData.height = 13;
				sepLData.right = new FormAttachment(1000, 1000, -12);
				sepLData.left = new FormAttachment(0, 1000, 12);
				sep.setLayoutData(sepLData);
				sep.setText("label1");
			}
			{
				rmProject = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData rmProjectLData = new FormData();
				rmProjectLData.left = new FormAttachment(0, 1000, 12);
				rmProjectLData.top = new FormAttachment(0, 1000, 309);
				rmProjectLData.width = 91;
				rmProjectLData.height = 23;
				rmProject.setLayoutData(rmProjectLData);
				rmProject.setText("Delete Project");
				rmProject.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						rmProjectWidgetSelected(evt);
					}
				});
			}
			{
				loadProject = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData loadProjectLData = new FormData();
				loadProjectLData.left = new FormAttachment(0, 1000, 179);
				loadProjectLData.top = new FormAttachment(0, 1000, 309);
				loadProjectLData.width = 92;
				loadProjectLData.height = 23;
				loadProject.setLayoutData(loadProjectLData);
				loadProject.setText("Load Project");
				loadProject.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						loadProjectWidgetSelected(evt);
					}
				});
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Workspace.getWorkspace().getCurrentProject();
	}

	private void loadProjectWidgetSelected(SelectionEvent evt) {
		int index = projectList.getSelectionIndex();
		if (index > -1) {
			String name = projectList.getItem(index);
			Workspace.getWorkspace().setCurrentProject(
					Workspace.getWorkspace().getProject(name));
			dialogShell.close();
		}
	}

	private void rmProjectWidgetSelected(SelectionEvent evt) {
		int index = projectList.getSelectionIndex();
		if (index > -1) {
			MessageBox messageBox = new MessageBox(getParent(), SWT.YES
					| SWT.NO);
			messageBox.setMessage("Really delete?");
			if (messageBox.open() == SWT.YES) {
				String name = projectList.getItem(index);
				Workspace.getWorkspace().removeProject(name);
			}
		}
	}

	private void cancelWidgetSelected(SelectionEvent evt) {
		dialogShell.close();
	}

	private void projectNewBtnWidgetSelected(SelectionEvent evt) {
		String name = projectNameTxt.getText();
		if (name != null && !name.trim().equals("")) {
			Workspace.getWorkspace().createProject(name);
		}

		projectNameTxt.setText("");
	}

	private void dialogShellWidgetDisposed(DisposeEvent evt) {
		Workspace.getWorkspace().removeProjectListener(listener);
	}
}
