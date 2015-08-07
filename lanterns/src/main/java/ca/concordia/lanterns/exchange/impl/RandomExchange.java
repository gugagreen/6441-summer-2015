package ca.concordia.lanterns.exchange.impl;

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
			int giveCardIndex = randomLanternIndex();
			Colour giveCard = Colour.values()[giveCardIndex];
			//Select one card to receive
			int receiveCardIndex = otherRandLanternIndex(giveCardIndex);
			Colour receiveCard = Colour.values()[receiveCardIndex];
					
			ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
	
		}
    }

	private static boolean allowExchange() {
		Random rand = new Random();
		boolean makeExchange = rand.nextBoolean();
		
		return makeExchange;	
	}
	
	private static int randomLanternIndex(){
		Random rand = new Random();
		int randLantern = rand.nextInt(6);
		
		return randLantern;
	}
	
	private int otherRandLanternIndex(int excludedIndex){
		boolean searchingRand = true;
		int newIndex = randomLanternIndex();	
		
		while(searchingRand){
			
			if(newIndex != excludedIndex){
				searchingRand = false;
			}
			else if(newIndex == excludedIndex){
				newIndex = randomLanternIndex();
			}
		}
		return newIndex;	
	}	
}

