package ca.concordia.lanterns.services;

import java.util.List;
import java.util.Stack;

import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;

public interface SetupService {
	
	/** 16 + 6 (3 tiles per player) + 1 initial tile */
	int TWO_PLAYERS_TILE_COUNT = 23;
	/** 18 + 9 (3 tiles per player) + 1 initial tile */
	int THREE_PLAYERS_TILE_COUNT = 28;
	/** 20 + 12 (3 tiles per player) + 1 initial tile */
	int FOUR_PLAYERS_TILE_COUNT = 33;

	/**
	 * Creates a new game
	 * 
	 * @param playerNames
	 *            The name of the players that will participate on the game. Should be between 2-4.
	 * @return The game that was just created.
	 */
	Game createGame(final String[] playerNames);

	/**
	 * Randomically assign one position for each player.
	 * 
	 * @param playerNames
	 *            The name of the players that will participate on the game. Should be between 2-4.
	 * @return reordered array, where 1st player will be on index 0, 2nd on 1, 3rd on 2 (if existent) and 4th on 3 (if existent).
	 */
	String[] decidePlayersOrder(final String[] playerNames);

	/**
	 * Setup step #1:
	 * <p>
	 * Place the starting Lake Tile in the center of the play area face down. Flip the tile face up and orient it so that one
	 * player is facing the red side and each other player is facing a different side.
	 * 
	 * @param lake
	 * @param initialTile
	 */
	void startLake(final List<LakeTile> lake, final LakeTile initialTile, int playerCount);

	/**
	 * Setup step #2:
	 * <p>
	 * Deal 3 Lake Tiles to each player face down. Lake Tiles are held in hand and kept secret from other players.
	 * 
	 * @param totalTiles
	 * @param players
	 * @throws IllegalArgumentException
	 *             If amount of tiles is not enough for the amount of players.
	 */
	void dealPlayerTiles(final LakeTile[] totalTiles, final Player[] players);

	/**
	 * Setup step #3:
	 * <p>
	 * Create a draw stack of Lake Tiles. The number of tiles in the stack depends on player count:
	 * <ul>
	 * <li>4 Players: 20 tiles</li>
	 * <li>3 Players: 18 tiles</li>
	 * <li>2 Players: 16 tiles</li>
	 * </ul>
	 * 
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
	 * Setup step #4:
	 * 
	 * <p>
	 * Separate the Lantern Cards by color into 7 stacks. These stacks are collectively called the supply The number of cards
	 * <p>
	 * in each stack depends on player count:
	 * <ul>
	 * <li>4 Players: 8 cards</li>
	 * <li>3 Players: 7 cards</li>
	 * <li>2 Players: 5 cards</li>
	 * </ul>
	 * 
	 * By the end of this method, the cards stacks will be populated.
	 * 
	 * @param cards
	 *            the cards stacks to populate.
	 * @param playerCount
	 *            number of players in the game.
	 * @throws IllegalArgumentException
	 *             If cards stack arrays are not prepared to be populated.
	 */
	void separateLanternCards(final LanternCardWrapper[] cards, final int playerCount);

	/**
	 * Setup step #5: Set aside the 3 generic Dedication Tokens.
	 * <p>
	 * Setup step #6: Separate Dedication Tokens by type into 3 stacks. Arrange each stack in descending order of value.
	 * <ul>
	 * <li>4 Players: use all tokens</li>
	 * <li>3 Players: remove tokens with 4 dots</li>
	 * <li>2 Players: remove tokens with 3 or 4 dots</li>
	 * </ul>
	 * 
	 * By the end of this method the dedication stacks will be populated.
	 * 
	 * @param dedications
	 *            the dedication token stacks to be set
	 * @param playerCount
	 *            number of players in the game.
	 * @throws IllegalArgumentException
	 *             If dedications stack arrays are not prepared to be populated.
	 */
	void setDedicationTokens(final DedicationTokenWrapper[] dedications, final int playerCount);

	/**
	 * Setup step #7: Give each player one Lantern Card corresponding to the color on the side of the starting Lake Tile he is
	 * facing. Each players Lantern Cards are always kept in front of them, visible for everyone to see.
	 * <p>
	 * fyi, setup step #8 (Give the player with the red Lantern Card, the color of good fortune, the start player marker) is
	 * unnecessary, as start player can be inferred by first tile in {@link Game#getLake()} plus first player in the
	 * {@link Game#getPlayers()}.
	 * 
	 * @param lake
	 *            game lake
	 * @param cards
	 *            lantern cards available to be distributed
	 * @param players
	 *            the players in the game.
	 */
	void distributeInitialLanterns(final List<LakeTile> lake, final LanternCardWrapper[] cards, final Player[] players);
}
