package ca.concordia.lanterns.exchange;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Many possible Lantern Exchange behaviors are possible, whether AI based or human controlled.
 * Dedication behavior is used only when an exchange is possible.
 */
public interface ExchangeBehavior {

	public void makeExchange(Game game, Player currentPlayer);
	
}
