package ca.concordia.lanterns.ai.impl;

import ca.concordia.lanterns.dedication.impl.UnpredictableDedication;
import ca.concordia.lanterns.exchange.impl.UnpredictableExchange;
import ca.concordia.lanterns.tileplacement.impl.UnpredictableTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

/**
 * The Unpredictable player makes a random  , greedy and unfriendly lake tile move. 
 * and will  choose to make unpredictable dedications or exchanges if capable.
 */

public class UnpredictableAI extends AI{

	public UnpredictableAI(Game game, Player currentPlayer) {
		super(AIType.UNPREDICTABLE, game, currentPlayer);
		this.exchangeBehavior = new UnpredictableExchange();
		this.dedicationBehavior = new UnpredictableDedication();
		this.tilePlayBehavior = new UnpredictableTile();
	}
	
	

}
