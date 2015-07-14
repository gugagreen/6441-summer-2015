package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;

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

	public List<LakeTile> getTiles() {
		return tiles;
	}

	public void setTiles(List<LakeTile> tiles) {
		this.tiles = tiles;
	}

}
