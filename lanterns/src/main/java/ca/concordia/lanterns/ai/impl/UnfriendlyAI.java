package ca.concordia.lanterns.ai.impl;

import ca.concordia.lanterns.ai.AI;
import ca.concordia.lanterns.dedication.impl.WorstDedication;
import ca.concordia.lanterns.exchange.impl.WorstExchange;
import ca.concordia.lanterns.tileplacement.impl.WorstTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;


public class UnfriendlyAI extends AI {
	public UnfriendlyAI(Game game, Player currentPlayer){
		super(game, currentPlayer);
		this.exchangeBehavior = new WorstExchange(); 
		this.dedicationBehavior = new WorstDedication();
		this.tilePlayBehavior = new WorstTile();
	}
}
