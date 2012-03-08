package com.voladroid.ui.cli.args;

public abstract class Argument implements IArgument {
	private int arity;
	private String usage;
	private ArgumentExecutor parent;

	public Argument(int arity, String usage) {
		this.arity = arity;
		this.usage = usage;
	}

	@Override
	public int arity() {
		return arity;
	}

	@Override
	public void parent(ArgumentExecutor a) {
		this.parent = a;
	}

	public Object getSafe(String key) {
		return parent().getSafe(key);
	}

	public <T> T get(String key) {
		return parent().get(key);
	}

	public void put(String key, Object data) {
		parent().put(key, data);
	}

	@Override
	public ArgumentExecutor parent() {
		return parent;
	}

	@Override
	public String usage() {
		return usage;
	}
}
