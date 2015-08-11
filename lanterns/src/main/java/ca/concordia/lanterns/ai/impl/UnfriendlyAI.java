package ca.concordia.lanterns.ai.impl;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.dedication.impl.WorstDedication;
import ca.concordia.lanterns.exchange.impl.WorstExchange;
import ca.concordia.lanterns.tileplacement.impl.WorstTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

@XmlRootElement
public class UnfriendlyAI extends AI {
	public UnfriendlyAI(Game game, Player currentPlayer){
		super(AIType.UNFRIENDLY, game, currentPlayer);
		// FIXME - behaviors not implemented
		System.err.println("ERROR - behaviors not implemented yet!");
		System.exit(0);
		this.exchangeBehavior = new WorstExchange(); 
		this.dedicationBehavior = new WorstDedication();
		this.tilePlayBehavior = new WorstTile();
	}
}
