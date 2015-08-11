package ca.concordia.lanterns.ai.impl;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.dedication.impl.PlayerDedication;
import ca.concordia.lanterns.exchange.impl.PlayerExchange;
import ca.concordia.lanterns.tileplacement.impl.PlayerTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

/**
 * The human player gets to read the current state of the game and
 *  input their choice of moves into the game using an input device.
 */
@XmlRootElement
public class HumanPlayer extends AI {

	public HumanPlayer(Game game, Player currentPlayer) {
		super(AIType.HUMAN, game, currentPlayer);
		this.exchangeBehavior = new PlayerExchange();
		this.dedicationBehavior = new PlayerDedication();
		this.tilePlayBehavior = new PlayerTile();
	}

}
