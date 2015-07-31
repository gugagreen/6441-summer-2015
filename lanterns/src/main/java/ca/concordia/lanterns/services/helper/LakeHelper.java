package ca.concordia.lanterns.services.helper;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.Direction;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.TileSide;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * LakeHelper helps in finding the tiles adjacent to any given Lake tile when that tile will be placed in the lake.
 */
public class LakeHelper {

    private static final List<Direction> directions = Arrays.asList(Direction
            .values());
    private static HashSet<LakeTile> visitedTiles;
    private static LakeTile startTile;
    private static Point location;

    /**
     * Finds the tiles adjacent to a given lake tile and sets them as adjacent in appropriate data structures.
     * @param newTile - The LakeTile to be placed in the lake
     * @param firstNeighbour - One of the adjacent tiles of this lake tile.
     * @param firstNeighbourDirection - A {@link Direction} object describing the direction of the firstNeighbour
     */
    public static void setAdjacentLakeTiles(LakeTile newTile, LakeTile firstNeighbour, Direction firstNeighbourDirection) {
        startTile = newTile;
        location = new Point();
        visitedTiles = new HashSet<LakeTile>();

        // Increment location
        firstNeighbourDirection.moveForward(location);

        dfs(firstNeighbour, new Point());
    }

    // Perform depth first search through the graph of laketiles in the lake. The graph is stored as an adjacency list in game.
    private static void dfs(LakeTile tile, Point cameFrom) {
        // Mark visited
    	visitedTiles.add(tile);

    	// location at the beining of this iteration
        Point startLocation = new Point(cameFrom);

        Point myLocation = new Point(location);

        for (int i = 0; i != LakeTile.TOTAL_SIDES; ++i) {

            LakeTile adjTile = tile.getSides()[i].getAdjacent();

            if (adjTile != null && !(visitedTiles.contains(adjTile))) {

                directions.get(i).moveForward(location);

                // Recursive Call
                dfs(adjTile, myLocation);
            }
        }

        setAsAdjacent(tile);

        location.setLocation(cameFrom);


    }

    // Through location, it checks if calling tile is adjacent to the startTile
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
                    throw new GameRuleViolationException("The placement of the lake is invalid");
                }
            }
        }
    }
}