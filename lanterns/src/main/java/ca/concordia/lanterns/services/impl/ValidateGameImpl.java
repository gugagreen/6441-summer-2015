package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.services.ValidateGame;
import ca.concordia.lanternsentities.*;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

import java.util.*;


/**
 * This is an implementation of {@link ValidateGame}.
 */
public class ValidateGameImpl implements ValidateGame {

    public void validatePlayerCount(Game game) {
        Player[] players = game.getPlayers();
        if (players.length >= MIN_PLAYERS && players.length <= MAX_PLAYERS) {
            Set<Integer> uniqueID = new HashSet<Integer>();
            for (int i = 0; i != players.length; ++i) {
                if (!(uniqueID.contains(players[i].getId()))) {
                    uniqueID.add(players[i].getId());
                } else {
                    throw new IllegalArgumentException("The players have duplicate ID");
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid number of players");
        }
    }

    /**
     * Method used to validate that the correct number of lantern cards are in a game.
     * Lanterns card numbers are dependent on the number of players in the game.
     */
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
                throw new IllegalArgumentException("Insufficient Lantern Cards for the color: " + Colour.values()[i].toString());
            }
        }
    }

    @Override
    public void validateStartPlayerMarker(final Player[] player, final int startPlayerMarker) {
        for (int i = 0; i != player.length; ++i) {
            if (player[i].getId() == startPlayerMarker) {
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
    public void validateLakeTileStack(final Player[] players, final LakeTile[][] lake, final Stack<LakeTile> tileStack,
                                      final int currentTurnPlayer) {
    	int lakeSize = MatrixOrganizer.count(lake);
        int sum = lakeSize + tileStack.size();
        for (int i = 0; i != players.length; ++i) {
            sum = sum + players[i].getTiles().size();
        }
        if ((players.length == 4 && sum != 33) || (players.length == 3 && sum != 28) || (players.length == 2 && sum != 23)) {
            throw new IllegalArgumentException("The quantity of Lake Tiles in the game has been Compromised");
        }
        //Validates that all players have no more than 3 lake tiles in their hand
        for (int i = 0; i != players.length; ++i) {

            if (players[i].getTiles().size() > 3) {
                throw new IllegalArgumentException("Player: " + players[i].getName() + "has too many Lake Tiles");
            }
        }
        //Validates that all players have 3 lake tiles for the beginning of the game, after this point players are allowed to have 2,1,0 tiles until game ends.
        for (int i = 0; i != players.length; ++i) {

            if ((players.length == 4 && lakeSize < 21) || (players.length == 3 && lakeSize < 19) || (players.length == 2 && lakeSize < 17)) {

                if ((players[i].getTiles().size() != 3) && (lakeSize > 0) && (players[i].getId() != currentTurnPlayer)) {
                    throw new IllegalArgumentException("Player: " + players[i].getName() + "has insufficient Lake Tiles");
                }
            }
        }

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
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
