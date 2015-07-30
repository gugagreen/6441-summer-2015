package ca.concordia.lanternsentities.enums;

public enum Colour {
	ORANGE('O'), GREEN('G'), PURPLE('P'), WHITE('W'), BLUE('B'), RED('R'), BLACK('K');
	
	public final char key;
	
	private Colour(char key) {
		this.key = key;
	}

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
