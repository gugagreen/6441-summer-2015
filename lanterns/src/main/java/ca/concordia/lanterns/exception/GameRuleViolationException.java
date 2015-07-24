package ca.concordia.lanterns.exception;

/**
 * This Exception represents a violation in one or multiple rules of the game.
 * @author parth
 * @version 1.0
 *
 */
public class GameRuleViolationException extends Exception {

	public GameRuleViolationException ( String message ) {
		super ( message ) ;
	}
}
