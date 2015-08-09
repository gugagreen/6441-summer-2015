package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.services.EndGameStrategy;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;

/**
 * Created by Ruixiang on 8/9/2015.
 */
public class NLakeTilesEndGameStrategy implements EndGameStrategy {

    private int nLakeTiles;

    public NLakeTilesEndGameStrategy(int nLakeTiles) {
        this.nLakeTiles = nLakeTiles;
    }

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

        return sum;
    }
}
