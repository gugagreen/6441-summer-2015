package ca.concordia.lanterns.services.enums;

import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Represent the cost of different types of dedication. The cost is in terms of
 * Lantern cards of different colors.
 *
 * @author parth
 */
public enum DedicationCost {
    FOUR_OF_A_KIND(1, 4), THREE_PAIRS(3, 2), SEVEN_UNIQUE(7, 1);

    private int requiredColors;
    private int requiredCardPerColor;

    private DedicationCost(int requiredColors, int requiredCardPerColor) {
        this.requiredColors = requiredColors;
        this.requiredCardPerColor = requiredCardPerColor;
    }

    public int getRequiredColors() {
        return this.requiredColors;
    }

    public int getRequiredCardPerColor() {
        return this.requiredCardPerColor;
    }
    
    public static DedicationCost getDedicationCost(DedicationType dedicationType) throws IllegalArgumentException {
    	if (dedicationType == DedicationType.FOUR_OF_A_KIND) {
    		return DedicationCost.FOUR_OF_A_KIND;
    	} else if (dedicationType == DedicationType.THREE_PAIRS){
    		return DedicationCost.THREE_PAIRS;
    	} else if (dedicationType == DedicationType.SEVEN_UNIQUE) {
    		return DedicationCost.SEVEN_UNIQUE;
    	} else {
    		throw new IllegalArgumentException ("Invalid DedicationType Corresponding to DedicationCost");
    	}
    }
}
