package com.voladroid.ui.cli.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;

public class ArgParser {

	private Map<String, IArg> args = new HashMap<String, IArg>();

	public void add(String key, IArg argument) {
		this.args.put(key, argument);
	}

	public void execute(String[] a) throws Exception {
		List<String> arguments = Arrays.asList(a);
		for (int n = 0; n < arguments.size(); n++) {

			String key = arguments.get(n);
			IArg arg = args.get(key);
			if (arg != null) {
				try {
					int arity = arg.arity();
					List<String> argArgs = arguments.subList(n + 1, n + arity
							+ 1);
					arg.execute(argArgs);
					n += arity;
				} catch (Exception e) {
					throw new ArgException(key + " " + arg.usage(), e);
				}
			} else {
				throw new UnsupportedOperationException(key + " not valid");
			}

		}
	}

	public String usage() {
		StringBuilder b = new StringBuilder();
		for (Map.Entry<String, IArg> kv : args.entrySet()) {
			b.append(kv.getKey() + " " + kv.getValue().usage() + "\n\t     ");
		}

		return b.toString();
	}
}
