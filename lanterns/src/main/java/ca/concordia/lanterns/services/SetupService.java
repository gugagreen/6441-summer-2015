package ca.concordia.lanterns.services;

import java.util.Stack;

import ca.concordia.lanterns.entities.DedicationToken;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.Player;

public interface SetupService {

	/**
	 * Creates a new game
	 * @param playerNames	The name of the players that will participate on the game. Should be between 2-4.
	 * @return	The game that was just created.
	 */
	Game createGame(String[] playerNames);
	
	/**
	 * Setup step #1:<br/>
	 * Place the starting Lake Tile in the center of the play area face down. Flip the tile face up and orient it so that one
	 * player is facing the red side and each other player is facing a different side.
	 * 
	 * @param lake
	 * @param initialTile
	 */
	void startLake(final Lake lake, final LakeTile initialTile);

	/**
	 * Setup step #2:<br/>
	 * Deal 3 Lake Tiles to each player face down. Lake Tiles are held in hand and kept secret from other players.
	 * 
	 * @param totalTiles
	 * @param players
	 * @throws IllegalArgumentException
	 *             If amount of tiles is not enough for the amount of players.
	 */
	void dealPlayerTiles(final LakeTile[] totalTiles, final Player[] players);

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
	void drawTileStack(final Stack<LakeTile> tiles, final LakeTile[] totalTiles, final int playerCount);

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
	void separateLanternCards(final Stack<LanternCard>[] cards, final int playerCount);
	
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
	void setDedicationTokens(final Stack<DedicationToken>[] dedications, final int playerCount);
	
	
	/**
	 * Setup step #7:<br/>
	 * Give each player one Lantern Card corresponding to the color on the side of the starting Lake Tile he is facing. 
	 * Each player’s Lantern Cards are always kept in front of them, visible for everyone to see.
	 * <p/>
	 * fyi, Setup step #8 (Give the player with the red Lantern Card, the color of good fortune, the start player marker) is unnecessary, as start player can be inferred by first tile in lake plus first player
	 */
	void distributeInitialLanterns(final Stack<LanternCard>[] cards, final Player[] players);
	
}
