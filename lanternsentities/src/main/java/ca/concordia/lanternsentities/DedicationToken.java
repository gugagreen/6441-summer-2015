package ca.concordia.lanternsentities;

import ca.concordia.lanternsentities.enums.DedicationType;

/** 
 * Dedication Token entity.
 * 
 * @version 1.0
 */
public class DedicationToken {
	private int value;
	private DedicationType type;
	
	/**
	 * @param tokenValue Value of the token should be in range <code>1..10</code> depending on game state and number of players
	 * @param tokenType Type of the token should be one of {@link DedicationType}.
	 */
	public void init(int tokenValue, DedicationType tokenType) {
		this.value = tokenValue;
		this.type = tokenType;
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
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		
		DedicationToken other = (DedicationToken) obj;
		if (type != other.type){
			return false;
		}
		if (value != other.value){
			return false;
		}
		return true;
	}

	public int getTokenValue() {
		return value;
	}

	public void setTokenValue(int tokenValue) {
		this.value = tokenValue;
	}

	public DedicationType getTokenType() {
		return type;
	}

	public void setTokenType(DedicationType tokenType) {
		this.type = tokenType;
	}
	
}
