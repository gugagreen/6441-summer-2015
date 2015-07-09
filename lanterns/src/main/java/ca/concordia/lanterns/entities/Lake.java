package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;
/**  Lake Object.
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
	/**
	 * Returns the tiles that are part of the lake.
	 * @return The lake tiles that are part of the lake.
	 */
	public List<LakeTile> getTiles() {
		return tiles;
	}
	
	
}
