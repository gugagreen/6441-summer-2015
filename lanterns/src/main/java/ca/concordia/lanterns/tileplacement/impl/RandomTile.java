package ca.concordia.lanterns.tileplacement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.TilePlayBehavior;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

public class RandomTile implements TilePlayBehavior {
	private GameController controller = new GameController();
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

			controller.placeLakeTile(game, currentPlayer.getId(), playerTileIndex,
					lakeTile.getId(), existingTileSideIndex, playerTileSideIndex);
		}
	}
	/**
	 * Using a list of the possible lake tile sides this chooses one to play at random.
	 * @param usableTileSides list of usable tile sides.
	 * @return A random usable tile side.
	 */
	private int randLakeTileSide(List<Integer> usableTileSides) {
		Random rand = new Random();
		int randomSideIndex = rand.nextInt(usableTileSides.size());
		int randUsableTileSide = usableTileSides.get(randomSideIndex);
		
		return randUsableTileSide;
	}
	
	/**
	 * Makes a list of lake tiles currently in play on the lake.
	 * @param game Takes in the current game state.
	 * @return A list of possible lake tiles to play on.
	 */
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
	
	/**
	 * Using the list of lake tiles currently in the lake, all sides are checked.
	 * Based on tile side availability tiles are put in a list of playable lake tiles.
	 * @param game Takes in the current game state.
	 * @return A list of tiles checked for usability..
	 */
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

	/**
	 * Selects a random lake tile from the usable list.
	 * @param game
	 * @return a random usable lake tile.
	 */
	private LakeTile randLakeTile(Game game) {
		Random rand = new Random();
		List<String> usableLakeTiles = usableLakeTilesIds(game);
		int randUsableLakeTileIndex = rand.nextInt(usableLakeTiles.size());

		String lakeTileId = usableLakeTiles.get(randUsableLakeTileIndex);
		return MatrixOrganizer.findTile(game.getLake(), lakeTileId);

	}

	/**
	 * Selects a random lake tile from the players hand.
	 * @param currentPlayer Player currently playing his or her turn.
	 * @return a random lake tile from the players hand.
	 */
	private int getRandPlayerTile(Player currentPlayer) {
		Random rand = new Random();
		int lakeTiles = currentPlayer.getTiles().size();
		int randTile = rand.nextInt(lakeTiles);

		return randTile;
	}
	/**
	 * Selects a random lake tile side to play from the players tile.
	 * @return a random lake tile side.
	 */
	private int getRandTileSideIndex() {
		Random rand = new Random();
		int tileSide = rand.nextInt(4);

		return tileSide;
	}
	
	/**
	 * Checks to make sure that a specific lake tile side is not currently used.
	 * @param lakeTile A specific lake tile ID.
	 * @param checkSide A specific lake tile side to check (0-3)
	 * @return A boolean value of whether the side is usable or not.
	 */
	private boolean validLakeTileSide(LakeTile lakeTile, int checkSide) {

		if (lakeTile.getSides()[checkSide].getAdjacent() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a list of empty lake tile sides for a specific lake tile.
	 * @param lakeTile Takes in a specific lake tile.
	 * @return A list of empty lake tile sides.
	 */
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
