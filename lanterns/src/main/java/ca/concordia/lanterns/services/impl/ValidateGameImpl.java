package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Lake;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.enums.PlayerID;
import ca.concordia.lanterns.services.ValidateGame;

public class ValidateGameImpl implements ValidateGame {

	public void validatePlayerCount(Game game) {
		Player[] players = game.getPlayers();
		if (players.length >= 2 && players.length <= 4) {
			Set<PlayerID> uniqueID = new HashSet<PlayerID>();
			for (int i = 0; i != players.length; ++i) {
				if (!(uniqueID.contains(players[i].getID()))) {
					uniqueID.add(players[i].getID());
				} else {
					throw new IllegalArgumentException("The players have duplicate ID");
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid number of players");
		}
	}

	public void validateLanternCards(Game game) {
		Player[] players = game.getPlayers();
		LanternCardWrapper[] cards = game.getCards();
		for (int i = 0; i != cards.length; ++i) {
			int sum = 0;
			// sum all players cards
			for (int j = 0; j != players.length; ++j) {
				sum = sum + players[j].getCards()[i].getQuantity();
			}
			// add cards on table
			sum = sum + cards[i].getQuantity();
			if ((players.length == 4 && sum == 8) || (players.length == 3 && sum == 7) || (players.length == 2 && sum == 5))
				continue;
			else {
				throw new IllegalArgumentException("Insufficient Lanter Cards for the color: " + Colour.values()[i].toString());
			}
		}
	}

	@Override
	public void validateStartPlayerMarker(final Player[] player, final PlayerID startPlayerMarker) {
		for (int i = 0; i != player.length; ++i) {
			if (player[i].getID() == startPlayerMarker) {
				return;
			}
		}
		throw new IllegalArgumentException("Incorrect Start Player Marker");
	}

	@Override
	public void validateFavorToken(final Game game) {
		Player[] player = game.getPlayers();
		int sum = 0;
		for (int i = 0; i != player.length; ++i) {
			sum = sum + player[i].getFavors();
		}

		sum = sum + game.getFavors();

		if (sum != 20) {
			throw new IllegalArgumentException("The quantity of favor tokens is compromised");
		}
	}

	@Override
	public void validateLakeTileStack(final Player[] players, final Lake lake, final Stack<LakeTile> lakeTile,
			final PlayerID currentTurnPlayer) {
		int sum = 0;
		sum = sum + lake.getTiles().size() + lakeTile.size();
		for (int i = 0; i != players.length; ++i) {
			sum = sum + players[i].getTiles().size();
		}
		if ((players.length == 4 && sum != 33) || (players.length == 3 && sum != 28) || (players.length == 2 && sum != 23)) {
			throw new IllegalArgumentException("The quantity of Lake Tiles in the game have been Compromised");
		}

		for (int i = 0; i != players.length; ++i) {
			if (players[i].getTiles().size() != 3 && !lake.getTiles().isEmpty() && players[i].getID() != currentTurnPlayer) {
				throw new IllegalArgumentException("Player: " + players[i].getName() + "have insufficient Lake Tiles");
			}
		}

	}

	@Override
	public void validateDedicationToken(final DedicationTokenWrapper[] dedications, final Player[] players) {
		DedicationType[] type = DedicationType.values();

		for (int i = 0; i != type.length - 1; ++i) {
			int[] values;
			if (players.length == 4) {
				values = type[i].getValuesFour();
			} else if (players.length == 3) {
				values = type[i].getValuesThree();
			} else {
				values = type[i].getValuesTwo();
			}

			List<Integer> remainingValues = new LinkedList(Arrays.asList(values));
			Stack<DedicationToken> tokenStack = dedications[i].getStack();
			int index = tokenStack.size();
			if (index != 0)
				for (int j = index - 1; j != values.length; ++i) {
					if (values[i] != tokenStack.get(i).getTokenValue()) {
						throw new IllegalArgumentException(type[i].toString() + " stack has been compromised");
					} else {
						remainingValues.remove(j);
					}
				}

			for (int k = 0; k != players.length; ++k) {

				List<DedicationToken> playerToken = players[k].getDedications();
				for (int l = 0; l != playerToken.size(); ++i) {
					if (playerToken.get(l).getTokenType() == type[i]) {
						if (remainingValues.contains(playerToken.get(l).getTokenValue())) {
							remainingValues.remove(playerToken.get(l).getTokenValue());
						} else {
							throw new IllegalArgumentException("Player :" + players[k].getName()
									+ " have illegitimate dedication of type: " + type[i].toString());
						}
					}
				}
			}

			if (!remainingValues.isEmpty()) {
				throw new IllegalArgumentException("The quantity of dedication token of type : " + type[i].toString()
						+ " is compromised");
			}

		}
	}

}
