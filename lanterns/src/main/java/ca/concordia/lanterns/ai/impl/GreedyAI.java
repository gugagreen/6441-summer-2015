package ca.concordia.lanterns.ai.impl;

import ca.concordia.lanterns.ai.AI;
import ca.concordia.lanterns.dedication.impl.BestDedication;
import ca.concordia.lanterns.exchange.impl.BestExchange;
import ca.concordia.lanterns.tileplacement.impl.BestTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public class GreedyAI extends AI {
	
	public GreedyAI(Game game, Player currentPlayer){
		super(game, currentPlayer);
		this.exchangeBehavior = new BestExchange();
		this.dedicationBehavior = new BestDedication();
		this.tilePlayBehavior = new BestTile();
		
	}
}
