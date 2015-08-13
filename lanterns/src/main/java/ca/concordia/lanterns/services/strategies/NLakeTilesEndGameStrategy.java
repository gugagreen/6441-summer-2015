package ca.concordia.lanterns.services.strategies;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;

/**
 * provide the service of ending game as soon as each player has placed N Lake Tiles on the board 
 * (where the value of N is chosen by the user, and must be at least 2 and at most M, 
 * where M is the number of Lake Tiles in the original draw stack divided by the number of players) .
 * Created by Ruixiang on 8/9/2015 .
 */
public class NLakeTilesEndGameStrategy implements EndGameStrategy {

    private int nLakeTiles;

    public NLakeTilesEndGameStrategy(int nLakeTiles) {
        this.nLakeTiles = nLakeTiles;
    }
    
    /**
     * Declare the end of game.
     *
    * @param {@link Game} object.
    * @return boolean object specifying the end of game result.
     */
    @Override
    public boolean isGameEnded(Game game) {

        return getCurrentLakeSize(game) / game.getPlayers().length == nLakeTiles;

    }

    private int getCurrentLakeSize(Game game) {
        LakeTile[][] lake = game.getLake();

        int sum = 0;
        for (int i = 0; i < lake.length; i++) {
            for (int j = 0; j < lake[i].length; j++) {
                if (lake[i][j] != null)
                    sum++;
            }
        }

        return sum - 1;
    }
}
