package com.voladroid.ui.cli.args;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.jboss.jreadline.complete.Completion;

import com.voladroid.model.Configurable;
import com.voladroid.ui.cli.VoladroidCli;

public abstract class Scope {

	private Scope parent = null;
	private Map<String, IArgument> args = new TreeMap<String, IArgument>();
	private Map<String, Object> data = new HashMap<String, Object>();

	private VoladroidCli app;

	/**
	 * Sub executor
	 * 
	 * @param parent
	 */
	public Scope(Scope parent) {
		this.parent = parent;
		if (parent != null) {
			this.app = parent.app;
		}
	}

	/**
	 * Root
	 */
	public Scope(VoladroidCli app) {
		this((Scope) null);
		this.app = app;
	}

	public Set<String> keys() {
		return args.keySet();
	}

	public void add(String key, IArgument argument) {
		this.args.put(key, argument);
	}

	public void add(String key, int arity, String name, String usage) {
		this.args.put(key, new MethodArgument(arity, usage, getMethod(name),
				this));
	}

	public void add(String key, int arity, String usage) {
		add(key, arity, key, usage);
	}

	public abstract String name();

	public Stack stack() {
		return Stack.getInstance();
	}

	public Method getMethod(String name) {
		try {
			return getClass().getMethod(name, Scope.class, List.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Scope execute(String[] a) throws Exception {
		List<String> arguments = Arrays.asList(a);
		String key = arguments.get(0);

		Scope current = this;
		while (current != null) {
			IArgument arg = current.args.get(key);
			if (arg != null) {
				try {
					int arity = arg.arity();
					if (arity < 0) {
						arity = arguments.size() - 1;
					}
					return arg.execute(this, arguments.subList(1, 1 + arity));
				} catch (Exception e) {
					throw new ScopeException(key + " " + arg.usage(), e);
				}
			}

			current = current.parent;
		}
		throw new ScopeException("Command not found '" + key + "'");

	}

	public void alias(String o, String n) {
		IArgument old = args.get(o);
		args.put(n, old);
	}

	public String usage() {
		StringBuilder b = new StringBuilder();
		b.append(name());
		b.append("\n===============\n");
		int max = 4;
		for (String s : args.keySet()) {
			if (s.length() > max)
				max = s.length();
		}
		for (Map.Entry<String, IArgument> kv : args.entrySet()) {
			if (kv.getValue() != null)
				b.append(kv.getKey()
						+ StringUtils.leftPad("", max - kv.getKey().length()
								+ 4) + kv.getValue().usage() + "\n");

		}

		if (parent != null)
			b.append("\n" + parent.usage());

		return b.toString();
	}

	public void put(String key, Object data) {
		this.data.put(key, data);
	}

	public Object getSafe(String key) {
		Object d = data.get(key);
		if (d == null && parent != null) {
			d = parent.getSafe(key);
		}

		return d;
	}

	public abstract Configurable getConfigurable();

	public abstract List<Completion> getCompleters();

	public String in(String msg) throws IOException {
		return app.in(msg);
	}

	public void error(Exception e) {
		app.error(e);
	}

	public void error(String msg, Object... args) {
		app.error(msg, args);
	}

	public void out(String msg, Object... args) {
		app.out(msg, args);
	}

	public void out(Object o) {
		app.out(o);
	}

	public VoladroidCli app() {
		return app;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) getSafe(key);
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Object> kv : data.entrySet()) {
			builder.append(kv.getKey());
			builder.append(": ");
			builder.append(kv.getValue());
			builder.append(", ");
		}
		if (data.size() > 0) {
			builder.replace(builder.length() - 2, builder.length(), "");
		}

		return name() + " (" + builder + ")";
	}
}
