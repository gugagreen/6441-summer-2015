package ca.concordia.lanterns.services.strategies;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * the game will end as soon as any one player has made enough dedications to earn N Honor points 
 * (where the value of N is chosen by the user, and must be at least 4 and at most M,
 *  where M is the sum of the values of all Dedication Tokens in the game divided by the number of players) 
 * Created by Ruixiang on 8/9/2015.
 */
public class NHonorPointsEndGameStrategy implements EndGameStrategy {

    private int nHonorPoint;

    public NHonorPointsEndGameStrategy(int nHonorPoint) {
        this.nHonorPoint = nHonorPoint;
    }
    
    /**
     * Declare the end of game.
     *
    * @param {@link Game} object.
    * @return boolean object specifying the end of game result.
     */
    @Override
    public boolean isGameEnded(Game game) {

        NormalEndGameStrategy normalEndGameStrategy = new NormalEndGameStrategy();

        if (!normalEndGameStrategy.isGameEnded(game)) {

            int sum = 0;
            for (Player player : game.getPlayers()) {
                for (DedicationToken dedicationToken : player.getDedications())
                    sum += dedicationToken.getTokenValue();

                if (sum >= nHonorPoint)
                    return true;

                sum = 0;
            }
            return false;
        }
        return true;
    }
}
