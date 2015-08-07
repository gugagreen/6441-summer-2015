package ca.concordia.lanterns.dedication.impl;

import java.util.Scanner;


import ca.concordia.lanterns.dedication.DedicationBehavior;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class PlayerDedication implements DedicationBehavior {
    private Scanner keyboardInstance;
    
    public PlayerDedication() {
        //
    }
	
	@Override
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible) {
		keyboardInstance = new Scanner(System.in);
		
		displayPlayerLanterns(game, currentPlayer.getId());
		int doDedication = getValidInt("Do you want to make a dedication?\n1) Yes\n2) No", 1, 2);

        if (doDedication == 1) {
            System.out.println("Select one dedication type:");
            int typeIndex = getValidInt(dedicationTypesString(), 0, 3);
            DedicationType type = DedicationType.values()[typeIndex];
            int requiredColours = getRequiredColors(type);
            Colour[] colours = new Colour[requiredColours];
            for (int i = 0; i < colours.length; i++) {
                System.out.println("Select one colour:");
                int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
                colours[i] = Colour.values()[giveCardIndex];
            }
            try {
            	ActivePlayerService.getInstance().makeDedication(game, currentPlayer.getId(), type, colours);
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

        private String dedicationTypesString() {
            StringBuffer sb = new StringBuffer();
            for (DedicationType type : DedicationType.values()) {
                sb.append(type.ordinal() + "=" + type + "; ");
            }
            return sb.toString();
        }
        
        private String coloursWithIndexesString() {
            StringBuffer sb = new StringBuffer();
            for (Colour colour : Colour.values()) {
                sb.append(colour.ordinal() + "=" + colour + "; ");
            }
            return sb.toString();
        }
        
        public int getRequiredColors(DedicationType dedicationType) {
            DedicationCost cost = ActivePlayerService.getInstance().getDedicationCost(dedicationType);
            ;
            return cost.getRequiredColors();
        }
        
        private static void displayPlayerLanterns(Game game, final int playerID) {
        	System.out.print("Lantern Cards:");
            for (int i = 0; i < game.getCards().length; i++) {
                System.out.print("\t" + game.getPlayers()[playerID].getCards()[i]);
            }
            System.out.println();
        }
}
