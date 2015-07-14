package ca.concordia.lanterns.dao;

import ca.concordia.lanterns.entities.Game;

/**
 * Data Access Object for the {@link Game entity}.
 */
public interface GameDao {
	
	/**
	 * Saves a {@link Game} object.
	 * @param resource	
	 * @param game
	 */
	void saveGame(String resource, Game game);
	
	Game loadGame(String resource);

}
