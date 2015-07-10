package ca.concordia.lanterns.services.impl;

import java.util.Random;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanterns.entities.DedicationToken;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupService implements SetupService {

	public Game createGame(Set<String> playerNames) {
		validatePlayersSet(playerNames);
		int playerCount = playerNames.size();
		
		Game game = new Game(playerNames);
		LakeTile[] totalTiles = generateTiles(playerCount);
		
		startLake(game.getLake(), totalTiles[0]);
		dealPlayerTiles(totalTiles, game.getPlayers());
		drawTileStack(game.getTiles(), totalTiles, playerCount);
		separateLanternCards(game.getCards(), playerCount);
		setDedicationTokens(game.getDedications(), playerCount);
		distributeInitialLanterns();
		setStartPlayer();

		return game;
	}
	
	protected void validatePlayersSet(Set<String> playerNames) {
		if ((playerNames == null) || (playerNames.size() < 2) || (playerNames.size() > 4)) {
			throw new IllegalArgumentException("Number of players should be between 2 and 4!");
		}
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
	 * 
	 * @param totalTiles
	 * @param players
	 * @throws IllegalArgumentException
	 *             If amount of tiles is not enough for the amount of players.
	 */
	protected void dealPlayerTiles(final LakeTile[] totalTiles, final Set<Player> players) {
		final int playerCount = players.size();
		int toAssign = (3 * playerCount);
		if (totalTiles.length > toAssign) {
			while (toAssign > 0) {
				for (Player player : players) {
					player.getTiles().add(totalTiles[1 + toAssign--]);
				}
			}
		} else {
			throw new IllegalArgumentException("There are not enough tiles to be assigned!");
		}
	}

	/**
	 * Setup step #3:<br/>
	 * Create a draw stack of Lake Tiles. The number of tiles in the stack depends on player count:<br/>
	 * <ul>
	 * <li>4 Players: 20 tiles</li>
	 * <li>3 Players: 18 tiles</li>
	 * <li>2 Players: 16 tiles</li>
	 * </ul>
	 * <br/>
	 * By the end of this method, the tiles collection will be populated.
	 * 
	 * @param tiles
	 *            stack to be populated.
	 * @param totalTiles
	 *            total stack of tiles in the game.
	 * @param playerCount
	 *            number of players in the game.
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

	/**
	 * Setup step #4:<br/>
	 * Separate the Lantern Cards by color into 7 stacks. These stacks are collectively called the “supply.” The number of cards
	 * in each stack depends on player count:<br/>
	 * <ul>
	 * <li>4 Players: 8 cards</li>
	 * <li>3 Players: 7 cards</li>
	 * <li>2 Players: 5 cards</li>
	 * </ul>
	 * <br/>
	 * By the end of this method, the cards stacks will be populated.
	 * @param cards	the cards stacks to populate.
	 * @param playerCount	number of players in the game.
	 * @throws IllegalArgumentException
	 *             If cards stack arrays are not prepared to be populated.
	 */
	protected void separateLanternCards(final Stack<LanternCard>[] cards, final int playerCount) {
		int count = 0;
		if (playerCount == 4) {
			count = 8;
		} else if (playerCount == 3) {
			count = 7;
		} else if (playerCount == 2) {
			count = 5;
		}
		
		Colour[] colours = Colour.values();
		
		if ((cards != null) && (cards.length == colours.length)) {
			for (int i = 0; i < colours.length; i++) {
				Stack<LanternCard> colourStack = cards[i];
				for (int j = 0; j < count; j++) {
					colourStack.push(new LanternCard(colours[i]));
				}
			}
		} else {
			throw new IllegalArgumentException("Stack of cards to be populated is invalid!");
		}
	}
	
	/**
	 * Setup step #5:<br/>
	 * Set aside the 3 generic Dedication Tokens.
	 * <p/>
	 * Setup step #6:<br/>
	 * Separate Dedication Tokens by type into 3 stacks. Arrange each stack in descending order of value.<br/>
	 * <ul>
	 * <li>4 Players: use all tokens</li>
	 * <li>3 Players: remove tokens with 4 dots</li>
	 * <li>2 Players: remove tokens with 3 or 4 dots</li>
	 * </ul>
	 * 
	 * By the end of this method the dedication stacks will be populated.
	 * 
	 * @param dedications	the dedication token stacks to be set
	 * @param playerCount	number of players in the game.
	 * @throws IllegalArgumentException
	 *             If dedications stack arrays are not prepared to be populated.
	 */
	protected void setDedicationTokens(final Stack<DedicationToken>[] dedications, final int playerCount) {
		DedicationType[] types = DedicationType.values();
		
		if ((dedications != null) && (dedications.length == types.length)) {
			for (int i = 0; i < types.length; i++) {
				Stack<DedicationToken> dedicationStack = dedications[i];
				DedicationType type = types[i];
				int[] values = null;
				if (playerCount == 4) {
					values = type.getValuesFour();
				} else if (playerCount == 3) {
					values = type.getValuesThree();
				} else if (playerCount == 2) {
					values = type.getValuesTwo();
				}
				
				for (int value : values) {
					dedicationStack.push(new DedicationToken(value, type));
				}
				
			}
		} else {
			throw new IllegalArgumentException("Stack of dedications to be populated is invalid!");
		}
	}
	
	
	/**
	 * Setup step #7:<br/>
	 * Give each player one Lantern Card corresponding to the color on the side of the starting Lake Tile he is facing. 
	 * Each player’s Lantern Cards are always kept in front of them, visible for everyone to see.
	 */
	protected void distributeInitialLanterns() {
		// FIXME - implement
	}
	/**
	 * Setup step #8:<br/>
	 * Give the player with the red Lantern Card, the color of good fortune, the start player marker.
	 */
	protected void setStartPlayer() {
		// FIXME - implement
	}

}
