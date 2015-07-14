package ca.concordia.lanterns.entities;

import java.util.Stack;

public class StackWrapper<T> {

	private Stack<T> stack;
	
	public StackWrapper() {
		this.stack = new Stack<T>();
	}

	public Stack<T> getStack() {
		return stack;
	}

	public void setStack(Stack<T> stack) {
		this.stack = stack;
	}
}
