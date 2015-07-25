package ca.concordia.lanterns.exception;

public class LanternsException extends RuntimeException {

	private static final long serialVersionUID = -2186590170476214831L;

	public LanternsException(String message, Throwable cause) {
		super(message, cause);
	}

	public LanternsException(String message) {
		super(message);
	}

}
