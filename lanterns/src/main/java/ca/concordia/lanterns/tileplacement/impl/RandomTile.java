package ca.concordia.lanterns.tileplacement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

public class RandomTile implements TilePlayBehavior {

	@Override
	public void placeTile(Game game, Player currentPlayer) throws GameRuleViolationException {
		//making sure that player is not trying to place tiles without tiles
		if (!currentPlayer.getTiles().isEmpty()) {
			// choosing a random player tile
			int playerTileIndex = getRandPlayerTile(currentPlayer);

			// Select the side of the tile to put next to the lake tile:");
			int playerTileSideIndex = getRandTileSideIndex();

			// Select one of the lake tiles to put your tile next to:");

			LakeTile lakeTile = randLakeTile(game);
			List<Integer> usableTileSides = usableLakeTileSides(lakeTile);
			int existingTileSideIndex = randLakeTileSide(usableTileSides);

			ActivePlayerService.getInstance().placeLakeTile(game, currentPlayer.getId(), playerTileIndex,
					lakeTile.getId(), existingTileSideIndex, playerTileSideIndex);
		}
	}

	private int randLakeTileSide(List<Integer> usableTileSides) {
		Random rand = new Random();
		int randomSideIndex = rand.nextInt(usableTileSides.size());
		int randUsableTileSide = usableTileSides.get(randomSideIndex);
		
		return randUsableTileSide;
	}

	private List<String> possibleLakeTile(Game game) {
		List<String> possibleLakeTiles = new ArrayList<String>();
		LakeTile[][] matrix = game.getLake();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != null) {
					possibleLakeTiles.add(matrix[i][j].getId());
				}
			}
		}
		return possibleLakeTiles;
	}
	
	private List<String> usableLakeTilesIds(Game game) {
		List<String> possibleLakeTiles = possibleLakeTile(game);
		List<String> usableLakeTiles = new ArrayList<String>();

		for (String lakeTile : possibleLakeTiles) {
			for (int i = 0; i < 4; i++) {
				boolean validSide = validLakeTileSide(MatrixOrganizer.findTile(game.getLake(), lakeTile), i);
				if (validSide) {
					usableLakeTiles.add(lakeTile);
					break;
				}
			}
		}
		return usableLakeTiles;
	}

	private LakeTile randLakeTile(Game game) {
		Random rand = new Random();
		List<String> usableLakeTiles = usableLakeTilesIds(game);
		int randUsableLakeTileIndex = rand.nextInt(usableLakeTiles.size());

		String lakeTileId = usableLakeTiles.get(randUsableLakeTileIndex);
		return MatrixOrganizer.findTile(game.getLake(), lakeTileId);

	}

	private int getRandPlayerTile(Player currentPlayer) {
		Random rand = new Random();
		int lakeTiles = currentPlayer.getTiles().size();
		int randTile = rand.nextInt(lakeTiles);

		return randTile;
	}

	private int getRandTileSideIndex() {
		Random rand = new Random();
		int tileSide = rand.nextInt(4);

		return tileSide;
	}

	private boolean validLakeTileSide(LakeTile lakeTile, int checkSide) {

		if (lakeTile.getSides()[checkSide].getAdjacent() == null) {
			return true;
		} else {
			return false;
		}
	}

	private List<Integer> usableLakeTileSides(LakeTile lakeTile) {
		List<Integer> usableTileSides = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (validLakeTileSide(lakeTile, i)) {
				usableTileSides.add(i);
			}
		}
		return usableTileSides;
	}

}
