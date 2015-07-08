package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.TokenType;

public class DedicationToken {
	private int value;
	private TokenType type;
	
	public DedicationToken(int value, TokenType type) {
		super();
		this.value = value;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + value;
		return result;
	}

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

	public int getValue() {
		return value;
	}

	public TokenType getType() {
		return type;
	}
	
}
