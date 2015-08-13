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
     * @param {@link Player} object.
     * @param {@link Game} object.
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
		//TODO change nextInt to 3 once worst dedication is implemented
		int possibleBehaviorIndex = rand.nextInt(2);
		
		return possibleBehaviorIndex;
	}
}
