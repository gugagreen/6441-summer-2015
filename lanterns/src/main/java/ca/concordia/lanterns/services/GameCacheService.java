package ca.concordia.lanterns.services;

import ca.concordia.lanterns.exception.LanternsException;
import ca.concordia.lanternsentities.Game;

public interface GameCacheService {

	/**
	 * Get one available {@link Game} (not started and with space for new players) from the pool.
	 * @return	The {@link Game}.
	 */
	Game getAvailableGame();
	
	/**
	 * Get one {@link Game} from the pool given its id.
	 * @param id	The {@link Game#getId()}.
	 * @return	The game, if existent in the pool, or null if not.
	 */
	Game getGame(final String id);
	
	/**
	 * Add a new {@link Game} to the pool.
	 * @param game game object
	 * @throws IllegalArgumentException	if game is invalid.
	 * @throws LanternsException	if game already exists in the pool.
	 */
	void addGame(final Game game);
	
	/**
	 * Load {@link Game} from repository (i.e. file or db) and put it in the pool.
	 * @param resource	The resource that identifies the game in the repository.
	 * @return	The loaded {@link Game}.
	 */
	Game loadGame(final String resource);
	
	/**
	 * Save a {@link Game} into the repository (i.e. file or db), and add it to the pool.
	 * @param resource	The resource where to save the game.
	 * @param game	The {@link Game} to be saved.
	 */
	void saveGame(final String resource, final Game game);

	/**
	 * Load {@link Game} from repository (i.e. file or db) validate the game and put it in the pool.
	 * @param resource	The resource that identifies the game in the repository.
	 * @return	The loaded {@link Game}.
	 */
	Game loadValidatedGame(String resource);
}
