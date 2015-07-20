package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.Colour;

/**
 * Lantern Card Wrapper
 * @version 1.0
 */
public class LanternCardWrapper {

	private Colour colour;
	private int quantity;

	public LanternCardWrapper() {
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
