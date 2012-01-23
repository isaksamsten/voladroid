package voladroid;

import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.service.Services;

public class Activator implements BundleActivator {
	private static Logger logger = Logger.getLogger(Activator.class
			.getSimpleName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
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
			Workspace s = Services.getEnvironment().getWorkspace();
			s.getConfig().save();
			for (Project p : s) {
				p.getConfig().save();
			}
		} catch (ConfigurationException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		DebugBridge.getInstance().terminate();
	}

}
