package ca.concordia.lanterns.exchange.impl.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Represents a threat/possibility of some {@link Player} making a dedication in near future.
 * <p>A {@link Player} is said to threaten a dedication if it requires exactly one more Lantern card to make the dedication of some type</p>
 * @author par_pat
 *
 */
public class DedicationThreat {
	
	private static final List<DedicationType> allDedicationType = Arrays.asList(DedicationType.values());
	
	// The token that will be earned from the game if the threat is realised.
	private DedicationToken possibleEarning;
	
	// The color that is absent in order to make the dedication
	private Colour absentColour;
	
	// The game in context of which their is a threat
	private Game game;
	
	// Only internal methods should be able to create a DedicationThreat
	private DedicationThreat(DedicationToken possibleEarning, Colour absentColour, Game game){
		this.possibleEarning = possibleEarning;
		this.absentColour = absentColour;
		this.game = game;
	}
	
	public DedicationToken getPossibleEarning() {
		return this.possibleEarning;
	}
	
	public Colour getAbsentColour() {
		return this.absentColour;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Checks if the player has a possibility of making a dedication of type dedicationType in the near future.
	 * @param dedicationType - Type of dedication that should be checked
	 * @param player - The player in who could make the dedication
	 * @param game - The game in context
	 * @return {@link DedicationThreat} - If the player do threaten a dedication of type dedicationType, null -otherwise
	 */
	public static DedicationThreat getThreat(DedicationType dedicationType, Player player, Game game) throws IllegalArgumentException {

		DedicationCost cost;
		
		// Generc type is not allowed
		cost = DedicationCost.getDedicationCost(dedicationType);

		DedicationThreat threat = null;

		LanternCardWrapper[]playerCards = player.getCards();
		LanternCardWrapper[] gameCards = game.getCards();
		
		int minGameCardQuantity = -1;
		Colour absentColour = null;
		
		int haveCardPerColorCount = 0;
		
		for (int i = 0; i != playerCards.length; ++i){
			int gameCardQuantity = gameCards[i].getQuantity();
			
			if ((playerCards[i].getQuantity() == (cost.getRequiredCardPerColor() - 1)) && gameCardQuantity >= 1){
				
				if (minGameCardQuantity == -1){
					absentColour = playerCards[i].getColour();
					minGameCardQuantity = gameCardQuantity;
				} else if (gameCardQuantity < minGameCardQuantity){
						absentColour = playerCards[i].getColour();
						minGameCardQuantity = gameCardQuantity;
				}
			} else if (playerCards[i].getQuantity() >= cost.getRequiredCardPerColor()) {
				++haveCardPerColorCount;
			}
		}
		
		// There should be exactly one lantern card left in the game
		if (haveCardPerColorCount < cost.getRequiredColors() && absentColour != null && minGameCardQuantity == 1){
			Stack<DedicationToken> dedicationStack = game.getDedications()[allDedicationType.indexOf(dedicationType)].getStack();
			
			if (! (dedicationStack.isEmpty())){
				DedicationToken possibleEarning = new DedicationToken();
				possibleEarning.init(dedicationStack.peek().getTokenValue(), dedicationType);
				threat = new DedicationThreat(possibleEarning, absentColour, game);
			} else {
				Stack<DedicationToken> genericStack = game.getDedications()[allDedicationType.indexOf(DedicationType.GENERIC)].getStack();
				if (! (genericStack.isEmpty())) {
					DedicationToken possibleEarning = new DedicationToken();
					possibleEarning.init(genericStack.peek().getTokenValue(), dedicationType);
					threat = new DedicationThreat(possibleEarning, absentColour, game);
					
				}
			}
		}
		
		return threat;
	}

	
	
//	private static DedicationThreat getFourOfAkind(Player player, Game game){
//		DedicationThreat threat = null;
//		
//		LanternCardWrapper[]playerCards = player.getCards();
//		LanternCardWrapper[] gameCards = game.getCards();
//		
//		int minGameCardQuantity = -1;
//		Colour absentColour = null;
//		for (int i = 0; i != playerCards.length; ++i){
//			int gameCardQuantity = gameCards[i].getQuantity();
//			
//			if (playerCards[i].getQuantity() == DedicationCost.FOUR_OF_A_KIND.getRequiredCardPerColor() - 1 && gameCardQuantity >= 1){
//				
//				if (minGameCardQuantity == -1){
//					absentColour = playerCards[i].getColour();
//					minGameCardQuantity = gameCardQuantity;
//				} else if (gameCardQuantity < minGameCardQuantity){
//						absentColour = playerCards[i].getColour();
//						minGameCardQuantity = gameCardQuantity;
//				}
//			} else if (playerCards[i].getQuantity() >= DedicationCost.FOUR_OF_A_KIND.getRequiredCardPerColor()) {
//				absentColour = null;
//				break;
//			}
//		}
//		
//		// There should be exactly one lantern card left in the game
//		if (absentColour != null && minGameCardQuantity == 1){
//			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.FOUR_OF_A_KIND)].getStack().peek().getTokenValue();
//			
//			DedicationToken possibleEarning = new DedicationToken();
//			possibleEarning.init(tokenValue, DedicationType.FOUR_OF_A_KIND);
//			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
//		}
//		
//		return threat;
//	}
//	
//	private static DedicationThreat getThreePairs(Player player, Game game){
//		DedicationThreat threat = null;
//		
//		LanternCardWrapper[]playerCards = player.getCards();
//		LanternCardWrapper[] gameCards = game.getCards();
//		
//		int requiredCardPerColour = DedicationCost.THREE_PAIRS.getRequiredCardPerColor();
//		
//		int minGameCardQuantity = -1;
//		
//		// Counts the number of Colours such that player has atleast two cards of that colour
//		int alreadyCollectedCount = 0;
//		Colour absentColour = null;
//		
//		for (int i = 0; i != playerCards.length; ++i){
//			int gameCardQuantity = gameCards[i].getQuantity();
//			
//			if (playerCards[i].getQuantity() == requiredCardPerColour - 1 && gameCardQuantity >= 1){
//				
//				if (minGameCardQuantity == -1){
//					absentColour = playerCards[i].getColour();
//					minGameCardQuantity = gameCardQuantity;
//				} else if (gameCards[i].getQuantity() < minGameCardQuantity){
//						absentColour = playerCards[i].getColour();
//						minGameCardQuantity = gameCardQuantity;
//				}
//			} else if (playerCards[i].getQuantity() >= requiredCardPerColour){
//				++alreadyCollectedCount;
//				
//				if (alreadyCollectedCount == DedicationCost.THREE_PAIRS.getRequiredColors()){
//					absentColour = null;
//					break;
//				}
//			}
//		}
//		
//		if (absentColour != null && minGameCardQuantity == 1) {
//			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.THREE_PAIRS)].getStack().peek().getTokenValue();
//			
//			DedicationToken possibleEarning = new DedicationToken();
//			possibleEarning.init(tokenValue, DedicationType.THREE_PAIRS);
//			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
//		}
//
//		return threat;
//	}
//	
//	private static DedicationThreat getSevenUnique(Player player, Game game){
//		DedicationThreat threat = null;
//		
//		LanternCardWrapper[]playerCards = player.getCards();
//		LanternCardWrapper[] gameCards = game.getCards();
//		
//		int requiredCardPerColour = DedicationCost.SEVEN_UNIQUE.getRequiredCardPerColor();
//		
//		int missingCardCount = 0;
//		Colour absentColour = null;
//		int missingCardGameCardQuantity = 0;
//		
//		for (int i = 0; i != playerCards.length; ++i){
//			int gameCardQuantity = gameCards[i].getQuantity();
//			
//			if (playerCards[i].getQuantity() == requiredCardPerColour - 1 && gameCardQuantity >= 1){
//				
//				absentColour = playerCards[i].getColour();
//				missingCardGameCardQuantity = gameCardQuantity;
//				++missingCardCount;
//				if (missingCardCount == 2){
//					absentColour = null;
//					break;
//				}
//			}
//		}
//		
//		if (absentColour != null && missingCardGameCardQuantity == 1) {
//			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.SEVEN_UNIQUE)].getStack().peek().getTokenValue();
//			
//			DedicationToken possibleEarning = new DedicationToken();
//			possibleEarning.init(tokenValue, DedicationType.SEVEN_UNIQUE);
//			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
//		}
//
//		return threat;
//	}
//	
}
