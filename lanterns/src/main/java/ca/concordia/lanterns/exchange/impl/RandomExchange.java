package ca.concordia.lanterns.exchange.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;


public class RandomExchange implements ExchangeBehavior {

	@Override
	public void makeExchange(Game game, Player currentPlayer) throws GameRuleViolationException {

		if(allowExchange()){
		
			//Select one card to give
			int giveCardIndex = randomLanternIndex(possibleLanternIndex(game, currentPlayer));
			Colour giveCard = Colour.values()[giveCardIndex];
			//Select one card to receive
			int receiveCardIndex = randIndexWithExclusion(giveCardIndex, possibleTableLanternsIndex(game) );
			Colour receiveCard = Colour.values()[receiveCardIndex];
					
			ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
	
		}
    }

	private static boolean allowExchange() {
		Random rand = new Random();
		boolean makeExchange = rand.nextBoolean();
		
		return makeExchange;	
	}
	
	private static int randomLanternIndex(List<Integer> allowablePlayerLanterns){
		Random rand = new Random();
		int randLantern = rand.nextInt(allowablePlayerLanterns.size());
		
		return allowablePlayerLanterns.get(randLantern) ;
	}
	
	private List<Integer> possibleLanternIndex(Game game, Player currentPlayer){
		List<Integer> allowPlayerLanterns = new ArrayList<Integer>(); 
		
		for (int i=0; i<7;i++) {
			if(currentPlayer.getCards()[i].getQuantity()>0){
				allowPlayerLanterns.add(i);
			}
		}
		return allowPlayerLanterns;
	}
	
	private List<Integer> possibleTableLanternsIndex(Game game){
		List<Integer> allowTableLanterns = new ArrayList<Integer>(); 
		
		for (int i=0; i<7;i++) {
			if(game.getCards()[i].getQuantity()>0){
				allowTableLanterns.add(i);
			}
		}
		return allowTableLanterns;
	}
	
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

