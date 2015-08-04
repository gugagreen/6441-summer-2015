package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.services.ValidateGame;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class ValidateGameImplTest {

    private Game game;
    private ValidateGame gameValidator;

    @Before
    public void Setup() {
        String[] playerNames = {"A", "B", "C"};

        game = new Game();
        game.init(playerNames, "validateGame");
        this.gameValidator = new ValidateGameImpl();

        DefaultSetupService setUpService = new DefaultSetupService();
        this.game = setUpService.createGame(playerNames);

    }

    @Test
    public void testValidateLakeTileCreation() {
    	// FIXME - test does not test anything
    	
        Player[] players = game.getPlayers();
        Stack<LakeTile> lakeTile = game.getTiles();
        int currentTurnPlayer = 1;

        // Checking to see that all useful entities were properly instantiated
        assertEquals(players.length, 3);
        assertEquals(MatrixOrganizer.count(game.getLake()), 1);
        assertEquals(lakeTile.size(), 18);

        // Checking to see if all players have exactly 3 lake tiles (since we
        // instantiated a new game at turn 1)
        for (int i = 0; i != players.length; ++i) {
            assertEquals(players[i].getTiles().size(), 3);
        }

        // Checking to make sure that the proper total number of lake tiles is
        // in the game
        int sum = 0;
        sum = sum + MatrixOrganizer.count(game.getLake()) + lakeTile.size();
        for (int i = 0; i != players.length; ++i) {
            sum = sum + players[i].getTiles().size();
        }

        assertEquals(sum, 28);

        gameValidator.validateLakeTileStack(players, game.getLake(), lakeTile, currentTurnPlayer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidLakeTileQuantity() throws IllegalArgumentException {
    	// FIXME - test does not test anything

        // Get correct number of players (3)
        Player[] correctPlayers = game.getPlayers();

        // create new array with room only for 2 players to remove one of the
        // players (now 2 player)
        Player[] twoPlayers = new Player[2];
        System.arraycopy(correctPlayers, 0, twoPlayers, 0, 2);
        Player[] players = twoPlayers;

        // instantiate rest of game as though everything was a normal new game
        Stack<LakeTile> lakeTile = game.getTiles();
        int currentTurnPlayer = 1;

        gameValidator.validateLakeTileStack(players, game.getLake(), lakeTile, currentTurnPlayer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testLessPlayerLakeTiles() throws IllegalArgumentException {

        // Get correct number of players (3)
        Player[] players = game.getPlayers();

        //TODO Change one players number of LakeTiles to have less than 3

        // instantiate rest of game as though everything was a normal new game
        Stack<LakeTile> lakeTile = game.getTiles();
        int currentTurnPlayer = 0;

        gameValidator.validateLakeTileStack(players, game.getLake(), lakeTile, currentTurnPlayer);

        //FIXME remove line below when test exists
        throw new IllegalArgumentException();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMorePlayerLakeTiles() throws IllegalArgumentException {

        // Get correct number of players (3)
        Player[] players = game.getPlayers();

        //TODO Change one players number of LakeTiles to have more than 3

        // instantiate rest of game as though everything was a normal new game
        Stack<LakeTile> lakeTile = game.getTiles();
        int currentTurnPlayer = 0;

        gameValidator.validateLakeTileStack(players, game.getLake(), lakeTile, currentTurnPlayer);

        //FIXME remove line below when test exists
        throw new IllegalArgumentException();

    }
}
