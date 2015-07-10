package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupServiceTest {
	
	private DefaultSetupService service;
	
	@Before
	public void setup() {
		this.service = new DefaultSetupService();
	}
	
	@Test
	public void testCreateGameSuccess() {
		final Set<String> playerNames = createPlayerNames(4);
		
		Game game = service.createGame(playerNames);
		
		Assert.assertNotNull(game);
		// FIXME - finish test
	}
	
	@Test
	public void testGenerateTiles4Players() {
		// expected 33 = 20 + 12 (3 tiles per player) + 1 initial tile
		callGenerateTiles(4, 33);
	}
	
	@Test
	public void testGenerateTiles3Players() {
		// expected 33 = 18 + 9 (3 tiles per player) + 1 initial tile
		callGenerateTiles(3, 28);
	}
	
	@Test
	public void testGenerateTiles2Players() {
		// expected 33 = 16 + 6 (3 tiles per player) + 1 initial tile
		callGenerateTiles(2, 23);
	}
	
	@Test
	public void testGenerateTiles0Players() {
		LakeTile[] tiles = service.generateTiles(0);
		Assert.assertNull(tiles);
	}
	
	private void callGenerateTiles(int playerCount, int expected) {
		LakeTile[] tiles = service.generateTiles(playerCount);
		Assert.assertNotNull(tiles);
		// 33 = 20 + 12 (3 tiles per player) + 1 initial tile
		Assert.assertEquals(expected, tiles.length);
	}
	
	@Test
	public void testStartLake() {
		Lake lake = new Lake();
		Colour[] colours = {};
		LakeTile initialTile = new LakeTile(colours, false);
		service.startLake(lake, initialTile);
		
		Assert.assertEquals(1, lake.getTiles().size());
		Assert.assertEquals(initialTile, lake.getTiles().get(0));
	}
	
	@Test
	public void testDealPlayerTiles() {
		final Set<Player> players = createPlayers(3);
		final LakeTile[] totalTiles = new LakeTile[36];
		for (int i = 0; i < totalTiles.length; i++) {
			totalTiles[i] = new LakeTile(new Colour[4], false);
		}
		
		service.dealPlayerTiles(totalTiles, players);
		for (Player player : players) {
			Assert.assertEquals(3, player.getTiles().size());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDealPlayerTilesWithNoTiles() {
		final Set<Player> players = createPlayers(3);
		final LakeTile[] totalTiles = {};
		service.dealPlayerTiles(totalTiles, players);
	}
	
	@Test
	public void testDrawTileStack() {
		// FIXME - implement
	}
	
	@Test
	public void testSeparateLanternCards() {
		// FIXME - implement
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = IllegalArgumentException.class)
	public void testSeparateLanternCardsWithWrongStack() {
		final Stack<LanternCard>[] cards = (Stack<LanternCard>[]) new Stack[1];
		service.separateLanternCards(cards, 4);
	}
	
	private Set<String> createPlayerNames(int quantity) {
		Set<String> playerNames = new HashSet<String>();
		for (int i = 0; i < quantity; i++) {
			playerNames.add(Integer.toString(i));
		}
		return playerNames;
	}
	
	private Set<Player> createPlayers(int quantity) {
		Set<Player> players = new HashSet<Player>();
		for (int i = 0; i < quantity; i++) {
			players.add(new Player(Integer.toString(i)));
		}
		return players;
	}
}
