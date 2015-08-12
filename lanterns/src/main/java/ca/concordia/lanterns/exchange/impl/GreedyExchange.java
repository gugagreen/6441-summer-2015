package ca.concordia.lanterns.exchange.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationForecaster;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;

/**
 * Behavior that tries to get an exchange that will help to make a dedication.
 */
public class GreedyExchange implements ExchangeBehavior {
	
	private GameController controller = new GameController();

	@Override
	public void makeExchange(Game game, Player player) {
		// if there are enough tokens to make exchange
		if (player.getFavors() >= 2) {
			if (shouldTryExchange(player)) {
				LanternCardWrapper[] giveReceive = DedicationForecaster.getInstance().getNextDedication(player);
				if (giveReceive != null) {
					// if game board still have receive card available, exchange
					if (isDesiredCardAvailable(game, giveReceive[1])) {
						controller.exchangeLanternCard(game, player.getId(), giveReceive[0].getColour(), giveReceive[1].getColour());
					}
				}
			}
		}
	}
	
	private boolean isDesiredCardAvailable(Game game, LanternCardWrapper receive) {
		boolean available = false;
		for (LanternCardWrapper lantern : game.getCards()) {
			if (lantern.getColour().equals(receive.getColour())) {
				if (lantern.getQuantity() > 0) {
					available = true;
				}
				break;
			}
		}
		return available;
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
