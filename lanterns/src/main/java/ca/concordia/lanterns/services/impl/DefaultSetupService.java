package ca.concordia.lanterns.services.impl;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupService implements SetupService {

	public Game createGame(Set<String> playerNames) {
		int playerCount = playerNames.size();
		// TODO -  validate amount of players
		Game game = new Game(playerNames);
		LakeTile[] totalTiles = generateTiles(playerCount);
		startLake(game.getLake(), totalTiles[0]);

		// TODO - store game???
		return game;
	}

	/**
	 * Generates tile stack depending on the number of players in the game.
	 * 
	 * @param playerCount
	 *            number of players in the game.
	 * @return Array of tiles.
	 */
	protected LakeTile[] generateTiles(int playerCount) {
		LakeTile[] totalTiles = null;

		// TODO - maybe move quantity for each player count to properties/enum
		switch (playerCount) {
		case 4:
			// 20 + 12 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[33];
			break;
		case 3:
			// 18 + 9 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[28];
			break;
		case 2:
			// 16 + 6 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[23];
			break;
		default:
			// TODO - throw exception
			break;
		}

		// if total tiles is valid, populate it
		if (totalTiles != null) {
			Random random = new Random();
			Colour[] colours = Colour.values();
			for (int i = 0; i < totalTiles.length; i++) {
				Colour[] tileColours = new Colour[LakeTile.TOTAL_SIDES];
				for (int j = 0; j < tileColours.length; j++) {
					int nextColour = random.nextInt(colours.length);
					tileColours[j] = colours[nextColour];
				}
				boolean platform = random.nextBoolean();
				totalTiles[i] = new LakeTile(tileColours, platform);
			}
		}

		return totalTiles;
	}

	/**
	 * Setup step #1:<br/>
	 * Place the starting Lake Tile in the center of the play area face down. Flip the tile face up and orient it so that one
	 * player is facing the red side and each other player is facing a different side.
	 * 
	 * @param lake
	 * @param initialTile
	 */
	protected void startLake(final Lake lake, final LakeTile initialTile) {
		lake.getTiles().add(initialTile);
	}
	
	/**
	 * Setup step #2:<br/>
	 * Deal 3 Lake Tiles to each player face down. Lake Tiles are held in hand and kept secret from other players.
	 */
	protected void dealPlayerTiles(final LakeTile[] totalTiles, final Set<Player> players) {
		final int playerCount = players.size();
		int toAssign = (3*playerCount);
		if (totalTiles.length > toAssign) {
			while (toAssign > 0) {
				for (Player player : players) {
					player.getTiles().add(totalTiles[1+toAssign--]);
				}
			}
		} else {
			// FIXME - throw exception
		}
	}
}
