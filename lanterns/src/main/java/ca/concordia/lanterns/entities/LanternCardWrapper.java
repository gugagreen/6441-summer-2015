package ca.concordia.lanterns.entities;

import java.util.Stack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Lantern Card Wrapper
 * @version 1.0
 */
@XmlRootElement
public class LanternCardWrapper {

	private Stack<LanternCard> stack;
	
	public LanternCardWrapper() {
		this.stack = new Stack<LanternCard>();
	}

	@XmlElement(name="card")
	public Stack<LanternCard> getStack() {
		return stack;
	}

	public void setStack(Stack<LanternCard> stack) {
		this.stack = stack;
	}
}
