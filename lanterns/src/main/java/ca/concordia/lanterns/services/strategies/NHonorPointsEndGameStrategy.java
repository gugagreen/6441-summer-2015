package ca.concordia.lanterns.services.strategies;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Created by Ruixiang on 8/9/2015.
 */
public class NHonorPointsEndGameStrategy implements EndGameStrategy {

    private int nHonorPoint;

    public NHonorPointsEndGameStrategy(int nHonorPoint) {
        this.nHonorPoint = nHonorPoint;
    }

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
