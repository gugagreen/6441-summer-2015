package ca.concordia.lanterns.services;

import java.util.Stack;

import ca.concordia.lanterns.entities.DedicationTokenWrapper;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.PlayerID;

public interface ValidateGame {

	void validatePlayerCount ( Game game ) ;
	
	void validateLanternCards ( Game game ) ;
	
	void validateDedicationToken ( final DedicationTokenWrapper[] dedications, final Player[] players ) ;
	
	void validateStartPlayerMarker ( final Player[] player, final PlayerID startPlayerMarker ) ;
	
	void validateFavorToken ( final Game game ) ;
	
	void validateLakeTileStack ( final Player[] players, final Lake lake, 
			final Stack<LakeTile> lakeTile, final PlayerID currentTurnPlayer ) ;

	
}
