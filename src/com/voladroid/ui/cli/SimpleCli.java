package com.voladroid.ui.cli;

import java.io.IOException;
import java.util.Scanner;

public class SimpleCli extends AbstractCli {
	private Scanner in = new Scanner(System.in);

	@Override
	public String in(String msg) throws IOException {
		System.out.print("dbg>> ");
		return in.nextLine();
	}

}
