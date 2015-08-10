package ca.concordia.lanterns.tileplacement.impl;

import java.util.List;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanterns.ui.GameCommandClient;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;

public class PlayerTile implements TilePlayBehavior {

	@Override
	public void placeTile(Game game, Player currentPlayer) {
		boolean playerNotDone = true;
	    while (playerNotDone) {
	        System.out.println("Now it is time to place a tile.");
	        List<LakeTile> playerTiles = currentPlayer.getTiles();
	        GameCommandClient.displayPlayerLakeTiles(game, currentPlayer.getId());
	        int playerTileIndex = GameCommandClient.getValidInt("Select one of your tiles:", 0, playerTiles.size() - 1);
	
	        System.out.println("Select one of the lake tiles to put your tile next to:");
	        GameCommandClient.displayLake(game);
	        
	        LakeTile lakeTile = null;
	        while (lakeTile == null) {
	        	int line = GameCommandClient.getValidInt("line: ", 0, game.getLake().length -1);
            	int column = GameCommandClient.getValidInt("column: ", 0, game.getLake()[0].length -1);
            	lakeTile = game.getLake()[line][column];
            	if (lakeTile == null) {
            		System.out.println("Invalid tile id. Please try again:");
            	}
	        }
	
	        System.out.println("Select the side of the lake tiles to put your tile next to:");
	        int existingTileSideIndex = GameCommandClient.getValidInt(GameCommandClient.getTileSidesString(lakeTile), 0, lakeTile.getSides().length - 1);
	
	        System.out.println("Select the side your tile to put next to the lake tile:");
	        LakeTile playerTile = playerTiles.get(playerTileIndex);
	        int playerTileSideIndex = GameCommandClient.getValidInt(GameCommandClient.getTileSidesString(playerTile), 0, playerTile.getSides().length - 1);
	
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
}
