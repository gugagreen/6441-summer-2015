package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.services.EndGameStrategy;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * Created by Ruixiang on 8/9/2015.
 */
public class NormalEndGameStrategy implements EndGameStrategy{

    @Override
    public boolean isGameEnded(Game game) {

        int playerNum = game.getPlayers().length;
        int count = 0;

        for (Player player : game.getPlayers()) {
            if (player.getTiles().isEmpty()) {
                count++;
            }
        }

        boolean isLastTurn = game.getTiles().empty() && (count == playerNum);
        boolean isLastPlayer = game.getCurrentTurnPlayer() == (game.getPlayers().length - 1);

        // if it is the last turn and it is the last player, game is ended
        return (isLastTurn && isLastPlayer);

    }

}
