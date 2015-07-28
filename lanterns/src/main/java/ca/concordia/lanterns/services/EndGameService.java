package ca.concordia.lanterns.services;

import java.util.Set;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public interface EndGameService {

	/**
	 * Checks if a game is ended. The rules for game ending are:
	 * <ul>
	 * <li>Players take their turns, placing a Lake Tile and drawing a Lake Tile (if possible), until all the 
	 * Lake Tiles are drawn and placed.</li>
	 * <li>After the last Lake Tile has been placed, players then each take one final turn in which they may perform the
	 * optional actions (Exchange a Lantern Card and Make a Dedication) as normal.</li>
	 * </ul>
	 * 
	 * @param game
	 *            The game to be checked.
	 * @return {@code true} if game is ended. {@code false} otherwise.
	 */
	boolean isGameEnded(Game game);

	/**
	 * Gets the winner of a game. The rules for winning the game are:
	 * <ul>
	 * <li>After the end of the game, the festival begins!</li>
	 * <li>Players add up the Honor they earned from their dedications. The player with the most Honor wins the game.</li>
	 * <li>In the case of a tie, the tied player with the most Favor Tokens remaining wins.</li>
	 * <li>In a further tie, the tied player with the most Lantern Cards remaining wins.</li>
	 * <li>In a further tie, the tied players enjoy their shared victory.</li>
	 * </ul>
	 * 
	 * @param game	The game to be checked.
	 * @return	The set of {@link Player} who are the winners of the game. Usually there should be only one winner, 
	 * but there could be more than one in case of a final tie game.
	 * @throws GameRuleViolationException	In case the game is not yet ended.
	 */
	Set<Player> getGameWinner(Game game) throws GameRuleViolationException;
}
