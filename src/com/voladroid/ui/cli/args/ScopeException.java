package com.voladroid.ui.cli.args;

@SuppressWarnings("serial")
public class ScopeException extends Exception {

	private String usage;

	public ScopeException(String string) {
		this.usage = string;
	}

	public ScopeException(String m, Throwable t) {
		super(t);
		this.usage = m;
	}

	@Override
	public String toString() {
		return usage;
	}

}
