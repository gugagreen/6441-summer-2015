package ca.concordia.lanterns.dedication;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public interface DedicationBehavior {
	
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible);
	
}
