package com.voladroid.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.FieldDescriptor;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.TreeAdapter;
import org.eclipse.swt.events.TreeEvent;

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
	private Tree tree1;
	private TreeColumn valCol;
	private TreeColumn keyCol;

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
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(655, 403);
			{
				GridData tree1LData = new GridData();
				tree1LData.verticalAlignment = GridData.FILL;
				tree1LData.horizontalAlignment = GridData.FILL;
				tree1LData.grabExcessHorizontalSpace = true;
				tree1LData.grabExcessVerticalSpace = true;
				tree1 = new Tree(dialogShell, SWT.NONE);
				tree1.setLayoutData(tree1LData);
				tree1.setHeaderVisible(true);
				tree1.addTreeListener(new TreeAdapter() {
					public void treeExpanded(TreeEvent evt) {
						tree1TreeExpanded(evt);
					}
				});
				{
					keyCol = new TreeColumn(tree1, SWT.NONE);
					keyCol.setText("Key");
					keyCol.setWidth(60);
				}
				{
					valCol = new TreeColumn(tree1, SWT.NONE);
					valCol.setText("Value");
					valCol.setWidth(60);
				}
			}

			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			try {
				for (int id : clazz.getObjectIds()) {
					IObject obj = clazz.getSnapshot().getObject(id);

					TreeItem item = new TreeItem(tree1, SWT.NONE);
					item.setText(obj.getDisplayName());
					item.setData(obj);
					item.setItemCount(1);
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

	private void tree1TreeExpanded(TreeEvent evt) {
		try {
			TreeItem item = (TreeItem) evt.item;
			IObject obj = (IObject) item.getData();
			item.removeAll();

			populate(item, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param item
	 * @param obj
	 * @throws SnapshotException
	 */
	protected void populate(TreeItem item, IObject obj)
			throws SnapshotException {
		java.util.List<NamedReference> values = obj.getOutboundReferences();
		Set<String> used = new HashSet<String>();
		for (NamedReference n : values) {
			TreeItem c = new TreeItem(item, SWT.NONE);
			c.setText(0, n.getName());
			String clsSpec = n.getObject().getClassSpecificName();
			if (clsSpec != null) {
				c.setText(1, clsSpec);
			} else {
				c.setData(n.getObject());
				c.setText(1, n.getObject().getDisplayName());
				c.setItemCount(1);
			}
			used.add(n.getName());
		}
		for (FieldDescriptor d : clazz.getFieldDescriptors()) {
			if (used.contains(d.getName()))
				continue;
			Object value = obj.resolveValue(d.getName());
			if (value != null) {
				TreeItem c = new TreeItem(item, SWT.NONE);
				c.setText(0, d.getName());
				c.setText(1, value.toString());
			}
		}
	}

}
