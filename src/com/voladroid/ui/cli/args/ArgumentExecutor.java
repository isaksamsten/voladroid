package com.voladroid.ui.cli.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class ArgumentExecutor {

	private ArgumentExecutor parent = null;
	private Map<String, IArgument> args = new TreeMap<String, IArgument>();
	private Map<String, Object> data = new HashMap<String, Object>();

	private String name;

	/**
	 * Sub executor
	 * 
	 * @param parent
	 */
	public ArgumentExecutor(String name, ArgumentExecutor parent) {
		this.name = name;
		this.parent = parent;
	}

	/**
	 * Root
	 */
	public ArgumentExecutor(String name) {
		this(name, null);
	}

	public void add(String key, IArgument argument) {
		argument.parent(this);
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
					return arg.execute(arguments.subList(1, 1 + arity));
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
			b.append(kv.getKey()
					+ StringUtils.leftPad("", max - kv.getKey().length() + 4)
					+ kv.getValue().usage() + "\n");
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
