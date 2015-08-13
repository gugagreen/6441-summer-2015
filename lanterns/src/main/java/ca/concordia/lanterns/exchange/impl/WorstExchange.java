package ca.concordia.lanterns.exchange.impl;

import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Provides services for making an exchange that is unfavourable to one or more {@link Player} of the {@link Game}.
 *  
 * @author par_pat
 *
 */
public class WorstExchange implements ExchangeBehavior{

	/**
	 * Makes an exchange on behalf of currentPlayer such that it is unfavourable to one or more players of the game.
	 * The rules of the exchange are as follows:
	 * <ul>
	 * <li><p>Starting with the player with next turn and moving clockwise attempt to stop atleast one player from making a dedication by emptying the supply of lantern card they would require to make the dedication.</p></li>
	 * <li>The attempt to stop dedications is made in decreasing order of the dedication value earned if the dedication could not be stopped</li>
	 * <li>If it is impossible to stop any player from making a dedication then decrease the supply of the least available lantern card.</li>
	 * </ul>
	 */
	@Override
	public void makeExchange(Game game, Player currentPlayer) {

		if (currentPlayer.getFavors() < 2) {
			return;
		}

		// sort the game dedications
		DedicationType[] sortedGameDedications = DedicationTokenWrapper.sortDedications (game.getDedications());
		
		int nextPlayerIndex = game.getCurrentTurnPlayer() + 1;
		Player[] players = game.getPlayers();
		
		int i = 0 ;
		
		while(i != players.length - 1){
			
			// Ensures clockwise movement and appropriate indexing
			nextPlayerIndex = nextPlayerIndex % 4 ;
			
			for (DedicationType dedicationType: sortedGameDedications){
				DedicationThreat threat = DedicationThreat.getThreat(dedicationType, players[nextPlayerIndex], game);
				if (threat != null){
					boolean isStopped = threat.stopThreat(players[nextPlayerIndex].getId());
					
					// We can only stop one dedication by making one exchange.
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
}
