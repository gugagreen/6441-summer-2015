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
		//TODO change nextInt to 3 once worst dedication is implemented
		int possibleBehaviorIndex = rand.nextInt(2);
		
		return possibleBehaviorIndex;
	}
}
