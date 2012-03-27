package com.voladroid.ui.cli.args;

import java.util.List;

public interface IArgument {
	public int arity();

	public String usage();

	public Scope execute(Scope self, List<String> args)
			throws Exception;

	public void alias(boolean b);

	public boolean alias();
	
	public void dispose();

	public void initialize();
	
}
