package ca.concordia.lanterns.exchange.impl;

import java.util.Arrays;

import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;
import ca.concordia.lanternsentities.enums.DedicationType;

public class WorstExchange implements ExchangeBehavior{

	@Override
	public void makeExchange(Game game, Player currentPlayer) {
		
		if (currentPlayer.getFavors() < 2) {
			return;
		}
		
		DedicationType[] sortedGameDedications = sortDedications (game.getDedications());
		
		int nextPlayerIndex = game.getCurrentTurnPlayer() + 1;
		Player[] players = game.getPlayers();
		
		int i = 0 ;
		
		while(i != players.length - 1){
			nextPlayerIndex = nextPlayerIndex % 4 ;
			
			for (DedicationType dedicationType: sortedGameDedications){
				DedicationThreat threat = DedicationThreat.getThreat(dedicationType, players[nextPlayerIndex], game);
				if (threat != null){
					boolean isStopped = threat.stopThreat(players[nextPlayerIndex].getId());
					if (isStopped) {
						return;
					}
				}
			}
			
			++nextPlayerIndex;
			++i;
		}
		
		//TODO Some form of default exchange
	}
	
	private DedicationType[] sortDedications(DedicationTokenWrapper[] gameDedications){
		int[] sortedGameDedications = new int[gameDedications.length - 1];
		DedicationType[] sortedType = new DedicationType[gameDedications.length - 1];
		
		int key;
		int j;
		sortedGameDedications[0] = gameDedications[0].getStack().peek().getTokenValue();
		sortedType[0] = gameDedications[0].getStack().peek().getTokenType();
		
		for (int i = 1; i != gameDedications.length - 1; ++i){
			DedicationToken top = gameDedications[i].getStack().peek();
				key = top.getTokenValue();
				
				j = i - 1;
				while (j != -1 && sortedGameDedications[j] < key){
					sortedGameDedications[j+1] = sortedGameDedications[j];
					sortedType[j+1] = sortedType[j];
					--j ;
				}
				sortedGameDedications[j+1] = key;
				sortedType[j+1] = top.getTokenType();
		}
		
		
		return sortedType;	
	}
}
