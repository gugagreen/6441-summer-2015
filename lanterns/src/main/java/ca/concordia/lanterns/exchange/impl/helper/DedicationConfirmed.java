package ca.concordia.lanterns.exchange.impl.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class DedicationConfirmed {

	
	private ArrayList<Colour> costCardColors;
	private DedicationType dedicationType;
	private Game game;
	private int playerID;
	
	public ArrayList<Colour> getCostCardColors() {
		return costCardColors;
	}
	
	public DedicationType getDedicationType() {
		return dedicationType;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return game.getPlayer(playerID);
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	
	private DedicationConfirmed(ArrayList<Colour> costCardColors, DedicationType dedicationType, Game game, int playerID) {
		this.costCardColors = costCardColors;
		this.dedicationType = dedicationType;
		this.game = game;
		this.playerID = playerID;
	}
	
	public static DedicationConfirmed getDedicationConfirmed(DedicationType dedicationType, Game game, int playerID) {
		DedicationConfirmed dedication = null;
		
		Player player = game.getPlayer(playerID);
		
		ArrayList<Colour> costCardColours = new ArrayList<Colour>();
		LanternCardWrapper[] playerCards = player.getCards();
		DedicationCost dedicationCost = DedicationCost.getDedicationCost(dedicationType);
		
		
		for (int i = 0; i != playerCards.length; ++i) {
			if (playerCards[i].getQuantity() >= dedicationCost.getRequiredCardPerColor()) {
				costCardColours.add(playerCards[i].getColour());
			}
		}
		
		if (costCardColours.size() >= dedicationCost.getRequiredColors()) {
			dedication = new DedicationConfirmed(costCardColours, dedicationType, game, playerID);
		}
		
		return dedication;
	}
	
	public boolean isDamageable() {
		List<DedicationType> dedications = Arrays.asList(DedicationType.values());
		
		Stack<DedicationToken> dedicationStack = game.getDedications()[dedications.indexOf(this.dedicationType)].getStack();
		int genericStackSize = game.getDedications()[dedications.indexOf(DedicationType.GENERIC)].getStack().size();
		
		if (dedicationStack.size() == 0) {
			if ( genericStackSize == 1) {
				return true;
			} else {
				return false;
			}
		} else if (dedicationStack.size() == 1) {
			if (genericStackSize == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (dedicationStack.peek().getTokenValue() > dedicationStack.get(dedicationStack.size() - 2).getTokenValue()) {
				return true;
			} else {
				return false;
			}
		} 
	}
}
