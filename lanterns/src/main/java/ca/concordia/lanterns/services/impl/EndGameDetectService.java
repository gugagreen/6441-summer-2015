package ca.concordia.lanterns.services.impl;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Created by Ruixiang on 7/27/2015.
 */
public class EndGameDetectService {

    public boolean isGameEnded (Game currentGame)
    {
        int playerNum = currentGame.getPlayers().length;
        int count = 0;

        for (Player player : currentGame.getPlayers())
        {
            if (player.getTiles().isEmpty())
                count++;
        }

        return count == playerNum;
    }

    public Player getGameWinner (Game currentGame)
    {
        Player winnerHolder = currentGame.getPlayers()[0];
        int sum = 0;
        int mostHonor = 0;

        for (Player player : currentGame.getPlayers())
        {
            for ( DedicationToken dedicationToken : player.getDedications())
                sum += dedicationToken.getTokenValue();

            if (sum > mostHonor) {
                mostHonor = sum;
                winnerHolder = player;
                sum = 0;
            }
            //In the case of a tie
            if (sum == mostHonor)
            {
                if (player.getFavors() > winnerHolder.getFavors())
                    winnerHolder = player;
                sum = 0;
            }
        }

        return winnerHolder;
    }
}
