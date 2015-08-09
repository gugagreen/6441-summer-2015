package ca.concordia.lanterns.dedication.impl;

import java.util.Arrays;
import java.util.Random;

import ca.concordia.lanterns.dedication.DedicationBehavior;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class RandomDedication implements DedicationBehavior {

	@Override
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible) throws GameRuleViolationException {
		if (allowDedication()) {
			// Select one dedication type and check to make sure it is valid
			int typeIndex = checkedDedicationIndex(dedicationsPossible);
			DedicationType type = DedicationType.values()[typeIndex];
			// get the colours to make that dedication
			Colour[] colours = getDedicationLanterns(type, currentPlayer);

			ActivePlayerService.getInstance().makeDedication(game, currentPlayer.getId(), type, colours);
		}
	}

	private static boolean allowDedication() {
		Random rand = new Random();
		boolean makeDedication = rand.nextBoolean();

		return makeDedication;
	}

	private int randDedicationIndex() {
		Random rand = new Random();
		int randDedicationIndex = rand.nextInt(3);

		return randDedicationIndex;
	}

	private int checkedDedicationIndex(boolean[] dedicationsPossible) {
		boolean checkingRandIndex = true;
		int randIndex = randDedicationIndex();

		while (checkingRandIndex) {

			if (dedicationsPossible[randIndex]) {
				checkingRandIndex = false;
			} else {
				randIndex = randDedicationIndex();
			}
		}
		return randIndex;
	}

	private Colour[] getDedicationLanterns(DedicationType type, Player currentPlayer) {
		int requiredColours = getRequiredColors(type);
		Colour[] colours = new Colour[requiredColours];

		if (type == DedicationType.FOUR_OF_A_KIND) {
			boolean searchingWrapper = true;
			int randCardIndex = getRandomColour();

			while (searchingWrapper) {

				if (currentPlayer.getCards()[randCardIndex].getQuantity() > 3) {
					colours[0] = Colour.values()[randCardIndex];
					searchingWrapper = false;
					return colours;

				} else {
					randCardIndex = getRandomColour();
				}
			}
		}

		if (type == DedicationType.THREE_PAIRS) {

			int randCardIndex = getRandomColour();
			int validIndex = 0;

			while (validIndex < 3) {

				if (currentPlayer.getCards()[randCardIndex].getQuantity() > 1
						&& !Arrays.asList(colours).contains(Colour.values()[randCardIndex])) {
					colours[validIndex] = Colour.values()[randCardIndex];
					validIndex++;
				} else {
					randCardIndex = getRandomColour();
				}
			}
		}

		if (type == DedicationType.SEVEN_UNIQUE) {
			for (int i = 0; i < colours.length; i++) {
				int giveCardIndex = i;
				colours[i] = Colour.values()[giveCardIndex];
			}
		}

		return colours;

	}

	private int getRandomColour() {
		Random rand = new Random();
		int randColourIndex = rand.nextInt(7);

		return randColourIndex;
	}

	public int getRequiredColors(DedicationType dedicationType) {
		DedicationCost cost = ActivePlayerService.getInstance().getDedicationCost(dedicationType);
		;
		return cost.getRequiredColors();
	}

}
