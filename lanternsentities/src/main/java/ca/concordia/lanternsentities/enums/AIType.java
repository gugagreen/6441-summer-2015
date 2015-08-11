package ca.concordia.lanternsentities.enums;

public enum AIType {

	HUMAN, RANDOM, GREEDY, UNFRIENDLY, UNPREDICTABLE;
	
	public static AIType getAIType(String value) {
		AIType select = null;
		for (AIType ai : values()) {
			if (ai.name().equals(value)) {
				select = ai;
				break;
			}
		}
		return select;
	}
}
