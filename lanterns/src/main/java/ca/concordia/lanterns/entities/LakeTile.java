package ca.concordia.lanterns.entities;

import java.util.HashMap;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.PlayerID;
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
	
	private final TileSide[] sides;
	private final boolean platform;
	private final HashMap<PlayerID, Integer> orientation = null ;
	/**
	 * Each Lake tile has four sides, each side can have a colour, there are 7 different colours.
	 * @param colours There are 7 possible colours.
	 * @param platform The tiles may be in play or in the deck.
	 * @throws IllegalArgumentException	if colours size does not match {@link #TOTAL_SIDES}.
	 */
	public LakeTile(Colour[] colours, boolean platform) {
		super();
		this.platform = platform;
		sides = new TileSide[TOTAL_SIDES];

		// populate sides
		if (colours != null && colours.length == TOTAL_SIDES) {
			for (int i = 0; i < TOTAL_SIDES; i++) {
				sides[i] = new TileSide(colours[i]);
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
	
	public boolean isPlatform() {
		return platform;
	}
	
	public void setOrientation ( PlayerID[] orientation ) {
		if ( this.orientation == null ) {
			if ( orientation.length == TOTAL_SIDES ) {
				for ( int i = 0 ; i != orientation.length ; ++i ) {
					if ( this.orientation.containsKey(orientation[i]) ) {
						throw new IllegalArgumentException ( "Invalid information for orienting the lake tile" );
					} else {
						this.orientation.put(orientation[i], i) ;
					}
				}
			} else {
				throw new IllegalArgumentException ( "Incomplete information for orienting lake tile" ) ;
			}
		} else {
			throw new IllegalArgumentException ( "Can't rellocate a lake tile" ) ;
		}
	}
}
