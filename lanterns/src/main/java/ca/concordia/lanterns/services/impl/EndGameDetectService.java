package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.EndGameService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ruixiang on 7/27/2015.
 */
public class EndGameDetectService implements EndGameService {

    public static EndGameDetectService getInstance() {
        return SingletonHolder.INSTANCE;
    }

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

    public Set<Player> getGameWinner(Game game) {
        if (isGameEnded(game)) {
            Set<Player> winners = new HashSet<Player>();
            Player winnerHolder = game.getPlayers()[0];
            Player secondPlace = new Player();
            int sum = 0;
            int mostHonor = 0;

            //testing dedications
            for (Player player : game.getPlayers()) {
                for (DedicationToken dedicationToken : player.getDedications()) {
                    sum += dedicationToken.getTokenValue();
                }

                if (sum > mostHonor) {
                    mostHonor = sum;
                    winnerHolder = player;
                }
                if (sum == mostHonor) {
                    secondPlace = player;
                }
            }
            // In the case of a tie
            if (sum == mostHonor) {
                if (secondPlace.getFavors() > winnerHolder.getFavors()) {
                    winnerHolder = secondPlace;
                }
                //In the case of a further tie
                if (secondPlace.getFavors() == winnerHolder.getFavors()) {
                    int playerRemainCard = 0;
                    int winnerHolderRemainCard = 0;

                    for (LanternCardWrapper lanternCardWrapper : secondPlace.getCards())
                        playerRemainCard += lanternCardWrapper.getQuantity();

                    for (LanternCardWrapper lanternCardWrapper : winnerHolder.getCards())
                        winnerHolderRemainCard += lanternCardWrapper.getQuantity();

                    if (playerRemainCard > winnerHolderRemainCard) {
                        winnerHolder = secondPlace;
                    } else {
                        //game is a tie
                        winners.add(winnerHolder);
                        winners.add(secondPlace);
                        return winners;
                    }
                }
            }

            sum = 0;

            // FIXME - implement other tie cases

            winners.add(winnerHolder);

            return winners;
        } else {
            throw new GameRuleViolationException("Game is not ended yet. Cannot calculate winner.");
        }
    }

    private static class SingletonHolder {
        static final EndGameDetectService INSTANCE = new EndGameDetectService();
    }
}
