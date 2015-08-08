package ca.concordia.lanterns.tileplacement.impl;

import java.util.List;
import java.util.Scanner;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanterns.ui.GameCommandClient;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;

// FIXME - move UI part to GameCommandClient
// FIXME - remove duplicated code already existent in GameCommandClient
public class PlayerTile implements TilePlayBehavior {
	private Scanner keyboardInstance;
	
	public PlayerTile() {
		//
	}

	@Override
	public void placeTile(Game game, Player currentPlayer) {
		keyboardInstance = new Scanner(System.in);
		
		boolean playerNotDone = true;
	    while (playerNotDone) {
	        System.out.println("Now it is time to place a tile.");
	        List<LakeTile> playerTiles = currentPlayer.getTiles();
	        GameCommandClient.displayPlayerLakeTiles(game, currentPlayer.getId());
	        int playerTileIndex = getValidInt("Select one of your tiles:", 0, playerTiles.size() - 1);
	
	        System.out.println("Select one of the lake tiles to put your tile next to:");
	        GameCommandClient.displayLake(game);
	        
	        LakeTile lakeTile = null;
	        while (lakeTile == null) {
	        	int line = getValidInt("line: ", 0, game.getLake().length -1);
            	int column = getValidInt("column: ", 0, game.getLake()[0].length -1);
            	lakeTile = game.getLake()[line][column];
            	if (lakeTile == null) {
            		System.out.println("Invalid tile id. Please try again:");
            	}
	        }
	
	        System.out.println("Select the side of the lake tiles to put your tile next to:");
	        int existingTileSideIndex = getValidInt(getTileSidesString(lakeTile), 0, lakeTile.getSides().length - 1);
	
	        System.out.println("Select the side your tile to put next to the lake tile:");
	        LakeTile playerTile = playerTiles.get(playerTileIndex);
	        int playerTileSideIndex = getValidInt(getTileSidesString(playerTile), 0, playerTile.getSides().length - 1);
	
	        try {
	        	ActivePlayerService.getInstance().placeLakeTile(game, currentPlayer.getId(), playerTileIndex,
	            		lakeTile.getId(), existingTileSideIndex, playerTileSideIndex);
	            playerNotDone = false;
	        } catch (GameRuleViolationException e) {
	            System.err.println(e.getMessage());
	            System.out.println();
	            System.out.println("Please reselect your tile and try again.");
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
	
    public String getValidString(final String message) {
        System.out.print(message);
        String value = null;
        boolean valid = false;
        while (!valid) {
            value = keyboardInstance.next();
            if (value != null && value.length() > 0) {
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid String: ");
            }
        }
        System.out.println(value);
        return value;
    }
    
    private String getTileSidesString(LakeTile tile) {
        StringBuffer sb = new StringBuffer();

        TileSide[] sides = tile.getSides();

        for (int i = 0; i < sides.length; i++) {
            sb.append(i + "=" + sides[i] + "; ");
        }
        return sb.toString();
    }
}
