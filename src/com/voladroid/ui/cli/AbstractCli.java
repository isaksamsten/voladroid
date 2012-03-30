package com.voladroid.ui.cli;

import java.io.IOException;
import java.util.Scanner;

import org.jboss.jreadline.console.Console;

import com.voladroid.service.Services;
import com.voladroid.ui.ProjectInspector;
import com.voladroid.ui.cli.args.Scope;
import com.voladroid.ui.cli.args.Stack;

public abstract class AbstractCli implements Cli {

	private Stack stack = Stack.getInstance();
	private ProjectInspector inspector = new ProjectInspector();

	private Scope root = new RootScope(this);

	public ProjectInspector inspector() {
		return inspector;
	}

	public void error(Exception e) {
		Boolean debug = stack.local().get("debug");
		if (debug != null && debug) {
			System.out.print(Services.getStackTrace(e));
		} else {
			System.out.println(e);
		}
	}

	public void error(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public void out(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public void out(Object o) {
		System.out.println(o);
	}

	public void push(Scope e) {
		stack.push(e);
	}

	@Override
	public String[] nextCommand() throws IOException {
		return in(">> ").split("\\s+");
	}

	@Override
	public boolean hasCommand() {
		return true;
	}

	@Override
	public void onStart() {
		new Thread(inspector).start();
	}

	@Override
	public void onStop() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.voladroid.ui.cli.Cli#start()
	 */
	@Override
	public void start() {
		onStart();
		push(root);
		while (hasCommand()) {
			try {
				String[] cmd = nextCommand();
				Scope executor = stack.local().execute(cmd);
				if (executor != null) {
					executor.onEnter();
					push(executor);
				} else if (stack.empty()) {
					break;
				}
			} catch (Exception e) {
				error(e);
			}
		}
		onStop();
	}

}
