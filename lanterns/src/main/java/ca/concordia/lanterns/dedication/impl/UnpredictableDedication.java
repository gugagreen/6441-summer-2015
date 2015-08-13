package ca.concordia.lanterns.dedication.impl;

import java.util.Random;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationBehavior;

/**
 * This class performs the  dedications for Unpredictable player.
 */
public class UnpredictableDedication implements DedicationBehavior {

	DedicationBehavior currentBehavior;
	/**
     * Perform dedications for Unpredictable player according to current Behavior.
     *
     * @param game The {@link Game} object.
     * @param currentPlayer The {@link Player} object.
     * @param dedicationsPossible The Boolean array of all possible dedications. 
     */
	@Override
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible) {
		switch (selectBehavior()) {
		case 0:
			this.currentBehavior = new RandomDedication();
			break;

		case 1:
			this.currentBehavior = new GreedyDedication();
			break;
			
		case 2:
			this.currentBehavior = new WorstDedication();
			break;
		}
		
		
		this.currentBehavior.makeDedication(game, currentPlayer, dedicationsPossible);
	}

	private int selectBehavior(){
		Random rand = new Random();
		int possibleBehaviorIndex = rand.nextInt(3);
		
		return possibleBehaviorIndex;
	}
}
