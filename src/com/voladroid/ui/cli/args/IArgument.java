package com.voladroid.ui.cli.args;

import java.util.List;

public interface IArgument {
	public int arity();

	public String usage();

	public ArgumentExecutor execute(List<String> args) throws Exception;

	public void parent(ArgumentExecutor argumentExecutor);

	public ArgumentExecutor parent();
}
