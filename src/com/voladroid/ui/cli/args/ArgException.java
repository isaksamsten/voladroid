package com.voladroid.ui.cli.args;

@SuppressWarnings("serial")
public class ArgException extends Exception {

	private String usage;

	public ArgException(String string) {
		this.usage = string;
	}

	public ArgException(String m, Throwable t) {
		super(t);
		this.usage = m;
	}

	@Override
	public String toString() {
		return usage;
	}

}
