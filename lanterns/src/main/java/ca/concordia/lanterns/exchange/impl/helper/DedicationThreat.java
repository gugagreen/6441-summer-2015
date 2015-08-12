package ca.concordia.lanterns.exchange.impl.helper;

import java.util.Arrays;
import java.util.List;

import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
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
			threat = getThreePairs(player, game);
		} else if(dedicationType == DedicationType.SEVEN_UNIQUE){
			threat = getSevenUnique(player, game);
		} else {
			throw new IllegalArgumentException("The type of dedication is invalid");
		}
		
		return threat;
	}
	
	private static DedicationThreat getFourOfAkind(Player player, Game game){
		DedicationThreat threat = null;
		
		LanternCardWrapper[]playerCards = player.getCards();
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
						minGameCardQuantity = gameCardQuantity;
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
	
	private static DedicationThreat getThreePairs(Player player, Game game){
		DedicationThreat threat = null;
		
		LanternCardWrapper[]playerCards = player.getCards();
		LanternCardWrapper[] gameCards = game.getCards();
		
		int requiredCardPerColour = DedicationCost.THREE_PAIRS.getRequiredCardPerColor();
		
		int minGameCardQuantity = -1;
		
		// Counts the number of Colours such that player has atleast two cards of that colour
		int alreadyCollectedCount = 0;
		Colour absentColour = null;
		
		for (int i = 0; i != playerCards.length; ++i){
			int gameCardQuantity = gameCards[i].getQuantity();
			
			if (playerCards[i].getQuantity() == requiredCardPerColour - 1 && gameCardQuantity >= 1){
				
				if (minGameCardQuantity == -1){
					absentColour = playerCards[i].getColour();
					minGameCardQuantity = gameCardQuantity;
				} else if (gameCards[i].getQuantity() < minGameCardQuantity){
						absentColour = playerCards[i].getColour();
						minGameCardQuantity = gameCardQuantity;
				}
			} else if (playerCards[i].getQuantity() >= requiredCardPerColour){
				++alreadyCollectedCount;
				
				if (alreadyCollectedCount == DedicationCost.THREE_PAIRS.getRequiredColors()){
					absentColour = null;
					break;
				}
			}
		}
		
		if (absentColour != null) {
			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.THREE_PAIRS)].getStack().peek().getTokenValue();
			
			DedicationToken possibleEarning = new DedicationToken();
			possibleEarning.init(tokenValue, DedicationType.THREE_PAIRS);
			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
		}

		return threat;
	}
	
	private static DedicationThreat getSevenUnique(Player player, Game game){
		DedicationThreat threat = null;
		
		LanternCardWrapper[]playerCards = player.getCards();
		LanternCardWrapper[] gameCards = game.getCards();
		
		int requiredCardPerColour = DedicationCost.SEVEN_UNIQUE.getRequiredCardPerColor();
		
		int missingCardCount = 0;
		Colour absentColour = null;
		
		for (int i = 0; i != playerCards.length; ++i){
			int gameCardQuantity = gameCards[i].getQuantity();
			
			if (playerCards[i].getQuantity() == requiredCardPerColour - 1 && gameCardQuantity >= 1){
				
				absentColour = playerCards[i].getColour();
				++missingCardCount;
				if (missingCardCount == 2){
					absentColour = null;
					break;
				}
			}
		}
		
		if (absentColour != null) {
			int tokenValue = game.getDedications()[dedicationType.indexOf(DedicationType.SEVEN_UNIQUE)].getStack().peek().getTokenValue();
			
			DedicationToken possibleEarning = new DedicationToken();
			possibleEarning.init(tokenValue, DedicationType.SEVEN_UNIQUE);
			threat = new DedicationThreat(new DedicationToken(), absentColour, game);
		}

		return threat;
	}
	
	public boolean stopThreat(int playerID) {
		Player player = null;
		for (Player p: game.getPlayers()){
			if (p.getId() == playerID){
				player = p;
				break;
			}
		}
		
		if (player == null){
			throw new IllegalArgumentException("The game do not have a player with ID: " + playerID);
		}
		
		if (player.getFavors() < 2) {
			return false;
		} 
		
		if (! (this.possibleEarning == this.game.getDedications()[dedicationType.indexOf(this.possibleEarning.getTokenType())].getStack().peek())) {
			return false;
		}
		
		List<Colour> colors = Arrays.asList(Colour.values());
		LanternCardWrapper absentColourCard = game.getCards()[colors.indexOf(absentColour)];
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

		if (absentColourCard.getQuantity() == 0){
			return false;
		}
		
		PlayerService services = new ActivePlayerService();
		services.exchangeLanternCard(game, playerID, playerCard.getColour(), absentColourCard.getColour());
		
		return true;
	}
}
