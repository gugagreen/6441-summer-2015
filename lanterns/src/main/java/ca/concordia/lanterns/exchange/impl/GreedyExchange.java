package ca.concordia.lanterns.exchange.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.dedication.DedicationForecaster;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;

public class GreedyExchange implements ExchangeBehavior {
	
	GameController controller = new GameController();

	@Override
	public void makeExchange(Game game, Player player) {
		// if there are enough tokens to make exchange
		if (player.getFavors() >= 2) {
			if (shouldTryExchange(player)) {
				LanternCardWrapper[] giveReceive = DedicationForecaster.getInstance().getNextDedication(player);
				if (giveReceive != null) {
					controller.exchangeLanternCard(game, player.getId(), giveReceive[0].getColour(), giveReceive[1].getColour());
				}
			}
		}
	}

	/**
	 * If dedication is already possible, no need to make exchange
	 * @param player
	 * @return	true if exchange is not currently possible
	 */
	private boolean shouldTryExchange(Player player) {
		boolean[] dedicationsPossible = DedicationForecaster.getInstance().dedicationPossible(player);
		boolean shouldExchange = true;
		
		for (boolean possible : dedicationsPossible) {
			if (possible) {
				shouldExchange = false;
				break;
			}
		}
		return shouldExchange;
	}
}
