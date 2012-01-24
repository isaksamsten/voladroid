package com.voladroid.ui;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

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
public class ClassDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private IClass clazz;
	private List instanceList;
	private Table values;
	private TableColumn tableColumn1;
	private TableColumn tableColumn2;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */

	public ClassDialog(Shell parent, IClass clazz, int style) {
		super(parent, style);
		this.clazz = clazz;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.numColumns = 2;
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(655, 403);
			{
				GridData list1LData = new GridData();
				list1LData.grabExcessVerticalSpace = true;
				list1LData.horizontalAlignment = GridData.FILL;
				list1LData.verticalAlignment = GridData.FILL;
				instanceList = new List(dialogShell, SWT.NONE);
				instanceList.setLayoutData(list1LData);
			}
			{
				GridData table1LData = new GridData();
				table1LData.grabExcessHorizontalSpace = true;
				table1LData.grabExcessVerticalSpace = true;
				table1LData.horizontalAlignment = GridData.FILL;
				table1LData.verticalAlignment = GridData.FILL;
				values = new Table(dialogShell, SWT.NONE);
				values.setLayoutData(table1LData);
				values.setHeaderVisible(true);
				{
					tableColumn1 = new TableColumn(values, SWT.NONE);
					tableColumn1.setText("Variable");
					tableColumn1.setWidth(60);
				}
				{
					tableColumn2 = new TableColumn(values, SWT.NONE);
					tableColumn2.setText("Value");
					tableColumn2.setWidth(139);
				}
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			try {
				for (int id : clazz.getObjectIds()) {
					IObject obj = clazz.getSnapshot().getObject(id);
					instanceList.add(obj.getDisplayName());
					java.util.List<NamedReference> value = obj
							.getOutboundReferences();
					for (NamedReference n : value) {
						System.out.println(n.getName());
						System.out.println(clazz.getSnapshot()
								.getObject(n.getObjectId())
								.getClassSpecificName());
					}
					for (FieldDescriptor d : clazz.getFieldDescriptors()) {

						TableItem item = new TableItem(values, SWT.NONE);
						item.setText(0, d.getName());
						item.setText(1, d.getVerboseSignature());
					}
				}
			} catch (SnapshotException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
