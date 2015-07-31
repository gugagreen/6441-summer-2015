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
 * Enables setting of attributes to lake tiles.
 */
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

        dfs(firstNeighbour, new Point());
    }

    private static void dfs(LakeTile tile, Point cameFrom) {
        visitedTiles.add(tile);

        Point startLocation = new Point(cameFrom);

        Point myLocation = new Point(location);

        for (int i = 0; i != LakeTile.TOTAL_SIDES; ++i) {

            LakeTile adjTile = tile.getSides()[i].getAdjacent();

            if (adjTile != null && !(visitedTiles.contains(adjTile))) {

                directions.get(i).moveForward(location);

                dfs(adjTile, myLocation);
            }
        }

        setAsAdjacent(tile);

        location.setLocation(cameFrom);


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
                    throw new GameRuleViolationException("The placement of the lake is invalid");
                }
            }
        }
    }
}