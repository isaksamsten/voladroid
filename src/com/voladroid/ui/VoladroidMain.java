package com.voladroid.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.cloudgarden.resource.SWTResourceManager;
import com.voladroid.model.Dump;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
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
public class VoladroidMain extends org.eclipse.swt.widgets.Composite {

	private Menu menu1;
	private MenuItem aboutMenuItem;
	private MenuItem contentsMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private ClassBrowser classBrowser1;
	private ProcessList processList1;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem projectFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;

	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public VoladroidMain(Composite parent, int style) {
		super(parent, style);
		initGUI();

		Workspace.getWorkspace().addProjectListener(new ProjectListener() {

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
				getShell().setText(p.getName());
				// for(Dump d : p.getDumps()) {
				// combo1.add(d.getName());
				// }

			}

			@Override
			public void projectChange(Project p) {
				getShell().setText(p.getName());
				// for(Dump d : p.getDumps()) {
				// combo1.add(d.getName());
				// }
			}
		});
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			this.setSize(540, 300);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			this.setLayout(thisLayout);
			{
				GridData processList1LData = new GridData();
				processList1LData.verticalAlignment = GridData.FILL;
				processList1LData.grabExcessVerticalSpace = true;
				processList1LData.minimumWidth = 500;
				processList1LData.widthHint = 255;
				processList1 = new ProcessList(this, SWT.NONE);
				GridLayout processList1Layout = new GridLayout();
				processList1Layout.horizontalSpacing = 0;
				processList1.setLayout(processList1Layout);
				processList1.setLayoutData(processList1LData);
			}
			{
				GridData classBrowser1LData = new GridData();
				classBrowser1LData.grabExcessHorizontalSpace = true;
				classBrowser1LData.grabExcessVerticalSpace = true;
				classBrowser1LData.verticalAlignment = GridData.FILL;
				classBrowser1LData.horizontalAlignment = GridData.FILL;
				classBrowser1 = new ClassBrowser(this, SWT.NONE);
				classBrowser1.setLayoutData(classBrowser1LData);
			}
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							projectFileMenuItem = new MenuItem(fileMenu,
									SWT.CASCADE);
							projectFileMenuItem.setText("Projects");
							projectFileMenuItem
									.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(
												SelectionEvent evt) {
											projectFileMenuItemWidgetSelected(evt);
										}
									});
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu,
									SWT.CASCADE);
							saveFileMenuItem.setText("Save");
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu,
									SWT.CASCADE);
							closeFileMenuItem.setText("Close");
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							contentsMenuItem = new MenuItem(helpMenu,
									SWT.CASCADE);
							contentsMenuItem.setText("Contents");
						}
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void projectFileMenuItemWidgetSelected(SelectionEvent evt) {
		ProjectDialog d = new ProjectDialog(getShell(), SWT.NONE);
		d.open();
	}
}
