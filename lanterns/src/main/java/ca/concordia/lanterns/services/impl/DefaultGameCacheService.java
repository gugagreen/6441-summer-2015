package ca.concordia.lanterns.services.impl;

import java.util.HashMap;
import java.util.Map;

import ca.concordia.lanterns.dao.GameDao;
import ca.concordia.lanterns.dao.impl.FileGameDao;
import ca.concordia.lanterns.exception.LanternsException;
import ca.concordia.lanterns.services.GameCacheService;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.Game;

/**
 * This is an implementation of {@link GameCacheService}.
 *
 */
public class DefaultGameCacheService implements GameCacheService {

	/** Pool that holds {@link Game} objects. */
	private static final Map<String, Game> cache = new HashMap<String, Game>();
	private static final GameDao dao = new FileGameDao();
	
	private static class SingletonHolder {
		static final DefaultGameCacheService INSTANCE = new DefaultGameCacheService();
	}
	
	public static DefaultGameCacheService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@Override
	public Game getAvailableGame() {
		for (Game game: cache.values()) {
			// TODO - only return games that are not started
			// if (game.isStarted)
		}
		return null;
	}
	
	@Override
	public Game getGame(final String id) {
		return cache.get(id);
	}
	
	@Override
	public void addGame(final Game game) {
		if ((game != null) && (game.getId() != null) && (!game.getId().trim().isEmpty())) {
			synchronized (cache) {
				// if game does not exist in pool, create it
				if (getGame(game.getId()) == null) {
					cache.put(game.getId(), game);
				} else {
					throw new LanternsException("Cannot add. Game already exists!");
				}
			}
		} else {
			throw new IllegalArgumentException("Game cannot be null and must have an valid id.");
		}
	}

	@Override
	public Game loadGame(final String resource) {
		// load game from file
		Game game = dao.loadGame(resource);
		// if game is not in pool yet, add it.
		Game poolGame = getGame(game.getId());
		if (poolGame == null) {
			synchronized (cache) {
				cache.put(game.getId(), game);
			}
		}
		return game;
	}

	@Override
	public void saveGame(final String resource, final Game game) {
		dao.saveGame(resource, game);
		// if game is not in pool yet, add it.
		Game poolGame = getGame(game.getId());
		if (poolGame == null) {
			synchronized (cache) {
				cache.put(game.getId(), game);
			}
		}
	}	
	
}
