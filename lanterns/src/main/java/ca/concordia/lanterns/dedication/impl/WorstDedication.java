package ca.concordia.lanterns.dedication.impl;

import java.util.ArrayList;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exchange.impl.helper.DedicationConfirmed;
import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Provides services for making  dedication that is unfavorable to one or more players
 */
public class WorstDedication implements DedicationBehavior {

	private GameController controller = new GameController();

	/**
     * Perform dedications for currentplayer such that it unfavorable to one or more other players
     * The rules of the dedication are
     * <ul>
     * <li><p>Starting with the player with next turn and moving clockwise attempt to reduce the honor earned by atleast one player
     *  who already have enough cards to make the dedication.</p></li>
     *  <li>Do not make a dedication otherwise</li>
     * </ul>
     *
     * @param game - The game in context
      * @param player - The player who will make unfriendly decision
       * @param The Boolean array of all possible dedications. 
     */
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
			nextPlayerIndex = nextPlayerIndex % game.getPlayers().length ;
			
			for (int j = 0; j != sortedGameDedications.length; ++j){
				
				// Check if current player can make this type of dedication
				if (currentPlayerDedicationConfirmed[j] != null) {
					
					// Check if other player can make this type of dedication
					DedicationConfirmed guarantedDedication = DedicationConfirmed.getDedicationConfirmed(sortedGameDedications[j], game, players[nextPlayerIndex].getId());

					// Is the current player damaging other player making this dedication?
					if (guarantedDedication != null && guarantedDedication.isDamageable()){

							Colour[] currentPlayerDedicationCardColour = getCurrentPlayerDedicationCardColour(currentPlayerDedicationConfirmed[j]);
							controller.makeDedication(game, currentPlayer.getId(), sortedGameDedications[j], currentPlayerDedicationCardColour);
							return;
					}
				}
			}
			
			++nextPlayerIndex;
			++i;
		}
		
		// If the current player can't damage the potential dedications of any other players than the current player do not make a dedication
		return;
	}
	
	// Get {@link DedicationConfirmed} object corresponding to all the dedications that the current player can make
	private DedicationConfirmed[] getCurrentPlayerDedicationConfirmed(Game game, Player player, DedicationType[] sortedGameDedications) {
		DedicationConfirmed[] currentPlayerDedicationConfirmed = new DedicationConfirmed[sortedGameDedications.length];
		
		for (int i = 0; i != sortedGameDedications.length; ++i) {
			currentPlayerDedicationConfirmed[i] = DedicationConfirmed.getDedicationConfirmed(sortedGameDedications[i], game, player.getId());
		}
		
		return currentPlayerDedicationConfirmed;
	}
	
	// Select Cards that will be dedicated by the current player out of all eligible cards for a particular dedications
	private Colour[] getCurrentPlayerDedicationCardColour(DedicationConfirmed currentPlayerDedicationConfirmed) {
		Colour[] colors = null;
		
		DedicationCost cost = DedicationCost.getDedicationCost(currentPlayerDedicationConfirmed.getDedicationType());
		
		int cardsCount = cost.getRequiredColors();
		colors = new Colour[cardsCount];
		int i = 0;
		ArrayList<Colour> costCardColors = currentPlayerDedicationConfirmed.getCostCardColors();
		while(cardsCount != 0){
			colors[i] = costCardColors.get(i);
			++i;
			--cardsCount;
		}
		return colors;
	}
}
