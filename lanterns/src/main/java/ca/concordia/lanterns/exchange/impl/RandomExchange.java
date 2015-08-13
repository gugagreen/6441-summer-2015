package ca.concordia.lanterns.exchange.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.ExchangeBehavior;
import ca.concordia.lanternsentities.enums.Colour;

/**
 * Provides services for making an exchange of Lanterns cards randomly for random AI player.
 *  
 */
public class RandomExchange implements ExchangeBehavior {
	private GameController controller = new GameController();
	/**
     * Make exchange of cards for Random AI player if possible.
     *
     * @param currentPlayer {@link Player} object.
     * @param game {@link Game} object.
     */
	@Override
	public void makeExchange(Game game, Player currentPlayer) throws GameRuleViolationException {
		if(allowExchange()){
			//Select one card to give
			int giveCardIndex = randomLanternIndex(possibleLanternIndex(game, currentPlayer));
			Colour giveCard = Colour.values()[giveCardIndex];
			//Select one card to receive
			int receiveCardIndex = randIndexWithExclusion(giveCardIndex, possibleTableLanternsIndex(game) );
			Colour receiveCard = Colour.values()[receiveCardIndex];
					
			controller.exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
		}
    }

	/**
	 * Randomly determines whether the random AI will choose to make an exchange.
	 * This takes place only when an exchange is possible.
	 * @return a randomly determined boolean value.
	 */
	private static boolean allowExchange() {
		Random rand = new Random();
		boolean makeExchange = rand.nextBoolean();
		
		return makeExchange;	
	}
	
	/**
	 * Using a list of allowable Lantern cards in the players hand randomly determines which card will be exchanged.
	 * @param allowablePlayerLanterns List of non zero lantern card stacks in the players hand.
	 * @return An exchangeable random lantern card.
	 */
	private static int randomLanternIndex(List<Integer> allowablePlayerLanterns){
		Random rand = new Random();
		int randLantern = rand.nextInt(allowablePlayerLanterns.size());
		
		return allowablePlayerLanterns.get(randLantern) ;
	}
	
	/**
	 * Makes a list of possible lantern cards to exchange in the players hand.
	 * @param game Current game state.
	 * @param currentPlayer Player currently trying to make an exchange.
	 * @return a list of possible lantern card indices to exchange.
	 */
	private List<Integer> possibleLanternIndex(Game game, Player currentPlayer){
		List<Integer> allowPlayerLanterns = new ArrayList<Integer>(); 
		
		for (int i=0; i<7;i++) {
			if(currentPlayer.getCards()[i].getQuantity()>0){
				allowPlayerLanterns.add(i);
			}
		}
		return allowPlayerLanterns;
	}
	
	/**
	 * Makes a list of possible lantern cards to exchange on the table.
	 * @param game Current game state.
	 * @return a list of possible lantern card indices to exchange with.
	 */
	private List<Integer> possibleTableLanternsIndex(Game game){
		List<Integer> allowTableLanterns = new ArrayList<Integer>(); 
		
		for (int i=0; i<7;i++) {
			if(game.getCards()[i].getQuantity()>0){
				allowTableLanterns.add(i);
			}
		}
		return allowTableLanterns;
	}
	
	/**
	 * Using a list of allowable Lantern cards on the table, randomly determines which card will be exchanged.
	 * @param excludedIndex The card index that the player is exchanging as we do not want a zero sum exchange.
	 * @param possibleTableLanterns The list of possible table Lantern cards.
	 * @return a random table Lantern card index.
	 */
	private int randIndexWithExclusion(int excludedIndex, List<Integer> possibleTableLanterns){
		boolean searchingRand = true;
		int newIndex = randomLanternIndex(possibleTableLanterns);	
		
		while(searchingRand){
			
			if(newIndex != excludedIndex){
				searchingRand = false;
			}
			else if(newIndex == excludedIndex){
				newIndex = randomLanternIndex(possibleTableLanterns);
			}
		}
		return newIndex;	
	}	
}

