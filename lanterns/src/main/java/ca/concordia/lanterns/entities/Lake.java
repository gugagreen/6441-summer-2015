package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.lanterns.entities.enums.PlayerID;
/**  Lake entity.
 * <P> Various attributes of the lake and related behavior.
 * @version 1.0
 *
 */
public class Lake {
	private final List<LakeTile> tiles;

	/**
	 * Lake Constructor.
	 */
	public Lake() {
		super();
		this.tiles = new ArrayList<LakeTile>();
		
	}
	
	public void placeStartTile ( LakeTile startTile, PlayerID[] orientation ) {
		startTile.setOrientation(orientation);
		this.tiles.add(startTile) ;
	}
	
	/**
	 * Returns the tiles that are part of the lake.
	 * @return The lake tiles that are part of the lake.
	 */
	public List<LakeTile> getTiles() {
		return tiles;
	}
	
	
}
