package com.voladroid.ui.cli.args;

import java.util.List;

public interface IArg {
	public int arity();

	public String usage();

	public void execute(List<String> args) throws Exception;
}
