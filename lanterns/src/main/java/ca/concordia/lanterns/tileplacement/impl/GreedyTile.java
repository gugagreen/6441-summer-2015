package ca.concordia.lanterns.tileplacement.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.ai.DedicationForecaster;
import ca.concordia.lanternsentities.ai.TilePlayBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;
import ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction;

public class GreedyTile implements TilePlayBehavior {

	// FIXME - inject controller from super
	private GameController controller = new GameController();

	@Override
	public void placeTile(Game game, Player player) {
		// making sure that player is not trying to place tiles without tiles
		if (!player.getTiles().isEmpty()) {
			// 1) check if dedication is already possible. In that case, just place next possible tile.
			boolean[] dedicationsPossible = DedicationForecaster.getInstance().dedicationPossible(player);
			if (dedicationsPossible[0] || dedicationsPossible[1] || dedicationsPossible[2]) {
				placeNextFree(game, player);
			} else {
				// 2) If not, check if there is a dedication close (with one missing card), and place a tile to get it
				boolean successfulTry = tryPlaceGetDedication(game, player);
				// 3) if none of those, just place next possible tile.
				if (!successfulTry) {
					placeNextFree(game, player);
				}
			}
		}
	}

	/**
	 * Place next free player tile into next free game lake tile.
	 * @param game
	 * @param player
	 */
	private void placeNextFree(Game game, Player player) {
		TileIdIndexSideIndex playerTile = getNextPlayerFreeTile(player);
		TileIdIndexSideIndex lakeTile = getNextLakeFreeTile(game);
		
		controller.placeLakeTile(game, player.getId(), playerTile.tileIndex, lakeTile.tileId, lakeTile.sideIndex, playerTile.sideIndex);
	}

	private TileIdIndexSideIndex getNextPlayerFreeTile(Player player) {
		LakeTile tile = player.getTiles().get(0);
		return new TileIdIndexSideIndex(tile.getId(), 0, 0);
	}
	
	private TileIdIndexSideIndex getNextLakeFreeTile(Game game) {
		LakeTile[][] lake = game.getLake();
		TileIdIndexSideIndex tiisi = null;
		
		// iterate over tiles on lake
		lakeiteration:
		for (int i = 0; i < lake.length; i++) {
			for (int j = 0; j < lake[i].length; j++) {
				LakeTile current = lake[i][j];
				// if it is a valid tile
				if (current != null) {
					TileSide[] sides = current.getSides();
					// check if there is any available side
					for (int s = 0; s < sides.length; s++) {
						if (sides[s].getAdjacent() == null) {
							tiisi = new TileIdIndexSideIndex(current.getId(), -1, s);
							break lakeiteration;
						}
					}
				}
			}
		}
		
		return tiisi;
	}
	
	/**
	 * Try to place a tile to get a lantern for a close dedication. First check if could find matching placement 
	 * to get a four of a kind, then three pairs and finally try seven unique.
	 * @param game
	 * @param player
	 * @return	<code>true</code> if has placed the tile. <code>false</code> if placement was not completed 
	 * (could not find any placement that would result in a dedication on next turn). 
	 */
	private boolean tryPlaceGetDedication(Game game, Player player) {
		boolean placed = false;
		DedicationForecaster forecaster = DedicationForecaster.getInstance();
		int TO_RECEIVE = 1;
		
		// check if four of a kind (foak) is possible
		LanternCardWrapper[] foak = forecaster.getNextFourOfAKind(player);
		if (foak != null) {
			placed = tryPlacing(game, player, foak[TO_RECEIVE].getColour());
		}
		// if not placed, try three pairs (tp)
		if (!placed) {
			LanternCardWrapper[] tp = forecaster.getNextThreePairs(player);
			if(tp != null) {
				placed = tryPlacing(game, player, tp[TO_RECEIVE].getColour());
			}
		}
		// if still not placed, try seven unique (su)
		if (!placed) {
			LanternCardWrapper[] su = forecaster.getNextSevenUnique(player);
			if(su != null) {
				placed = tryPlacing(game, player, su[TO_RECEIVE].getColour());
			}
		}
		
		return placed;
	}
	
