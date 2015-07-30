package ca.concordia.lanternsentities;

import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanternsentities.enums.Colour;
/**
 * Tile Side entity
 * @version 1.0
 *
 */
@XmlRootElement
public class TileSide {
	private Colour colour;
	private LakeTile adjacent;
	
	/**
	 * @param colour There are seven possible colours for a tile side.
	 */
	public void init(Colour colour) {
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

	@Override
	public String toString() {
		String adjacentString = null;
		if (adjacent != null) {
			adjacentString = adjacent.toShortString();
		}
		return "[" + colour.key + "-" +  adjacentString + "]";
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	@XmlIDREF
	public LakeTile getAdjacent() {
		return adjacent;
	}

	public void setAdjacent(LakeTile adjacent) {
		if ( this.adjacent == null ) {
			this.adjacent = adjacent ;
		} else {
			throw new IllegalArgumentException ( "This Tileside already have an adjacent tile" );
		}
	}
}
