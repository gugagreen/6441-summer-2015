package ca.concordia.lanterns.services;

import java.util.List;
import java.util.Stack;

import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;

/**
 * 
 * Generic Validate Game interface. Validates if information in game object is valid.
 *
 */
public interface ValidateGame {
	
	int MIN_PLAYERS = 2;
	int MAX_PLAYERS = 4;
	

	/**
	 * Validates the number of players in the game. Number of players should be between 2-4.
	 * @param game a particular game session or state, either resumed from a saved file or new session.
	 */
	void validatePlayerCount(final Game game) ;
	
	/**
	 * Validates the number of lantern cards in player hands and on the table based on player count and card colours.
	 * @param game a particular game session or state, either resumed from a saved file or new session.
	 */
	void validateLanternCards(final Game game) ;
	
	/**
	 * Validates the number of dedication tokens in player hands and on the table.
	 * @param dedications a maximum number of 30 dedication tokens can be in the game. There are four possible types. 
	 * @param players number of players in the game (2-4)
	 */
	void validateDedicationToken(final DedicationTokenWrapper[] dedications, final Player[] players) ;
	
	/**
	 * Validates that the start player marker has been given to a player.
	 * @param player only one player should have a start player marker.
	 * @param startPlayerMarker should exist, and should be in the hand of a player.
	 */
	void validateStartPlayerMarker(final Player[] player, final int startPlayerMarker) ;
	
	/**
	 * Validates that the total number of favor tokens in the game is 20.
	 * @param game a particular game session or state, either resumed from a saved file or new session should have 20 favor tokens.
	 */
	void validateFavorToken(final Game game) ;
	
	/**
	 * Validates that the correct number of lake cards is present in the game, relative to the number of players.
	 * <p> Validates that players have no more than 3 lake tiles in their hand at any time.
	 * <p> Also validates that player have 3 lake tiles in their had for the begining on the game.
	 * @param players each player should have 3 lake cards in their hand at the start of the game.
	 * @param lake The lake may contain anywhere from 1 to all lake Tiles.
	 * @param lakeTile There are different numbers of lake tiles in the game based on the number of players.
	 * @param currentTurnPlayer The player currently playing his turn must put down a lake tile.
	 */
	void validateLakeTileStack(final Player[] players, final List<LakeTile> lake, 
			final Stack<LakeTile> lakeTile, final int currentTurnPlayer) ;

	
}
