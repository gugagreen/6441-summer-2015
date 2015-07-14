package ca.concordia.lanterns.entities.enums;

import java.util.Arrays;

public enum DedicationType {
	// FIXME - add correct quantities
	FOUR_OF_A_KIND(new int[]{}, new int[]{}, new int[]{}), 
	THREE_PAIRS(new int[]{}, new int[]{}, new int[]{}), 
	SEVEN_UNIQUE(new int[]{10,9,9,8,8,7,7,6,5}, new int[]{10,9,8,8,7,7,6,5}, new int[]{10,9,8,7,6,5}), 
	GENERIC(new int[]{4,4,4}, new int[]{4,4,4}, new int[]{4,4,4});
	
	private final int[] valuesFour;
	private final int[] valuesThree;
	private final int[] valuesTwo;
	
	private DedicationType(int[] valuesFour, int[] valuesThree, int[] valuesTwo) {
		this.valuesFour = valuesFour;
		this.valuesThree = valuesThree;
		this.valuesTwo = valuesTwo;
	}

	public int[] getValuesFour() {
		return Arrays.copyOf(valuesFour, valuesFour.length);
	}

	public int[] getValuesThree() {
		return Arrays.copyOf(valuesThree, valuesThree.length);
	}

	public int[] getValuesTwo() {
		return Arrays.copyOf(valuesTwo, valuesTwo.length);
	}
	
	
	
	
}
