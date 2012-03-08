package com.voladroid.ui.cli.args;

@SuppressWarnings("serial")
public class ArgumentException extends Exception {

	private String usage;

	public ArgumentException(String string) {
		this.usage = string;
	}

	public ArgumentException(String m, Throwable t) {
		super(t);
		this.usage = m;
	}

	@Override
	public String toString() {
		return usage;
	}

}
