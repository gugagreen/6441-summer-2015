package ca.concordia.lanterns.entities;

import java.util.Stack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DedicationTokenWrapper {

	private Stack<DedicationToken> stack;
	
	public DedicationTokenWrapper() {
		this.stack = new Stack<DedicationToken>();
	}

	@XmlElement(name="token")
	public Stack<DedicationToken> getStack() {
		return stack;
	}

	public void setStack(Stack<DedicationToken> stack) {
		this.stack = stack;
	}
}
