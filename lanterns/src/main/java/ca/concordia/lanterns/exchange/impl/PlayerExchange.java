package ca.concordia.lanterns.exchange.impl;

import java.util.Scanner;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;

public class PlayerExchange implements ExchangeBehavior {
	private Scanner keyboardInstance;
	
	public PlayerExchange() {
		//
	}

	@Override
	public void makeExchange(Game game, Player currentPlayer) {
		keyboardInstance = new Scanner(System.in);
		System.out.println("You have [" + currentPlayer.getFavors() + "] favors.");

        int doExchange = 2;
        
        displayPlayerLanterns(game, currentPlayer.getId());
        doExchange = getValidInt("Do you want to make an exchange?\n1) Yes\n2) No", 1, 2);


        if (doExchange == 1) {
            System.out.println("Select one card to give:");
            int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
            Colour giveCard = Colour.values()[giveCardIndex];
            System.out.println("Select one card to receive:");
            int receiveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
            Colour receiveCard = Colour.values()[receiveCardIndex];
            try {
            	ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
            } catch (GameRuleViolationException e) {
                System.err.println(e.getMessage());
            }
        }
		
		
        
	}
    public int getValidInt(final String message, final int min, final int max) {
        System.out.print(message);
        int userChoice = 0;
        boolean valid = false;
        while (!valid) {
            try {
                userChoice = keyboardInstance.nextInt();
                if (userChoice >= min && userChoice <= max) {
                    valid = true;
                }
            } catch (Exception e) {
            	keyboardInstance.nextLine();
            }
            if (!valid) {
                System.out.println("Invalid input. Please enter an integer between " + min + "-" + max);
            }
        }
        System.out.println(userChoice);
        return userChoice;
    }
    
    private String coloursWithIndexesString() {
        StringBuffer sb = new StringBuffer();
        for (Colour colour : Colour.values()) {
            sb.append(colour.ordinal() + "=" + colour + "; ");
        }
        return sb.toString();
    }
    
    private static void displayPlayerLanterns(Game game, final int playerID) {
    	System.out.print("Lantern Cards:");
        for (int i = 0; i < game.getCards().length; i++) {
            System.out.print("\t" + game.getPlayers()[playerID].getCards()[i]);
        }
        System.out.println();
    }
}
