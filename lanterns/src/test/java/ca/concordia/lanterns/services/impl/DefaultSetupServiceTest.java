package ca.concordia.lanterns.services.impl;

import java.util.Stack;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;

public class DefaultSetupServiceTest {
	
	private DefaultSetupService service;
	
	@Before
	public void setup() {
		this.service = new DefaultSetupService();
	}
	
	@Test
	public void testCreateGameSuccess() {
		final String[] playerNames = createPlayerNames(4);
		
		Game game = service.createGame(playerNames);
		
		assertNotNull(game);
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
		assertNull(tiles);
	}
	
	private void callGenerateTiles(int playerCount, int expected) {
		LakeTile[] tiles = service.generateTiles(playerCount);
		assertNotNull(tiles);
		// 33 = 20 + 12 (3 tiles per player) + 1 initial tile
		assertEquals(expected, tiles.length);
	}
	
	@Test
	public void testStartLake() {
		Lake lake = new Lake();
		Colour[] colours = {};
		LakeTile initialTile = new LakeTile(colours, false);
		service.startLake(lake, initialTile);
		
		assertEquals(1, lake.getTiles().size());
		assertEquals(initialTile, lake.getTiles().get(0));
	}
	
	@Test
	public void testDealPlayerTiles() {
		final Player[] players = createPlayers(3);
		final LakeTile[] totalTiles = new LakeTile[36];
		for (int i = 0; i < totalTiles.length; i++) {
			totalTiles[i] = new LakeTile(new Colour[4], false);
		}
		
		service.dealPlayerTiles(totalTiles, players);
		for (Player player : players) {
			assertEquals(3, player.getTiles().size());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDealPlayerTilesWithNoTiles() {
		final Player[] players = createPlayers(3);
		final LakeTile[] totalTiles = {};
		service.dealPlayerTiles(totalTiles, players);
	}
	
	@Test
	public void testDrawTileStackFourPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 4);
		Assert.assertEquals(20, tiles.size());
	}
	
	@Test
	public void testDrawTileStackThreePlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 3);
		Assert.assertEquals(18, tiles.size());
	}
	
	@Test
	public void testDrawTileStackTwoPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 2);
		Assert.assertEquals(16, tiles.size());
	}
	
	@Test
	public void testDrawTileStackZeroPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 0);
		Assert.assertTrue(tiles.isEmpty());
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
	
	@Test
	public void testSetDedicationTokens() {
		// FIXME - implement
	}
	
	@Test
	public void testDistributeInitialLanterns() {
		// FIXME - implement
	}
	
	private String[] createPlayerNames(int quantity) {
		String[] playerNames = new String[quantity];
		for (int i = 0; i < quantity; i++) {
			playerNames[i] = Integer.toString(i);
		}
		return playerNames;
	}
	
	private Player[] createPlayers(int quantity) {
		Player[] players = new Player[quantity];
		for (int i = 0; i < quantity; i++) {
			players[i] = new Player(Integer.toString(i));
		}
		return players;
	}
}
