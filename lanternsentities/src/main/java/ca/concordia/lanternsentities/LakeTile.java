package ca.concordia.lanternsentities;

import ca.concordia.lanternsentities.enums.Colour;
/** Lake Tile entity
 * 
 * @version 1.0
 *
 */
public class LakeTile {
	
	/**
	 * Each Lake Tile has 4 sides.
	 */
	public static final int TOTAL_SIDES = 4;
	
	private TileSide[] sides;
	/** Index of which element in {@link #sides} is facing the first player in the game. */
	private int orientation;
	private Boolean platform;
	/**
	 * Each Lake tile has four sides, each side can have a colour, there are 7 different colours.
	 * @param colours There are 7 possible colours.
	 * @param platform The tiles may be in play or in the deck.
	 * @throws IllegalArgumentException	if colours size does not match {@link #TOTAL_SIDES}.
	 */
	public void init(Colour[] colours, boolean platform) {
		this.platform = platform;
		sides = new TileSide[TOTAL_SIDES];

		// populate sides
		if (colours != null && colours.length == TOTAL_SIDES) {
			for (int i = 0; i < TOTAL_SIDES; i++) {
				TileSide side = new TileSide();
				side.init(colours[i]);
				sides[i] = side;
			}
		} else {
			throw new IllegalArgumentException("Invalid colours size!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = result + sidesHashCode();
		result = prime * result + (platform ? 1231 : 1237);
		return result;
	}
	
	public int sidesHashCode() {
		final int prime = 31;
		int result = 1;
		for (TileSide side: sides) {
			result = prime * result + side.hashCode();
		}
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
		LakeTile other = (LakeTile) obj;
		if (platform != other.platform)
			return false;
		if (sidesHashCode() != other.sidesHashCode())
			return false;
		return true;
	}

	public TileSide[] getSides() {
		return sides;
	}

	public void setSides(TileSide[] sides) {
		this.sides = sides;
	}

	public boolean isPlatform() {
		return platform;
	}

	public void setPlatform(Boolean platform) {
		this.platform = platform;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	
}
