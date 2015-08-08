package ca.concordia.lanterns.ai.impl;

import ca.concordia.lanterns.ai.AI;
import ca.concordia.lanterns.dedication.impl.RandomDedication;
import ca.concordia.lanterns.exchange.impl.RandomExchange;
import ca.concordia.lanterns.tileplacement.impl.RandomTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public class RandomAI extends AI {
	public RandomAI(Game game, Player currentPlayer){
		super(game, currentPlayer);
		this.exchangeBehavior = new RandomExchange(); 
		this.dedicationBehavior = new RandomDedication();
		this.tilePlayBehavior = new RandomTile();
	}
}