package voladroid;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.android.ddmlib.Log.ILogOutput;
import com.android.ddmlib.Log.LogLevel;
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
//		logger.info("Initialize");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
//		logger.info("Exit");
		try {
			Workspace s = Workspace.getWorkspace();
			s.getConfig().save();
			for (Project p : s) {
				p.getConfig().save();
			}
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
		if (DebugBridge.getInstance().isInit()) {
			DebugBridge.getInstance().terminate();
		}
	}
}