	private boolean tryPlacing(Game game, Player player, Colour colour) {
		boolean placed = false;
		TileIdIndexSideIndex lakeTile = null;
		TileIdIndexSideIndex playerTile = null;
		
		// first, try to check if can get lantern necessary for dedication from matching lake colour
		lakeTile = findGameTileForDedication(game, player, colour);
		if (lakeTile != null) {
			playerTile = getNextPlayerFreeTile(player);
			controller.placeLakeTile(game, player.getId(), playerTile.tileIndex, lakeTile.tileId, lakeTile.sideIndex, playerTile.sideIndex);
			placed = true;
			// if cannot, try to get lantern necessary for dedication from matching player tile
		} else {
			playerTile = findPlayerTileForDedication(player, colour);
			if (playerTile != null) {
				// need to get opposite side, so colour side will be facing player
				Direction[]  directions = MatrixOrganizer.Direction.values();
				Direction opposite = MatrixOrganizer.getOppositeTileSideIndex(directions[playerTile.sideIndex]);
				lakeTile = getNextLakeFreeTile(game);
				controller.placeLakeTile(game, player.getId(), playerTile.tileIndex, lakeTile.tileId, lakeTile.sideIndex, opposite.ordinal());
				placed = true;
			}
		}
		
		return placed;
	}
	
	private int getPlayerPosition(Game game, Player player) {
		int index = -1;
		for (int i = 0; i < game.getPlayers().length; i++) {
			Player current = game.getPlayers()[i];
			if (current.equals(player)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	private TileIdIndexSideIndex findGameTileForDedication(Game game, Player player, Colour colour) {
		LakeTile[][] lake = game.getLake();
		TileIdIndexSideIndex tiisi = null;
		int playerIndex = getPlayerPosition(game, player);
		
		// iterate over tiles on lake
		lakeiteration:
		for (int i = 0; i < lake.length; i++) {
			for (int j = 0; j < lake[i].length; j++) {
				LakeTile current = lake[i][j];
				// if it is a valid tile
				if (current != null) {
					TileSide facingSide = current.getSides()[playerIndex];
					
					// check if side facing player is avaliable, and colour matches desired
					if ((facingSide.getAdjacent() == null) && (facingSide.getColour().equals(colour))) {
						tiisi = new TileIdIndexSideIndex(current.getId(), -1, playerIndex);
						break lakeiteration;
					}
				}
			}
		}
		
		return tiisi;
	}
	
	private TileIdIndexSideIndex findPlayerTileForDedication(Player player, Colour colour) {
		TileIdIndexSideIndex tiisi = null;
		
		// iterate through player tiles to find a match
		searchTiles:
		for (int i = 0; i < player.getTiles().size(); i++) {
			LakeTile tile = player.getTiles().get(i);
			for (int j = 0; j < tile.getSides().length; j++) {
				TileSide side = tile.getSides()[j];
				// check if side has same colour
				if (colour.equals(side.getColour())) {
					tiisi = new TileIdIndexSideIndex(tile.getId(), i, j);
					break searchTiles;
				}
			}
		}
		return tiisi;
	}

	class TileIdIndexSideIndex {
		private final String tileId;
		private final int tileIndex;
		private final int sideIndex;
		
		/**
		 * Constructor of helper class TileIdIndexSideIndex.
		 * @param tileId	Id of the tile, as in {@link Player#getId()}.
		 * @param tileIndex	In case of player, the index in the {@link Player#getTiles()}. In case of game, 
		 * it is not used (use a negative number to mark it as invalid).
		 * @param sideIndex	The side index in {@link LakeTile#getSides()}.
		 */
		public TileIdIndexSideIndex(String tileId, int tileIndex, int sideIndex) {
			super();
			this.tileId = tileId;
			this.tileIndex = tileIndex;
			this.sideIndex = sideIndex;
		}

		public String getTileId() {
			return tileId;
		}

		public int getTileIndex() {
			return tileIndex;
		}

		public int getSideIndex() {
			return sideIndex;
		}
	}

}
