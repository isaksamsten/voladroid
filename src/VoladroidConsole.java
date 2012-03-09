import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.voladroid.model.adb.DebugBridge;
import com.voladroid.ui.cli.VoladroidMain;

public class VoladroidConsole implements IApplication {
	@Override
	public Object start(IApplicationContext context) throws Exception {
		DebugBridge.getInstance().init(true);

		VoladroidMain main = new VoladroidMain();
		main.start();

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
