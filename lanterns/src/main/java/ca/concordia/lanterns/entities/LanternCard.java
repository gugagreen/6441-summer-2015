package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.Colour;

/** Lantern Card object
 * <P> Various attributes of the Lantern Cards and related behaviors.
 * @version 1.0
 *
 */
public class LanternCard {
	private Colour colour;

	/**
	 * Lantern Card constructor.
	 * @param colour
	 */
	public LanternCard(Colour colour) {
		super();
		this.colour = colour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LanternCard other = (LanternCard) obj;
		if (colour != other.colour)
			return false;
		return true;
	}

	public Colour getColour() {
		return colour;
	}
}
