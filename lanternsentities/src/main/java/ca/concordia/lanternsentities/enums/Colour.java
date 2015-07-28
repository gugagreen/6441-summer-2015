package ca.concordia.lanternsentities.enums;

public enum Colour {
	ORANGE, GREEN, PURPLE, WHITE, BLUE, RED, BLACK;
	
	/**
	 * Get a {@link Colour} for its given name (case insensitive).
	 * @param name	the name of the {@link Colour} to look for
	 * @return	the {@link Colour} or null if there is no {@link Colour} with the given name.
	 */
	public static Colour getColourByName(String name) {
		Colour response = null;
		if ((name != null) && (name.trim().length() > 0)) {
			for (Colour colour : Colour.values()) {
				if (colour.name().equals(name.toUpperCase())) {
					response = colour;
				}
			}
		}
		return response;
	}
}
