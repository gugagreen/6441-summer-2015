package ca.concordia.lanterns.exchange.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Provides services for making an exchange that is unfavourable to one or more {@link Player} of the {@link Game}.
 *  
 * @author par_pat
 *
 */
public class WorstExchange implements ExchangeBehavior{

	private GameController controller = new GameController();

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
					boolean isStopped = stopThreat(threat,players[nextPlayerIndex].getId());
					
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
	
	/**
	 * Tries to stop this threat
	 * @param threat - the {@link DedicationThreat} that we are trying to stop
	 * @param playerID - id of the player who wish to stop this threat
	 * @return true - If the threat is stopped(i.e a successful and meaningful exchange), false - otherwise (Not enough cards with player or threat is already realised)
	 */
	private boolean stopThreat(DedicationThreat threat, int playerID) {
		Player player = threat.getGame().getPlayer(playerID);
		
		if (player == null){
			throw new IllegalArgumentException("The game do not have a player with ID: " + playerID);
		}
		
		if (player.getFavors() < 2) {
			return false;
		} 
		
		List<Colour> colors = Arrays.asList(Colour.values());
		LanternCardWrapper absentColourCard = threat.getGame().getCards()[colors.indexOf(threat.getAbsentColour())];

		if (absentColourCard.getQuantity() != 1){
			return false;
		}
		
		LanternCardWrapper playerCard = null;
		for (LanternCardWrapper pc: player.getCards()){
			if ( (! (pc.getColour().equals(absentColourCard.getColour())) ) && pc.getQuantity() >= 1){
				playerCard = pc;
				break;
			}
		}
		
		if (playerCard == null) {
			return false;
		}
		
		controller.exchangeLanternCard(threat.getGame(), playerID, playerCard.getColour(), absentColourCard.getColour());
		return true;
	}
}
