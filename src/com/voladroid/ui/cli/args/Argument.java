package com.voladroid.ui.cli.args;

public abstract class Argument implements IArgument {
	private int arity;
	private String usage;
	private boolean alias;

	public Argument(int arity, String usage) {
		this.arity = arity;
		this.usage = usage;
	}

	@Override
	public int arity() {
		return arity;
	}

	@Override
	public void alias(boolean b) {
		this.alias = b;
	}

	@Override
	public boolean alias() {
		return alias;
	}

	@Override
	public String usage() {
		return usage;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}
}
