package ca.concordia.lanterns.exception;

/**
 * This Exception represents a violation in one or multiple rules of the game.
 * @author parth
 * @version 1.0
 *
 */
public class GameRuleViolationException extends LanternsException {

	private static final long serialVersionUID = 4627159268292645417L;

	public GameRuleViolationException ( String message ) {
		super ( message ) ;
	}
}
