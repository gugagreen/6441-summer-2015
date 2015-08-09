package ca.concordia.lanterns.tileplacement.impl;

import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public class GreedyTile implements TilePlayBehavior {

	@Override
	public void placeTile(Game game, Player currentPlayer) {
		// FIXME - implement
		// algorithm
		// 1) check if dedication is already possible (get random)
		// 2) check if there is a dedication close (get missing card)
		// 3) if none of those, get random
	}
}
