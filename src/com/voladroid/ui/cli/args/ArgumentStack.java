package com.voladroid.ui.cli.args;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class ArgumentStack implements Iterable<ArgumentExecutor> {

	private LinkedList<ArgumentExecutor> stack = new LinkedList<ArgumentExecutor>();
	private static ArgumentStack instance = null;

	public static ArgumentStack getInstance() {
		if (instance == null) {
			instance = new ArgumentStack();
		}

		return instance;
	}

	private ArgumentStack() {
	}

	public ArgumentExecutor local() {
		return stack.getFirst();
	}

	public ArgumentExecutor global() {
		return stack.getLast();
	}

	public void push(ArgumentExecutor executor) {
		stack.push(executor);
	}

	public ArgumentExecutor pop() {
		return stack.pop();
	}

	public boolean empty() {
		return stack.isEmpty();
	}

	@Override
	public Iterator<ArgumentExecutor> iterator() {
		return stack.iterator();
	}

	public ListIterator<ArgumentExecutor> listIterator() {
		return stack.listIterator();
	}

	public ListIterator<ArgumentExecutor> listIterator(int index) {
		return stack.listIterator(index);
	}

	public void clear() {
		stack.clear();
	}

}
