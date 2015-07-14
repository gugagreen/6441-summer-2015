package ca.concordia.lanterns.entities;

import ca.concordia.lanterns.entities.enums.DedicationType;

/** Dedication Token entity.
 * 
 * @version 1.0
 */
public class DedicationToken {
	private int tokenValue;
	private DedicationType tokenType;
	
	/**
	 * @param tokenValue should be in range <code>1..10</code> depending on game state and number of players
	 * @param tokenType should be in range <code>1-3</code> Based on three possible Dedication token types
	 */
	public void init(int tokenValue, DedicationType tokenType) {
		this.tokenValue = tokenValue;
		this.tokenType = tokenType;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokenType == null) ? 0 : tokenType.hashCode());
		result = prime * result + tokenValue;
		return result;
	}

	/**
	 * 
	 */
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
		if (tokenType != other.tokenType){
			return false;
		}
		if (tokenValue != other.tokenValue){
			return false;
		}
		return true;
	}

	public int getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(int tokenValue) {
		this.tokenValue = tokenValue;
	}

	public DedicationType getTokenType() {
		return tokenType;
	}

	public void setTokenType(DedicationType tokenType) {
		this.tokenType = tokenType;
	}
	
}
