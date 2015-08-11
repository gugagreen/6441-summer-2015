package ca.concordia.lanterns.ai.impl;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.dedication.impl.RandomDedication;
import ca.concordia.lanterns.exchange.impl.RandomExchange;
import ca.concordia.lanterns.tileplacement.impl.RandomTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

/**
 * The random player always makes a random lake tile move. 
 * and will randomly choose to make random dedications or exchanges if capable.
 */
@XmlRootElement
public class RandomAI extends AI {
	public RandomAI(Game game, Player currentPlayer){
		super(AIType.RANDOM, game, currentPlayer);
		this.exchangeBehavior = new RandomExchange(); 
		this.dedicationBehavior = new RandomDedication();
		this.tilePlayBehavior = new RandomTile();
	}
}
