package ca.concordia.lanterns.ai.impl;

import ca.concordia.lanterns.ai.AI;
import ca.concordia.lanterns.dedication.impl.PlayerDedication;
import ca.concordia.lanterns.exchange.impl.PlayerExchange;
import ca.concordia.lanterns.tileplacement.impl.PlayerTile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public class HumanPlayer extends AI {

	public HumanPlayer(Game game, Player currentPlayer) {
		super(game, currentPlayer);
		this.exchangeBehavior = new PlayerExchange();
		this.dedicationBehavior = new PlayerDedication();
		this.tilePlayBehavior = new PlayerTile();
	}

}
