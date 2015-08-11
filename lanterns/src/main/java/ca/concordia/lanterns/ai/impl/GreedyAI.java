package ca.concordia.lanterns.ai.impl;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.dedication.impl.GreedyDedication;
import ca.concordia.lanterns.exchange.impl.GreedyExchange;
import ca.concordia.lanterns.tileplacement.impl.GreedyTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

/**
 * The greedy player always makes the move which will bring the best immediate return 
 * (i.e. always try to place a Lake Tile which will help arrange a dedication, 
 * and always make a dedication as soon as possible)
 */
@XmlRootElement
public class GreedyAI extends AI {
	
	public GreedyAI(Game game, Player currentPlayer){
		super(AIType.GREEDY, game, currentPlayer);
		this.exchangeBehavior = new GreedyExchange();
		this.dedicationBehavior = new GreedyDedication();
		this.tilePlayBehavior = new GreedyTile();
		
	}
}
