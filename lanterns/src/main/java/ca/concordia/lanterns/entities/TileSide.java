package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.Colour;
/**
 * Tile Side entity
 * @version 1.0
 *
 */
public class TileSide {
	private Colour colour;
	private LakeTile adjacent;
	
	/**
	 * Tile side constructor.
	 * @param colour There are seven possible colours for a tile side.
	 */
	public TileSide(Colour colour) {
		super();
		this.colour = colour;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		return result;
	}

	/**
	 * 
	 */
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

	/**
	 * Returns adjacent lake tile(s) to the constructor.
	 * @return Any adjacent lake tiles.
	 */
	public LakeTile getAdjacent() {
		return adjacent;
	}

	/**
	 * Sets the tile side as adjacent.
	 * @param adjacent It is possible to be adjacent on 4 sides.
	 */
	public void setAdjacent(LakeTile adjacent) {
		this.adjacent = adjacent;
	}

	/**
	 * Returns the colour of the tile side to the constructor.
	 * @return Tile side colour.
	 */
	public Colour getColour() {
		return colour;
	}
}
