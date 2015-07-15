package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.DedicationToken;
import ca.concordia.lanterns.entities.DedicationTokenWrapper;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.LanternCardWrapper;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.entities.enums.PlayerID;

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
		assertNotNull(game.getPlayers());
		assertEquals(playerNames.length, game.getPlayers().length);
		assertNotNull(game.getCards());
		assertNotNull(game.getDedications());
		assertNotNull(game.getLake());
		assertNotNull(game.getTiles());
		//... validate content
		assertEquals(Game.TOTAL_FAVORS, game.getFavors());
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
		Colour[] colours = {Colour.RED, Colour.BLUE, Colour.GREEN, Colour.PURPLE};
		LakeTile initialTile = new LakeTile();
		initialTile.init(colours, false);
		service.startLake(lake, initialTile);
		
		assertEquals(1, lake.getTiles().size());
		assertEquals(initialTile, lake.getTiles().get(0));
	}
	
	@Test
	public void testDealPlayerTiles() {
		final Player[] players = createPlayers(3);
		final LakeTile[] totalTiles = new LakeTile[36];
		for (int i = 0; i < totalTiles.length; i++) {
			totalTiles[i] = new LakeTile();
			totalTiles[i].init(new Colour[4], false);
		}
		
		service.dealPlayerTiles(totalTiles, players);
		for (Player player : players) {
			assertEquals(3, player.getTiles().size());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDealPlayerTilesWithNoTiles() {
		final Player[] players = createPlayers(3);
		assertNotNull(players);
		final LakeTile[] totalTiles = {};
		service.dealPlayerTiles(totalTiles, players);
	}
	
	@Test
	public void testDrawTileStackFourPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 4);
		assertEquals(20, tiles.size());
	}
	
	@Test
	public void testDrawTileStackThreePlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 3);
		assertEquals(18, tiles.size());
	}
	
	@Test
	public void testDrawTileStackTwoPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 2);
		assertEquals(16, tiles.size());
	}
	
	@Test
	public void testDrawTileStackZeroPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 0);
		assertTrue(tiles.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSeparateLanternCardsFourPlayers() {
		final LanternCardWrapper[] cards = new LanternCardWrapper[Colour.values().length];
		assertTrue( cards.length > 0 );
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new LanternCardWrapper();
		}
		
		service.separateLanternCards(cards, 4);
		assertNotNull(cards);
		for (LanternCardWrapper wrapper : cards) {
			assertEquals(8, wrapper.getStack().size());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void testSeparateLanternCardsWrongStack() {
		final LanternCardWrapper[] cards = new LanternCardWrapper[Colour.values().length + 1];
		assertTrue( cards.length > 0 );
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new LanternCardWrapper();
		}
		
		service.separateLanternCards(cards, 4);
		assertNotNull(cards);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSetDedicationTokensFourPlayers() {
		final DedicationTokenWrapper[] dedications = new DedicationTokenWrapper[DedicationType.values().length];
		assertTrue( dedications.length > 0 );
		for (int i = 0; i < dedications.length; i++) {
			dedications[i] = new DedicationTokenWrapper();
		}
		
		service.setDedicationTokens(dedications, 4);
		assertTrue( dedications.length > 0 );
		for (int i = 0; i < dedications.length; i++) {
			Stack<DedicationToken> dedication = dedications[i].getStack();
			assertNotNull(dedication);
			assertEquals(DedicationType.values()[i].getValuesFour().length, dedication.size());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDistributeInitialLanterns() {
		List<Colour> colourValues = Arrays.asList(Colour.values());
		final Lake lake = new Lake();
		Colour[] colours = {Colour.RED, Colour.BLUE, Colour.GREEN, Colour.PURPLE};
		assertNotNull(colours);
		LakeTile initialTile = new LakeTile();
		initialTile.init(colours, false);
		lake.getTiles().add(initialTile);
		assertNotNull(initialTile);
		final LanternCardWrapper[] cards = new LanternCardWrapper[colourValues.size()];
		assertTrue( cards.length > 0 );
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new LanternCardWrapper();
			LanternCard card = new LanternCard();
			card.init(colourValues.get(i));
			cards[i].getStack().push(card);
		}
		Player[] players = createPlayers(4);
		assertNotNull(players);
		service.distributeInitialLanterns(lake, cards, players);
		assertNotNull(lake);
		assertNotNull(cards);
		assertTrue( players.length > 0 );
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			Colour colour = initialTile.getSides()[i].getColour();
			assertNotNull(colour);
			final LanternCardWrapper[] playerCards = player.getCards();
			assertTrue( playerCards.length > 0 );
			for (int j = 0; j < playerCards.length; j++) {
				LanternCardWrapper colourStack = playerCards[j];
				assertNotNull(colourStack);
				if (j == colourValues.indexOf(colour)) {
					assertEquals(1, colourStack.getStack().size());
				} else {
					assertEquals(0, colourStack.getStack().size());
				}
			}
		}
	}
	
	private String[] createPlayerNames(int quantity) {
		String[] playerNames = new String[quantity];
		assertTrue(quantity > 0 );
		for (int i = 0; i < quantity; i++) {
			playerNames[i] = Integer.toString(i);
		}
		assertNotNull(playerNames);
		return playerNames;
	}
	
	private Player[] createPlayers(int quantity) {
		Player[] players = new Player[quantity];
		PlayerID[] id = PlayerID.values() ;
		for (int i = 0; i < quantity; i++) {
			players[i] = new Player();
			players[i].init(Integer.toString(i), id[i]);
		}
		assertNotNull(players);
		return players;
	}
}
