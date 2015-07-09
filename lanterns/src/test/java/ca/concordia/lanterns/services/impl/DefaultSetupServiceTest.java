package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
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
		String player1 = "one";
		String player2 = "two";
		String player3 = "three";
		String player4 = "four";
		final Set<String> playerNames = new HashSet<String>(Arrays.asList(new String[] {player1, player2, player3, player4}));
		
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
}
