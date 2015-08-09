package ca.concordia.lanterns.tileplacement.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.dedication.DedicationForecaster;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;

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
				LanternCardWrapper[] giveReceive = DedicationForecaster.getInstance().getNextDedication(player);
				// 2) If not, check if there is a dedication close (get missing card), and place a tile to get it
				if (giveReceive != null) {
					// FIXME - implement
				} else { // 3) if none of those, get random
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

	class TileIdIndexSideIndex {
		private final String tileId;
		private final int tileIndex;
		private final int sideIndex;
		
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
