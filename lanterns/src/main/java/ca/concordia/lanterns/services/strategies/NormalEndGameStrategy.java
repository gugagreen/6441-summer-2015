package ca.concordia.lanterns.services.strategies;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * provide the service of ending game normally.
 * Created by Ruixiang on 8/9/2015 .
 */
public class NormalEndGameStrategy implements EndGameStrategy{
	
	/**
     * It ends the game in normal way.
     *
    * @param {@link Game} object.
    * @return boolean object specifying the end of game result.
     */
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
