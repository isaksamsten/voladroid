package com.voladroid.ui.cli.args;

public abstract class Argument implements IArgument {
	private int arity;
	private String usage;
	private ArgumentExecutor parent;
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

	public String in(String msg) {
		return parent().in(msg);
	}

	public void error(Exception e) {
		parent().error(e);
	}

	public void error(String msg, Object... args) {
		parent().error(msg, args);
	}

	public void out(String msg, Object... args) {
		parent().out(msg, args);
	}

	public void out(Object o) {
		parent().out(o);
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
	public ArgumentExecutor parent() {
		return parent;
	}

	@Override
	public String usage() {
		return usage;
	}
}
