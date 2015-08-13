package ca.concordia.lanterns.exchange.impl;

import java.util.Random;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;
/**
 * Provides services for making an exchange lantern cards for unpredictable player.
  */
public class UnpredictableExchange implements ExchangeBehavior{

	ExchangeBehavior currentBehavior;
	/**
     * Make exchange of cards for Unpredictable player if possible.
     *
     * @param currentPlayer {@link Player} object.
     * @param game {@link Game} object.
     */
	@Override
	public void makeExchange(Game game, Player currentPlayer) {
		switch (selectBehavior()) {
		case 0:
			this.currentBehavior = new RandomExchange();
			break;

		case 1:
			this.currentBehavior = new GreedyExchange();
			break;
			
		case 2:
			this.currentBehavior = new WorstExchange();
			break;
		}
		
		
		this.currentBehavior.makeExchange(game, currentPlayer);;
	}

	private int selectBehavior(){
		Random rand = new Random();
		int possibleBehaviorIndex = rand.nextInt(3);
		
		return possibleBehaviorIndex;
	}
}
