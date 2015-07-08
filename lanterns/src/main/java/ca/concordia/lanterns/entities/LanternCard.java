package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.Colour;

public class LanternCard {
	private Colour colour;

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
