package ca.concordia.lanterns.tileplacement.impl;

import java.util.Arrays;
import java.util.List;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exchange.impl.helper.DedicationThreat;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.ai.TilePlayBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;
import ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction;

public class WorstTile implements TilePlayBehavior {

	private GameController controller = new GameController();

	@Override
	public void placeTile(Game game, Player currentPlayer) {
		
		List<LakeTile> playerTiles = currentPlayer.getTiles();
		if(playerTiles.isEmpty()) {
			return;
		}
		
		// sort the game dedications
		DedicationType[] sortedGameDedications = DedicationTokenWrapper.sortDedications (game.getDedications());
		
		int nextPlayerIndex = game.getCurrentTurnPlayer() + 1;
		Player[] players = game.getPlayers();
		
		int i = 0 ;
		
		while(i != players.length - 1){
			
			// Ensures clockwise movement and appropriate indexing
			nextPlayerIndex = nextPlayerIndex % players.length ;
			
			for (DedicationType dedicationType: sortedGameDedications){
				DedicationThreat threat = DedicationThreat.getThreat(dedicationType, players[nextPlayerIndex], game);
				if (threat != null){
					boolean isStopped = stopThreat(game, threat, players[nextPlayerIndex].getId());
					
					// We can only stop one dedication by making one exchange.
					if (isStopped) {
						return;
					}
				}
			}
			
			++nextPlayerIndex;
			++i;
		}

		// Assign the first available lake tile to the first available place in lake
		LakeTile[][] lake = game.getLake();
		
		for (int k = 0; k != lake.length; ++k) {
			for (int j = 0; j != lake[k].length; ++j) {
				if (lake[k][j] != null){
					TileSide[] side = lake[k][j].getSides();
					for (int p = 0; p != side.length; ++p) {
						if (side[p].getAdjacent() == null) {
							controller.placeLakeTile(game, currentPlayer.getId(), 0, lake[k][j].getId(), p, 0); 
							return;
						}
					}
				}
			}
		}
		
	}
	
	
	private boolean stopThreat(Game game, DedicationThreat threat, int playerID){
		
		Player player = game.getPlayer(playerID);
		Colour absentColour = threat.getAbsentColour();

		if (player == null){
			throw new IllegalArgumentException("The game do not have a player with ID: " + playerID);
		}
		
		List<LakeTile> playerTiles = player.getTiles();
		
		int maxFoundColourCount = 0;
		int selectedPlayerTileIndex = -1;
		int selectedPlayerTileSideIndex = -1;
		
		for (int i = 0; i != playerTiles.size(); ++i) {
			int foundColourCount = 0;
			int playerTileSideIndex = 0;
			TileSide[] side = playerTiles.get(i).getSides();
			for (int j = 0; j != side.length; ++j) {
				if (side[j].getColour() == absentColour) {
					++foundColourCount;
					playerTileSideIndex = j;
				}
			}
			
			if (foundColourCount > maxFoundColourCount){
				maxFoundColourCount = foundColourCount;
				selectedPlayerTileIndex = i;
				selectedPlayerTileSideIndex = playerTileSideIndex;
			}
		}
		
		if (selectedPlayerTileIndex == -1){
			return false;
		}
		
		List<MatrixOrganizer.Direction> directions = Arrays.asList(MatrixOrganizer.Direction.values());
		LakeTile[][] lake = game.getLake();
		
		Direction oppDirection = MatrixOrganizer.getOppositeTileSideIndex(directions.get(playerID));
		int lakeTileSideIndex = directions.indexOf(oppDirection);
		for (int i = 0; i != lake.length; ++i) {
			for (int j = 0; j != lake[i].length; ++j) {
				if (lake[i][j] != null) {
					if (lake[i][j].getSides()[lakeTileSideIndex].getAdjacent() == null) {
						controller.placeLakeTile(game, playerID, selectedPlayerTileIndex, lake[i][j].getId(), lakeTileSideIndex, selectedPlayerTileSideIndex); 
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
