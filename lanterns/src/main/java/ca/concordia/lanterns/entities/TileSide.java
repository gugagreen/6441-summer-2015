package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.Colour;

public class TileSide {
	private Colour colour;
	private LakeTile adjacent;
	
	public TileSide(Colour colour) {
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
		TileSide other = (TileSide) obj;
		if (colour != other.colour)
			return false;
		return true;
	}

	public LakeTile getAdjacent() {
		return adjacent;
	}

	public void setAdjacent(LakeTile adjacent) {
		this.adjacent = adjacent;
	}

	public Colour getColour() {
		return colour;
	}
}
