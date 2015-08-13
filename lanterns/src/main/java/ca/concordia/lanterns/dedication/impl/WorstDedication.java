package ca.concordia.lanterns.dedication.impl;

import java.util.ArrayList;

import ca.concordia.lanterns.exchange.impl.helper.DedicationConfirmed;
import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationBehavior;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This class performs the  dedications for greedy player.
 */
public class WorstDedication implements DedicationBehavior {


	@Override
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible) {
		
		// sort the game dedications
		DedicationType[] sortedGameDedications = DedicationTokenWrapper.sortDedications (game.getDedications());
		DedicationConfirmed[] currentPlayerDedicationConfirmed = getCurrentPlayerDedicationConfirmed(game, currentPlayer, sortedGameDedications);
		
		int nextPlayerIndex = game.getCurrentTurnPlayer() + 1;
		Player[] players = game.getPlayers();
		
		int i = 0 ;
		
		while(i != players.length - 1){
			
			// Ensures clockwise movement and appropriate indexing
			nextPlayerIndex = nextPlayerIndex % 4 ;
			
			for (int j = 0; j != sortedGameDedications.length; ++j){
				
				if (currentPlayerDedicationConfirmed[j] != null) {
					DedicationConfirmed guarantedDedication = DedicationConfirmed.getDedicationConfirmed(sortedGameDedications[j], game, players[i].getId());
					if (guarantedDedication != null){
						if (guarantedDedication.isDamageable()) {
							
							return;
						}
				}
					
				}
			}
			
			++nextPlayerIndex;
			++i;
		}
		
	}
	
	private DedicationConfirmed[] getCurrentPlayerDedicationConfirmed(Game game, Player player, DedicationType[] sortedGameDedications) {
		DedicationConfirmed[] currentPlayerDedicationConfirmed = new DedicationConfirmed[sortedGameDedications.length];
		
		for (int i = 0; i != sortedGameDedications.length; ++i) {
			currentPlayerDedicationConfirmed[i] = DedicationConfirmed.getDedicationConfirmed(sortedGameDedications[i], game, player.getId());
		}
		
		return currentPlayerDedicationConfirmed;
	}
	
	
}
