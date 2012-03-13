package com.voladroid.ui.cli.args;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Stack implements Iterable<Scope> {

	private LinkedList<Scope> stack = new LinkedList<Scope>();
	private static Stack instance = null;

	public static Stack getInstance() {
		if (instance == null) {
			instance = new Stack();
		}

		return instance;
	}

	private Stack() {
	}

	public Scope local() {
		return stack.getFirst();
	}

	public Scope global() {
		return stack.getLast();
	}

	public void push(Scope executor) {
		stack.push(executor);
	}

	public Scope pop() {
		return stack.pop();
	}

	public boolean empty() {
		return stack.isEmpty();
	}

	@Override
	public Iterator<Scope> iterator() {
		return stack.iterator();
	}

	public ListIterator<Scope> listIterator() {
		return stack.listIterator();
	}

	public ListIterator<Scope> listIterator(int index) {
		return stack.listIterator(index);
	}

	public void clear() {
		stack.clear();
	}

}
