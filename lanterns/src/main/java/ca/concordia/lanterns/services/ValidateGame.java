package ca.concordia.lanterns.services;

import java.util.List;
import java.util.Stack;

import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;

public interface ValidateGame {
	
	int MIN_PLAYERS = 2;
	int MAX_PLAYERS = 2;
	
	// FIXME - there is no javadoc for any method here

	void validatePlayerCount(final Game game) ;
	
	void validateLanternCards(final Game game) ;
	
	void validateDedicationToken(final DedicationTokenWrapper[] dedications, final Player[] players) ;
	
	void validateStartPlayerMarker(final Player[] player, final int startPlayerMarker) ;
	
	void validateFavorToken(final Game game) ;
	
	void validateLakeTileStack(final Player[] players, final List<LakeTile> lake, 
			final Stack<LakeTile> lakeTile, final int currentTurnPlayer) ;

	
}
