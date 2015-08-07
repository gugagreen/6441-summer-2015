package ca.concordia.lanterns.tileplacement.impl;

import java.util.List;
import java.util.Scanner;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

public class PlayerTile implements TilePlayBehavior {
	private Scanner keyboardInstance;
	
	public PlayerTile() {
		//
	}

	@Override
	public void placeTile(Game game, Player currentPlayer) {
		keyboardInstance = new Scanner(System.in);
		
		Boolean playerNotDone = true;
	    while (playerNotDone) {

        System.out.println("Now it is time to place a tile.");
        List<LakeTile> playerTiles = currentPlayer.getTiles();
        displayPlayerLakeTiles(game, currentPlayer.getId());
        int playerTileIndex = getValidInt("Select one of your tiles:", 0, playerTiles.size() - 1);

        System.out.println("Select one of the lake tiles to put your tile next to:");
        displayLake(game);
        
        LakeTile lakeTile = null;
        while (lakeTile == null) {
        	String lakeTileId = getValidString("");
        	lakeTile = MatrixOrganizer.findTile(game.getLake(), lakeTileId);
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
    
    private static void displayLake(Game game) {
        LakeTile[][] matrix = game.getLake();
        
        StringBuffer[] lines1 = new StringBuffer[matrix.length];
		StringBuffer[] lines2 = new StringBuffer[matrix.length];
		StringBuffer[] lines3 = new StringBuffer[matrix.length];
		
		for (int i = 0; i < matrix.length; i++) {
			lines1[i] = new StringBuffer();
			lines2[i] = new StringBuffer();
			lines3[i] = new StringBuffer();

			for (int j = 0; j < matrix[i].length; j++) {
				String[] tileLines = LakeTile.get3Lines(matrix[i][j]);
				lines1[i].append(tileLines[0]);
				lines2[i].append(tileLines[1]);
				lines3[i].append(tileLines[2]);
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.println(lines1[i]);
			System.out.println(lines2[i]);
			System.out.println(lines3[i]);
			System.out.println();
		}
		
		System.out.println("ids");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != null) {
					System.out.println(matrix[i][j].getId());
				}
			}
		}
    }
    
    private static void displayPlayerLakeTiles(Game game, final int playerID) {
    	List<LakeTile> playerTiles = game.getPlayers()[playerID].getTiles();
        for (int i = 0; i < playerTiles.size(); i++) {
            System.out.println("Lake Tile: " + i);
            LakeTile tile = playerTiles.get(i);
            String[] lines = LakeTile.get3Lines(tile);
            for(String line : lines) {
            	System.out.println(line);
            }
        }

    }
}
