package ca.concordia.lanterns.exception;


/**
 * This Exception represents a violation in the handling of lantern cards.
 *
 */
public class LanternsException extends RuntimeException {

	private static final long serialVersionUID = -2186590170476214831L;

	public LanternsException(String message, Throwable cause) {
		super(message, cause);
	}

	public LanternsException(String message) {
		super(message);
	}

}
