package ca.concordia.lanterns.dedication.impl;

import java.util.Arrays;
import java.util.Random;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This class performs the  dedications for Random player.
 */

public class RandomDedication implements DedicationBehavior {
	
	/**
     * Perform dedications for Random player according to possible dedication type.
     *
     * @param The {@link Game} object.
      * @param The {@link Player} object.
       * @param The Boolean array of all possible dedications. 
     */
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
	/**
	 * Randomly determines if the random AI will make a dedication or not this turn.
	 * Only takes place if a dedication is possible.
	 * @return a randomly determined boolean value. 
	 */
	private static boolean allowDedication() {
		Random rand = new Random();
		boolean makeDedication = rand.nextBoolean();

		return makeDedication;
	}
	
	/**
	 * Selects a random dedication index to play.
	 * @return A value between 0-2 that covers all possible dedications.
	 */
	private int randDedicationIndex() {
		Random rand = new Random();
		int randDedicationIndex = rand.nextInt(3);

		return randDedicationIndex;
	}
	
	/**
	 * Checks to make sure that the randomly chosen dedication is possible.
	 * If not, it finds another dedication until one is possible. 
	 * @param dedicationsPossible Boolean value that tells if a dedication can be made this turn.
	 * @return A random dedication index value that has been verified to be possible for this player.
	 */
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
	
	/**
	 * Based on which dedication was chosen randomly, this gets the appropriate lantern cards to make the dedication.
	 * @param type Takes in the dedication type chose.
	 * @param currentPlayer Which player is currently making the dedication.
	 * @return The Colour list of the lantern cards required to make the dedication.
	 */
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
	
	/**
	 * Gets a random colour lantern card for the dedications in which that is possible.
	 * @return a random colour index value (0-6).
	 */
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
