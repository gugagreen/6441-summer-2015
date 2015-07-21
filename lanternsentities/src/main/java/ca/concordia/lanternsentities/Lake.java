package ca.concordia.lanternsentities;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.lanternsentities.enums.PlayerID;

/**
 * Lake entity.
 * 
 * @version 1.0
 */
public class Lake {
	private List<LakeTile> tiles;

	public Lake() {
		super();
		this.tiles = new ArrayList<LakeTile>();
	}

	public void placeTile ( LakeTile tile, PlayerID[] orientation ) {
		tile.setOrientation(orientation);
		this.tiles.add(tile) ;
	}
	
	public List<LakeTile> getTiles() {
		return tiles;
	}

	public void setTiles(List<LakeTile> tiles) {
		this.tiles = tiles;
	}

}
