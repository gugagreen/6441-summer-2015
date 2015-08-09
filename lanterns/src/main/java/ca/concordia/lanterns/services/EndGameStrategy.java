package ca.concordia.lanterns.services;

import ca.concordia.lanternsentities.Game;

/**
 * Created by Ruixiang on 8/9/2015.
 */
public interface EndGameStrategy {

    public boolean isGameEnded(Game game);

}
