package ca.concordia.lanterns.services.impl;

import java.util.Random;
import java.util.Set;
import java.util.Stack;

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
		dealPlayerTiles(totalTiles, game.getPlayers());
		drawTileStack(game.getTiles(), totalTiles, playerCount);
		// TODO - step 4
		// TODO - step 5
		// TODO - step 6
		// TODO - step 7
		// TODO - step 8
		
		return game;
	}

	/**
	 * Generates tile stack depending on the number of players in the game.
	 * 
	 * @param playerCount
	 *            number of players in the game.
	 * @return Array of tiles.
	 */
	protected LakeTile[] generateTiles(final int playerCount) {
		LakeTile[] totalTiles = null;

		// TODO - maybe move quantity for each player count to properties/enum
		// TODO - maybe just create a single big stack of tiles, and let splitting per player on step 3
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
	 * @param totalTiles
	 * @param players
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
	
	/**
	 * Setup step #3:<br/>
	 * Create a draw stack of Lake Tiles. The number of tiles in the stack depends on player count:<br/>
	 * <ul>
	 * <li> 4 Players: 20 tiles</li>
	 * <li> 3 Players: 18 tiles</li>
	 * <li> 2 Players: 16 tiles</li>
	 * </ul>
	 * <br/>
	 * In the end of this method, the tiles collection will be populated.
	 */
	protected void drawTileStack(final Stack<LakeTile> tiles, final LakeTile[] totalTiles, final int playerCount) {
		int count = 0;
		if (playerCount == 4) {
			count = 20;
		} else if (playerCount == 3) {
			count = 18;
		} else if (playerCount == 2) {
			count = 16;
		}
		
		// start with a shift: 1st tile + 3 tiles per player
		int shift = 1 + 3 * playerCount;
		
		for (int i = shift; i < (shift + count); i++) {
			tiles.add(totalTiles[i]);
		}
	}
	
	
}
