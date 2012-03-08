import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.print.attribute.standard.PDLOverrideSupported;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.mat.util.ConsoleProgressListener;
import org.eclipse.mat.util.VoidProgressListener;

import com.voladroid.model.Dump;
import com.voladroid.model.Project;
import com.voladroid.model.Workspace;
import com.voladroid.model.compare.BinaryResultProducer;
import com.voladroid.model.compare.CompareUtils;
import com.voladroid.model.compare.ObjectResultProducer;
import com.voladroid.model.compare.Result;
import com.voladroid.model.compare.ResultProducer;
import com.voladroid.ui.cli.args.Argument;
import com.voladroid.ui.cli.args.ArgumentExecutor;
import com.voladroid.ui.cli.args.ArgumentStack;

public class VoladroidConsole implements IApplication {

	private ArgumentStack stack = ArgumentStack.getInstance();
	private ArgumentExecutor root = new ArgumentExecutor("Root");
	{
		root.put("workspace", Workspace.getWorkspace());
		root.add("help", new Argument(0, "Show this information") {
			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local().usage());
				return null;
			}
		});

		root.add("exit", new Argument(0, "Exit the current scope") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				ArgumentExecutor e = stack.pop();
				System.out.println("Leaving '" + e.name() + "'");
				return null;
			}
		});

		root.add("stack", new Argument(0, "Show the stack (* = current)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local().name() + "*");
				Iterator<ArgumentExecutor> it = stack.listIterator(1);
				while (it.hasNext()) {
					System.out.println(it.next().name());
				}
				return null;
			}
		});

		root.add("where", new Argument(0, "Show your current location") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				System.out.println(stack.local().name());
				return null;
			}
		});

		root.add("workspace", new Argument(-1,
				"<name> Enter workspace (<name> is optional)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				System.out.println("Current workspace is "
						+ space.getLocation());
				return workspace;
			}

		});

		root.add("exit!", new Argument(0, "Quit the application") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				stack.clear();
				return null;
			}
		});
	}

	private ArgumentExecutor workspace = new ArgumentExecutor("Workspace", root);
	{
		workspace.add("projects", new Argument(0, "List projects") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				for (Project p : space.getProjects()) {
					String name = p.getName();
					if (space.getCurrentProject() != null
							&& space.getCurrentProject().equals(p)) {
						name += "*";
					}

					System.out.println(name);
				}

				return null;
			}
		});

		workspace.add("select", new Argument(1,
				"Select a project as the current project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				String name = args.get(0);
				Workspace space = get("workspace");
				Project p = space.getProject(name);
				if (p != null) {
					space.setCurrentProject(p);
				} else {
					System.out.println("No such project");
				}
				return null;
			}
		});

		workspace.add("use", new Argument(-1,
				"<name> Use the project (<name> optional)") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Workspace space = get("workspace");
				Project p = null;
				if (args.isEmpty()) {
					p = space.getCurrentProject();
				} else {
					String name = args.get(0);
					p = space.getProject(name);
				}

				if (p != null) {
					project.put("project", p);
					return project;
				}

				return null;
			}
		});
	}

	private ArgumentExecutor project = new ArgumentExecutor("Project",
			workspace);
	{
		project.add("compare", new Argument(1,
				"<object|byte> Compare the memory images") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");

				String cmp = "object";
				if (!args.isEmpty()) {
					cmp = args.get(0);
				}

				ResultProducer producer = null;
				if (cmp.equals("object")) {
					producer = new ObjectResultProducer();
				} else if (cmp.equals("byte")) {
					producer = new BinaryResultProducer();
				} else {
					throw new Exception();
				}

				Result res = CompareUtils.subsequent(p.getDumps(), producer,
						new VoidProgressListener());
				System.out.println("Sample avrage: " + res.getSampleAvrage());
				System.out.println("Standard Deviation: "
						+ res.getStandardDeviation());
				System.out.println("Total avrage: " + res.getTotalAvrage());

				System.out.println("Sample avrage (%): "
						+ res.getSampleAvrage() / res.getTotalAvrage());
				System.out.println("Standard Deviation (%): "
						+ res.getStandardDeviation() / res.getTotalAvrage());

				return null;
			}
		});

		project.add("info", new Argument(0, "Show information about project") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");
				System.out.println(p);
				return null;
			}
		});

		project.add("images", new Argument(0, "List the memory images") {

			@Override
			public ArgumentExecutor execute(List<String> args) throws Exception {
				Project p = get("project");
				int i = 0;
				for (Dump d : p.getDumps()) {
					System.out.println(i++ + " " + d.getName());
				}

				return null;
			}
		});
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		stack.push(workspace);

		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print(">> ");
			String[] cmd = in.nextLine().split("\\s+");

			try {
				ArgumentExecutor executor = stack.local().execute(cmd);
				if (executor != null) {
					stack.push(executor);
				} else if (stack.empty()) {
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
				//e.printStackTrace();
			}
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
