package com.voladroid.ui.cli;

import java.io.IOException;

import org.jboss.jreadline.console.Console;

import com.voladroid.ui.cli.args.Scope;

public class ConsoleCli extends AbstractCli {

	private Console console = null;

	public ConsoleCli() throws IOException {
		super();
		this.console = new Console();
	}

	@Override
	public void push(Scope e) {
		if (e.getCompleters() != null && console != null)
			console.addCompletions(e.getCompleters());
		super.push(e);
	}

	@Override
	public String in(String msg) throws IOException {
		return console.read(">> ");
	}

}
