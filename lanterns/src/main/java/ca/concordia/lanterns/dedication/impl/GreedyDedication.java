package ca.concordia.lanterns.dedication.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.dedication.DedicationBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class GreedyDedication implements DedicationBehavior {
	
	// FIXME - inject controller from super
	private GameController controller = new GameController();

	@Override
	public void makeDedication(Game game, Player player, boolean[] dedicationsPossible) {
		if (dedicationsPossible != null) {
			if (dedicationsPossible[0]) {
				fourOfKindDedication(game, player);
			} else if (dedicationsPossible[1]) {
				threePairDedication(game, player);
			} else if (dedicationsPossible[2]) {
				sevenUniqueDedication(game, player);
			}
		}
	}
	
	private void fourOfKindDedication(Game game, Player player){
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if (lanternCardWrapper.getQuantity() > 3){
				Colour[] colours = new Colour[]{lanternCardWrapper.getColour()};
				controller.makeDedication(game, player.getId(), DedicationType.FOUR_OF_A_KIND, colours);
				break;
			}
		}
	}
	
	private void threePairDedication(Game game, Player player){
		Colour[] colours = new Colour[3];
		int pairIndex = 0;
		
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if (lanternCardWrapper.getQuantity() > 1){
				colours[pairIndex] = lanternCardWrapper.getColour();
				if (pairIndex == 2) {
					break;
				}
				pairIndex++;
			}
		}
		
		controller.makeDedication(game, player.getId(), DedicationType.THREE_PAIRS, colours);
	}
	
	private void sevenUniqueDedication(Game game, Player player){
		controller.makeDedication(game, player.getId(), DedicationType.SEVEN_UNIQUE, Colour.values());
	}


}
