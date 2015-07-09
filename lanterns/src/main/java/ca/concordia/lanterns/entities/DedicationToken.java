package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.DedicationType;
/** Dedication Token entity.
 * 
 * @version 1.0
 * 
 */
public class DedicationToken {
	private int value;
	private DedicationType type;
	
	/**
	 * Dedication Token constructor.
	 * @param value should be in range <code>1..10</code> depending on game state and number of players
	 * @param type should be in range <code>1-3</code> Based on three possible Dedication token types
	 */
	public DedicationToken(int value, DedicationType type) {
		super();
		this.value = value;
		this.type = type;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + value;
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DedicationToken other = (DedicationToken) obj;
		if (type != other.type)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	/**
	 * This method returns the value of the dedication token to the constructor.
	 * @return The value of the dedication token.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * This method returns the type of dedication token to the constructor.
	 * @return The type of dedication.
	 */
	public DedicationType getType() {
		return type;
	}
	
}
