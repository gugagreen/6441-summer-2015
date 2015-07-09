package ca.concordia.lanterns.services;

import java.util.Set;

import ca.concordia.lanterns.entities.Game;

public interface SetupService {

	/**
	 * Creates a new game
	 * @param playerNames	The name of the players that will participate on the game. Should be between 2-4.
	 * @return	The game that was just created.
	 */
	Game createGame(Set<String> playerNames);
	
	
}
