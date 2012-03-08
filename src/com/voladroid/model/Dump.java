package com.voladroid.model;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.util.IProgressListener;

public class Dump implements Comparable<Dump> {
	private Project project;
	private ISnapshot snapshot = null;
	private String name;

	public Dump(Project p, String name) {
		this.project = p;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getLocation() {
		return FileUtils.getFile(project.getDumpLocation(), getName()
				+ ".hprof");
	}

	/**
	 * Return a snapshot
	 * 
	 * @param listener
	 * @return {@link ISnapshot}
	 * @throws SnapshotException
	 */
	public ISnapshot getSnapshot(IProgressListener listener)
			throws SnapshotException {
		if (snapshot == null)
			snapshot = SnapshotFactory.openSnapshot(getLocation(), listener);

		return snapshot;
	}

	public void clear() {
		snapshot = null;
	}

	@Override
	public int compareTo(Dump o) {
		return getName().compareTo(o.getName());
	}

	@Override
	public String toString() {
		return "Dump [getName()=" + getName() + ", getLocation()="
				+ getLocation() + "]";
	}

}
