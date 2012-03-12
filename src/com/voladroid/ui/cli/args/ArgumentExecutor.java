package com.voladroid.ui.cli.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.voladroid.model.Configurable;
import com.voladroid.service.Services;
import com.voladroid.ui.cli.VoladroidMain;

public class ArgumentExecutor {

	private ArgumentExecutor parent = null;
	private Map<String, IArgument> args = new TreeMap<String, IArgument>();
	private Map<String, Object> data = new HashMap<String, Object>();

	private Configurable configurable = null;
	private String configKey = null;

	private String name;
	private VoladroidMain app;

	/**
	 * Sub executor
	 * 
	 * @param parent
	 */
	public ArgumentExecutor(String name, ArgumentExecutor parent) {
		this.name = name;
		this.parent = parent;
		if (parent != null) {
			this.app = parent.app;
		}
	}

	/**
	 * Root
	 */
	public ArgumentExecutor(String name, VoladroidMain app) {
		this(name, (ArgumentExecutor) null);
		this.app = app;
	}

	public void add(String key, IArgument argument) {
		this.args.put(key, argument);
	}

	public String name() {
		return name;
	}

	public ArgumentExecutor execute(String[] a) throws Exception {
		List<String> arguments = Arrays.asList(a);
		String key = arguments.get(0);

		ArgumentExecutor current = this;
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
					throw new ArgumentException(key + " " + arg.usage(), e);
				}
			}

			current = current.parent;
		}
		throw new ArgumentException("Command not found '" + key + "'");

	}

	public void alias(String o, String n) {
		IArgument old = args.get(o);
		// old.alias(true);
		args.put(n, old);
	}

	public String usage() {
		StringBuilder b = new StringBuilder();
		b.append(name);
		b.append("\n===============\n");
		int max = 4;
		for (String s : args.keySet()) {
			if (s.length() > max)
				max = s.length();
		}
		for (Map.Entry<String, IArgument> kv : args.entrySet()) {
			if (!kv.getValue().alias()) {
				b.append(kv.getKey()
						+ StringUtils.leftPad("", max - kv.getKey().length()
								+ 4) + kv.getValue().usage() + "\n");
			}
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

	public Configurable getConfigurable() {
		return get(configKey);
	}

	public void setConfigurableKey(String key) {
		this.configKey = key;
	}

	public String in(String msg) {
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
