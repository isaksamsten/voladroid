import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.jboss.jreadline.console.settings.Settings;

import com.android.ddmlib.Log.ILogOutput;
import com.android.ddmlib.Log.LogLevel;
import com.voladroid.model.adb.DebugBridge;
import com.voladroid.ui.cli.VoladroidCli;

public class VoladroidConsole implements IApplication {
	@Override
	public Object start(IApplicationContext context) throws Exception {
		Settings.getInstance().setReadInputrc(false);
		com.android.ddmlib.Log.setLogOutput(new ILogOutput() {

			@Override
			public void printLog(LogLevel arg0, String arg1, String arg2) {
			}

			@Override
			public void printAndPromptLog(LogLevel arg0, String arg1,
					String arg2) {

			}
		});

		DebugBridge.getInstance().init(true);
		VoladroidCli main = new VoladroidCli();
		main.start();

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
