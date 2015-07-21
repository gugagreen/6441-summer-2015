package ca.concordia.lanterns.services;

import java.util.Stack;

import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Lake;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.PlayerID;

public interface ValidateGame {

	void validatePlayerCount ( Game game ) ;
	
	void validateLanternCards ( Game game ) ;
	
	void validateDedicationToken ( final DedicationTokenWrapper[] dedications, final Player[] players ) ;
	
	void validateStartPlayerMarker ( final Player[] player, final PlayerID startPlayerMarker ) ;
	
	void validateFavorToken ( final Game game ) ;
	
	void validateLakeTileStack ( final Player[] players, final Lake lake, 
			final Stack<LakeTile> lakeTile, final PlayerID currentTurnPlayer ) ;

	
}
