package ca.concordia.lanterns.services.impl;

import java.util.Set;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupService implements SetupService {

	public Game createGame(Set<String> playerNames) {
		Game game = new Game(playerNames);
		LakeTile[] totalTiles = generateTiles(playerNames.size());
				
		// TODO - store game???
		return game;
	}
	
	protected LakeTile[] generateTiles(int playerCount) {
		LakeTile[] totalTiles = null;
		
		// TODO - maybe move quantity for each player count to properties/enum
		switch (playerCount) {
		case 4:
			// 20 + 12 (3 tiles per player)
			totalTiles = new LakeTile[32];
			break;
		case 3:
			// 18 + 9 (3 tiles per player)
			totalTiles = new LakeTile[27];
			break;
		case 2:
			// 16 + 6 (3 tiles per player)
			totalTiles = new LakeTile[22];
			break;
		default:
			// TODO - throw exception
			break;
		}
		if (totalTiles != null) {
			// TODO - populate totalTiles
		}
		
		return totalTiles;
	}
	
	protected void startLake(Lake lake, LakeTile initialTile) {
		lake.getTiles().add(initialTile);
	}
}
