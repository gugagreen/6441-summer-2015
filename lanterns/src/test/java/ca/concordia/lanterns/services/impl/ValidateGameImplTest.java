package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Stack;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.services.ValidateGame;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanterns.services.SetupService;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class ValidateGameImplTest {

	private Game game;
	private ValidateGame gameValidator;

	@Before
	public void Setup() {
		String[] playerNames = { "A", "B", "C" };

		game = new Game();
		game.init(playerNames, "validateGame");
		this.gameValidator = new ValidateGameImpl();

		DefaultSetupService setUpService = new DefaultSetupService();
		this.game = setUpService.createGame(playerNames);

	}

	@Test
	public void testValidateLakeTileCreation() {

		Player[] players = game.getPlayers();
		List<LakeTile> lake = game.getLake();
		Stack<LakeTile> lakeTile = game.getTiles();
		int currentTurnPlayer = 1;

		// Checking to see that all useful entities were properly instantiated
		assertEquals(players.length, 3);
		assertEquals(lake.size(), 1);
		assertEquals(lakeTile.size(), 18);

		// Checking to see if all players have exactly 3 lake tiles (since we
		// instantiated a new game at turn 1)
		for (int i = 0; i != players.length; ++i) {
			assertEquals(players[i].getTiles().size(), 3);
		}

		// Checking to make sure that the proper total number of lake tiles is
		// in the game
		int sum = 0;
		sum = sum + lake.size() + lakeTile.size();
		for (int i = 0; i != players.length; ++i) {
			sum = sum + players[i].getTiles().size();
		}

		assertEquals(sum, 28);

		gameValidator.validateLakeTileStack(players, lake, lakeTile, currentTurnPlayer);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testValidLakeTileQuantity() throws IllegalArgumentException {

		// Get correct number of players (3)
		Player[] correctPlayers = game.getPlayers();

		// create new array with room only for 2 players to remove one of the
		// players (now 2 player)
		Player[] twoPlayers = new Player[2];
		System.arraycopy(correctPlayers, 0, twoPlayers, 0, 2);
		Player[] players = twoPlayers;

		// instantiate rest of game as though everything was a normal new game
		List<LakeTile> lake = game.getLake();
		Stack<LakeTile> lakeTile = game.getTiles();
		int currentTurnPlayer = 1;

		gameValidator.validateLakeTileStack(players, lake, lakeTile, currentTurnPlayer);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLessPlayerLakeTiles() throws IllegalArgumentException {

		// Get correct number of players (3)
		Player[] players = game.getPlayers();
		
		//TODO Change one players number of LakeTiles to have less than 3

		// instantiate rest of game as though everything was a normal new game
		List<LakeTile> lake = game.getLake();
		Stack<LakeTile> lakeTile = game.getTiles();
		int currentTurnPlayer = 0;

		gameValidator.validateLakeTileStack(players, lake, lakeTile, currentTurnPlayer);
		
		//FIXME remove line below when test exists
		throw new IllegalArgumentException();

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMorePlayerLakeTiles() throws IllegalArgumentException {

		// Get correct number of players (3)
		Player[] players = game.getPlayers();
		
		//TODO Change one players number of LakeTiles to have more than 3

		// instantiate rest of game as though everything was a normal new game
		List<LakeTile> lake = game.getLake();
		Stack<LakeTile> lakeTile = game.getTiles();
		int currentTurnPlayer = 0;

		gameValidator.validateLakeTileStack(players, lake, lakeTile, currentTurnPlayer);
		
		//FIXME remove line below when test exists
		throw new IllegalArgumentException();

	}
}