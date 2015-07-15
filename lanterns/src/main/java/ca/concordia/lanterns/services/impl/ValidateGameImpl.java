package ca.concordia.lanterns.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanterns.entities.DedicationTokenWrapper;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCardWrapper;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.entities.enums.PlayerID;
import ca.concordia.lanterns.services.ValidateGame;

public class ValidateGameImpl implements ValidateGame {

	public void validatePlayerCount ( Game game ) {
		Player[] players = game.getPlayers() ;
		if ( players.length >= 2 && players.length <= 4) {
			Set<PlayerID> uniqueID = new HashSet<PlayerID> () ;
			for ( int i = 0; i != players.length; ++i  ) {
				if ( !( uniqueID.contains(players[i].getID()) )){
					uniqueID.add(players[i].getID() ) ;
				} else {
					throw new IllegalArgumentException ( "The players have duplicate ID" ) ;
				}
			}
		} else {
			throw new IllegalArgumentException ( "Invalid number of players" ) ;
		}
	}
	
	public void validateLanternCards ( Game game ) {
		
		Player[] players = game.getPlayers();
		LanternCardWrapper[] cards = game.getCards() ;
			for ( int i = 0; i != cards.length ; ++i ) {
				int sum = 0 ;
				for ( int j = 0; j != players.length ; ++j ) {
					sum = sum + players[j].getCards()[i].getStack().size() ;
				}
				sum = sum + cards[i].getStack().size() ;
				if ( ( players.length == 4 && sum == 8 ) ||
					 (	players.length == 3 && sum == 7 ) || 
					 ( players.length == 2 && sum == 5 ) ) 
					continue ;
				else {
					throw new IllegalArgumentException ( "Insufficient Lanter Cards for the color: " 
				+ Colour.values()[i].toString()) ;
				}
			}		
	}
	
	@Override
	public void validateStartPlayerMarker ( final Player[] player, final PlayerID startPlayerMarker ) {
		for ( int i = 0 ; i != player.length; ++i ) {
			if ( player[i].getID() == startPlayerMarker ) {
				return ;
			}
		}
		throw new IllegalArgumentException ( "Incorrect Start Player Marker" ) ;
	}
	
	@Override
	public void validateFavorToken ( final Game game ) {
		Player[] player = game.getPlayers() ;
		int sum = 0 ;
		for ( int i = 0; i != player.length; ++i ) {
			sum = sum + player[i].getFavors() ;
		}
		
		sum = sum + game.getFavors() ;
		
		if ( sum != 20 ) {
			throw new IllegalArgumentException ( "The quantity of favor tokens is compromised") ;
		}
	}
	
	@Override
	public void validateLakeTileStack ( final Player[] players, final Lake lake, 
			final Stack<LakeTile> lakeTile, final PlayerID currentTurnPlayer ) {
		int sum = 0 ;
		sum = sum + lake.getTiles().size() + lakeTile.size() ;
		for ( int i = 0; i != players.length; ++i ) {
			sum = sum + players[i].getTiles().size() ;
		}
		if ( ( players.length == 4 && sum != 33 ) || 
				( players.length == 3 && sum != 28 ) ||
				( players.length == 2 && sum != 23) ) {
					throw new IllegalArgumentException ( "The quantity of Lake Tiles in the game have been Compromised" );
		}
		
		for ( int i = 0 ; i != players.length; ++i ) {
			if ( players[i].getTiles().size() != 3 && ! lake.getTiles().isEmpty() 
					&& players[i].getID() != currentTurnPlayer ) {
				throw new IllegalArgumentException ( "Player: " + players[i].getName() + "have insufficient Lake Tiles" ) ;
			}
		}
		
	}
	
	@Override
	public void validateDedicationToken ( final DedicationTokenWrapper[] dedications, final Player[] players ) {
		DedicationType[] type = DedicationType.values() ;
		
		//TODO
		for ( int i = 0; i != type.length ; ++i ) {
			int[] values ;
			if ( players.length == 4 ) {
				values = type[i].getValuesFour() ;
			} else if ( players.length == 3 ) {
				values = type[i].getValuesThree() ;
			} else if ( players.length == 2 ) {
				values = type[i].getValuesTwo() ;
			}
		}
	}

	
}
