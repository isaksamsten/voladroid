package voladroid;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.ConsoleProgressListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.model.adb.DebugBridge;

public class Activator implements BundleActivator {
	private static Log logger = LogFactory.getLog(Activator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		logger.info("Starting...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		logger.info("Stopping...");
		try {
			Workspace s = Workspace.getWorkspace();
			s.getConfig().save();
			for (Project p : s) {
				p.getConfig().save();
			}
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
		DebugBridge.getInstance().terminate();
	}
}
