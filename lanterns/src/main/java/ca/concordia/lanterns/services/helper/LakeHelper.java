package ca.concordia.lanterns.services.helper;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.Direction;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.TileSide;

public class LakeHelper {

	private static final List<Direction> directions = Arrays.asList(Direction
			.values());
	private static HashSet<LakeTile> visitedTiles;
	private static LakeTile startTile;
	private static Point location;

	public static void setAdjacentLakeTiles(LakeTile newTile, LakeTile firstNeighbour, Direction firstNeighbourDirection) {
		startTile = newTile;
		location = new Point();
		visitedTiles = new HashSet<LakeTile>();
		
		firstNeighbourDirection.moveForward(location);
		
		dfs(firstNeighbour);
	}

	private static void dfs(LakeTile tile) {
		visitedTiles.add(tile);

		Point startLocation = new Point(location);

		for (int i = 0; i != LakeTile.TOTAL_SIDES; ++i) {

			LakeTile adjTile = tile.getSides()[i].getAdjacent();

			if (adjTile != null && ! (visitedTiles.contains(adjTile))) {
				directions.get(i).moveForward(location);

				dfs(adjTile);
			}
		}

		setAsAdjacent(tile);

		location.setLocation(startLocation);
	}

	private static void setAsAdjacent(LakeTile neighbour) {

		for (int i = 0; i != directions.size(); ++i) {

			Point neighbourPoint = directions.get(i).getPoint();

			if (location.equals(neighbourPoint)) {

				TileSide neighbourSide = neighbour.getSides()[directions.get(i)
						.getOppositeTileSideIndex()];
				TileSide startTileSide = startTile.getSides()[directions.get(i)
						.getTileSideIndex()];

				if (neighbourSide.getAdjacent() == null
						&& startTileSide.getAdjacent() == null) {
					neighbourSide.setAdjacent(startTile);
					startTileSide.setAdjacent(neighbour);
					break;
				} else {
					throw new GameRuleViolationException ("The placement of the lake is invalid");
				}
			}
		}
	}
}