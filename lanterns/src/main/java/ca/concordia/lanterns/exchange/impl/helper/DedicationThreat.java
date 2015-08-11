package ca.concordia.lanterns.exchange.impl.helper;

import java.util.Arrays;
import java.util.List;

import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class DedicationThreat {
	
	private static final List<DedicationType> dedicationType = Arrays.asList(DedicationType.values());
	
	// The token that will be earned from the game if the threat is realised.
	private DedicationToken possibleEarning;
	
	// The color that is absent in order to make the dedication
	private Colour absentColour;
	
	// The game in context of which their is a threat
	private Game game;
	
	private DedicationThreat(DedicationToken posssibleEarning, Colour absentColour, Game game){
		this.possibleEarning = possibleEarning;
		this.absentColour = absentColour;
		this.game = game;
	}
	
	public static DedicationThreat getThreat(DedicationType dedicationType, Player player, Game game){
		DedicationThreat threat = null;
		
		if (dedicationType == DedicationType.FOUR_OF_A_KIND){
			threat = getFourOfAkind(player, game);
		} else if(dedicationType == DedicationType.THREE_PAIRS){
//			threat = getThreePairs(player, game);
		} else if(dedicationType == DedicationType.SEVEN_UNIQUE){
//			threat = getSevenUnique(player, game);
		}
		
		return threat;
	}
	
	private static DedicationThreat getFourOfAkind(Player player, Game game){
		DedicationThreat threat = null;
		
		LanternCardWrapper[]playerCards = game.getCards();
		LanternCardWrapper[] gameCards = game.getCards();
		
		int minGameCardQuantity = -1;
		Colour absentColour = null;
		for (int i = 0; i != playerCards.length; ++i){
			int gameCardQuantity = gameCards[i].getQuantity();
			
			if (playerCards[i].getQuantity() == DedicationCost.FOUR_OF_A_KIND.getRequiredCardPerColor() - 1 && gameCardQuantity >= 1){
				
				if (minGameCardQuantity == -1){
					absentColour = playerCards[i].getColour();
					minGameCardQuantity = gameCardQuantity;
				} else if (gameCards[i].getQuantity() < minGameCardQuantity){
						absentColour = playerCards[i].getColour();
						minGameCardQuantity = gameCards[i].getQuantity();
				}
			}
		}
		
		if (absentColour != null){
			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.FOUR_OF_A_KIND)].getStack().peek().getTokenValue();
			
			DedicationToken possibleEarning = new DedicationToken();
			possibleEarning.init(tokenValue, DedicationType.FOUR_OF_A_KIND);
			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
		}
		
		return threat;
	}
}
