package ca.concordia.lanterns.dedication;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Many possible dedication behaviors are possible, whether AI based or human controlled.
 * Dedication behavior is used only when a dedication is possible.
 */
public interface DedicationBehavior {
	
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible);
	
}
