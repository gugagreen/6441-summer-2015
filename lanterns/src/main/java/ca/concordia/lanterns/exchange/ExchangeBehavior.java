package ca.concordia.lanterns.exchange;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public interface ExchangeBehavior {

	public void makeExchange(Game game, Player currentPlayer);
	
}
