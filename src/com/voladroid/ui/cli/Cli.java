package com.voladroid.ui.cli;

import java.io.IOException;

public interface Cli {

	public abstract void start();

	public abstract void onStart();

	public abstract boolean hasCommand();

	public abstract String[] nextCommand() throws IOException;

	public abstract void onStop();

	public abstract String in(String msg) throws IOException;

}