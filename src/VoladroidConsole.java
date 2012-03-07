import java.util.Arrays;
import java.util.List;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.ui.cli.args.Arg;
import com.voladroid.ui.cli.args.ArgParser;

public class VoladroidConsole implements IApplication {

	private Workspace workspace = null;
	private Project project = null;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = new String[] { "--project", "Facebook" };// (String[])
																	// context.getArguments().get("application.args"));

		workspace = Workspace.getWorkspace();
		ArgParser parser = new ArgParser();
		parser.add("--project", new Arg(1, "<project>") {
			@Override
			public void execute(List<String> args) throws Exception {
				String name = args.get(0);
				project = workspace.getProject(name);
			}
		});
		
		parser.add("--compare", new Arg(0, "") {
			
			@Override
			public void execute(List<String> args) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});

		if (args.length > 0) {
			parser.execute(args);
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
