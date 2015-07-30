package ca.concordia.lanterns.dao;

import ca.concordia.lanternsentities.Game;

/**
 * Data Access Object for the {@link Game entity}.
 */
public interface GameDao {
	
	/**
	 * Saves a {@link Game} object into a place (e.g. file, db) specified by the resource.
	 * @param resource save game name
	 * @param game game object
	 */
	void saveGame(String resource, Game game);
	
	/**
	 * Loads a {@link Game} object from a given resource.
	 * @param resource	Resource that specifies where the {@link Game} is stored.
	 * @return	The {@link Game}.
	 */
	Game loadGame(String resource);

}
