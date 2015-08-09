package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.EndGameService;
import ca.concordia.lanterns.services.EndGameStrategy;
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
    private EndGameStrategy endGameStrategy;

    //The default end game strategy is the normal way, please do remember to set the strategy first.
    private EndGameDetectService()
    {
        this.endGameStrategy = new NormalEndGameStrategy();
    }

    public void setEndGameStrategy(EndGameStrategy endGameStrategy)
    {
        this.endGameStrategy = endGameStrategy;
    }

	public boolean isGameEnded(Game game) {
		return endGameStrategy.isGameEnded(game);
	}

	public Set<Player> getGameWinner(Game game) {
		if (isGameEnded(game)) {
			Set<Player> winners = new HashSet<Player>();

			for (Player player : game.getPlayers()) {
				// if it is the first player, just add it
				if (winners.isEmpty()) {
					winners.add(player);
					// otherwise, compare with existent winners
				} else {
					Player winnerHolder = winners.iterator().next();
					int sum = countDedications(player);
					int mostHonor = countDedications(winnerHolder);
					
					// if current player has the most dedications, it is the winner
					if (sum > mostHonor) {
						winners = new HashSet<Player>();
						winners.add(player);
						// otherwise, go to tie break
					} else if (sum == mostHonor) {
						// if current player has the most favors, it is the winner
						if (player.getFavors() > winnerHolder.getFavors()) {
							winners = new HashSet<Player>();
							winners.add(player);
							// otherwise, go to tie break
						} else if (player.getFavors() == winnerHolder.getFavors()) {
							int playerRemainCard = countCards(player);
							int winnerHolderRemainCard = countCards(winnerHolder);
							// if current player has the most cards, it is the winner
							if (playerRemainCard > winnerHolderRemainCard) {
								winners = new HashSet<Player>();
								winners.add(player);
								// otherwise, it is a tie
							} else if (playerRemainCard == winnerHolderRemainCard) {
								winners.add(player);
							}
						}
					}
				}
			}

			return winners;
		} else {
			throw new GameRuleViolationException("Game is not ended yet. Cannot calculate winner.");
		}
	}
	
	private int countDedications(Player player) {
		int sum = 0;
		for (DedicationToken dedicationToken : player.getDedications()) {
			sum += dedicationToken.getTokenValue();
		}
		return sum;
	}
	
	private int countCards(Player player) {
		int sum = 0;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			sum += lanternCardWrapper.getQuantity();
		}
		return sum;
	}

	private static class SingletonHolder {
		static final EndGameDetectService INSTANCE = new EndGameDetectService();
	}
}
