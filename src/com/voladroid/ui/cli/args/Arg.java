package com.voladroid.ui.cli.args;

public abstract class Arg implements IArg {
	private int arity;
	private String usage;

	public Arg(int arity, String usage) {
		this.arity = arity;
		this.usage = usage;
	}

	@Override
	public int arity() {
		return arity;
	}

	@Override
	public String usage() {
		return usage;
	}
}
