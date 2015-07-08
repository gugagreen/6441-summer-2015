package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;

public class Lake {
	private final List<LakeTile> tiles;

	public Lake() {
		super();
		this.tiles = new ArrayList<LakeTile>();
	}

	public List<LakeTile> getTiles() {
		return tiles;
	}
	
	
}
