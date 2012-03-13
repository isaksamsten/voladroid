package com.voladroid.ui.cli.args;

import java.lang.reflect.Method;
import java.util.List;

public class MethodArgument extends Argument {

	private Method method = null;
	private Scope thiz = null;

	public MethodArgument(int arity, String usage, Method method,
			Scope thiz) {
		super(arity, usage);
		this.method = method;
		this.thiz = thiz;
	}

	@Override
	public Scope execute(Scope self, List<String> args)
			throws Exception {
		Object ret = method.invoke(thiz, self, args);
		if (ret != null && ret instanceof Scope) {
			return (Scope) ret;
		}

		return null;
	}

}
