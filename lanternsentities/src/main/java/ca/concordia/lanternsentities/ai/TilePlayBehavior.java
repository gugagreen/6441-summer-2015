package ca.concordia.lanternsentities.ai;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Many Lake Tile Play behaviors are possible, whether AI based or human controlled.
 * Every turn each player, AI or human must play a lake tile.
 */
public interface TilePlayBehavior {

	public void placeTile(Game game, Player currentPlayer); 
}
